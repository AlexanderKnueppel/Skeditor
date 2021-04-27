package de.tubs.skeditor.synthesis.prover;

import java.util.Arrays;

public enum KnownFunctions {

	SIN, COS, TAN, MIN, MAX, ABS;

	public static String[] getNames(Class<? extends Enum<?>> e) {
		return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
	}

}