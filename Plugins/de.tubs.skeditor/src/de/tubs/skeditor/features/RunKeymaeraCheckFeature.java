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

public class RunKeymaeraCheckFeature extends AbstractCustomFeature {

	public RunKeymaeraCheckFeature(IFeatureProvider fp) {
		super(fp);

	}

	@Override
	public String getName() {
		return "KeYmaera check";
	}

	@Override
	public String getDescription() {
		return "Runs an automatic check if the requirements are valid. If the check cannot validate the requirements automatically, a KeYmaera program is generated for manual checks";
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		return getBusinessObjectForPictogramElement(context.getInnerPictogramElement()) instanceof Node;
	}

	@Override
	public void execute(ICustomContext context) {
		Graph graph = (Graph) getBusinessObjectForPictogramElement(getDiagram());
		Node node = (Node) getBusinessObjectForPictogramElement(context.getInnerPictogramElement());
		String template = "";
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1) {
            Object bo = getBusinessObjectForPictogramElement(pes[0]);
            KeymaeraString ks = new KeymaeraString(bo);
            template = new String(ks.getString());
		}
		DynamicModel dynamicModel = new DynamicModel(template);

		StringBuilder modelEquations = new StringBuilder();
		EList<Equation> equations = new BasicEList<Equation>();
		equations.addAll(node.getEquations());
		equations.addAll(GraphUtil.getPropagatedEquations(node));
		for (Equation eq : equations) {
			dynamicModel.addEquation(eq.getEquation());
			modelEquations.append(eq.getEquation());
		}

		for (Parameter parameter : graph.getParameterList()) {
			if (!parameter.getDefaultValue().equals("")) {
				String value = parameter.getDefaultValue().replace(" ", "");
				String[] valueSplit = value.split("\\[");
				String defaultValue = valueSplit[0];
				String lowLimit = null, highLimit = null;
				if (valueSplit.length > 1) { // this is just horrible, but it works
					lowLimit = valueSplit[1].split(":")[0];
					highLimit = valueSplit[1].split(":")[1].split("\\]")[0];
				}
				if (parameter.isVariable()) {
					if (lowLimit == null) {
						dynamicModel.addVariable(parameter.getAbbreviation(), defaultValue);
					} else if (defaultValue == null) {
						dynamicModel.addVariable(parameter.getAbbreviation(), lowLimit, highLimit);
					} else {
						dynamicModel.addVariable(parameter.getAbbreviation(), lowLimit, highLimit, defaultValue);
					}
				} else {
					if (lowLimit != null) {
						dynamicModel.addConstant(new ValuedParameter(parameter.getAbbreviation(), lowLimit, parameter.getUnit(), highLimit));
					} else {
						dynamicModel.addConstant(new ValuedParameter(parameter.getAbbreviation(), parameter.getDefaultValue(), parameter.getUnit()));
					}
				}
			} else if (parameter.isVariable()) {
				dynamicModel.addVariable(parameter.getAbbreviation());
			} else {
				dynamicModel.addConstant(new ValuedParameter(parameter.getAbbreviation(), null, parameter.getUnit())); //
			}
		}

		try {
			String preCondition = buildPreCondition(node);
			String postCondition = buildPostCondition(node);
			
			// New version:
			Contract c = ContractPropagator.computeContract(node);
			preCondition = c.getAssumption();
			postCondition = c.getGuarantee();
			Object bo = null;
			if (pes != null && pes.length == 1) {
	            bo = getBusinessObjectForPictogramElement(pes[0]);
	            }
			KeymaeraString keyString = new KeymaeraString(bo);
			String dynamicModelString = keyString.getString();
			System.out.println(dynamicModelString);

			KeYmaeraBridge bridge = KeYmaeraBridge.getInstance();
			ProvableSig f = bridge.proveAutomatically(KeYmaeraBridge.parseProgramAsFormula(dynamicModelString));

			System.out.println(f.prettyString());
			if (f.isProved()) {
				MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Success!", "Equations are provable!\n\n" + f.prettyString());
			} else {
				MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Failure!",
						"Was not able to prove equations automatically. The equations might still be provable.\n\n" + f.prettyString());
			}

		} catch (Exception e) {
			e.printStackTrace();
			MessageDialog.openInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(), "Error",
					"There was an error, please check the inputs. \nError message:\n\n" + e.toString());
		}
	}

	private String buildPreCondition(Node node) {
		StringBuilder preConditionBuilder = new StringBuilder();
		boolean moreThanOne = false;
		for (Requirement req : GraphUtil.getPreConditionRequirements(node)) {
			if (moreThanOne) {
				preConditionBuilder.append(" & ");
			}
			preConditionBuilder.append(req.getTerm());
			moreThanOne = true;
		}

		return preConditionBuilder.toString();
	}

	private String buildPostCondition(Node node) {
		StringBuilder postConditionBuilder = new StringBuilder();
		boolean moreThanOne = false;
		for (Requirement req : node.getRequirements()) {
			if (moreThanOne) {
				postConditionBuilder.append(" & ");
			}
			postConditionBuilder.append(req.getTerm());
			moreThanOne = true;
		}
		return postConditionBuilder.toString();
	}
}
