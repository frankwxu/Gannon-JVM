package com.gannon.gui;

import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.gannon.asm.classgenerator.BClassGenerator;
import com.gannon.asm.components.BClass;
import com.gannon.asm.components.BMethod;
import com.gannon.bytecode.controlflowgraph.BuildCFG;
import com.gannon.bytecode.controlflowgraph.CFGMethod;
import com.gannon.bytecode.controlflowgraph.CGraph;
import com.gannon.bytecode.controlflowgraph.CPath;
import com.gannon.jvm.data.input.Input;
import com.gannon.jvm.input.generator.GannonInputGeneratorJVM;
import com.gannon.jvm.progam.path.TestPath;
import com.gannon.jvm.utilities.ConstantsUtility;

public class MethodTreePanel extends JScrollPane implements TreeSelectionListener, MouseListener {
	/**
	 * Navigation panel
	 */
	private static final long serialVersionUID = 1L;
	private static final String COM_GANNON_IMAGES16X16_PATH_PNG = "/com/gannon/images16x16/path.png";
	private static final String COM_GANNON_IMAGES16X16_FLOW_CHART_PNG = "/com/gannon/images16x16/flow_chart.png";
	private static final String COM_GANNON_IMAGES16X16_DATA_GENERATION_PNG = "/com/gannon/images16x16/data_generation.png";
	private static final String COM_GANNON_IMAGES16X16_PATH_INSTRUCTION_PNG = "/com/gannon/images16x16/path_instruction.png";
	private static final String COM_GANNON_IMAGES16X16_METHOD_INSTRUCTION_PNG = "/com/gannon/images16x16/method_instruction.png";

	protected DefaultMutableTreeNode rootNode;
	protected DefaultTreeModel treeModel;
	protected JTree tree;
	private String rootName;
	private Main mainFrame;
	private BClass myclass;
	private DefaultMutableTreeNode clickedTreeNode;

	public MethodTreePanel() {
		super();
	}

	public MethodTreePanel(Main mainFrame, String rootName) {
		this.mainFrame = mainFrame;
		this.rootName = rootName;
		myclass = BClassGenerator.getBClass(rootName);
		rootNode = new DefaultMutableTreeNode(rootName);
		treeModel = new DefaultTreeModel(rootNode);
		treeModel.addTreeModelListener(new MethodTreeModelListener());
		tree = new JTree(treeModel);
		tree.setEditable(false);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setShowsRootHandles(true);
		tree.addTreeSelectionListener(this);
		// tree node right click
		tree.addMouseListener(this);

		add(tree);
		setViewportView(tree);
	}

	/** Remove all nodes except the root node. */
	public void clear() {
		rootNode.removeAllChildren();
		treeModel.reload();
	}

	/** Remove the currently selected node. */
	public void removeCurrentNode() {
		TreePath currentSelection = tree.getSelectionPath();
		if (currentSelection != null) {
			DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (currentSelection.getLastPathComponent());
			MutableTreeNode parent = (MutableTreeNode) (currentNode.getParent());
			if (parent != null) {
				treeModel.removeNodeFromParent(currentNode);
				return;
			}
		}
	}

	/** Add child to the currently selected node. */
	public DefaultMutableTreeNode addObject(Object child) {
		DefaultMutableTreeNode parentNode = null;
		TreePath parentPath = tree.getSelectionPath();

		if (parentPath == null) {
			parentNode = rootNode;
		} else {
			parentNode = (DefaultMutableTreeNode) (parentPath.getLastPathComponent());
		}

		return addObject(parentNode, child, true);
	}

	public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child) {
		return addObject(parent, child, false);
	}

	public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent, Object child, boolean shouldBeVisible) {
		DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);

		if (parent == null) {
			parent = rootNode;
		}

		// It is key to invoke this on the TreeModel, and NOT
		// DefaultMutableTreeNode
		treeModel.insertNodeInto(childNode, parent, parent.getChildCount());

		// Make sure the user can see the lovely new node.
		if (shouldBeVisible) {
			tree.scrollPathToVisible(new TreePath(childNode.getPath()));
		}
		return childNode;
	}

	class MethodTreeModelListener implements TreeModelListener {
		@Override
		public void treeNodesChanged(TreeModelEvent e) {
			DefaultMutableTreeNode node;
			node = (DefaultMutableTreeNode) (e.getTreePath().getLastPathComponent());

			/*
			 * If the event lists children, then the changed node is the child
			 * of the node we've already gotten. Otherwise, the changed node and
			 * the specified node are the same.
			 */

			int index = e.getChildIndices()[0];
			node = (DefaultMutableTreeNode) (node.getChildAt(index));

			System.out.println("The user has finished editing the node.");
			System.out.println("New value: " + node.getUserObject());
		}

		@Override
		public void treeNodesInserted(TreeModelEvent e) {
		}

		@Override
		public void treeNodesRemoved(TreeModelEvent e) {
		}

		@Override
		public void treeStructureChanged(TreeModelEvent e) {
		}
	}

	public String getClassName() {
		return rootName;
	}

	public void setClassName(String className) {
		this.rootName = className;
	}

	@Override
	public void valueChanged(TreeSelectionEvent arg0) {
		clickedTreeNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (myclass != null) {
			if (tree.getSelectionPath().getPathCount() == ConstantsUtility.METHOD_LEVEL) {
				BMethod selectedMethod = myclass.getMethod(clickedTreeNode.toString());

				showMethodInstructionsTab(selectedMethod);

				showInputTablePanel(selectedMethod);

			} else if (tree.getSelectionPath().getPathCount() == ConstantsUtility.TESTPATH_LEVEL) {
				// get current method
				DefaultMutableTreeNode selectedMethodNode = (DefaultMutableTreeNode) tree.getSelectionPath()
						.getParentPath().getLastPathComponent();

				showPathInstructionTab(selectedMethodNode, clickedTreeNode);
				showConsolePanel(selectedMethodNode, clickedTreeNode);
			}
		}
	}

	private void showConsolePanel(DefaultMutableTreeNode selectedMethodNode, DefaultMutableTreeNode clickedTreeNode) {
		BMethod selectedMethod = myclass.getMethod(selectedMethodNode.toString());
		CFGMethod cfgMethod = new CFGMethod(selectedMethod);
		int selectedPathID = Integer.parseInt(clickedTreeNode.toString());
		CPath selectedPath = cfgMethod.buildGraph().computeAllCPaths().findPath(selectedPathID);
		// log
		mainFrame.txtrConsole.append("\nClick path: " + selectedPathID + " in method " + selectedMethodNode.toString());
		mainFrame.txtrConsole.append("\n" + selectedPath);
	}

	// ====Implementing MouseListener for right clicking the tree=
	@Override
	public void mouseClicked(MouseEvent e) {
		// right click pop menu
		if (SwingUtilities.isRightMouseButton(e)) {
			TreePath path = tree.getPathForLocation(e.getX(), e.getY());
			Rectangle pathBounds = tree.getUI().getPathBounds(tree, path);
			if (pathBounds != null && pathBounds.contains(e.getX(), e.getY())) {
				pathBounds.setFrame(pathBounds);
				if (path.getPathCount() == ConstantsUtility.METHOD_LEVEL) {
					final DefaultMutableTreeNode clickedMethodNode = (DefaultMutableTreeNode) path
							.getLastPathComponent();
					JPopupMenu methodPopMenu = new JPopupMenu();
					JMenuItem CFGMenuItem = new JMenuItem("Generating CFG");
					CFGMenuItem.setIcon(new ImageIcon(Main.class
							.getResource(COM_GANNON_IMAGES16X16_FLOW_CHART_PNG)));
					CFGMenuItem.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							showCFGTab(clickedMethodNode);
						}
					});
					
					JMenuItem CFGMenuItem2 = new JMenuItem("Resultant CFG");
					CFGMenuItem2.setIcon(new ImageIcon(Main.class
							.getResource(COM_GANNON_IMAGES16X16_FLOW_CHART_PNG)));
					CFGMenuItem2.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							showResultantCFGTab(clickedMethodNode);
						}
					});

					methodPopMenu.add(CFGMenuItem);
					methodPopMenu.add(CFGMenuItem2);
					methodPopMenu.show(tree, e.getX(), e.getY());

				} else if (path.getPathCount() == ConstantsUtility.TESTPATH_LEVEL) {
					// get current method
					final DefaultMutableTreeNode selectedMethodNode = (DefaultMutableTreeNode) path.getParentPath()
							.getLastPathComponent();
					final DefaultMutableTreeNode clickedPathIDNode = (DefaultMutableTreeNode) path
							.getLastPathComponent();

					JPopupMenu menu = new JPopupMenu();
					JMenuItem inputGenerationMenuItem = new JMenuItem("Generating Inputs");
					inputGenerationMenuItem.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							showGeneraedInputsTab(selectedMethodNode, clickedPathIDNode);
						}
					});

					JMenuItem pathVisulMenuItem = new JMenuItem("Path Visualization");
					pathVisulMenuItem.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent arg0) {
							showPathGraphTab(selectedMethodNode, clickedPathIDNode);
						}
					});

					inputGenerationMenuItem.setIcon(new ImageIcon(Main.class
							.getResource(COM_GANNON_IMAGES16X16_DATA_GENERATION_PNG)));
					pathVisulMenuItem.setIcon(new ImageIcon(Main.class
							.getResource(COM_GANNON_IMAGES16X16_PATH_PNG)));
					menu.add(inputGenerationMenuItem);
					menu.add(pathVisulMenuItem);
					menu.show(tree, e.getX(), e.getY());
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		//show the the selected path
		TreePath path = tree.getPathForLocation(e.getX(), e.getY());
		if (path != null) {
			tree.setSelectionPath(path);
		}
	}

	// =============== all tabs are here===

	private void showPathInstructionTab(DefaultMutableTreeNode selectedMethodNode,
			DefaultMutableTreeNode clickedTreeNode) {
		BMethod selectedMethod = myclass.getMethod(selectedMethodNode.toString());
		// get pathId from user click
		int selectedPathID = Integer.parseInt(clickedTreeNode.toString());

		CFGMethod cfgMethod = new CFGMethod(selectedMethod);
		CPath selectedPath = cfgMethod.buildGraph().computeAllCPaths().findPath(selectedPathID);

		Icon icon = new ImageIcon(Main.class.getResource(COM_GANNON_IMAGES16X16_PATH_INSTRUCTION_PNG));
		TabComponent.addClosableTab(mainFrame.tabbedPane, mainFrame.scrollPaneIPathInstruction, "Path Instructions",
				icon);

		JTextArea txtrInstructionarea = new JTextArea();
		txtrInstructionarea.setFont(new Font("CourierNew", Font.PLAIN, 12));
		txtrInstructionarea.setText("");
		TestPath testPath = new TestPath(selectedPath);
		txtrInstructionarea.append(testPath.toString());
		mainFrame.scrollPaneIPathInstruction.setViewportView(txtrInstructionarea);
	}

	private void showInputTablePanel(BMethod selectedMethod) {
		// display input table panel
		InputTablePanel inputTablePanel = new InputTablePanel(mainFrame, selectedMethod);
		mainFrame.outputPanel.removeAll();
		mainFrame.outputPanel.add(inputTablePanel);
	}

	private void showMethodInstructionsTab(BMethod selectedMethod) {
		Icon icon = new ImageIcon(Main.class.getResource(COM_GANNON_IMAGES16X16_METHOD_INSTRUCTION_PNG));
		TabComponent.addClosableTab(mainFrame.tabbedPane, mainFrame.scrollPaneInstruction, "Method Instructions", icon);

		JTextArea txtrInstructionarea = new JTextArea();
		txtrInstructionarea.setFont(new Font("CourierNew", Font.PLAIN, 12));
		txtrInstructionarea.setText("");
		txtrInstructionarea.append(selectedMethod.toString());
		mainFrame.scrollPaneInstruction.setViewportView(txtrInstructionarea);
	}

	private void showCFGTab(final DefaultMutableTreeNode clickedMethodNode) {
		BMethod selectedMethod = myclass.getMethod(clickedMethodNode.toString());

		Icon icon = new ImageIcon(Main.class.getResource(COM_GANNON_IMAGES16X16_FLOW_CHART_PNG));
		TabComponent.addClosableTab(mainFrame.tabbedPane, mainFrame.scrollPaneCFG, "CFG", icon);

		CFGMethod m = new CFGMethod(selectedMethod);
		CGraph buildGraph = m.buildGraph();
		// need to change this
		buildGraph.processDominatorNodes();
		// switch tab
		mainFrame.scrollPaneCFG.setViewportView(new CFGPanel(buildGraph, 200, 400));
	}
	
	private void showResultantCFGTab(final DefaultMutableTreeNode clickedMethodNode) {
		BMethod selectedMethod = myclass.getMethod(clickedMethodNode.toString());

		Icon icon = new ImageIcon(Main.class.getResource(COM_GANNON_IMAGES16X16_FLOW_CHART_PNG));
		TabComponent.addClosableTab(mainFrame.tabbedPane, mainFrame.scrollPaneCFG, "CFG", icon);

		//CFGMethod m = new CFGMethod(selectedMethod);
		BuildCFG m = new BuildCFG(myclass.getMethods(), selectedMethod);
		//CGraph buildGraph = m.buildGraph();
		CGraph buildGraph = m.getResultGraph();
		// need to change this
		buildGraph.processDominatorNodes();
		// switch tab
		mainFrame.scrollPaneCFG.setViewportView(new CFGPanel(buildGraph, 200, 400));
	}

	private void showGeneraedInputsTab(final DefaultMutableTreeNode selectedMethodNode,
			final DefaultMutableTreeNode clickedPathIDNode) {
		final BMethod selectedMethod = myclass.getMethod(selectedMethodNode.toString());

		// get pathId from user click

		int selectedPathID = Integer.parseInt(clickedPathIDNode.toString());

		// get Path
		CFGMethod cfgMethod = new CFGMethod(selectedMethod);
		final CPath selectedPath = cfgMethod.buildGraph().computeAllCPaths().findPath(selectedPathID);
		final TestPath testPath = new TestPath(selectedPath);
		// we don't need to hard code here.
		Input input = new Input(1, hardCodeInput());

		testPath.setbClass(myclass);
		testPath.setbMethod(selectedMethod);

		GannonInputGeneratorJVM executor = new GannonInputGeneratorJVM();
		int numberOfResultsNeeded = 100;
		Set<Input> results = executor.run(testPath, input, numberOfResultsNeeded);
		GeneratedInputsArea generatedInputsArea = new GeneratedInputsArea(results);

		Icon icon = new ImageIcon(Main.class.getResource(COM_GANNON_IMAGES16X16_DATA_GENERATION_PNG));
		TabComponent
				.addClosableTab(mainFrame.tabbedPane, mainFrame.scrollPaneGeneratedInputs, "Generated Inputs", icon);
		mainFrame.scrollPaneGeneratedInputs.setViewportView(generatedInputsArea);
	}

	private ArrayList<Object> hardCodeInput() {
		ArrayList<Object> inputs = new ArrayList<Object>();
		inputs.add(-1);
		inputs.add(111);
		inputs.add(6565);
		inputs.add(667);
		return inputs;
	}

	private void showPathGraphTab(final DefaultMutableTreeNode selectedMethodNode,
			final DefaultMutableTreeNode clickedPathIDNode) {
		final BMethod selectedMethod = myclass.getMethod(selectedMethodNode.toString());

		// get pathId from user click

		int selectedPathID = Integer.parseInt(clickedPathIDNode.toString());

		// get Path
		CFGMethod cfgMethod = new CFGMethod(selectedMethod);
		final CPath selectedPath = cfgMethod.buildGraph().computeAllCPaths().findPath(selectedPathID);
		// covert to the path to a graph so it can be
		// displayed
		CGraph pathGraph = selectedPath.convertToGraph();

		pathGraph.processDominatorNodes();

		Icon icon = new ImageIcon(Main.class.getResource(COM_GANNON_IMAGES16X16_PATH_PNG));
		TabComponent.addClosableTab(mainFrame.tabbedPane, mainFrame.scrollPanePath, "Path Graph", icon);
		mainFrame.scrollPanePath.setViewportView(new CFGPanel(pathGraph, 200, 400));
	}

}
