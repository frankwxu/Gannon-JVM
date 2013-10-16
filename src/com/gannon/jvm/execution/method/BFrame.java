package com.gannon.jvm.execution.method;

import java.util.Stack;

import com.gannon.asm.components.BClass;
import com.gannon.asm.components.BMethod;
import com.gannon.jvm.shared.Frame;
import com.gannon.jvm.utilities.ConstantsUtility;

public class BFrame extends Frame {
	private int lineNumber = ConstantsUtility.INIT_PROGRAM_LINE_NUMBER;

	public BFrame(BClass bClass, BMethod bMethod, Stack operandStack, BLocalVarTable localVariableTable, int lineNumber) {
		super(bClass, bMethod, operandStack, localVariableTable);
		this.lineNumber = lineNumber;
	}

	public BFrame(BClass bClass, Stack operandStack, BLocalVarTable localVariableTable, int lineNumber) {
		super(bClass, operandStack, localVariableTable);
		this.lineNumber = lineNumber;
	}

	public BFrame(Stack operandStack, BLocalVarTable localVariableTable, int lineNumber) {
		super(operandStack, localVariableTable);
		this.lineNumber = lineNumber;
	}

	public int getLineNumber() {
		return this.lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

}
