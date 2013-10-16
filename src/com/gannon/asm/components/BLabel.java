package com.gannon.asm.components;

import org.objectweb.asm.Label;

// e.g, L0, L1
public class BLabel {
	private Label lb;// L234343
	private char ch;// L
	private int sequenceID;//L1, l2, L is ch
	private int goToLineNumber;// real instruction iD jump to, go lineNumber

	public BLabel(Label lb) {
		super();
		this.lb = lb;
	}

	public BLabel(Label lb, int sequenceID) {
		super();
		this.lb = lb;
		this.ch = 'L';
		this.sequenceID = sequenceID;
		this.goToLineNumber=-1;
	}

	public Label getLabel() {
		return lb;
	}

	public char getCh() {
		return ch;
	}

	public int getID() {
		return sequenceID;
	}


	public int getGoToLineNumber() {
		return goToLineNumber;
	}

	public void setGoToLineNumber(int goToLineNumber) {
		this.goToLineNumber = goToLineNumber;
	}

	@Override
	public String toString() {
		StringBuilder s=new StringBuilder();
		s.append(" " + ch + sequenceID + "("+lb.toString()+ ")  "+ goToLineNumber+" \n");
		return s.toString();
	}
}
