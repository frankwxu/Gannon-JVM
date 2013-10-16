package com.gannon.asm.components;

import java.util.ArrayList;

import org.objectweb.asm.Label;

import com.gannon.jvm.instructions.BInstruction;
import com.gannon.jvm.instructions.BPredicateInstruction;

/**
 * This class is a part of data structure created to organize the information
 * about the class being visited. This Class will have the information of method
 * being visited and object list of local variables, instrutions, line details,
 * lable deatils, stack details.
 *
 *
 * @param name
 *            Name of the Method
 * @param methodAccess
 *            Access variable for Method
 * @param MethodDesc
 *            In parameters and out parameter details.
 * @param instructions
 *            Object list of BInstruction class
 * @param localVariables
 *            Object list of BLocalVariable class
 * @param lineDetails
 *            Object list of BLineNumber class
 * @param blockList
 *            Object list of BLable class
 * @param stackDetails
 *            Object list of BStackMaxLocals class
 * **/
public class BMethod {
	private String name;
	private int methodAccess;
	private String methodDesc;
	private ArrayList<BLocalVariable> localVariables = new ArrayList<BLocalVariable>();
	private ArrayList<BBlock> blockList = new ArrayList<BBlock>();
	private ArrayList<BStackMaxLocals> stackDetails = new ArrayList<BStackMaxLocals>();

	public BMethod() {

	}

	public BMethod(int access, String name, String desc) {
		this.name = name;
		this.methodAccess = access;
		this.methodDesc = desc;
	}

	public String getName() {
		return name;
	}

	public void addLocalVariableTable(BLocalVariable aLocalVariable) {
		localVariables.add(aLocalVariable);
	}

	public void addStackVariable(BStackMaxLocals aStackVariable) {
		stackDetails.add(aStackVariable);
	}


	public void addBlock(BBlock aBlock) {
		blockList.add(aBlock);
	}

	public ArrayList<BLocalVariable> getLocalVariables() {
		return localVariables;
	}

	public void setLocalVariables(ArrayList<BLocalVariable> localVariables) {
		this.localVariables = localVariables;
	}

	public ArrayList<BStackMaxLocals> getStackDetails() {
		return stackDetails;
	}

	public ArrayList<BBlock> getBlockList() {
		return blockList;
	}

	public void setBlockList(ArrayList<BBlock> blocks) {
		this.blockList = blocks;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<BInstruction> getInstructions() {
		ArrayList<BInstruction> instructionList = new ArrayList<BInstruction>();
		for (BBlock block : blockList) {
			instructionList.addAll(block.getInstructions());
		}
		return instructionList;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (BBlock block : blockList) {
			sb.append(block.toString());
		}
		return sb.toString();
	}

	public BLabel findBLabel(Label label){
		for(BBlock block: blockList){
			if(block.getbLable().getLabel().equals(label)){
				return block.getbLable();
			}
		}
		return null;
	}

	//change IFcompge BLabel to line number
	public void updateJumpLabel(){
		for(BBlock block: blockList){
			for(BInstruction instruction: block.getInstructions()){
				if(instruction instanceof BPredicateInstruction){
					BLabel bLabel= findBLabel(((BPredicateInstruction) instruction).getOperand().getLabel());
					((BPredicateInstruction) instruction).setOperand(bLabel);
				}
			}
		}

	}

	//for triangle desc=(III)I,e.g., three inputs and one output
	public int getNumberOfParameter(){
		int start=methodDesc.indexOf("(");
		int end=methodDesc.lastIndexOf(")");
		return end-start-1;
	}
}
