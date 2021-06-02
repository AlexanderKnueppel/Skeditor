package de.tubs.skeditor.simulation.core;

public interface Optionable {
	
	String getValue();
	OptionableContainer getOptionableContainer();
	
	default String getOutputString(){
		return getType() + "::" + getValue()+";";
	}
	
	default String getType(){
		return getOptionableContainer().getValue();
	}
}
