package de.tubs.skeditor.HMA.composition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.util.EcoreUtil;

import SkillGraph.Node;
import de.tubs.skeditor.HMA.HybridModeAutomaton;
import de.tubs.skeditor.HMA.Mode;
import de.tubs.skeditor.HMA.utils.HMAUtil;
import de.tubs.skeditor.keymaera.generator.evaluator.Visitor;
import de.tubs.skeditor.sdl.Assert;
import de.tubs.skeditor.sdl.Assignment;
import de.tubs.skeditor.sdl.Assume;
import de.tubs.skeditor.sdl.InputBinding;
import de.tubs.skeditor.sdl.SDLModel;
import de.tubs.skeditor.sdl.SdlFactory;
import de.tubs.skeditor.sdl.SubMode;
import de.tubs.skeditor.ui.handler.SkillDescriptionLanguageHandler;

public class Resolver {

	public static HybridModeAutomaton getHMAFromSubeMode(SubMode mode, HybridModeAutomaton... hma) {
		for (HybridModeAutomaton A : hma) {
			if (A.getName().equals(mode.getName()))
				return A;
		}
		return null;
	}

	public static HybridModeAutomaton resolveAllSubmodes(HybridModeAutomaton top, HybridModeAutomaton... hma) {
		HybridModeAutomaton result = top, result2 = null;

		// Find Fixpoint
		while (!result.equals(result2)) {
			result2 = result;
			result = resolveSubmodes(result, hma);
		}

		return result;
	}

	public static HybridModeAutomaton abstractSubmodes(HybridModeAutomaton hma, Node skill) {
		HybridModeAutomaton result = hma;

		Map<Node, HybridModeAutomaton> HMAMap = HMAUtil.createHMAsForSkillAndBelowMap(skill);

		for (Mode m : result.getModes()) {
			Map<Node, HybridModeAutomaton> toCompose = new HashMap<Node, HybridModeAutomaton>();
			for (SubMode sub : m.getSubModes()) {
				List<Entry<Node, HybridModeAutomaton>> tmp = HMAMap.entrySet().stream()
						.filter(e -> e.getValue().getName().equals(sub.getName())).collect(Collectors.toList());
				if (tmp == null || tmp.isEmpty())
					return null;
				
				toCompose.put(tmp.get(0).getKey(), tmp.get(0).getValue());
			}
			
			if(toCompose.isEmpty()) {
				continue;
			}
			
			if(toCompose.size() == 1) {
				Node node = toCompose.entrySet().iterator().next().getKey();
				SDLModel model = null;
				try {
					model = SkillDescriptionLanguageHandler.textToModel(node.getSDLModel());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//Visitor visitor = new Visitor();
				m.getSubModes().clear();
				//m.getController().getStmt().		
				Assert assertStmt = SdlFactory.eINSTANCE.createAssert();
				assertStmt.setFormula(model.getSkill().getAssumption().getFormula());
				Assume assumeStmt = SdlFactory.eINSTANCE.createAssume();
				assumeStmt.setFormula(model.getSkill().getGuarantee().getFormula());
				m.getController().getStmt().add(assertStmt);
				m.getController().getStmt().add(assumeStmt);
			} else {
				//TODO
			}
		}
		return result;
	}

	public static HybridModeAutomaton resolveSubmodes(HybridModeAutomaton top, HybridModeAutomaton... hma) {
		HybridModeAutomaton result = top;
		HashMap<Mode, HybridModeAutomaton> map = new HashMap<Mode, HybridModeAutomaton>();

		boolean hasSubmodes = false;
		for (Mode m : top.getModes()) {
			Set<HybridModeAutomaton> subs = new HashSet<HybridModeAutomaton>();
			for (SubMode sub : m.getSubModes()) {
				HybridModeAutomaton tmp = getHMAFromSubeMode(sub, hma);
				if (tmp == null)
					return null;
				if(sub.getBindings() != null) {
					for(InputBinding binding : sub.getBindings()) {
						Assignment assignment =  SdlFactory.eINSTANCE.createAssignment();
						assignment.setVariable(EcoreUtil.copy(binding.getVariable()));
						assignment.setExpr(EcoreUtil.copy(binding.getValue()));
						tmp.addBinding(assignment);
					}
				}
				
				subs.add(tmp);
			}

			if (!subs.isEmpty()) {
				hasSubmodes = true;
				HybridModeAutomaton composite = Composer.compose(subs.toArray(new HybridModeAutomaton[0]));
				map.put(m, composite);
			}
		}

		if (!hasSubmodes)
			return top;

		for (Entry<Mode, HybridModeAutomaton> elem : map.entrySet()) {
			result = Refiner.refinement(result, elem.getKey(), elem.getValue());
		}
		result.setName(top.getName());
		return result;
	}
}
