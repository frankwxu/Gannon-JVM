package com.gannon.bytecode.controlflowgraph;

import java.util.*;

import com.gannon.asm.components.BMethod;
import com.gannon.jvm.instructions.BInstruction;
import com.gannon.jvm.instructions.BPredicateInstruction;
import com.gannon.jvm.utilities.ConstantsUtility;

/*
 * A wrapper of BMethod
 * It used for building CFG
 */
public class CFGMethod {
	private BMethod bMethod;

	public CFGMethod(BMethod bMethod) {
		super();
		this.bMethod = bMethod;
	}

	public CGraph buildGraph() {
		CBlocks listOfBlock = buildBlocks();
		int edgeId = 0;
		LinkedHashSet<CEdge> edges = new LinkedHashSet<CEdge>();
		LinkedHashSet<CNode> nodes = new LinkedHashSet<CNode>();
		//ArrayList<CEdge> edges = new ArrayList<CEdge>();
		//ArrayList<CNode> nodes = new ArrayList<CNode>();

		// insert the block to be the CFG Starting Node
		listOfBlock.add(0, new CBlock(Integer.MIN_VALUE));
		// adding the CFG Ending Node
		listOfBlock.add(new CBlock(Integer.MAX_VALUE));

		for (int i = 0; i < listOfBlock.size(); i++) {
			CBlock block = listOfBlock.get(i);
			if (block.findIfInstruction() != null) {
				// first edge is next to next if predicate result is false
				CNode sourceNode = new CNode(i, this.bMethod.getName(), listOfBlock.get(i));
				CNode targetNode1 = new CNode(i + 1, this.bMethod.getName(), listOfBlock.get(i + 1));
				nodes.add(sourceNode);
				nodes.add(targetNode1);
				//System.out.println(sourceNode.getBlock().toString());
				//System.out.println(targetNode1.getBlock().toString());
				/*
				CNode sourceNode = CNode.retreaveNode(nodes, i, this.bMethod.getName(),listOfBlock.get(i));
				if (sourceNode == null) {
					sourceNode = new CNode(i, this.bMethod.getName(),listOfBlock.get(i));
					nodes.add(sourceNode);
				}
				System.out.println(sourceNode.getBlock().toString());
				CNode targetNode1 = CNode.retreaveNode(nodes,i + 1, this.bMethod.getName(), listOfBlock.get(i + 1));
				if (targetNode1 == null) {
					targetNode1 = new CNode(i + 1, this.bMethod.getName(), listOfBlock.get(i + 1));
					nodes.add(targetNode1);
				}
				System.out.println(targetNode1.getBlock().toString());
				*/
				CEdge cEdge1 = new CEdge(edgeId++, sourceNode, targetNode1);
				//we save predicated value in CEdges
				CEdgeValue cEdgeValue1 = new CEdgeValue();
				cEdgeValue1.setExpectedPredicateResult(ConstantsUtility.EXPECTED_FALSE);
				cEdge1.setValue(cEdgeValue1);  
				
				edges.add(cEdge1);

				// second edge connects to a block contains the instruction with
				// gotolinenumber if the expected predicate result is true
				int operandLineNumber = block.findIfInstruction().getOperand().getGoToLineNumber();
				int destBlockID = listOfBlock.findBlockIndexByLineNumber(operandLineNumber);
				CNode targetNode2 = new CNode(destBlockID, this.bMethod.getName(), listOfBlock.get(destBlockID));
				nodes.add(targetNode2);
				//System.out.println(targetNode2.getBlock().toString());
				/*
				CNode targetNode2 = CNode.retreaveNode(nodes, destBlockID, this.bMethod.getName(), listOfBlock.get(destBlockID));
				if (targetNode2 == null) {
					targetNode2 =  new CNode(destBlockID, this.bMethod.getName(), listOfBlock.get(destBlockID));
					nodes.add(targetNode2);
				}
				System.out.println(targetNode2.getBlock().toString());
				*/
				CEdge cEdge2 = new CEdge(edgeId++, sourceNode, targetNode2);
				//we save predicated value in CEdges
				CEdgeValue cEdgeValue2 = new CEdgeValue();
				cEdgeValue2.setExpectedPredicateResult(ConstantsUtility.EXPECTED_TRUE);
				cEdge2.setValue(cEdgeValue2);
				edges.add(cEdge2);
			} else if (block.findGotoInstruction() != null) {
				int operandLineNumber = block.findIfInstruction().getOperand().getGoToLineNumber();
				int destBlockID = listOfBlock.findBlockIndexByLineNumber(operandLineNumber);
				CNode sourceNode = new CNode(i, this.bMethod.getName(), listOfBlock.get(i));
				CNode targetNode = new CNode(destBlockID, this.bMethod.getName(), listOfBlock.get(destBlockID));
				nodes.add(sourceNode);
				nodes.add(targetNode);
				//System.out.println(sourceNode.getBlock().toString());
				//System.out.println(targetNode.getBlock().toString());
				/*
				CNode sourceNode = CNode.retreaveNode(nodes, i, this.bMethod.getName(), listOfBlock.get(i));
				if (sourceNode ==  null) {
					sourceNode = new CNode(i, this.bMethod.getName(), listOfBlock.get(i));
					nodes.add(sourceNode);
				}
				System.out.println(sourceNode.getBlock().toString());
				CNode targetNode = CNode.retreaveNode(nodes, destBlockID, this.bMethod.getName(), listOfBlock.get(destBlockID));
				if (targetNode == null) {
					targetNode = new CNode(destBlockID, this.bMethod.getName(), listOfBlock.get(destBlockID));
					nodes.add(targetNode);
				}
				System.out.println(targetNode.getBlock().toString());
				*/
				edges.add(new CEdge(edgeId++, sourceNode, targetNode));
			} else if (block.findReturnInstruction() != null) {
				// connect to ending node if there is a return instruction
				CNode sourceNode = new CNode(i, this.bMethod.getName(), listOfBlock.get(i));
				CNode endingNode = new CNode(listOfBlock.size() - 1, this.bMethod.getName(), listOfBlock.getLast());
				nodes.add(sourceNode);
				nodes.add(endingNode);
				//System.out.println(sourceNode.getBlock().toString());
				//System.out.println(endingNode.getBlock().toString());
				/*
				CNode sourceNode = CNode.retreaveNode(nodes, i, this.bMethod.getName(), listOfBlock.get(i));
				if (sourceNode == null) {
					sourceNode = new CNode(i, this.bMethod.getName(), listOfBlock.get(i));
					nodes.add(sourceNode);
				}
				System.out.println(sourceNode.getBlock().toString());
				CNode endingNode = CNode.retreaveNode(nodes, listOfBlock.size() - 1, this.bMethod.getName(), listOfBlock.getLast());
				if (endingNode ==  null) {
					endingNode = new CNode(listOfBlock.size() - 1, this.bMethod.getName(), listOfBlock.getLast());
					nodes.add(endingNode);
				}
				System.out.println(endingNode.getBlock().toString());
				*/
				edges.add(new CEdge(edgeId++, sourceNode, endingNode));

			} else if (i < listOfBlock.size() - 1) {
				// connect to next block if this block doesn't have all above
				// instructions and not reach the end of the block
				CNode sourceNode = new CNode(i, this.bMethod.getName(), listOfBlock.get(i));
				CNode targetNode1 = new CNode(i + 1, this.bMethod.getName(), listOfBlock.get(i + 1));
				nodes.add(sourceNode);
				nodes.add(targetNode1);
				//System.out.println(sourceNode.getBlock().toString());
				//System.out.println(targetNode1.getBlock().toString());
				/*
				CNode sourceNode = CNode.retreaveNode(nodes, i, this.bMethod.getName(), listOfBlock.get(i));
				if (sourceNode == null) {
					sourceNode = new CNode(i, this.bMethod.getName(), listOfBlock.get(i));
					nodes.add(sourceNode);
				}
				System.out.println(sourceNode.getBlock().toString());
				CNode targetNode1 = CNode.retreaveNode(nodes, i + 1, this.bMethod.getName(), listOfBlock.get(i + 1));
				if (targetNode1 == null) {
					targetNode1 = new CNode(i + 1, this.bMethod.getName(), listOfBlock.get(i + 1));
					nodes.add(targetNode1);
				}
				System.out.println(targetNode1.getBlock().toString());
				*/
				edges.add(new CEdge(edgeId++, sourceNode, targetNode1));
			}

		}

		// now we need to add start and end nodes
		//return new CGraph(listOfBlock.convertToSet(), edges);
		return new CGraph(nodes, edges);
	}

	public CBlocks buildBlocks() {
		int blockID = 0;
		CBlocks blocks = new CBlocks(bMethod.getName());
		boolean blockLeader[] = computeLeadingLineFlags();
		ArrayList<BInstruction> instructions = bMethod.getInstructions();
		CBlock block = new CBlock(bMethod.getName(), blockID++);

		for (int j = 0; j < blockLeader.length; j++) {
			if (blockLeader[j]) {
				System.out.println(block.toString());
				blocks.add(block);
				block = new CBlock(bMethod.getName(), blockID++);
			}
			block.addInstruction(instructions.get(j));
		}
		System.out.println(block.toString());
		blocks.add(block);
		return blocks; 
	}

	// return a flag array to indicate if an instruction is a leader instruction
	// of a block
	public boolean[] computeLeadingLineFlags() {
		ArrayList<BInstruction> instrList = bMethod.getInstructions();
		boolean leaders[] = new boolean[instrList.size()];

		for (int i = 0; i < instrList.size(); i++) {
			BInstruction instr = instrList.get(i);
			String opcode = instr.getOpCodeCommand();

			// rule 1: first instruction is a leader, however, for
			// implementation purpose, we omit it
			// if (i == 0) {
			// leaders[i] = true;
			// }

			// rule 2: Each instruction that is the target of a conditional "if"
			// /unconditional "goto" branch is a leader.
			// and its next line is a leader
			if (opcode.contains("if") || opcode.contains("goto")) {
				// this line is a leader so the if statement is a block
				leaders[i] = true;
				// next line is a leader
				leaders[i + 1] = true;
				// target line number is a leader
				if (instr instanceof BPredicateInstruction) {
					int targetLineNumber = ((BPredicateInstruction) instr).getOperand().getGoToLineNumber();
					leaders[targetLineNumber - 1] = true;
				}
			}

			// rule 3: Each instruction that immediately follows a
			// <T>return(¡°ireturn¡±,
			// ¡°return¡±) a is a leader.
			if (opcode.contains("return")) {
				leaders[i - 1] = true;
			}
			if (opcode.contains("invoke")) {
				leaders[i] = true;
				leaders[i + 1] = true;
			}
		}
		return leaders;
	}

	public void displayLeadingFlags(boolean[] computeLeadingLineFlags) {
		for (int i = 0; i < computeLeadingLineFlags.length; i++) {
			System.out.println("Line Number " + (i + 1) + " Flag: " + computeLeadingLineFlags[i] + "\n ");
		}
	}

}
