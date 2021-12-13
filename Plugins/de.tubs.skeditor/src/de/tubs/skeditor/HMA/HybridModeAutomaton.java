package de.tubs.skeditor.HMA;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import de.tubs.skeditor.sdl.Assignment;
import de.tubs.skeditor.sdl.Formula;

public class HybridModeAutomaton {
	private String name;
	private List<Mode> modes;
	private int start;
	private Set<String> inputs, outputs, parameters, locals;
	private List<Transition> transitions;
	private List<Assignment> bindings;

	public HybridModeAutomaton(String name, List<Mode> modes, Mode start, Set<String> inputs, Set<String> outputs,
			Set<String> parameters, Set<String> locals, List<Transition> transitions) {
		super();
		this.name = name;
		this.modes = modes;
		if (start != null)
			this.start = modes.indexOf(start);
		else
			this.start = -1;
		this.inputs = inputs;
		this.outputs = outputs;
		this.parameters = parameters;
		this.locals = locals;
		this.transitions = transitions;
		this.bindings = new ArrayList<Assignment>();
	}

	public HybridModeAutomaton(String name, List<Mode> modes, Mode start, Set<String> inputs, Set<String> outputs,
			Set<String> parameters, List<Transition> transitions) {
		this(name, modes, start, inputs, outputs, parameters, new HashSet<String>(), transitions);
	}

	public HybridModeAutomaton(String name, List<Mode> modes, Mode start, Set<String> inputs, Set<String> outputs,
			List<Transition> transitions) {
		this(name, modes, start, inputs, outputs, new HashSet<String>(), new HashSet<String>(), transitions);
	}

	public HybridModeAutomaton(String name, List<Mode> modes, Mode start) {
		this(name, modes, start, new HashSet<String>(), new HashSet<String>(), new HashSet<String>(),
				new HashSet<String>(), new ArrayList<Transition>());
	}

	public HybridModeAutomaton() {
		this("undefined", new ArrayList<Mode>(), null, new HashSet<String>(), new HashSet<String>(),
				new HashSet<String>(), new HashSet<String>(), new ArrayList<Transition>());
	}

	public void addMode(Mode mode) {
		if (modes == null)
			modes = new ArrayList<Mode>();
		modes.add(mode);
	}

	public void addInput(String input) {
		if (inputs == null)
			inputs = new HashSet<String>();
		inputs.add(input);
	}

	public void addOutput(String output) {
		if (outputs == null)
			outputs = new HashSet<String>();
		outputs.add(output);
	}

	public void addLocal(String local) {
		if (locals == null)
			locals = new HashSet<String>();
		locals.add(local);
	}

	public void addParameter(String parameter) {
		if (parameters == null)
			parameters = new HashSet<String>();
		parameters.add(parameter);
	}

	public void addTransition(Transition t) {
		if (transitions == null)
			transitions = new ArrayList<Transition>();
		transitions.add(t);
	}

	public void addTransition(Mode from, Formula guard, Mode to) {
		if (transitions == null)
			transitions = new ArrayList<Transition>();
		transitions.add(new Transition(from, guard, to));
	}
	
	public void addBinding(Assignment assg) {
		if (bindings == null)
			bindings = new ArrayList<Assignment>();
		bindings.add(assg);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStart(Mode m) {
		start = modes.indexOf(m);
	}

	public void setStart(int i) {
		start = i;
	}

	public void setStart(String name) {
		Optional<Mode> res = modes.stream().filter(m -> m.getName().equals(name)).findFirst();
		if (res.isPresent())
			start = modes.indexOf(res.get());
		else
			start = 0;
	}

	public List<Mode> getModes() {
		return modes;
	}

	public Mode getModeByName(String name) {
		for (Mode m : modes) {
			if (m.getName().equals(name))
				return m;
		}
		return null;
	}

	public Mode getStart() {
		return modes.get(start);
	}

	public Set<String> getInputs() {
		return inputs;
	}

	public Set<String> getOutputs() {
		return outputs;
	}

	public Set<String> getParameters() {
		return parameters;
	}

	public Set<String> getLocals() {
		return locals;
	}

	public List<Transition> getTransitions() {
		return transitions;
	}
	
	public List<Assignment> getBindings() {
		return bindings;
	}

//	public HybridModeAutomaton clone(HybridModeAutomaton A) {
//		HybridModeAutomaton result = new HybridModeAutomaton();
//		result.name = A.name;
//
//		for(Mode m : A.getModes()) {
//			modes.add(m.clone());
//		}
//
//		result.start = A.start;
//		result.inputs = A.inputs;
//		result.outputs = A.outputs;
//		result.parameters = A.parameters;
//		result.locals = A.locals;
//
//		for(Transition t : A.getTransitions()) {
//			transitions.add(t.clone());
//		}
//		
//		return result;
//	}
	
//	public int hashCode() {
//		return this.getName().hashCode();
//	}
//	
//	public boolean equals(Object hma) {
//		return this.getName().equals(((HybridModeAutomaton)hma).getName()) || this == hma;
//	}
}
