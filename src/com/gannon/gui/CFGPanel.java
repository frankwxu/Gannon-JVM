package com.gannon.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.gannon.bytecode.controlflowgraph.CEdgeValue;
import com.gannon.bytecode.controlflowgraph.CGraph;
import com.gannon.bytecode.controlflowgraph.CNode;
import com.gannon.bytecode.controlflowgraph.CPath;
import com.gannon.program.cfg.EdgeData;
import com.gannon.program.cfg.ForceDirectedLayout;
import com.gannon.program.cfg.GraphPanel;
import com.gannon.program.cfg.Node;
import com.gannon.program.cfg.NodeData;



public class CFGPanel extends GraphPanel {

    /**
	 * Control Flow Graph Panel
	 */
	private static final long serialVersionUID = 1L; 
	private final ArrayList<Node> listOfNodes;
    private CGraph cGraph;    // contains list of cfg node
    CNode rootNode;  // root node of the cfg tree

    public Node findNode(int nodeID, String methodName) {
        for (int i = 0; i < listOfNodes.size(); i++) {
            Node node = listOfNodes.get(i);
            if (node.getData().getLabel().equals( String.valueOf(nodeID)) && node.getData().getmName().equals(methodName)) {
                return node;
            }
        }
        return null;
    }

    

    public void drawCFGTree() {
        // getting list of nodes and adding them to the graph
    	LinkedHashSet<CNode> listOfCNodes =cGraph.getCNodes();
        for (Iterator<CNode> iterator = listOfCNodes.iterator(); iterator.hasNext(); ) {
        	CNode nodeToInsert =  iterator.next();
        	//adding tnode to graph panel
            Node newNode = addNode( String.valueOf( nodeToInsert.getId()), nodeToInsert.getMethodName(),Color.BLACK, Color.GREEN, Color.BLUE, 20.0f);
            // adding new node to listOfNodes
            listOfNodes.add(newNode);
        }

        // adding edges between nodes
        for (Iterator<CNode> iterator = listOfCNodes.iterator(); iterator.hasNext(); ) {
        	CNode parentNode =  iterator.next();
            // getting node from listOfNodes
            Node node = findNode(parentNode.getId(), parentNode.getMethodName());

            // getting list of child node for tnode
            List<CNode> listOfChildNodes =  cGraph.getAdjacentNodes(parentNode);// .getAdjacentNodes(tnode);
            
            // processing child nodes
            for (int j = 0; j < listOfChildNodes.size(); j++) {
            	CNode childNode = listOfChildNodes.get(j);
            	
            	// getting edge value
            	CEdgeValue value = cGraph.getCEdgeValue(parentNode, childNode);
            	
            	// getting node from listOfNodes
                Node graphNode = findNode(childNode.getId(), childNode.getMethodName());
                
                CPath longestPath =  cGraph.getLongestPath(parentNode,childNode);
                
                int  lengthOfLongestPath = longestPath.getNodes().size();// cGraph.getLongestPath(childNode, cnode)"path between " + tnode.getName() + " and " + tnode.getLeft().getName() + " is : " + numberOfPaths);
                
                int numberOfPaths  = cGraph.getNumberOfPathsBetweenTwoNodes(parentNode, childNode);
                //System.out.println("Longest path between " + tnode.getName() + " and " + tnode.getLeft().getName() + " is : " + longestPath);
                
               
                if(cGraph.isLoopEdge(parentNode, childNode)){
                    // Finding max path for loop edge
                  //  int longestPathForLoop = cfgTree.findLongestBetweenTwoNodes( childNode,tnode);
                     // adding an  invisible edge between the two nodes
                    addEdgeWithNumberOfInvisibleNodes(node, graphNode, Color.BLUE, Color.RED, 2.0f, true, value,lengthOfLongestPath);
                }
                //  checking if the edge is a branch statement (goto,if-then)
                else if(cGraph.isLoopEdge( childNode,parentNode) && (numberOfPaths > 1))
                {
                    // adding an edge between the two nodes
                    addEdgeWithNumberOfInvisibleNodes(node, graphNode, Color.BLUE, Color.RED, 2.0f, true, value,lengthOfLongestPath);
                    
                }else{
                    addEdge(node, graphNode, Color.BLUE, Color.RED, 2.0f, true, value);
                }   
            }
        }

        // setting root node flag for the root node
        findNode(rootNode.getId(), rootNode.getMethodName()).getData().setIsRoot(true);
    }

    public CFGPanel(CGraph cGraph, int width,int height) {
    	super(new ForceDirectedLayout(), width, height);
    	// initializing graph panel
    	// initializing list of nodes
        this.listOfNodes = new ArrayList<Node>();

        // reading CFG file to get nodes and edges
        this.cGraph = cGraph;
        
        // getting root node
        rootNode = this.cGraph.getRoot();
        

        // drawing CFG
        drawCFGTree();
    }

    public void addEdge(Node node, Node otherNode, Color edgeColor, Color edgeLabelColor, float weight, boolean directed,  CEdgeValue value) {
    	String edgeLabel="";
    	
    	// assigning edge value to label
        if(value != null)
        {
        	 if(value.getExpectedPredicateResult()==1){
        		 edgeLabel = "T";
             }
        	 else  if(value.getExpectedPredicateResult()==0){
        		 edgeLabel = "F";
        	 }
        }
                
    	this.addEdge(node, otherNode,
                new EdgeData().color(edgeColor).labelColor(edgeLabelColor).weight(weight)
                .directional(directed).label(edgeLabel).edgeValue(value));
    }

    public void addEdgeWithNumberOfInvisibleNodes(Node node, Node otherNode, Color edgeColor, Color edgeLabelColor, float weight, boolean directed, CEdgeValue value,int numberOfNodes) {
    	String edgeLabel="";
    	
    	// creating invicible node        
        Node invicibleNode = addInvicibleNode("","", Color.BLACK, Color.BLACK, Color.WHITE, 1);
        
        // assigning edge value to label
        if(value != null)
        {
        	if(value.getExpectedPredicateResult()==1){
       		 edgeLabel = "T";
            }
	       	 else  if(value.getExpectedPredicateResult()==0){
	       		 edgeLabel = "F";
	       	 }
        }
        
        // adding edge between first connected node and the invicible node
        this.addEdge(node, invicibleNode,
                new EdgeData().color(edgeColor).labelColor(edgeLabelColor).weight(weight)
                .directional(false).label(edgeLabel).edgeValue(value));
        
        edgeLabel = "";
        
        for(int i=0;i<numberOfNodes-1;i++){
            Node newInvicibleNode = addInvicibleNode("", "", Color.BLACK, Color.BLACK, Color.WHITE, 1);
            
            // adding edge between the invicible node and the new invisible node 
            this.addEdge(invicibleNode, newInvicibleNode,
                new EdgeData().color(edgeColor).labelColor(edgeLabelColor).weight(weight).directional(false).label(edgeLabel).isInvisible(true).edgeValue(value));
            
            invicibleNode = newInvicibleNode;
        }
        
         // adding edge between the invicible node and the second connected node
        this.addEdge(invicibleNode, otherNode,
                new EdgeData().color(edgeColor).labelColor(edgeLabelColor).weight(weight)
                .directional(directed).label(edgeLabel).edgeValue(value));

    }
    
    public Node addNode(String label, String mName, Color labelColor, Color nodeColor, Color boxColor, float mass) {
        final Node node = this.addNode(new NodeData().label(label).mName(mName).labelColor(labelColor)
                .backgroundColor(nodeColor).boxColor(boxColor).mass(mass));
        return node;
    }

    public Node addInvicibleNode(String label, String mName, Color labelColor, Color nodeColor, Color boxColor, float mass) {
        final Node node = this.addNode(new NodeData().label(label).mName(mName).labelColor(labelColor)
                .backgroundColor(nodeColor).boxColor(boxColor).mass(mass));
        node.getData().setIsInvicible(true);
        return node;
    }
}
