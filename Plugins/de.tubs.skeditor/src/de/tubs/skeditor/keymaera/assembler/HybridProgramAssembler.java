package de.tubs.skeditor.keymaera.assembler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import SkillGraph.Node;
import SkillGraph.Parameter;
import de.tubs.skeditor.contracting.Contract;
import de.tubs.skeditor.contracting.ContractPropagator;
import de.tubs.skeditor.sdl.HPInterfaceElement;
import de.tubs.skeditor.sdl.SDLModel;
import de.tubs.skeditor.ui.handler.SkillDescriptionLanguageHandler;
import de.tubs.skeditor.utils.GraphUtil;
import de.tubs.skeditor.utils.ParameterUtil;

public class HybridProgramAssembler {

	private static class Record {
		private Node node;
		private Contract contract;
		private Map<String, String> providedHP;

		public Record(Node node) throws IOException {
			this.node = node;
			contract = ContractPropagator.computeContract(node);
			providedHP = new HashMap<String, String>();

			if (node.getSDLModel() != null) {
				SDLModel model = SkillDescriptionLanguageHandler.textToModel(node.getSDLModel());

				if (model.getSkill().getHpprogram() != null && model.getSkill().getHpprogram().getInterface() != null) {
					if (model.getSkill().getHpprogram().getInterface().getProvides() != null) {
						String completeProgram = model.getSkill().getHpprogram().getProgram();
						for (HPInterfaceElement prov : model.getSkill().getHpprogram().getInterface().getProvides()) {
							int start = indexOf(Pattern.compile(prov.getName() + "\\s*::="), completeProgram);
							if(start == -1)
								continue;
							
							while(start < completeProgram.length() && completeProgram.charAt(start) != '{')
								start++;
							
							int end = start;
							while(end-1 < completeProgram.length() && !completeProgram.substring(end, end+2).equals("}.")) {
								//String substring = completeProgram.substring(end, end+2);
								end++;
							}
							
							String key = prov.getAliasname() != null ? prov.getAliasname() : prov.getName();
							String value = completeProgram.substring(start, end+2);
							providedHP.put(key, value);
						}
					}
				}
			}
		}
		
		private static int indexOf(Pattern pattern, String s) {
		    Matcher matcher = pattern.matcher(s);
		    return matcher.find() ? matcher.start() : -1;
		}

		public Contract getContract() {
			return contract;
		}

		public Map<String, String> getProvidedPrograms() {
			return providedHP;
		}

		public Node getNode() {
			return node;
		}
	}

	public static String computeProgram(Node node) {
		return computeProgram(node, false, false);
	}

	public static String computeProgram(Node node, boolean interpreteDeps, boolean contracting) {
		StringBuilder sb = new StringBuilder();
		List<String> constDefs = new ArrayList<String>();

		try {
			new Record(node);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// Parameter Definitions
		Set<Parameter> parameters = GraphUtil.getCollectedParameters(node);
		sb.append("Definitions\n");
		for (Parameter p : parameters) {
			if (!p.isVariable()) {
				String str = "Real " + p.getAbbreviation();
				if (p.getDefaultValue() == null || p.getDefaultValue().isEmpty()) {
					// empty
				} else if (p.getDefaultValue().matches("\\d+")) {
					str += " = " + p.getDefaultValue();
				} else {
					constDefs.add(ParameterUtil.createBounds(p.getAbbreviation(), p.getDefaultValue()));
				}
				sb.append("\t" + str + "; /* " + p.getName() + "*/\n");
			}
		}

		// Const definitions
		String constdef = "\tBool consts <-> (" + String.join("&", constDefs) + ");\n";
		sb.append(constdef);

		// Init
		Contract c = ContractPropagator.computeContract(node);
		String init = "\tBool init <-> (" + c.getAssumption() + "); /* Constant definitions */\n";
		String safe = "\tBool safe <-> (" + c.getGuarantee() + "); /* Safety goal */\n";

		sb.append(init);
		sb.append(safe);
		sb.append("\n");

		// Resolve stuff TODO

		List<HPInterfaceElement> requires = new ArrayList<HPInterfaceElement>();

		Map<String, Record> information = new HashMap<String, Record>();
		try {
			getInterfaceInformation(node, information);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String controller = "";
		String plant = "";
		SDLModel model;
		if (node.getSDLModel() != null) {
			try {
				model = SkillDescriptionLanguageHandler.textToModel(node.getSDLModel());

				if (model.getSkill().getHpprogram().getInterface() != null) {
					if (model.getSkill().getHpprogram().getInterface().getRequires() != null) {
						for (HPInterfaceElement req : model.getSkill().getHpprogram().getInterface().getRequires()) {
							sb.append(resolveHPInterface(req, node, interpreteDeps, information));
							requires.add(req);
						}
					}
				}

				String program = model.getSkill().getHpprogram().getProgram();
				controller = model.getSkill().getHpprogram().getController();
				plant = model.getSkill().getHpprogram().getPlant();
				sb.append(program + "\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		if (contracting) {
			List<String> parts = new ArrayList<String>();
			for (HPInterfaceElement req : requires) {
				Contract contract = information.get(req.getName()).getContract();
				parts.add("(" + contract.getAssumption() + ") -> [" + req.getAliasname() + "]("
						+ contract.getGuarantee() + ")");
			}

			// if(!parts.isEmpty())
			sb.append("\tBool functionCalls <-> " + (parts.isEmpty() ? "true" : String.join(" & ", parts)));
		}

		sb.append("End.\n");
		sb.append("\n");

		sb.append("ProgramVariables\n");
		for (Parameter p : parameters) {
			if (p.isVariable()) {
				String str = "Real " + p.getAbbreviation();
				sb.append("\t" + str + "; /* " + p.getName() + "*/\n");
			}
		}
		sb.append("End.\n");
		sb.append("\n");

		sb.append("Problem\n");

		sb.append("(init() &" + (contracting ? " functionCalls() &" : "") + " consts()) ->\n");
		sb.append("[\n{" + controller + ";" + plant + ";}*\n] safe()");
		sb.append("\n");

		sb.append("End.\n");

		return sb.toString();
	}

	private static void getInterfaceInformation(Node root, Map<String, Record> information) throws IOException {
		Record rec = new Record(root);
		for (Entry<String, String> entry : rec.getProvidedPrograms().entrySet()) {
			information.put(entry.getKey(), rec);
		}
		for (Node child : GraphUtil.getChildNodes(root)) {
			getInterfaceInformation(child, information);
		}
	}

	private static String resolveHPInterface(HPInterfaceElement elem, Node node, boolean interpreteDeps,
			Map<String, Record> information) {
		if (!interpreteDeps)
			return "HP " + elem.getAliasname() + "; /* Uninterpreted program */ \n";
		else {
			String content = information.get(elem.getName()).getProvidedPrograms().get(elem.getName());
			return "HP " + elem.getAliasname() + " ::= " + content + " /* Interpreted program*/";
		}
	}

}
