package de.tubs.skeditor.HMA.composition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;

import de.tubs.skeditor.HMA.HybridModeAutomaton;
import de.tubs.skeditor.HMA.Mode;
import de.tubs.skeditor.HMA.Transition;
import de.tubs.skeditor.sdl.BooleanExpression;
import de.tubs.skeditor.sdl.BooleanLiteral;
import de.tubs.skeditor.sdl.Controller;
import de.tubs.skeditor.sdl.Dynamics;
import de.tubs.skeditor.sdl.ODE;
import de.tubs.skeditor.sdl.SdlFactory;

public class Composer {

	public final static String MODE_NAME_DELIMITER = "-";
	public final static String HMA_NAME_DELIMITER = "__";

	public static HybridModeAutomaton compose(HybridModeAutomaton... A) {
		HybridModeAutomaton result = new HybridModeAutomaton();

		for (HybridModeAutomaton a : A) {
			result = binaryCompose(result, a);
		}
		return result;
	}

	public static HybridModeAutomaton binaryCompose(HybridModeAutomaton A1, HybridModeAutomaton A2) {
		if (A1.getModes().isEmpty()) {
			return A2;
		}
		if (A2.getModes().isEmpty()) {
			return A1;
		}

		HybridModeAutomaton result = new HybridModeAutomaton();
		result.setName(A1.getName() + HMA_NAME_DELIMITER + A2.getName());

		// Modes
		for (Mode q1 : A1.getModes()) {
			for (Mode q2 : A2.getModes()) {
				Mode newMode = new Mode();
				newMode.setName(q1.getName() + MODE_NAME_DELIMITER + q2.getName());
				newMode.setDynamics(composeDynamics(q1.getDynamics(), q2.getDynamics()));
				newMode.setController(composeControllers(q1.getController(), q2.getController()));
				newMode.getSubModes().addAll(q1.getSubModes());
				newMode.getSubModes().addAll(q2.getSubModes());
				result.addMode(newMode);
			}
		}

		// Initial mode
		result.setStart(A1.getStart().getName() + MODE_NAME_DELIMITER + A2.getStart().getName());

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

		// Transitions
		for (Transition t1 : A1.getTransitions()) {
			for (Transition t2 : A2.getTransitions()) {
				Mode m1 = result.getModeByName(t1.getFrom().getName() + MODE_NAME_DELIMITER + t2.getFrom().getName());
				Mode m2 = result.getModeByName(t1.getTo().getName() + MODE_NAME_DELIMITER + t2.getTo().getName());

				BooleanLiteral trueLiteral = SdlFactory.eINSTANCE.createBooleanLiteral();
				trueLiteral.setValue("true");

				BooleanExpression conjunction = SdlFactory.eINSTANCE.createBooleanExpression();
				conjunction.setLeft(t1.getGuard() == null ? trueLiteral : EcoreUtil.copy(t1.getGuard()));
				conjunction.setRight(t2.getGuard() == null ? trueLiteral : EcoreUtil.copy(t2.getGuard()));
				conjunction.setOp("&&");

				result.addTransition(m1, conjunction, m2);
			}
		}
		
		result.getBindings().addAll(EcoreUtil.copyAll(A1.getBindings()));
		result.getBindings().addAll(EcoreUtil.copyAll(A2.getBindings()));
		
		return result;
	}

	private static Dynamics composeDynamics(Dynamics D1, Dynamics D2) {
		if (D1 == null)
			return D2;
		if (D2 == null)
			return D1;

		Dynamics result = SdlFactory.eINSTANCE.createDynamics();

		List<ODE> tmpODEs = new ArrayList<ODE>();
		tmpODEs.addAll(EcoreUtil.copyAll(D1.getOde()));
		tmpODEs.addAll(EcoreUtil.copyAll(D2.getOde()));

		Iterator<ODE> iterator = tmpODEs.iterator();
		while (iterator.hasNext()) {
			ODE o = (ODE) iterator.next();
			if (!result.getOde().stream()
					.filter(elem -> elem.getExpression().toString().equals(o.getExpression().toString()))
					.collect(Collectors.toList()).isEmpty())
				result.getOde().add(o);
		}

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
