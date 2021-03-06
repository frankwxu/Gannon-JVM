package com.gannon.Executor.BytecodeObjectFactories;

import com.gannon.jvm.instructions.BIAdd;
import com.gannon.jvm.instructions.BIConst_0;
import com.gannon.jvm.instructions.BIConst_1;
import com.gannon.jvm.instructions.BIConst_2;
import com.gannon.jvm.instructions.BIConst_3;
import com.gannon.jvm.instructions.BIConst_4;
import com.gannon.jvm.instructions.BIConst_5;
import com.gannon.jvm.instructions.BIReturn;
import com.gannon.jvm.instructions.BInstruction;
import com.gannon.jvm.instructions.BPop;
import com.gannon.jvm.instructions.BReturn;
import com.gannon.jvm.utilities.OpcodeUtility;

public class VisitInstructionFactory {
	BInstruction instr;

	public BInstruction getInstr() {
		return instr;
	}

	public void setInstr(BInstruction instr) {
		this.instr = instr;
	}

	public BInstruction createInst(int opCode, int linNumber) {
		if (OpcodeUtility.getOpCodeCommand(opCode).equals("ireturn")) {
			//System.out.println("In VisitInstructionFactory ireturn ");
			instr = new BIReturn(linNumber);
		}
		else if (OpcodeUtility.getOpCodeCommand(opCode).equals("return")) {
			//System.out.println("In VisitInstructionFactory return ");
			instr = new BReturn(linNumber);
		}
		else if (OpcodeUtility.getOpCodeCommand(opCode).equals("pop")) {
			//System.out.println("In VisitInstructionFactory pop ");
			instr = new BPop(linNumber);
		}
		else if (OpcodeUtility.getOpCodeCommand(opCode).equals("iadd")) {
			//System.out.println("In VisitInstructionFactory pop ");
			instr = new BIAdd(linNumber);
		}
		else if (OpcodeUtility.getOpCodeCommand(opCode).equals("iconst_0")) {
			//System.out.println("In VisitInstructionFactory iconst_0 ");
			instr = new BIConst_0(linNumber);
		}
		else if (OpcodeUtility.getOpCodeCommand(opCode).equals("iconst_1")) {
			//System.out.println("In VisitInstructionFactory iconst_1 ");
			instr = new BIConst_1(linNumber);
		}
		else if (OpcodeUtility.getOpCodeCommand(opCode).equals("iconst_2")) {
			//System.out.println("In VisitInstructionFactory iconst_2 ");
			instr = new BIConst_2(linNumber);
		}
		else if (OpcodeUtility.getOpCodeCommand(opCode).equals("iconst_3")) {
			//System.out.println("In VisitInstructionFactory iconst_3 ");
			instr = new BIConst_3(linNumber);
		}
		else if (OpcodeUtility.getOpCodeCommand(opCode).equals("iconst_4")) {
			//System.out.println("In VisitInstructionFactory iconst_4 ");
			instr = new BIConst_4(linNumber);
		}
		else if (OpcodeUtility.getOpCodeCommand(opCode).equals("iconst_5")) {
			//System.out.println("In VisitInstructionFactory iconst_5 ");
			instr = new BIConst_5(linNumber);
		}
		return instr;
	}
}
