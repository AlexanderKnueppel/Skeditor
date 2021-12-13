package de.tubs.skeditor.HMA.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.util.EcoreUtil;

import SkillGraph.Category;
import SkillGraph.Graph;
import SkillGraph.Node;
import de.tubs.skeditor.HMA.HybridModeAutomaton;
import de.tubs.skeditor.HMA.Mode;
import de.tubs.skeditor.HMA.Transition;
import de.tubs.skeditor.HMA.composition.Resolver;
import de.tubs.skeditor.sdl.Argument;
import de.tubs.skeditor.sdl.BooleanLiteral;
import de.tubs.skeditor.sdl.CtrlInvocation;
import de.tubs.skeditor.sdl.DynInvocation;
import de.tubs.skeditor.sdl.Dynamics;
import de.tubs.skeditor.sdl.HMA;
import de.tubs.skeditor.sdl.Invocation;
import de.tubs.skeditor.sdl.InvocationArgument;
import de.tubs.skeditor.sdl.InvocationFunction;
import de.tubs.skeditor.sdl.NormalBehavior;
import de.tubs.skeditor.sdl.ODE;
import de.tubs.skeditor.sdl.Parameters;
import de.tubs.skeditor.sdl.SDLModel;
import de.tubs.skeditor.sdl.SdlFactory;
import de.tubs.skeditor.sdl.Skill;
import de.tubs.skeditor.sdl.VariableDeclarations;
import de.tubs.skeditor.sdl.Variables;
import de.tubs.skeditor.ui.handler.SkillDescriptionLanguageHandler;
import de.tubs.skeditor.utils.GraphUtil;

public class HMAUtil {
	
	public static List<HybridModeAutomaton> createAllHMAsFromGraph(Graph sg) {
		List<HybridModeAutomaton> hma = new ArrayList<HybridModeAutomaton>();
		for (Node skill : sg.getNodes()) {
			if (skill.getCategory().equals(Category.ACTION)
					|| skill.getCategory().equals(Category.OBSERVABLE_EXTERNAL_BEHAVIOR)) {
				HybridModeAutomaton tmp = HMAUtil.createHMAFromSKill(skill);
				if(tmp == null)
					continue;
				
				if (GraphUtil.getParentNodes(skill).stream().map(n -> n.getCategory()).collect(Collectors.toSet()).contains(Category.MAIN)) {
					hma.add(0,tmp);
				} else {
					hma.add(tmp);
				}
			}
		}
                                                                                                                                                                                                                                                                                                 
		return hma;
	}
	
	public static List<HybridModeAutomaton> createHMAsForSkillAndBelow(Node skill) {
		List<HybridModeAutomaton> hma = new ArrayList<HybridModeAutomaton>();
		if (skill.getCategory().equals(Category.ACTION)
				|| skill.getCategory().equals(Category.OBSERVABLE_EXTERNAL_BEHAVIOR)) {
			HybridModeAutomaton tmp = HMAUtil.createHMAFromSKill(skill);
			if(tmp == null)
				return hma;
			hma.add(tmp);
		}
		
		for(Node child : GraphUtil.getChildNodes(skill)) {
			hma.addAll(createHMAsForSkillAndBelow(child));
		}
                                                                                                                                                                                                                                                                                                 
		return hma.stream().distinct().collect(Collectors.toList());
	}
	
	public static Map<Node, HybridModeAutomaton> createHMAsForSkillAndBelowMap(Node skill) {
		Map<Node, HybridModeAutomaton> hma = new HashMap<Node, HybridModeAutomaton>();
		if (skill.getCategory().equals(Category.ACTION)
				|| skill.getCategory().equals(Category.OBSERVABLE_EXTERNAL_BEHAVIOR)) {
			HybridModeAutomaton tmp = HMAUtil.createHMAFromSKill(skill);
			if(tmp == null)
				return hma;
			hma.put(skill, tmp);
		}
		
		for(Node child : GraphUtil.getChildNodes(skill)) {
			hma.putAll(createHMAsForSkillAndBelowMap(child));
		}
                                                                                                                                                                                                                                                                                                 
		return hma;
	}

	public static HybridModeAutomaton createHMAFromSKill(Node skill) {
		if (!skill.getCategory().equals(Category.ACTION)
				&& !skill.getCategory().equals(Category.OBSERVABLE_EXTERNAL_BEHAVIOR))
			return null;

		SDLModel model = null;
		if(skill.getSDLModel() == null)
			return null;
		
		try {
			model = SkillDescriptionLanguageHandler.textToModel(skill.getSDLModel());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(model == null)
			return null;

		HMA hma = model.getSkill().getHma();

		if (hma == null)
			return null;

		HashMap<String, DynInvocation> dynInvokenMap = new HashMap<String, DynInvocation>();
		if (model.getSkill().getInvocations() != null) {
			for (Invocation inv : model.getSkill().getInvocations()) {
				if (inv instanceof DynInvocation)
					dynInvokenMap.put(inv.getName(), (DynInvocation) inv);
			}
		}

		HybridModeAutomaton result = new HybridModeAutomaton();
		result.setName(hma.getName());

		// add modes
		for (NormalBehavior behavior : hma.getNormal_behaviors()) {
			Mode mode = new Mode();
			mode.setName(behavior.getName());
			mode.setController(behavior.getController());

			if (behavior.getInvocation() != null) {
				DynInvocation dynamics = dynInvokenMap.get(behavior.getInvocation().getCall().getName());
				mode.setDynamics(replaceArgumentsDyn(dynamics, behavior.getInvocation().getCall()).getDynamics());
			} else
				mode.setDynamics(behavior.getDynamics());

			if (behavior.getSmodes() != null)
				mode.getSubModes().addAll(behavior.getSmodes());
			result.addMode(mode);
		}
		// start
		result.setStart(hma.getInit());
		// transitions
		for (NormalBehavior behavior : hma.getNormal_behaviors()) {
			for (de.tubs.skeditor.sdl.Transition t_sdl : behavior.getTransitions()) {
				Transition t = new Transition(result.getModeByName(behavior.getName()), t_sdl.getWhen(),
						result.getModeByName(t_sdl.getToState()));
				result.addTransition(t);
			}
			//Add self transition?
			if(behavior.getTransitions().isEmpty()) {
				BooleanLiteral trueLiteral = SdlFactory.eINSTANCE.createBooleanLiteral();
				trueLiteral.setValue("true");
				Transition t = new Transition(result.getModeByName(behavior.getName()), trueLiteral,
						result.getModeByName(behavior.getName()));
				result.addTransition(t);
			} else {
				// TODO: generate self transition based on current transitions if not equvialent to conjunction
			}
		}

		// remaining stuff
		result.getInputs().addAll(getInputSet(model.getSkill()));
		result.getOutputs().addAll(getOutputSet(model.getSkill()));
		result.getParameters().addAll(getParameterSet(model.getSkill()));
		result.getLocals().addAll(getLocalSet(model.getSkill()));

		return result;
	}

	public static Set<String> getInputSet(Skill skill) {
		Set<String> result = new HashSet<String>();
		Variables variables = skill.getVariables();
		if (variables.getRequires() == null || variables.getRequires().getVardecls() == null) {
			return result;
		}
		for (VariableDeclarations vardecs : variables.getRequires().getVardecls()) {
			vardecs.getVariables().stream().forEach(e -> result.add(e.getName()));
		}
		return result;
	}

	public static Set<String> getOutputSet(Skill skill) {
		Set<String> result = new HashSet<String>();
		Variables variables = skill.getVariables();
		if (variables.getProvides() == null || variables.getProvides().getVardecls() == null) {
			return result;
		}
		for (VariableDeclarations vardecs : variables.getProvides().getVardecls()) {
			vardecs.getVariables().stream().forEach(e -> result.add(e.getName()));
		}
		return result;
	}

	public static Set<String> getLocalSet(Skill skill) {
		Set<String> result = new HashSet<String>();
		Variables variables = skill.getVariables();
		if (variables.getLocals() == null || variables.getLocals().getVardecls() == null) {
			return result;
		}
		for (VariableDeclarations vardecs : variables.getLocals().getVardecls()) {
			vardecs.getVariables().stream().forEach(e -> result.add(e.getName()));
		}
		return result;
	}

	public static Set<String> getParameterSet(Skill skill) {
		Parameters paras = skill.getParameters();
		Set<String> result = new HashSet<String>();
		if (paras == null || paras.getVardecls() == null) {
			return result;
		}
		for (VariableDeclarations vardecs : paras.getVardecls()) {
			vardecs.getVariables().stream().forEach(e -> result.add(e.getName()));
		}
		return result;
	}
	
//	public static Invocation replaceArguments(Invocation fun, InvocationFunction call) {
//		Invocation result = EcoreUtil.copy(fun);
//		int index = 0;
//		for(InvocationArgument arg : fun.getArgs()) {
//				String replacement = call.getArgs().get(index++).getName();
//				result.get
//		}
//	}
	
	public static DynInvocation replaceArgumentsDyn(DynInvocation fun, InvocationFunction call) {
		DynInvocation result = EcoreUtil.copy(fun);
//		int index = 0;
//		for(InvocationArgument arg : fun.getArgs()) {
//				String replacement = call.getArgs().get(index++).getName();
//				Dynamics dynamics = SdlFactory.eINSTANCE.createDynamics();
//				for(ODE ode : dynamics.getOde()) {
//					ODE odeNew = SdlFactory.eINSTANCE.createODE();
//					odeNew.setVariable(ode.getVariable().replace(fun.getArgs().get(index).getName(), replacement));
//					if(dynamics.getOde()
//					dynamics.getOde().add(odeNew);
//				}
//		}
		return fun;
	}
	
//	public static CtrlInvocation replaceArgumentsDyn(CtrlInvocation fun, InvocationFunction call) {
//		CtrlInvocation result = EcoreUtil.copy(fun);
//		int index = 0;
//		for(InvocationArgument arg : fun.getArgs()) {
//				String replacement = call.getArgs().get(index++).getName();
//				result.get
//		}
//	}
}
