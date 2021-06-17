package de.tubs.skeditor.simulation.plugin.handle;

public class SimulatorDescription<T> {

	private T simulator;
	private String name;
	private String handle;
	private String description;

	public SimulatorDescription(T simulator, String name, String handle, String description) {
		this.simulator = simulator;
		this.name = name;
		this.handle = handle;
		this.description = description;
	}

	public T getSimulator() {
		return simulator;
	}

	public String getName() {
		return name;
	}
	
	public String getHandle() {
		return handle;
	}

	public String getDescription() {
		return description;
	}

}
