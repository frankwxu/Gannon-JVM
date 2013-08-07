package com.gannon.bytecode.controlflowgraph;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class CPath {
	private int id;
	private List<CNode> nodes = new LinkedList<CNode>();
	private  Set<CEdge> edges=new HashSet<CEdge>();;
	
	public CPath(int id) {
		super();
		this.id = id;
	}
	
	public void add(CNode node){
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

	
	public int size() {
		return nodes.size();
	}

	public CGraph convertToGraph(){
		Set<CNode> nodeSet=new HashSet<CNode>(nodes);
		
		return new CGraph(nodeSet,edges); 
	}
	
	@Override
	public String toString() {
		StringBuffer sb=new StringBuffer();
		sb.append("CPath [id=" + id  +"]\n"); 
		for(CNode node: nodes){
			sb.append(node);
		}
		return sb.toString();
	}
	
	
}
