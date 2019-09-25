package de.tubs.skeditor.examples.wizards;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.dialogs.IOverwriteQuery;
import org.eclipse.ui.wizards.datatransfer.ImportOperation;

import de.tubs.skeditor.examples.ExamplePlugin;
import de.tubs.skeditor.examples.utils.ProjectRecord;
import de.tubs.skeditor.examples.utils.SimpleStructureProvider;

public class ExampleNewWizard extends Wizard implements INewWizard, IOverwriteQuery {
	public static final String ID = ExamplePlugin.PLUGIN_ID;

	private ExampleNewWizardPage mainPage = null;
	private static final String[] response = new String[] { YES, ALL, NO, NO_ALL, CANCEL };

	/**
	 * Constructor for SampleNewWizard.
	 */
	public ExampleNewWizard() {
		super();
		setNeedsProgressMonitor(true);
	}

	/**
	 * Adding the page to the wizard.
	 */
	@Override
	public void addPages() {
		mainPage = new ExampleNewWizardPage();
		addPage(mainPage);
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection currentSelection) {
		setWindowTitle("Examples");
	}

	@Override
	public boolean performCancel() {
		return true;
	}

	@Override
	public boolean performFinish() {
		return createProjects();
	}

	/**
	 * Create the selected projects
	 *
	 * @return boolean <code>true</code> if all project creations were successful.
	 */
	public boolean createProjects() {
		final Object[] selected = mainPage.getCheckedProjects();

		// run the new project creation operation
		try {
			getContainer().run(true, true, new IRunnableWithProgress() {

				@Override
				public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
					for (final Object selectedObject : selected) {
						if (selectedObject instanceof ProjectRecord) {
							final ProjectRecord projectRecord = (ProjectRecord) selectedObject;
							createExistingProject(projectRecord, SubMonitor.convert(monitor, 1));
						} else if (selectedObject instanceof String) {
							// do nothing
						}
					}
				}

			});
		} catch (final InterruptedException e) {
			return false;
		} catch (final InvocationTargetException e) {
			// one of the steps resulted in a core exception
			final Throwable t = e.getTargetException();
			final String message = "";
			IStatus status;
			if (t instanceof CoreException) {
				status = ((CoreException) t).getStatus();
			} else {
				status = new Status(IStatus.ERROR, "org.eclipse.ui.ide", 1, message, t);
			}
			ErrorDialog.openError(getShell(), message, null, status);
			ExamplePlugin.getDefault().logError(e);
			return false;
		}
		return true;
	}

	/**
	 *
	 * Create the project described in record. If it is successful return true.
	 *
	 * @param record
	 * @return boolean <code>true</code> if successful
	 * @throws InvocationTargetException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	private boolean createExistingProject(final ProjectRecord record, IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		final String projectName = record.getProjectName();
		final IWorkspace workspace = ResourcesPlugin.getWorkspace();
		final IProject project = workspace.getRoot().getProject(projectName);

		final InputStream inputStream;
		try {
			final URL url = new URL("platform:/plugin/de.tubs.skeditor.examples/" + record.getIndexDocumentPath());
			inputStream = url.openConnection().getInputStream();
		} catch (final IOException e) {
			ExamplePlugin.getDefault().logError(e);
			return false;
		}

		final List<String> res = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")))) {
			String line;
			while ((line = br.readLine()) != null) {
				res.add(line);
			}
			new ImportOperation(project.getFullPath(), null, new SimpleStructureProvider(record.getRelativePath()), this, res).run(monitor);

		} catch (final IOException e) {
			ExamplePlugin.getDefault().logError(e);
			return false;
		}
		return true;
	}

	@Override
	public String queryOverwrite(String pathString) {
		final Path path = new Path(pathString);

		String messageString;
		// Break the message up if there is a file name and a directory
		// and there are at least 2 segments.
		if ((path.getFileExtension() == null) || (path.segmentCount() < 2)) {
			messageString = pathString + " already exists. Would you like to overwrite it?";
		} else {
			messageString = "Overwrite " + path.lastSegment() + " in folder " + path.removeLastSegments(1).toOSString() + " ?";
		}

		final MessageDialog dialog =
			new MessageDialog(getContainer().getShell(), "Question", null, messageString, MessageDialog.QUESTION, new String[] { IDialogConstants.YES_LABEL,
				IDialogConstants.YES_TO_ALL_LABEL, IDialogConstants.NO_LABEL, IDialogConstants.NO_TO_ALL_LABEL, IDialogConstants.CANCEL_LABEL }, 0);

		// run in syncExec because callback is from an operation,
		// which is probably not running in the UI thread.
		mainPage.getControl().getDisplay().syncExec(new Runnable() {

			@Override
			public void run() {
				dialog.open();
			}
		});
		return dialog.getReturnCode() < 0 ? CANCEL : response[dialog.getReturnCode()];
	}
}
