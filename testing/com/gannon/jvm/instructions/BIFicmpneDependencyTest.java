package com.gannon.jvm.instructions;

import static org.junit.Assert.assertEquals;

import java.util.Stack;

import org.junit.Test;
import org.objectweb.asm.Label;

import com.gannon.asm.components.BLabel;
import com.gannon.asm.components.BMethod;
import com.gannon.jvm.data.dependency.BinNode;
import com.gannon.jvm.data.dependency.BinPredicateNode;
import com.gannon.jvm.data.dependency.Dependency;
import com.gannon.jvm.data.dependency.DependencyFrame;
import com.gannon.jvm.instructions.BIFicmpne;
import com.gannon.jvm.instructions.BPredicateInstruction;
import com.gannon.jvm.progam.path.TestPath;

public class BIFicmpneDependencyTest {

	@Test
	public void testDependency() {
		Label newLabel = new Label();
		BLabel label = new BLabel(newLabel);
		Stack<String> operandStack = new Stack<String>();
		operandStack.add("5");
		operandStack.add("8");

		DependencyFrame dependency = new DependencyFrame();
		dependency.setTempVariableStack(operandStack);
		TestPath targetPath = new TestPath();
		BMethod method = new BMethod(1, "", "(III)I");
		targetPath.setbMethod(method);
		dependency.setTargetPath(targetPath);
		dependency.initParameterRelation();

		BPredicateInstruction ifNEqual=new BIFicmpne(label, 2);
		ifNEqual.analyzing(dependency);
		Dependency actualTree=dependency.getRelations().getRelation(4);
		actualTree.inorderBST();

		BinNode rightNode= new BinNode("8");
		BinNode leftNode= new BinNode("5");
		BinPredicateNode rootNode=new BinPredicateNode("100");
		Dependency expectedTree=new Dependency(rootNode, ifNEqual);
		
		expectedTree.insertToLeft(leftNode);
		expectedTree.insertToRight(rightNode);
		expectedTree.inorderBST();

		assertEquals(expectedTree, actualTree);
	}

}