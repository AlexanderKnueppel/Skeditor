/**
* This feature creates a .kyx file. 
* 
*
* @author  Arne Windeler
* @version 1.0
* @since   2020-01-14 
*/

package de.tubs.skeditor.features;

import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

import SkillGraph.Node;
import de.tubs.skeditor.keymaera.assembler.HybridProgramAssembler;

public class CreateKeymaeraFileFeature extends AbstractCustomFeature {

	private boolean inline = true;

	public CreateKeymaeraFileFeature(IFeatureProvider fp, boolean inline) {
		super(fp);
		this.inline = inline;
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
		// TODO Auto-generated method stub

		PrintWriter pWriter = null;

		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
			Object bo = getBusinessObjectForPictogramElement(pes[0]);
			if (bo instanceof Node) {
				Node node = (Node) bo;

				if (node.getSDLModel() != null && !node.getSDLModel().isEmpty()) {
					String HP = "";
					HP = HybridProgramAssembler.computeProgram(node, inline, !inline);

					if (!HP.isEmpty()) {
						File file = null;
						try {
							file = File.createTempFile(node.getName(), ".kyx", null);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						try {
							pWriter = new PrintWriter(new BufferedWriter(new FileWriter(file)));
							pWriter.println(HP.toString());
						} catch (IOException ioe) {
							ioe.printStackTrace();
						} finally {
							if (pWriter != null) {
								pWriter.flush();
								pWriter.close();
							}
							Desktop desktop = Desktop.getDesktop();

							try {
								desktop.open(file);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
	}

	@Override
	public String getName() {
		return "Generate .kyx (" + (inline ? "full inline" : "uninterpreted") + ")";
	}

	@Override
	public String getDescription() {
		return "Generate and open a temporary *.kyx file";
	}

//	public boolean isAlphabet(char c) {
//		if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
//			return true;
//		}
//
//		return false;
//	}

}
