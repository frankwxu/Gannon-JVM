package com.gannon.bytecode.controlflowgraph;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import com.gannon.asm.classgenerator.BClassGenerator;
import com.gannon.asm.components.BClass;
import com.gannon.asm.components.BMethod;

public class BuildCFGTest {
	@Rule
	public TestRule watcher = new TestWatcher() {
		@Override
		protected void starting(Description description) {
			System.out.println("Starting test: " + description.getMethodName());
		}
	};
	
	@Test
	public void testgetResultGraphFunction() {
		BClass myclass = BClassGenerator.getBClass("TestGoTo.class");
		BMethod m = myclass.getMethod("MethodA");
		assertEquals("MethodA", m.getName());
		
		ArrayList<BMethod> allMethodList = new ArrayList<BMethod>(); 
		allMethodList = myclass.getMethods();
		
		BuildCFG objCFG =  new BuildCFG(allMethodList, m);
		CGraph g = objCFG.getResultGraph();
		System.out.print(g.printNodesToString());
		
	}
}
