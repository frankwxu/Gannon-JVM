package com.gannon.asm.classgenerator;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import com.gannon.Executor.BytecodeObjectFactories.VisitFieldInstructionFactory;
import com.gannon.Executor.BytecodeObjectFactories.VisitInstructionFactory;
import com.gannon.Executor.BytecodeObjectFactories.VisitIntegerInstructionFactory;
import com.gannon.Executor.BytecodeObjectFactories.VisitJumpInstructionFactory;
import com.gannon.Executor.BytecodeObjectFactories.VisitMethodInstructionFactory;
import com.gannon.Executor.BytecodeObjectFactories.VisitVariableInstructionFactory;
import com.gannon.asm.components.BBlock;
import com.gannon.asm.components.BLabel;
import com.gannon.asm.components.BLocalVariable;
import com.gannon.asm.components.BMethod;
import com.gannon.asm.components.BStackMaxLocals;
import com.gannon.jvm.utilities.ConstantsUtility;

public class ClassMethodVisitor extends MethodVisitor {
	/**
	 * Object of Method being visited *
	 */
	private BMethod currentMethod;
	private BBlock currentBlock;
	private int linNumber = ConstantsUtility.INIT_PROGRAM_LINE_NUMBER;
	private int lableID=0;

	/**
	 * Constructor of ClasMethodVisitor
	 *
	 * @param arg0
	 * @param currentMethod
	 *            object of BMethod *
	 */
	public ClassMethodVisitor(int arg0, BMethod currentMethod) {
		super(arg0);
		this.currentMethod = currentMethod;
	}

	// GETSTATIC, PUTSTATIC, GETFIELD or PUTFIELD.
	@Override
	public void visitFieldInsn(int opcode, String owner, String name,
			String desc) {
		currentBlock.addInstruction(new VisitFieldInstructionFactory()
				.createInst(opcode, owner, name, desc, linNumber));

		super.visitFieldInsn(opcode, owner, name, desc);
		linNumber++;
	}

	// ILOAD, LLOAD, FLOAD, DLOAD, ALOAD, ISTORE, LSTORE, FSTORE, DSTORE, ASTORE
	// or RET.
	@Override
	public void visitVarInsn(int opcode, int operand1) {
		currentBlock.addInstruction(new VisitVariableInstructionFactory()
				.createInst(opcode, operand1, linNumber));


		linNumber++;
	}

	// GOTO or THROW
//	 @Override
//	 public void visitFrame(int i, int i1, Object[] os, int i2, Object[] os1)
//	 {
//	 super.visitFrame(i, i1, os, i2, os1); //To change body of generated methods,
//	 //choose Tools | Templates.
//	 System.out.println("visit frame "+i +" "+i1);
//	 System.out.println(os);
//	 System.out.println(os1);
//	 }

	// IFEQ, IFNE, IFLT, IFGE, IFGT, IFLE, IF_ICMPEQ, IF_ICMPNE, IF_ICMPLT,
	// IF_ICMPGE, IF_ICMPGT, IF_ICMPLE, IF_ACMPEQ, IF_ACMPNE, GOTO, JSR, IFNULL
	// or IFNONNULL
	@Override
	public void visitJumpInsn(int opcode, Label label) {
		super.visitJumpInsn(opcode, label); // To change body of generated
		// methods, choose Tools |
		// Templates.
		currentBlock.addInstruction(new VisitJumpInstructionFactory()
				.createInst(opcode, label, linNumber));

		linNumber++;
	}

	// IINC
	@Override
	public void visitIincInsn(int opcode, int inc) {

		// int opCode = 132;
		// currentMethod.addInstruction(new BInstruction(opCode, opcode, inc));


		super.visitIincInsn(opcode, inc); // To change body of generated
											// methods, choose Tools |
											// Templates.
		linNumber++;
	}

	// NOP, ACONST_NULL, ICONST_M1, ICONST_0, ICONST_1, ICONST_2, ICONST_3,
	// ICONST_4,
	// ICONST_5, LCONST_0, LCONST_1, FCONST_0, FCONST_1, FCONST_2, DCONST_0,
	// DCONST_1,
	// IALOAD, LALOAD, FALOAD, DALOAD, AALOAD, BALOAD, CALOAD, SALOAD, IASTORE,
	// LASTORE,
	// FASTORE, DASTORE, AASTORE, BASTORE, CASTORE, SASTORE, POP, POP2, DUP,
	// DUP_X1, DUP_X2,
	// DUP2, DUP2_X1, DUP2_X2, SWAP, IADD, LADD, FADD, DADD, ISUB, LSUB, FSUB,
	// DSUB, IMUL, LMUL,
	// FMUL, DMUL, IDIV, LDIV, FDIV, DDIV, IREM, LREM, FREM, DREM, INEG, LNEG,
	// FNEG, DNEG, ISHL,
	// LSHL, ISHR, LSHR, IUSHR, LUSHR, IAND, LAND, IOR, LOR, IXOR, LXOR, I2L,
	// I2F, I2D, L2I, L2F,
	// L2D, F2I, F2L, F2D, D2I, D2L, D2F, I2B, I2C, I2S, LCMP, FCMPL, FCMPG,
	// DCMPL, DCMPG, IRETURN,
	// LRETURN, FRETURN, DRETURN, ARETURN, RETURN, ARRAYLENGTH, ATHROW,
	// MONITORENTER, or MONITOREXIT.
	@Override
	public void visitInsn(int opcode) {
		currentBlock.addInstruction(new VisitInstructionFactory().createInst(
				opcode, linNumber));


		linNumber++;
	}

	// INVOKEVIRTUAL, INVOKESPECIAL, INVOKESTATIC or INVOKEINTERFACE.
	@Override
	public void visitMethodInsn(int opcode, String owner, String name,
			String desc) {
		currentBlock.addInstruction(new VisitMethodInstructionFactory()
				.createInst(opcode, owner, name, desc, linNumber));
		linNumber++;
	}

	// BIPUSH, SIPUSH or NEWARRAY
	@Override
	public void visitIntInsn(int opcode, int operand) {
		currentBlock.addInstruction(new VisitIntegerInstructionFactory()
				.createInst(opcode, operand, linNumber));


		linNumber++;
	}

	@Override
	public void visitLabel(Label label) {
		BLabel bLabel=new BLabel(label,lableID);
		//The label contains the instruction ID/line number the execution jumps to
		bLabel.setGoToLineNumber(linNumber);
		currentBlock = new BBlock(bLabel);
		currentMethod.addBlock(currentBlock);
		lableID++;
		super.visitLabel(label);
	}

	@Override
	public void visitLineNumber(int line, Label start) {
		//line is the line number of source code
		super.visitLineNumber(line, start);
		//System.out.println("visit line Number"+ line+"  "+start.toString());
	}

	@Override
	public void visitMaxs(int maxStack, int maxLocals) {
		currentMethod
				.addStackVariable(new BStackMaxLocals(maxStack, maxLocals));
		super.visitMaxs(maxStack, maxLocals);
	}

	@Override
	public void visitLocalVariable(String name, String desc, String signature,
			Label start, Label end, int index) {
		BLocalVariable aLocalVariable = new BLocalVariable(name, desc,
				signature, index);
		currentMethod.addLocalVariableTable(aLocalVariable);
		//System.out.println("local variables: " +name+ " " +desc +" "+signature+ " "  + start.toString()+" "+ end.toString()+ " "+ index);
	}
}
