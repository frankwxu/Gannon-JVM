/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gannon.jvm.instructions;

import static org.junit.Assert.assertEquals;

import java.util.Stack;

import org.junit.Test;

import com.gannon.jvm.BFrame;
import com.gannon.jvm.BLocalVarTable;
import com.gannon.jvm.data.dependency.BinTree;
import com.gannon.jvm.data.dependency.DependencyDataHolder;

public class BIAddTest {

	public BIAddTest() {
	}

	/**
	 * Test of execute method, of class BIAdd.
	 */
	@Test
	public void testExecute() {
		System.out.println("execute");
		// init local Variable table
		BLocalVarTable varTable = new BLocalVarTable();
		Stack<Integer> operandStack = new Stack<Integer>();
		operandStack.add(5);
		operandStack.add(9);

		BFrame activeFrame = new BFrame(0, varTable, operandStack);
		BIAdd bIAdd = new BIAdd();

		// Before calling the execute method,  LocalVariableTable will have 7 on its 0th position.
		// operand stack will have 5 and 9,where 9 is top of the stack.
		// Expectation is, operand stack should load he summation of two values on top of the stack,
		// Should be copied to TOP of the stack of operand. the later two values must be removed from the operand stack

		bIAdd.execute(activeFrame);

		Stack<Integer> afterExe = activeFrame.getOperandStack();

		Stack<Integer> expectedOperandStack = new Stack<Integer>();
		expectedOperandStack.push(new Integer(14));
		assertEquals(expectedOperandStack, afterExe);
	}

	/**
	 * Test of getOpcode method, of class BIAdd.
	 */
	@Test
	public void testGetOpcode() {
		System.out.println("getOpcode");
		BIAdd instance = new BIAdd();
		int expResult = 96;
		int result = instance.getOpcode();
		assertEquals(expResult, result);
	}

	/**
	 * Test of getOpcodeCommand method, of class BIAdd.
	 */
	@Test
	public void testGetOpcodeCommand() {
		System.out.println("getOpcodeCommand");
		BIAdd instance = new BIAdd();
		String expResult = "iadd";
		String result = instance.getOpCodeCommand();
		assertEquals(expResult, result);
	}

	@Test
	public void testDependcy(){
		Stack<String> operandStack = new Stack<String>();
		operandStack.add("5");
		operandStack.add("9");

		DependencyDataHolder dependency=new DependencyDataHolder();
		dependency.setTempVarialbeStack(operandStack);

		BIAdd iadd=new BIAdd();
		iadd.analyzing(dependency);
		BinTree tree=dependency.getListOfTrees().get(0);
		tree.inorderBST();


	}

	@Test
	public void testDependcy2(){
		Stack<String> operandStack = new Stack<String>();
		operandStack.add("15");
		operandStack.add("9");

		DependencyDataHolder dependency=new DependencyDataHolder();
		dependency.setTempVarialbeStack(operandStack);

		BIAdd iadd=new BIAdd();
		iadd.analyzing(dependency);
		BinTree tree=dependency.getListOfTrees().get(0);
		tree.inorderBST();
	}
}