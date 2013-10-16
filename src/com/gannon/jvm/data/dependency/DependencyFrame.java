package com.gannon.jvm.data.dependency;

import java.util.Stack;

import com.gannon.jvm.execution.method.BLocalVarTable;
import com.gannon.jvm.progam.path.TestPath;
import com.gannon.jvm.shared.Frame;

/**
 * @author Frank Xu
 * 
 *         A relation frame is similar to a BFrame, it only keeps relations
 *         during runtime analyzing. No concept of the active frame necessary
 *         because we don't need to deal with method to method calling
 * 
 */
public class DependencyFrame extends Frame {
	// a given path for analyzing predicate dependency
	private TestPath targetPath;
	private Dependencies relations = new Dependencies();;
	// intermediate variables
	private Stack<String> variableNameStack = new Stack<String>();

	public DependencyFrame() {
		super();
	}

	// for triangle , assume tempVariableStack[1-3] are used for storing inputs
	// variables
	// we convert each variable into a relation
	public void initParameterRelation() {
		if (targetPath == null) {
			System.out.print("Add a target path to the relation frame before analyzing dependency!");
		} else {
			int numberOfPara = targetPath.getbMethod().getNumberOfParameter();
			// System.out.println("numberOfPara " + numberOfPara);

			// 0 is never used
			for (int i = 0; i <= numberOfPara; i++) {
				relations.add(new Dependency(new BinNode(Integer.toString(i))));
			}
		}
	}

	// for triangle , assume tempVariableStack[1-3] are used for storing inputs
	// variables
	// we convert each variable into a relation
	public void initInputs() {
		if (targetPath == null) {
			System.out.print("Add a target path to the relation frame before analyzing dependency!");
		} else {
			// if we dont't care about values
			if (localVariableTable.size() == 0) {
				int numberOfPara = targetPath.getbMethod().getNumberOfParameter();

				// 0 is never used
				for (int i = 0; i <= numberOfPara; i++) {
					localVariableTable.add(0);
				}
			}
		}
	}

	public TestPath getTargetPath() {
		return targetPath;
	}

	public void setTargetPath(TestPath targetPath) {
		this.targetPath = targetPath;
	}

	public Dependencies getRelations() {
		return relations;
	}

	public void setRelations(Dependencies relations) {
		this.relations = relations;
	}

	public Stack<String> getIntermediateVariableNameStack() {
		return variableNameStack;
	}

	public void setIntermediateVariableNameStack(Stack<String> tempVariableStack) {
		this.variableNameStack = tempVariableStack;
	}
}
