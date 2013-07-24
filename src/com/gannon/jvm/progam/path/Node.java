package com.gannon.jvm.progam.path;

import com.gannon.jvm.instructions.BInstruction;

public class Node {
	private BInstruction instruction;

	public Node(BInstruction ins) {
		super();
		this.instruction = ins;
	}

	public BInstruction getInstruction() {
		return instruction;
	}

	public void setInstruction(BInstruction instruction) {
		this.instruction = instruction;
	}

	public Integer getInstructionLineNumber(){
		return instruction.getLineNumber();
	}
}