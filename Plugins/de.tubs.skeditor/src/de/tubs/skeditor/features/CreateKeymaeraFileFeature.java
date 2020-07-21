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
import de.tubs.skeditor.compositionality.KeymaeraString;

public class CreateKeymaeraFileFeature extends AbstractCustomFeature {

	public CreateKeymaeraFileFeature(IFeatureProvider fp) {
		super(fp);
		// TODO Auto-generated constructor stub
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
				if (!node.getController().isEmpty()) {

					File file = null;
					try {
						file = File.createTempFile(node.getName(), ".kyx", null);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					try {
						KeymaeraString ks = new KeymaeraString(bo);
						pWriter = new PrintWriter(new BufferedWriter(new FileWriter(file)));
						pWriter.println(ks.getString());
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

	@Override
	public String getName() {
		return "Open Keymaera";
	}

	@Override
	public String getDescription() {
		return "Generate and open a KeymaeraX file";
	}

	public boolean isAlphabet(char c) {

		if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
			return true;
		}

		return false;

	}

}
