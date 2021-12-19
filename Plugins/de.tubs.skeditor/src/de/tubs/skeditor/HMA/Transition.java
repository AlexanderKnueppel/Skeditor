package de.tubs.skeditor.HMA;

import org.eclipse.emf.ecore.util.EcoreUtil;

import de.tubs.skeditor.sdl.Formula;
import de.tubs.skeditor.sdl.SubMode;

public class Transition {
	private Mode from;
	private Mode to;
	private Formula guard;

	public Transition(Mode from,  Formula guard, Mode to) {
		super();
		this.from = from;
		this.to = to;
		this.guard = guard;
	}

	public Mode getFrom() {
		return from;
	}

	public void setFrom(Mode from) {
		this.from = from;
	}

	public Mode getTo() {
		return to;
	}

	public void setTo(Mode to) {
		this.to = to;
	}

	public Formula getGuard() {
		return guard;
	}

	public void setGuard(Formula guard) {
		this.guard = guard;
	}

//	public Transition clone(Transition m) {
//		Mode result = new Mode(m.name, EcoreUtil.copy(m.dynamics), EcoreUtil.copy(m.controller));
//		for(SubMode sub : m.getSubModes()) {
//			result.addSubMode(EcoreUtil.copy(sub));
//		}
//		return result;
//	}
}
