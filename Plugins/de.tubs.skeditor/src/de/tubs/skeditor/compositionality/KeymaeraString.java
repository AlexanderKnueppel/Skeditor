/**
* The program createKeymaeraString Creates a String  
* representing a hybrid program. 
* 
*
* @author  Arne Windeler
* @version 1.0
* @since   2020-01-14 
*/

package de.tubs.skeditor.compositionality;

import SkillGraph.Graph;
import SkillGraph.Node;
import de.tubs.skeditor.utils.GraphUtil;

public class KeymaeraString {

	String keymaeraString;

	public KeymaeraString(Object o) {
		this.keymaeraString = createKeymaeraString(o);
	}

	public String createKeymaeraString(Object bo) {
		String s = null;
		if (bo instanceof Node) {
			Node node = (Node) bo;
			if (!node.getController().isEmpty()) {

				s = "Definitions \n" + "/*** Required definitions. Provided by child skills. ***/ \n";

				String[] reqDef = new String[0];
				String[] newDef = new String[0];
				Graph g = (Graph) node.eContainer();
				String parameter = null;
				for (int x = 0; x < g.getParameterList().size(); x++) {
					parameter = g.getParameterList().get(x).getAbbreviation();
					for (int i = 0; i < GraphUtil.getPreConditionRequirements(node).size(); i++) {
						String[] preCon = GraphUtil.getPreConditionRequirements(node).get(i).getTerm()
								.split("\\P{Alpha}+");
						if (stringContainsInList(parameter, preCon) && !g.getParameterList().get(x).isVariable()
								&& !stringContainsInList(parameter, reqDef)) {
							String[] safe = reqDef;
							reqDef = new String[reqDef.length + 1];
							for (int index = 0; index < safe.length; index++) {
								reqDef[index] = safe[index];
							}
							reqDef[reqDef.length - 1] = parameter;
						}
					}
					for (int i = 0; i < node.getRequirements().size(); i++) {
						String[] con = node.getRequirements().get(i).getTerm().split("\\P{Alpha}+");
						if (stringContainsInList(parameter, con) && !g.getParameterList().get(x).isVariable()
								&& !stringContainsInList(parameter, reqDef)
								&& !stringContainsInList(parameter, newDef)) {
							String[] safe = newDef;
							newDef = new String[newDef.length + 1];
							for (int index = 0; index < safe.length; index++) {
								newDef[index] = safe[index];
							}
							newDef[newDef.length - 1] = parameter;
						}
					}
					String[] controller = node.getController().get(0).getCtrl().split("\\P{Alpha}+");
					if (stringContainsInList(parameter, controller) && !g.getParameterList().get(x).isVariable()
							&& !stringContainsInList(parameter, reqDef) && !stringContainsInList(parameter, newDef)) {
						String[] safe = newDef;
						newDef = new String[newDef.length + 1];
						for (int index = 0; index < safe.length; index++) {
							newDef[index] = safe[index];
						}
						newDef[newDef.length - 1] = parameter;
					}

				}

				for (String def : reqDef) {
					s += "Real " + def + "; \n";
				}

				s += "\n" + "/*** New definitions.***/ \n";
				for (String def : newDef) {
					s += "Real " + def + "; \n";
				}

				// method for req def

				s += "\n" + "B initial() <-> (  \n" + "/* Generate init... */ \n";

				boolean pre = false;
				for (int i = 0; i < GraphUtil.getPreConditionRequirements(node).size(); i++) {
					pre = true;
					s += GraphUtil.getPreConditionRequirements(node).get(i).getTerm();
					if (i != GraphUtil.getPreConditionRequirements(node).size() - 1) {
						s += " & ";
					} else {
						s += "\n";
					}
				}
//				if (pre == true) {
//					s += " & ";
//				}
//				if(!node.getRequirements().isEmpty()) {
//					s += node.getRequirements().get(0).getTerm();
//					for(int i = 1; i < node.getRequirements().size(); i++) {
//
//						s += " & "+node.getRequirements().get(i).getTerm();
//					}
//				}
				s += "\n" + "). \n";

				for (int x = 0; x < node.getController().size(); x++) {
					s += node.getController().get(x).getCtrl();
				}

				String[] reqVar = new String[0];
				String[] newVar = new String[0];

				for (int x = 0; x < g.getParameterList().size(); x++) {
					parameter = g.getParameterList().get(x).getAbbreviation();
					for (int i = 0; i < GraphUtil.getPreConditionRequirements(node).size(); i++) {
						String[] preCon = GraphUtil.getPreConditionRequirements(node).get(i).getTerm()
								.split("\\P{Alpha}+");
						if (stringContainsInList(parameter, preCon) && g.getParameterList().get(x).isVariable()
								&& !stringContainsInList(parameter, reqVar)) {
							String[] safe = reqVar;
							reqVar = new String[reqVar.length + 1];
							for (int index = 0; index < safe.length; index++) {
								reqVar[index] = safe[index];
							}
							reqVar[reqVar.length - 1] = parameter;
						}
					}
					for (int i = 0; i < node.getRequirements().size(); i++) {
						String[] con = node.getRequirements().get(i).getTerm().split("\\P{Alpha}+");
						if (stringContainsInList(parameter, con) && g.getParameterList().get(x).isVariable()
								&& !stringContainsInList(parameter, reqVar)
								&& !stringContainsInList(parameter, newVar)) {
							String[] safe = newVar;
							newVar = new String[newVar.length + 1];
							for (int index = 0; index < safe.length; index++) {
								newVar[index] = safe[index];
							}
							newVar[newVar.length - 1] = parameter;
						}
					}
					String[] controller = node.getController().get(0).getCtrl().split("\\P{Alpha}+");
					if (stringContainsInList(parameter, controller) && g.getParameterList().get(x).isVariable()
							&& !stringContainsInList(parameter, reqVar) && !stringContainsInList(parameter, newVar)) {
						String[] safe = newVar;
						newVar = new String[newVar.length + 1];
						for (int index = 0; index < safe.length; index++) {
							newVar[index] = safe[index];
						}
						newVar[newVar.length - 1] = parameter;
					}

				}

				s += "\n" + "End. \n" + "ProgramVariables \n" + "\n" + " /*** Required variables. ***/ \n" + "\n";
				for (String var : reqVar) {
					if (!var.equals("t")) {
						s += "Real " + var + "; \n";
					}
				}

				s += "\n" + "/*** New variables. ***/ \n" + "\n";

				for (String var : newVar) {
					if (!var.equals("t")) {
						s += "Real " + var + "; \n";
					}

				}

				s += "/* clock */ \n" + "Real t; \n" + "\n" + "End.\n" + "\n";

				s += "Problem \n" + "\n" + "initial() \n" + "\n" + "-> [ \n" + "{ \n" + "main; \n" + "} \n" + "](";

				for (int i = 0; i < node.getRequirements().size(); i++) {
					if (i == node.getRequirements().size() - 1) {
						s += node.getRequirements().get(i).getTerm();
					} else {
						s += (node.getRequirements().get(i).getTerm() + " & ");
					}

				}

				s += ") \n" + "End. \n";
			}

		}
		return s;
	}

	public String getString() {
		return this.keymaeraString;
	}

	public static boolean stringContainsInList(String inputStr, String[] items) {
		if (!(items.length == 0)) {
			for (int i = 0; i < items.length; i++) {
				if (inputStr.equals(items[i])) {
					return true;
				}
			}

		}
		return false;

	}

}
