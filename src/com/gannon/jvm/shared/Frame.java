package com.gannon.jvm.shared;

import java.util.Stack;

import com.gannon.asm.components.BClass;
import com.gannon.asm.components.BMethod;
import com.gannon.jvm.execution.method.BLocalVarTable;
import com.gannon.jvm.utilities.ConstantsUtility;

public class Frame {
	protected BClass bClass;
	protected BMethod bMethod;
	protected Stack operandStack = new Stack();
	protected BLocalVarTable localVariableTable=new BLocalVarTable(); 

	public Frame() {
		super();
	}

	public Frame(BClass bClass, BMethod bMethod, Stack operandStack, BLocalVarTable localVariableTable) {
		super();
		this.bClass = bClass;
		this.bMethod = bMethod;
		this.operandStack = operandStack;
		this.localVariableTable = localVariableTable;
	}

	public Frame(BClass bClass, Stack operandStack, BLocalVarTable localVariableTable) {
		super();
		this.bClass = bClass;
		this.operandStack = operandStack;
		this.localVariableTable = localVariableTable;
	}

	public Frame(Stack operandStack, BLocalVarTable localVariableTable) {
		super();
		this.operandStack = operandStack;
		this.localVariableTable = localVariableTable;
	}

	public Frame(BLocalVarTable localVariableTable) {
		super();
		this.localVariableTable = localVariableTable;
	}

	public BClass getbClass() {
		return bClass;
	}

	public void setbClass(BClass bClass) {
		this.bClass = bClass;
	}

	public BMethod getbMethod() {
		return bMethod;
	}

	public void setbMethod(BMethod bMethod) {
		this.bMethod = bMethod;
	}

	public Stack getOperandStack() {
		return operandStack;
	}

	public void setOperandStack(Stack operandStack) {
		this.operandStack = operandStack;
	}

	public BLocalVarTable getLocalVariableTable() {
		return localVariableTable;
	}

	public void setLocalVariableTable(BLocalVarTable localVariableTable) {
		this.localVariableTable = localVariableTable;
	}

}
