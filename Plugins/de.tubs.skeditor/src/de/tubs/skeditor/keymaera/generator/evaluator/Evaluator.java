package de.tubs.skeditor.keymaera.generator.evaluator;

import org.eclipse.emf.ecore.EObject;

public interface Evaluator<T extends EObject> {

	default String evaluate(T expression) {
		return String.format("No evaluator found for expresion: {%s}", expression.toString());
	}

}