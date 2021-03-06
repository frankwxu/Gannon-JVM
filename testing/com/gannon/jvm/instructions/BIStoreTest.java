/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gannon.jvm.instructions;

import static org.junit.Assert.assertEquals;

import java.util.Stack;

import org.junit.Test;

import com.gannon.asm.components.BMethod;
import com.gannon.jvm.data.dependency.DependencyFrame;
import com.gannon.jvm.execution.method.BFrame;
import com.gannon.jvm.execution.method.BLocalVarTable;
import com.gannon.jvm.progam.path.TestPath;

public class BIStoreTest {

	public BIStoreTest() {
	}

	/**
	 * Test of execute method, of class BIStore.
	 */
	@Test
	public void testExecutePosition2() {
		System.out.println("execute");
		BIStore bIStore = new BIStore(2,0);// Initialize BIStore, pass 02 as operand value
		// init local Variable table
		BLocalVarTable varTable = new BLocalVarTable();
		Stack<Integer> operandStack = new Stack<Integer>();
		operandStack.add(10); // add value 10 to index 0 of operand stack
		operandStack.add(1);
		operandStack.add(9);
		varTable.add(7);
		varTable.add(2);
		varTable.add(5);

		BFrame activeFrame = new BFrame(operandStack, varTable, 0);
		
		// Before calling the execute method, LocalVariableTable will have 7 on its 0th, 
		// 2 on its first and 5 on its 2nd position. OperandStack will have 10,1 and 9. 9 will be the top of the stack.
		// Expectation is, BIStore(2) should load what ever is there on top of operand stack to the 2nd position of ,
		// LocalVariableTable. 

		bIStore.execute(activeFrame);

		BLocalVarTable resultVarTable = activeFrame.getLocalVariableTable();
		
		Object result = resultVarTable.getLocalVariable(2);
		
		assertEquals(result, new Integer(9));
	}

	@Test
	public void testExecutePosition1() {
		System.out.println("execute");
		BIStore bIStore = new BIStore(1,0);// Initialize BIStore, pass 1 as operand value
		// init local Variable table
		BLocalVarTable varTable = new BLocalVarTable();
		Stack<Integer> operandStack = new Stack<Integer>();
		operandStack.add(10); // add value 10 to index 0 of operand stack
		operandStack.add(1);
		operandStack.add(9);
		varTable.add(7);
		varTable.add(2);
		varTable.add(5);

		BFrame activeFrame = new BFrame(operandStack, varTable, 0);
		
		// Before calling the execute method, LocalVariableTable will have 7 on its 0th, 
		// 2 on its first and 5 on its 2nd position. OperandStack will have 10,1 and 9. 9 will be the top of the stack.
		// Expectation is, BIStore(1) should load what ever is there on top of operand stack to the 1st position of ,
		// LocalVariableTable. 

		bIStore.execute(activeFrame);

		BLocalVarTable resultVarTable = activeFrame.getLocalVariableTable();
		
		Object result = resultVarTable.getLocalVariable(1);
		
		assertEquals(result, new Integer(9));
	}
	
	/**
	 * Test of getOpcode method, of class BIStore.
	 */
	@Test
	public void testGetOpcode() {
		System.out.println("getOpcode");
		BIStore instance = new BIStore(1,0);
		int expResult = 54;
		int result = instance.getOpcode();
		assertEquals(expResult, result);
		
	}

	/**
	 * Test of getOpcodeCommand method, of class BIStore.
	 */
	@Test
	public void testGetOpcodeCommand() {
		System.out.println("getOpcodeCommand");
		BIStore instance = new BIStore(1,0);
				
		String result = instance.getOpCodeCommand();

		String expResult = "istore";
		System.out.println(expResult);
		assertEquals(expResult, result);
	}
	
	@Test
	public void testDependency() {
		DependencyFrame dependency = new DependencyFrame();
		TestPath targetPath = new TestPath();
		BMethod method = new BMethod(1, "", "(III)I");
		targetPath.setbMethod(method);
		dependency.setTargetPath(targetPath);
		dependency.initParameterRelation();
		dependency.getIntermediateVariableNameStack().push("6");
		BIStore iStore = new BIStore(10,10);
		iStore.analyzing(dependency);
		Stack<String> resultStack = dependency.getIntermediateVariableNameStack();
		//String result = resultStack.peek();
		assertEquals(new Stack(), resultStack);
	}
}