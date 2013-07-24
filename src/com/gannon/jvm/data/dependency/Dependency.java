package com.gannon.jvm.data.dependency;

//i1>i2 or i1+i2=i3
import java.util.ArrayList;
import java.util.Arrays;

import com.gannon.jvm.instructions.BInstruction;
import com.gannon.jvm.instructions.BPredicateInstruction;
import com.gannon.jvm.progam.path.PredicateNode;
import com.gannon.jvm.utilities.OpcodeUtility;

public class Dependency {
	private BinNode theBTRootNode;
	private BInstruction inst;

	public Dependency(BinNode theBTRootNode) {
		super();
		this.theBTRootNode = theBTRootNode;
	}

	public Dependency(BinNode theBTRootNode, BInstruction inst) {
		super();
		this.theBTRootNode = theBTRootNode;
		this.inst = inst;
	}

	public BinNode insertToLeft(BinNode myLeftNewNode) {
		if (theBTRootNode != null) {
			theBTRootNode.setLeftBNode(myLeftNewNode);
		}
		return theBTRootNode;
	}

	public BinNode insertToRight(BinNode myRightNewNode) {
		if (theBTRootNode != null) {
			theBTRootNode.setRightBNode(myRightNewNode);
		}
		return theBTRootNode;
	}

	public BinNode getLeftNode() {
		if (theBTRootNode != null) {
			return theBTRootNode.getLeftBNode();
		}
		return null;
	}

	public BinNode getRightNode() {
		if (theBTRootNode != null) {
			return theBTRootNode.getRightBNode();
		}
		return null;
	}

	public boolean isPredicateRelation() {
		return (inst instanceof BPredicateInstruction);
	}

	public boolean isParamterRelation() {
		return Integer.parseInt(theBTRootNode.getLocalVariableName()) < OpcodeUtility.MAX_PARAMETER_ID_ALLOWED;
	}

	// ------------------ InOrder traversal-------------------
	protected void inorder(BinNode theRootNode) {
		if (theRootNode != null) {
			inorder(theRootNode.getLeftBNode());
			theRootNode.show();
			inorder(theRootNode.getRightBNode());
		}
	}

	// calls the method to do in order
	public void inorderBST() {
		inorder(theBTRootNode);
	}

	public void inorderRoot(BinNode theRootNode) {
		inorder(theRootNode);
	}

	// --------------------------------------------------------------

	// ------------------display nicely---------------------------
	public void niceDisplay() {
		traverse(0, theBTRootNode);
	}

	// level is a recursion level, 0 for root
	public void traverse(int level, BinNode theRootNode) {
		// build indent according to the recursion level
		char[] indent = new char[level];
		Arrays.fill(indent, ' ');

		if (theRootNode != null) {
			theRootNode.showIndent(new String(indent));
			traverse(level + 1, theRootNode.getLeftBNode());
			traverse(level + 1, theRootNode.getRightBNode());
		}
	}
	
	
	
	// ------------------display nicely to string---------------------------
	public String toString() {
		StringBuffer sb = new StringBuffer();
		traverse(0, theBTRootNode,sb);
		return sb.toString();
	}

	// level is a recursion level, 0 for root
	public void traverse(int level, BinNode theRootNode, StringBuffer sb) {
		// build indent according to the recursion level
		char[] indent = new char[level];
		Arrays.fill(indent, ' ');

		if (theRootNode != null) {
			sb.append(theRootNode.showIndentString(new String(indent)));
			traverse(level + 1, theRootNode.getLeftBNode(),sb);
			traverse(level + 1, theRootNode.getRightBNode(),sb);
		}
	}

	// -------------------------------------------------------------------

	

	// -------------------------------------------------------------------

	public BinNode getTheBTRootNode() {
		return theBTRootNode;
	}

	public BInstruction getInst() {
		return inst;
	}

	public void setInst(BInstruction inst) {
		this.inst = inst;
	}

	public boolean equals(Object relation){
		if(!(relation instanceof Dependency)){
			return false;
		}
		return theBTRootNode.equals(((Dependency) relation).getTheBTRootNode())
				&&theBTRootNode.getLeftBNode().equals(((Dependency) relation).getTheBTRootNode().getLeftBNode())
			&& theBTRootNode.getRightBNode().equals(((Dependency) relation).getTheBTRootNode().getRightBNode());
	}
	
	public int hashCode() {
		return theBTRootNode.hashCode();
	}
}