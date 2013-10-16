package com.gannon.bytecode.controlflowgraph;

import java.util.*;
import com.gannon.jvm.instructions.BPredicateInstruction;

public class CBlocks {
	private String methodName;
	private List<CBlock> blocks = new ArrayList<CBlock>();

	public CBlocks(String methodName) {
		super();
		this.methodName = methodName;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public List<CBlock> getBlocks() {
		return blocks;
	}

	public void setBlocks(List<CBlock> blocks) {
		this.blocks = blocks;
	}

	public boolean add(CBlock blcok) {
		return blocks.add(blcok);
	}

	public void add(int arg0, CBlock arg1) {
		blocks.add(arg0, arg1);
	}

	public void clear() {
		blocks.clear();
	}

	public CBlock get(int block) {
		return blocks.get(block);
	}
	
	public CBlock getLast() {
		return blocks.get(size()-1);
	}

	// find blockID with line number x, where x is in conditional instructions,
	// e.g., goto X, if X
	public int findPredicateInstructionTargtBlockId(
			BPredicateInstruction predicateInstruction) {
		int goToLineNumber = predicateInstruction.getOperand()
				.getGoToLineNumber();
		return findBlockIndexByLineNumber(goToLineNumber);
	}

	public int findBlockIndexByLineNumber(int lineNumber) {
		for (int i = 0; i < blocks.size(); i++) {
			CBlock b = blocks.get(i);
			if (b.containLineNumber(lineNumber)) {
				return i;
			}
		}
		return -1;// not in the block
	}

	public int size() {
		return blocks.size();
	}

	public Set<CNode> convertToSet() {
		int nodeId = 0;
		Set<CNode> nodes = new HashSet<CNode>();
		for (CBlock block : blocks) {
			//nodes.add(new CNode(nodeId++, block));
			nodes.add(new CNode(nodeId++, methodName, block));
		}
		return nodes;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (CBlock b : blocks) {
			sb.append("=========================\n");
			sb.append(b);
		}
		return sb.toString();
	}

}
