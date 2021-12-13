package de.tubs.skeditor.HMA;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.util.EcoreUtil;

import de.tubs.skeditor.sdl.Controller;
import de.tubs.skeditor.sdl.Dynamics;
import de.tubs.skeditor.sdl.SubMode;

public class Mode {
	private String name;
	private Dynamics dynamics;
	private Controller controller;
	private List<SubMode> smodes;
	
	public Mode(String name, Dynamics dynamics, Controller controller) {
		super();
		this.name = name;
		this.dynamics = dynamics;
		this.controller = controller;
		this.smodes = new ArrayList<SubMode>();
	}

	public Mode() {
		dynamics = null;
		controller = null;
		this.smodes = new ArrayList<SubMode>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Dynamics getDynamics() {
		return dynamics;
	}

	public void setDynamics(Dynamics dynamics) {
		this.dynamics = dynamics;
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}
	
	public List<SubMode> getSubModes() {
		return smodes;
	}
	
	public void addSubMode(SubMode smode) {
		smodes.add(smode);
	}
	
	public boolean equals(Mode m) {
		return this.getName() == m.getName();
	}
	
	public Mode clone(Mode m) {
		Mode result = new Mode(m.name, EcoreUtil.copy(m.dynamics), EcoreUtil.copy(m.controller));
		for(SubMode sub : m.getSubModes()) {
			result.addSubMode(EcoreUtil.copy(sub));
		}
		return result;
	}
}
