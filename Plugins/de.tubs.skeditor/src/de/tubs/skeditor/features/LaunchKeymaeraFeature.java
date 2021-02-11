package de.tubs.skeditor.features;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.PlatformUI;

import SkillGraph.Equation;
import SkillGraph.Graph;
import SkillGraph.Node;
import SkillGraph.Parameter;
import SkillGraph.Requirement;
import de.tubs.skeditor.compositionality.KeymaeraString;
import de.tubs.skeditor.contracting.Contract;
import de.tubs.skeditor.contracting.ContractPropagator;
import de.tubs.skeditor.keymaera.DynamicModel;
import de.tubs.skeditor.keymaera.KeYmaeraBridge;
import de.tubs.skeditor.keymaera.ValuedParameter;
import de.tubs.skeditor.utils.GraphUtil;
import de.tubs.skeditor.features.CreateKeymaeraFileFeature;
import edu.cmu.cs.ls.keymaerax.pt.ProvableSig;

public class LaunchKeymaeraFeature extends AbstractCustomFeature {

	public LaunchKeymaeraFeature(IFeatureProvider fp) {
		super(fp);

	}

	@Override
	public String getName() {
		return "Verify (interactively)";
	}

	@Override
	public String getDescription() {
		return "Launches keymaera with the respective problem file for interactive proving!";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		return getBusinessObjectForPictogramElement(context.getInnerPictogramElement()) instanceof Node;
	}

	@Override
	public void execute(ICustomContext context) {
		KeYmaeraBridge.getInstance().launchKeYmaera();
		
//			if (f.isProved()) {
//				MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Success!", "Equations are provable!\n\n" + f.prettyString());
//			} else {
//				MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Failure!",
//						"Was not able to prove equations automatically. The equations might still be provable.\n\n" + f.prettyString());
//			}
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Error",
//					"There was an error, please check the inputs. \nError message:\n\n" + e.toString());
//		}
	}
}
