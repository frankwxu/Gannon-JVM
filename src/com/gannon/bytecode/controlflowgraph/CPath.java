package com.gannon.bytecode.controlflowgraph;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;

import com.gannon.jvm.instructions.BInstruction;
import com.gannon.jvm.utilities.ConstantsUtility;

//a CPath is generated from CFG
public class CPath {
	private int id;
	private List<CNode> nodes = new LinkedList<CNode>();
	//store expected predicate result, such as True or False
	//its value is already set when we build the path
	private LinkedHashSet<CEdge> edges = new LinkedHashSet<CEdge>();;

	public CPath(int id) {
		super();
		this.id = id; 
	}

	public void add(CNode node) {
		nodes.add(node);
	}

	public boolean add(CEdge arg0) {
		return edges.add(arg0);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<CNode> getNodes() {
		return nodes;
	}

	public void setNodes(ArrayList<CNode> nodes) {
		this.nodes = nodes;
	}

	public void addNodes(CNode nodes) {
		this.nodes.add(nodes);
	}

	public LinkedHashSet<CEdge> getEdges() {
		return edges;
	}

	public void LinkedHashSet(LinkedHashSet<CEdge> edges) {
		this.edges = edges;
	}

	public int size() {
		return nodes.size();
	}
	

	// not used and tested, only implemented IF statement. 
	// we need to find expected predicate value from edge for a given instruction
	public int findExpectedPredicateValue(BInstruction inst){
		for(CEdge edge: edges){
			BInstruction ifInstruction = edge.getSource().getBlock().getIFInstruction();
			if(ifInstruction!=null && ifInstruction.equals(inst)){
				return edge.getValue().getExpectedPredicateResult();
			}
		}
		return ConstantsUtility.UNDEFINED_EXPECTED_VALUE;
	}

	public CGraph convertToGraph() {
		LinkedHashSet<CNode> nodeSet = new LinkedHashSet<CNode>(nodes);
		return new CGraph(nodeSet, edges);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nodes == null) ? 0 : nodes.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CPath other = (CPath) obj;
		if (nodes == null) {
			if (other.nodes != null)
				return false;
		}
		for (int i = 0; i < nodes.size(); i++) {
			if (!nodes.get(i).equals(((CPath) obj).getNodes().get(i))) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("CPath [id=" + id + "]\n");
		for (CNode node : nodes) {
			sb.append(node);
		}

		for (CEdge edge : edges) {
			if (edge.getValue().getExpectedPredicateResult() != ConstantsUtility.UNDEFINED_EXPECTED_VALUE) {
				sb.append("\nEdge value: [" + edge.toString() + " Expected PredicateResult="
						+ edge.getValue().getExpectedPredicateResult() + "]");
			}
		}
		return sb.toString();
	}

}
