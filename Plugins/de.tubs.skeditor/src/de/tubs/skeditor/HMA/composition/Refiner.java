package de.tubs.skeditor.HMA.composition;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.util.EcoreUtil;

import de.tubs.skeditor.HMA.HybridModeAutomaton;
import de.tubs.skeditor.HMA.Mode;
import de.tubs.skeditor.HMA.Transition;
import de.tubs.skeditor.sdl.BooleanExpression;
import de.tubs.skeditor.sdl.BooleanLiteral;
import de.tubs.skeditor.sdl.Controller;
import de.tubs.skeditor.sdl.Dynamics;
import de.tubs.skeditor.sdl.Formula;
import de.tubs.skeditor.sdl.SdlFactory;

public class Refiner {

	public static HybridModeAutomaton refinement(HybridModeAutomaton A1, Mode q, HybridModeAutomaton A2) {
		if (A2.getModes().isEmpty()) {
			return A1;
		}

		HybridModeAutomaton result = new HybridModeAutomaton();

		// Modes from q1
		for (Mode q1 : A1.getModes()) {
			if (!q1.equals(q))
				result.addMode(q1);
		}

		// compose q with A2
		for (Mode q2 : A2.getModes()) {
			Mode newMode = new Mode();
			newMode.setName(q.getName() + "-" + q2.getName());
			newMode.setDynamics(composeDynamics(q.getDynamics(), q2.getDynamics()));
			newMode.setController(composeControllers(q.getController(), q2.getController()));
			//newMode.getSubModes().addAll(q.getSubModes());
			newMode.getSubModes().addAll(q2.getSubModes());
			result.addMode(newMode);
		}

		// Initial mode
		if (!A1.getStart().equals(q))
			result.setStart(A1.getStart());
		else
			result.setStart(result.getModeByName(q.getName() + Composer.MODE_NAME_DELIMITER + A2.getStart().getName()));

		// Inputs
		Set<String> tmp = new HashSet<String>();
		tmp.addAll(A1.getInputs());
		tmp.addAll(A2.getInputs());
		tmp.removeAll(A1.getOutputs());
		tmp.removeAll(A2.getOutputs());
		result.getInputs().addAll(tmp);

		// Outputs
		result.getOutputs().addAll(A1.getOutputs());
		result.getOutputs().addAll(A2.getOutputs());

		// Locals
		result.getLocals().addAll(A1.getLocals());
		result.getLocals().addAll(A2.getLocals());

		// Parameters
		result.getParameters().addAll(A1.getParameters());
		result.getParameters().addAll(A2.getParameters());
		
		// Bindings
		result.getBindings().addAll(EcoreUtil.copyAll(A1.getBindings()));
		result.getBindings().addAll(EcoreUtil.copyAll(A2.getBindings()));

		// Transitions
		for (Transition t1 : A1.getTransitions()) {
			// valid transitions of A1
			if (!t1.getFrom().equals(q) && !t1.getTo().equals(q)) {
				result.addTransition(t1);
			}
			// Incoming
			if (!t1.getFrom().equals(q) && t1.getTo().equals(q)) {
				Mode to = result.getModeByName(q.getName() + Composer.MODE_NAME_DELIMITER + A2.getStart().getName());
		
				result.addTransition(t1.getFrom(), t1.getGuard(), to);
			}
			// Outgoing
			if (t1.getFrom().equals(q) && !t1.getTo().equals(q)) {
				for (Mode m : A2.getModes()) {
					Mode from = result.getModeByName(q.getName() + Composer.MODE_NAME_DELIMITER + m.getName());
					result.addTransition(from, t1.getGuard(), t1.getTo());
				}
			}
		}
			// Transitions of A2
		Optional<Transition> self = A1.getTransitions().stream()
				.filter(t -> t.getFrom().equals(t.getTo()) && t.getFrom().equals(q)).findAny();
		Formula guard = null;
		if (self.isPresent()) {
			guard = self.get().getGuard();
		} else {
			// guard
			List<Transition> outgoing = A1.getTransitions().stream().filter(t -> t.getFrom().equals(q))
					.collect(Collectors.toList());
			
			if(outgoing.size() == 0) {
				BooleanLiteral trueLiteral = SdlFactory.eINSTANCE.createBooleanLiteral();
				trueLiteral.setValue("true");
				guard = trueLiteral;
			} else {
				Formula conjunction = outgoing.get(0).getGuard();
				for(int i = 1; i < outgoing.size(); ++i) {
					conjunction = conjoin(conjunction, outgoing.get(i).getGuard());
				}
				
				guard = conjunction;
			}
		}

		for (Transition t2 : A2.getTransitions()) {
			BooleanExpression conjunction = SdlFactory.eINSTANCE.createBooleanExpression();
			conjunction.setLeft(EcoreUtil.copy(guard));
			BooleanLiteral trueLiteral = SdlFactory.eINSTANCE.createBooleanLiteral();
			trueLiteral.setValue("true");
			conjunction.setRight(t2.getGuard() == null? trueLiteral : EcoreUtil.copy(t2.getGuard()));
			conjunction.setOp("&&");

			Mode from = result.getModeByName(q.getName() + Composer.MODE_NAME_DELIMITER + t2.getFrom().getName());
			Mode to = result.getModeByName(q.getName() + Composer.MODE_NAME_DELIMITER + t2.getTo().getName());
			result.addTransition(from, conjunction, to);
		}
		
		return result;
	}

	private static BooleanExpression conjoin(Formula f1, Formula f2) {
		BooleanExpression conjunction = SdlFactory.eINSTANCE.createBooleanExpression();
		BooleanLiteral trueLiteral = SdlFactory.eINSTANCE.createBooleanLiteral();
		trueLiteral.setValue("true");
		
		conjunction.setLeft(f1==null? trueLiteral : EcoreUtil.copy(f1));
		conjunction.setRight(f2==null? trueLiteral : EcoreUtil.copy(f2));
		conjunction.setOp("&&");
		return conjunction;
	}

	private static Dynamics composeDynamics(Dynamics D1, Dynamics D2) {
		if (D1 == null)
			return D2;
		if (D2 == null)
			return D1;

		Dynamics result = SdlFactory.eINSTANCE.createDynamics();
		result.getOde().addAll(EcoreUtil.copyAll(D1.getOde()));
		result.getOde().addAll(EcoreUtil.copyAll(D2.getOde()));

		BooleanExpression conjunction = SdlFactory.eINSTANCE.createBooleanExpression();
		conjunction.setLeft(EcoreUtil.copy(D1.getEvolution()));
		conjunction.setRight(EcoreUtil.copy(D2.getEvolution()));
		conjunction.setOp("&&");

		result.setEvolution(conjunction);
		return result;
	}

	private static Controller composeControllers(Controller C1, Controller C2) {
		if (C1 == null)
			return C2;
		if (C2 == null)
			return C1;

		Controller result = SdlFactory.eINSTANCE.createController();
		result.getStmt().addAll(EcoreUtil.copyAll(C1.getStmt()));
		result.getStmt().addAll(EcoreUtil.copyAll(C2.getStmt()));
		return result;
	}
}
