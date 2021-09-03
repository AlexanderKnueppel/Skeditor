package de.tubs.skeditor.keymaera.assembler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import SkillGraph.Category;
import SkillGraph.Graph;
import SkillGraph.Node;
import SkillGraph.Parameter;
import de.tubs.skeditor.contracting.Contract;
import de.tubs.skeditor.contracting.ContractPropagator;
import de.tubs.skeditor.sdl.HPInterfaceElement;
import de.tubs.skeditor.sdl.SDLModel;
import de.tubs.skeditor.sdl.State;
import de.tubs.skeditor.sdl.Transition;
import de.tubs.skeditor.ui.handler.SkillDescriptionLanguageHandler;
import de.tubs.skeditor.utils.GraphUtil;
import de.tubs.skeditor.utils.ParameterUtil;

public class HybridProgramConstructor {

	private static class Record {
		private Node node;
		private String program;
		private String stateName;
		private int initIndex;
		private int numberStates;

		public Record(Node node) throws IOException {
			this.node = node;

			if (node.getSDLModel() != null) {
				SDLModel model = SkillDescriptionLanguageHandler.textToModel(node.getSDLModel());

				if (model.getSkill().getBehavior() != null) {
					if (model.getSkill().getBehavior().getStates() != null) {
						numberStates = model.getSkill().getBehavior().getStates().size();
						if(model.getSkill().getBehavior().getInitialState() == null) {
							initIndex = -1;
						} else {
							initIndex = model.getSkill().getBehavior().getStates().indexOf(model.getSkill().getBehavior().getInitialState());
						}
						program = "";
						if (model.getSkill().getBehavior().getStates().size() == 1) {
							State st = model.getSkill().getBehavior().getStates().get(0);
							program += st.getReq() != null ? "?" + st.getReq() + ";\n" : "";
							program += st.getEffect() != null ? parseEffect(st.getEffect()) + "\n" : "";
							program += st.getDynamics() != null ? "{" + st.getDynamics() + "}\n" : "";
						} else {
							List<String> parts = new ArrayList<String>();
							stateName = model.getSkill().getBehavior().getName() + "CurrentState";

							for (State st : model.getSkill().getBehavior().getStates()) {
								String part = "{\n";
								part += st.getReq() != null ? "?" + st.getReq() + " & " + stateName + "="
										+ model.getSkill().getBehavior().getStates().indexOf(st) + ";\n" : "";
								part += st.getEffect() != null ? parseEffect(st.getEffect())  + "\n" : "";

								if (st.getTransitions() != null) {
									part += "{ /*transitions*/ \n";
									List<String> tran = new ArrayList<String>();
									for (Transition tr : st.getTransitions()) {
										tran.add("?" + tr.getWhen() + ";" + stateName + ":="
												+ indexOf(tr.getToState(), model.getSkill().getBehavior().getStates())
												+ ";");
									}
									part += String.join("\n ++ \n", tran);
									part += "}\n";
								}
								part += st.getDynamics() != null ? "{" + st.getDynamics() + "}\n" : "";
								part += "}\n";
								parts.add(part);
							}

							program += String.join("\n ++ \n", parts);
						}

						// program += "}.";
					}
				}
			}
		}
		
		private String parseEffect(String effect) {
			String result = effect;
			result = result.replaceAll("\\bcall\\b", "");
			result = result.replaceAll("skip;", "");
			return result;
		}

		private int indexOf(String id, List<State> states) {
			Optional<State> op = states.stream().filter(e -> e.getName().equals(id)).findFirst();
			if (op.isPresent())
				return states.indexOf(op.get());
			else
				return -1;
		}

		public Node getNode() {
			return node;
		}

		public String getProgram() {
			return program;
		}
		
		public String getStateName() {
			return stateName;
		}
		
		public int getIndexOfInitialState() {
			return initIndex;
		}
		
		public int getNumberOfStates() {
			return numberStates;
		}
	}

	public static String constructHybridProgram(Node node) {
		return constructHybridProgram(node, false, false);
	}

	public static String constructHybridProgram(Node node, boolean interpreteDeps, boolean contracting) {
		StringBuilder sb = new StringBuilder();
		List<String> constDefs = new ArrayList<String>();

		Graph g = (Graph) node.eContainer();
		//Set<Parameter> parameters = new HashSet<>g.getParameterList()//GraphUtil.getCollectedParameters(node);
		sb.append("Definitions\n");

		for (Parameter p : g.getParameterList()) {
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

//		sb.append("Real currentState;");
//		sb.append("\n");

		if (node.getSDLModel() == null) {
			return "";
		}

		SDLModel model = null;
		try {
			model = SkillDescriptionLanguageHandler.textToModel(node.getSDLModel());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (model.getSkill().getBehavior() == null)
			return "";

		List<String> states = model.getSkill().getBehavior().getStateIDList();
		sb.append("/*");
		for (String id : states) {
			sb.append(states.indexOf(id) + ": " + id + "; ");
		}
		sb.append("*/\n");

		Map<String, Node> deps = null;
		try {
			deps = computeDependencies(node);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<String> calls = new ArrayList<String>();
		for (State state : model.getSkill().getBehavior().getStates()) {
			if (state.getEffect() != null) {
				String parts[] = state.getEffect().split("\\bcall\\b");
				for (int i = 1; i < parts.length; ++i) {
					calls.add(parts[i].split("\\(")[0].trim());
				}
			}
		}

//		if (contracting) {
//			List<String> parts = new ArrayList<String>();
//			for (String call : calls) {
//				Contract contract = ContractPropagator.computeContract(deps.get(call));
//				parts.add("(" + contract.getAssumption() + ") -> [" + call + ";](" + contract.getGuarantee() + ")");
//			}
//
//			if (!calls.isEmpty())
//				sb.append("\tBool functionCalls <-> "
//						+ (parts.isEmpty() ? "true" : "(" + String.join(" & ", parts) + ")"));
//		}

		sb.append("\n");

		List<String> statePointers = new ArrayList<String>();
		List<Integer> statePointersInitialIndices = new ArrayList<Integer>();
		for (String call : calls) {
			if (!interpreteDeps)
				sb.append("\tHP " + call + "; \n");
			else {
				Record record = null;
				try {
					record = new Record(deps.get(call));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				statePointers.add(record.getStateName());
				statePointersInitialIndices.add(record.getIndexOfInitialState());
				String content = record.getProgram();// information.get(elem.getName()).getProvidedPrograms().get(elem.getName());
				sb.append("\tHP " + call + " ::= {\n" + content + "\n\t}. /*Skill*/ \n");
			}

		}

		sb.append("\nEnd.\n");
		sb.append("\n");
		
		Record record = null;
		try {
			record = new Record(node);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		sb.append("ProgramVariables\n");
		for (Parameter p : g.getParameterList()) {
			if (p.isVariable()) {
				String str = "Real " + p.getAbbreviation();
				sb.append("\t" + str + "; /* " + p.getName() + "*/\n");
			}
		}
		for(String p : statePointers) {
			sb.append("\tReal " + p + ";");
		}
		sb.append("\tReal " + record.stateName + ";\n");
		sb.append("End.\n");
		sb.append("\n");
		
		sb.append("Problem\n");
		
		String initStartPointers = "";
		if(record.getIndexOfInitialState() == -1) {
			initStartPointers += "(";
			List<String> l = new ArrayList<String>();
			for(int i = 0; i < model.getSkill().getBehavior().getStates().size(); ++i) {
				l.add(record.getStateName() + "=" + i);
			}
			initStartPointers += String.join(" | ", l) + ")";
		} else {
			initStartPointers += record.getStateName() + "=" + record.getIndexOfInitialState();
		}
		
//		for(int i = 0; i < ) {
//			
//		}
		
		sb.append("(init() &" + (contracting ? " functionCalls() &" : "") + " consts() & "+initStartPointers+") ->\n");
		sb.append("[\n {\t\t" + record.getProgram() + "\n }*\n] safe()\n");
		sb.append("\n");

		sb.append("End.\n");

		return sb.toString();
	}

	private static Map<String, Node> computeDependencies(Node n) throws IOException {
		Map<String, Node> result = new HashMap<String, Node>();

		Stack<List<Node>> stack = new Stack<List<Node>>();
		stack.push(GraphUtil.getChildNodes(n));

		while (!stack.empty()) {
			List<Node> nodeList = stack.pop();
			for (Node elem : nodeList) {
				if ((elem.getCategory().equals(Category.ACTION)
						|| elem.getCategory().equals(Category.OBSERVABLE_EXTERNAL_BEHAVIOR))
						&& elem.getSDLModel() != null) {
					SDLModel content = SkillDescriptionLanguageHandler.textToModel(elem.getSDLModel());
					if (content.getSkill().getBehavior() != null) {
						result.put(content.getSkill().getBehavior().getName(), elem);
					}
				}
				if (!GraphUtil.getChildNodes(elem).isEmpty())
					stack.push(GraphUtil.getChildNodes(elem));
			}
		}
		return result;
	}
}
