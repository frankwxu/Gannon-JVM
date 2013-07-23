package com.gannon.jvm.instructions;

import java.util.Stack;

import org.objectweb.asm.Label;

import com.gannon.asm.components.BBlock;
import com.gannon.asm.components.BLabel;
import com.gannon.jvm.data.dependency.BinNode;
import com.gannon.jvm.data.dependency.BinPredicateNode;
import com.gannon.jvm.data.dependency.Relation;
import com.gannon.jvm.data.dependency.RelationFrame;
import com.gannon.jvm.execution.method.BFrame;
import com.gannon.jvm.execution.method.BLocalVarTable;
import com.gannon.jvm.execution.path.PathFrame;
import com.gannon.jvm.utilities.Utility;

public class BIFicmpeq extends BPredicateInstruction {
	public BIFicmpeq(BLabel label, int lineNumber) {
		super(label, lineNumber);
	}

	@Override
	public Object execute(BFrame activeFrame) {
		Stack<Object> myOperandStack = activeFrame.getOperandStack();
		BLocalVarTable myLocalVariableTable = activeFrame.getVarTable();

		// next instruction will fetched for execution
		Integer programCounter = activeFrame.getPC();

		Integer firstValue = (Integer) myOperandStack.pop();
		Integer secondValue = (Integer) myOperandStack.pop();

		boolean predicateResult=firstValue.equals(secondValue);
		if (predicateResult) {
			programCounter = getOperand().getGoToLineNumber();
			activeFrame.setPC(programCounter);
		} else {
			myOperandStack.clear();
			// set next instruction to executed
			activeFrame.setPC(++programCounter);
		}

		activeFrame.setOperandStack(myOperandStack);
		activeFrame.setVarTable(myLocalVariableTable);

		return predicateResult;
	}

	public String toString() {
		return super.toString()+" "+getOperand();
	}

	public int getOpcode() {
		return 159;
	}

	@Override
	public void analyzing(RelationFrame rFrame) {
		Stack<String> myOperandStack = rFrame.getTempVariableStack(); 
		BinNode rightNode= new BinNode(myOperandStack.pop());
		BinNode leftNode= new BinNode(myOperandStack.pop());
		BinPredicateNode rootNode=new BinPredicateNode(Integer.toString(Utility.getNextID()));
		Relation relation=new Relation(rootNode, this);
		relation.insertToLeft(leftNode); 
		relation.insertToRight(rightNode);

		myOperandStack.push(rootNode.getLocalVariableName());
		rFrame.getRelations().add(relation);
		rFrame.setTempVariableStack(myOperandStack);
	}

	@Override
	public Object execute(PathFrame pathFrame) {
		Stack<Object> myOperandStack = pathFrame.getOperandStack();
		BLocalVarTable myLocalVariableTable = pathFrame.getLocalVariableTable();

		Integer firstValue = (Integer) myOperandStack.pop();
		Integer secondValue = (Integer) myOperandStack.pop();

		boolean predicateResult=firstValue.equals(secondValue);
		if (predicateResult) {


		} else {
			myOperandStack.clear();
			
		}

		pathFrame.setOperandStack(myOperandStack);
		pathFrame.setLocalVariableTable(myLocalVariableTable);

		return predicateResult;
	}
}
