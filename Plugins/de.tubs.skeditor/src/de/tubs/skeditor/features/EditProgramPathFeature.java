package de.tubs.skeditor.features;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import SkillGraph.Node;
import de.tubs.skeditor.utils.FileUtils;

/**
 * Class that allows to edit program path
 * 
 * @author Metin
 *
 */
public class EditProgramPathFeature extends AbstractCustomFeature {

	public EditProgramPathFeature(IFeatureProvider fp) {
		super(fp);
		// TODO Auto-generated constructor stub
	}

	/**
	 * boolean that indicate if something changes
	 */
	private boolean hasDoneChanges = false;

	@Override
	public String getName() {
		return "Set program path";
	}

	@Override
	public String getDescription() {
		return "Edit program path of a Node";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		boolean ret = false;
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof Node) {
				ret = true;
			}
		}
		return ret;
	}

	@Override
	public void execute(ICustomContext context) {
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof Node) {
				Node node = (Node) bo;
				String current = "";
				if (!node.getController().isEmpty()) {
					current = node.getController().get(0).getCtrl();
				}

				String path = askPath();

				Shell shell = getShell();

				File file = new File(path);
				if (file.isFile()) {
					if(!file.canExecute()) {
						MessageBox errorbox = new MessageBox(shell, SWT.OK);
						errorbox.setMessage("File is not executable");
						errorbox.open();
						return;
					}
				}else if (!new File(file, "CMakeLists.txt").exists()) {

					MessageBox mb = new MessageBox(shell, SWT.OK | SWT.CANCEL);
					mb.setMessage("No Project Files found, they will be generated in a subdirectory called " + node.getName());
					if (mb.open() == SWT.OK) {

						String name = node.getName().replace(" ", "_");

						file = new File(file, name);

						if (file.exists()) {
							MessageBox errorbox = new MessageBox(shell, SWT.OK);
							errorbox.setMessage(
									"A directory named " + node.getName() + " already exists in selected folder");
							errorbox.open();
							return;
						}

						file.mkdir();
						new File(file, "src").mkdir();
						try {
							FileUtils.copyFromResource("/project_template/CMakeLists.txt", file.getAbsolutePath()+"/CMakeLists.txt");
							FileUtils.copyFromResource("/project_template/package.xml", file.getAbsolutePath()+"/package.xml");
							FileUtils.copyFromResource("/project_template/src/Skill.h", file.getAbsolutePath()+"/src/Skill.h");
							FileUtils.copyFromResource("/project_template/src/main.cpp", file.getAbsolutePath()+"/src/main.cpp");

							FileUtils.replaceInFile(file.getAbsolutePath() + "/CMakeLists.txt", "SKILL_NAME", name);
							FileUtils.replaceInFile(file.getAbsolutePath() + "/package.xml", "SKILL_NAME", name);
							FileUtils.replaceInFile(file.getAbsolutePath() + "/src/main.cpp", "SKILL_NAME", name);
						} catch (IOException io) {
							MessageBox errorbox = new MessageBox(shell, SWT.OK);
							errorbox.setMessage("An error occured");
							errorbox.open();
							io.printStackTrace();
							FileUtils.deleteDirectory(file);
							return;
						}
					}
				}

				node.setProgramPath(file.getAbsolutePath());

			}

		}
	}

	@Override
	public boolean hasDoneChanges() {
		return this.hasDoneChanges;
	}

	/**
	 * Helper method that asks for a Path
	 * 
	 * @return The new path
	 */
	public static String askPath() {
		Shell shell = getShell();

		DirectoryDialog pathDlg = new DirectoryDialog(shell);
		String path = pathDlg.open();

		return path;
	}

	/**
	 * Returns the currently active Shell.
	 * 
	 * @return The currently active Shell.
	 */
	private static Shell getShell() {
		return PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
	}

} 
