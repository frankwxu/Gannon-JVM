package com.gannon.asm.components;

import java.util.ArrayList;

import com.gannon.jvm.instructions.BInstruction;

/**
 * This class is a part of data structure created to organize the information
 * about the class being visited. This Class will have the label informations.
 *
 *
 * @param Label
 *
 *            A block consists of a Lable and a list of instructions Eg. of a
 *            block The content of the BBlock will be set in
 *            ClassMethodVisitor.java
 *
 *            L1 iload 1 iload 2
 *
 * **/
public class BBlock {
	private ArrayList<BInstruction> instructions = new ArrayList<BInstruction>();
	private BLabel bLable;// The fixed name, it won't be changed

	public BBlock(BLabel bLable) {
		super();
		this.bLable = bLable;
	}

	public void addInstruction(BInstruction instruction) {
		instructions.add(instruction);
	}

	public ArrayList<BInstruction> getInstructions() {
		return instructions;
	}

	public void setInstructions(ArrayList<BInstruction> instructions) {
		this.instructions = instructions;
	}

	public BLabel getbLable() {
		return bLable;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(bLable);
		for (BInstruction intr : instructions) {
			sb.append(intr + "\n");
		}
		return sb.toString();
	}
}
