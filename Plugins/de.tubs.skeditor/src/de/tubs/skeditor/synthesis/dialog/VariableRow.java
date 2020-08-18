package de.tubs.skeditor.synthesis.dialog;

public class VariableRow {
	
	enum Type{
		PROVIDED,
		REQUIRED
	}
	private final String name;
	private final Type type;
	
	public VariableRow(String name, Type type) {
		this.name = name;
		this.type = type;
	}
	
	public String getName() {
		return this.name;
	}
	
	public Type getType() {
		return this.type;
	}
	
	public String getTypeAsString() {
		switch(this.type) {
			case PROVIDED: return "PROVIDED";
			case REQUIRED: return "REQUIRED";
			default: return null;
		}
	}
	
	@Override 
	public String toString() {
		switch(this.type) {
			case PROVIDED: return this.name+" [PROVIDED]";
			case REQUIRED: return this.name+" [REQUIRED]";
			default: return null;
		}
	}
}
