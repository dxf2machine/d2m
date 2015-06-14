package myDXF.Graphics.TreeView;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.dnd.Autoscroll;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DropTarget;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Vector;

import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.ToolTipManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import myDXF.myUnivers;
import myDXF.Entities.myEntity;
import myDXF.Graphics.myCanvas;
import myDXF.Graphics.myCoord;
import myDXF.Graphics.myLabel;
import myDXF.Header.myHeader;
import myDXF.Header.myLayer;
import myDXF.Header.myLineType;
import myDXF.Header.myTable;

public class myJTree extends JTree implements Autoscroll {

	private static final long serialVersionUID = 6881617688450744514L;

	DropTarget dropTarget;
	DragSource dragSource;
	myDragGestureListener dragGestureListener;
	myDropTargetListener dropTargetListener;
	myDragSourceListener dragSourceListener;

	private int margin = 12;

	public myCanvas _refCanva = null;

	public myJTree(myCanvas mc) {
		_refCanva = mc;

		setDragEnabled(true);

		dropTargetListener = new myDropTargetListener(this);
		dropTarget = new DropTarget(this, dropTargetListener);
		dragSource = new DragSource();
		dragGestureListener = new myDragGestureListener();
		dragSourceListener = new myDragSourceListener();
		dragSource.createDefaultDragGestureRecognizer(myCanvas._dxf.tree,
				java.awt.dnd.DnDConstants.ACTION_MOVE, dragGestureListener);

		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				DefaultMutableTreeNode node = null;

				// Précaution !!!
				if (myCanvas._dxf.treeMenu._mc == null) {
					myCanvas._dxf.treeMenu._mc = _refCanva;
				}

				if (SwingUtilities.isRightMouseButton(e)) {
					TreePath selPath = myCanvas._dxf.tree.getPathForLocation(
							e.getX(), e.getY());
					if (selPath == null)
						return;
					node = (DefaultMutableTreeNode) selPath
							.getLastPathComponent();
					if (node == null)
						return;

					setPath(node);

					myCanvas._dxf.treeMenu.show((Component) e.getSource(),
							e.getX(), e.getY(), node.getUserObject());
				}
			}
		});
		setScrollsOnExpand(true);
		setDoubleBuffered(true);
		setDragEnabled(true);

		ToolTipManager.sharedInstance().registerComponent(this);
		updateSelection();
	}

	@Override
	public boolean isPathEditable(TreePath path) {
		if (isEditable()) {
			return getModel().isLeaf(path.getLastPathComponent());
		}
		return false;
	}

	public void createNodes() {

		this.removeAll();
		if ((_refCanva == null) || (myCanvas._dxf == null)
				|| (myCanvas._dxf._u == null)) {
			return;
		}

		try {
			setShowsRootHandles(false);
			setModel(new myTreeModel(myCanvas._dxf._u.getNode()));
			getSelectionModel().setSelectionMode(
					TreeSelectionModel.SINGLE_TREE_SELECTION);
			setCellRenderer(new myTreeRenderer());// Icones
			setEditable(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setPath(DefaultMutableTreeNode node) {
		TreePath tp = new TreePath(node.getPath());
		setSelectionPath(tp);
		scrollPathToVisible(tp);
	}

	public class myTreeModel extends DefaultTreeModel {

		private static final long serialVersionUID = -4842640805882215970L;

		public myTreeModel(TreeNode root) {
			super(root);
		}

		@Override
		public void valueForPathChanged(TreePath path, Object newValue) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) path
					.getLastPathComponent();
			Object obj = null;

			if (node.isRoot()) {
				obj = (node.getUserObject());

				myCanvas._dxf.treeMenu.saveAsDXF();
				super.valueForPathChanged(path, obj);
				((DefaultTreeModel) getModel()).nodeStructureChanged(node);
				updateSelection();
				return;
			}

			if (((DefaultMutableTreeNode) node.getParent()).getUserObject() instanceof myUnivers) {
				obj = ((myUnivers) ((DefaultMutableTreeNode) node.getParent())
						.getUserObject()).getNewLabel(node, newValue);
				if (obj != null) {
					super.valueForPathChanged(path, obj);

					node.setUserObject(obj);
					((DefaultTreeModel) getModel()).nodeStructureChanged(node);
					updateSelection();
				}
			} else if (((DefaultMutableTreeNode) node.getParent())
					.getUserObject() instanceof myHeader) {
				obj = ((myHeader) ((DefaultMutableTreeNode) node.getParent())
						.getUserObject()).getNewLabel(node, newValue);
				super.valueForPathChanged(path, obj);

				node.setUserObject(obj);
				((DefaultTreeModel) getModel()).nodeStructureChanged(node);
				updateSelection();
			} else if (((DefaultMutableTreeNode) node.getParent())
					.getUserObject() instanceof myEntity) {

				if (node.getParent() == null
						|| ((DefaultMutableTreeNode) node.getParent())
								.getUserObject() == null)
					obj = null;
				else
					obj = ((myEntity) ((DefaultMutableTreeNode) node
							.getParent()).getUserObject()).getNewLabel(
							((myLabel) node.getUserObject())._code, newValue);
				super.valueForPathChanged(path, obj);

				node.setUserObject(obj);
				((DefaultTreeModel) getModel()).nodeStructureChanged(node);
				updateSelection(node);
			} else if (((DefaultMutableTreeNode) node.getParent())
					.getUserObject() instanceof myLineType) {
				obj = ((myLineType) ((DefaultMutableTreeNode) node.getParent())
						.getUserObject()).getNewLabel(node, newValue);
				super.valueForPathChanged(path, obj);

				node.setUserObject(obj);
				((DefaultTreeModel) getModel()).nodeStructureChanged(node);
				updateSelection();
			}
		}
	}

	// the method of saving the tree's initial expanded state
	private TreePath[] saveExpandedState(DefaultMutableTreeNode node) {
		Vector<Object> list = findExpanded(node);
		return list.toArray(new TreePath[1]);
	}

	// the recursive method for traversing the tree
	private Vector<Object> findExpanded(DefaultMutableTreeNode node) {
		Vector<Object> list = new Vector<Object>();
		TreeModel treeModel = getModel();
		TreePath treePath = new TreePath(
				((DefaultTreeModel) treeModel).getPathToRoot(node));
		if (isExpanded(treePath)) {
			list.add(treePath);
		}

		for (int i = 0; i < node.getChildCount(); i++) {
			DefaultMutableTreeNode child = (DefaultMutableTreeNode) node
					.getChildAt(i);
			if (!child.isLeaf()) {
				list.addAll(findExpanded(child));
			}
		}

		return list;
	}

	private void openPath(TreePath[] treePaths) {
		if (treePaths != null) {
			for (int i = 0; i < treePaths.length; i++) {
				expandPath(treePaths[i]);
			}
		}
	}

	public void updateSelection(DefaultMutableTreeNode node) {
		updateEnv();

		TreePath[] expandedState = null;
		if (node == null) {
			expandedState = getSelectionPaths();
		} else {
			((DefaultTreeModel) getModel()).nodeStructureChanged(node);
			expandedState = saveExpandedState(node);
		}
		openPath(expandedState);
	}

	public void updateSelection() {
		updateSelection((DefaultMutableTreeNode) getLastSelectedPathComponent());
	}

	public void addEntity(myEntity obj) {

		DefaultMutableTreeNode child1 = null, child2 = null, child3 = null, node = null;

		if (getSelectionPath() == null)
			node = (DefaultMutableTreeNode) ((DefaultTreeModel) getModel())
					.getRoot();
		else
			node = (DefaultMutableTreeNode) ((DefaultMutableTreeNode) getSelectionPath()
					.getLastPathComponent()).getRoot();

		for (int i = 0; i < node.getChildCount(); i++) {
			child1 = (DefaultMutableTreeNode) node.getChildAt(i);
			if (child1.getUserObject() instanceof myLabel) {
				if (((myLabel) child1.getUserObject())._code
						.equals(myLabel.LST_TABLE)) {
					for (int j = 0; j < child1.getChildCount(); j++) {
						child2 = ((DefaultMutableTreeNode) child1.getChildAt(j));
						if (child2.getUserObject() instanceof myTable)
							for (int k = 0; k < child2.getChildCount(); k++) {
								child3 = ((DefaultMutableTreeNode) child2
										.getChildAt(k));
								if (child3.getUserObject() instanceof myLayer
										&& ((myLayer) child3.getUserObject())
												.equals(myCanvas._dxf._u.currLayer)) {
									child3.insert(obj.getNode(),
											child3.getChildCount());
									updateSelection(child3);
									return;
								}
							}
					}
				}
			}
		}

	}

	public void updateEnv() {
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) ((DefaultTreeModel) this
				.getModel()).getRoot();

		if (root.getChildCount() == 9) {
			root.setUserObject(myCanvas._dxf._u);
			((DefaultMutableTreeNode) root.getChildAt(0)).setUserObject(myCoord
					.getLabelRatio());
			((DefaultMutableTreeNode) root.getChildAt(1)).setUserObject(myCoord
					.getLabelX());
			((DefaultMutableTreeNode) root.getChildAt(2)).setUserObject(myCoord
					.getLabelY());
			((DefaultMutableTreeNode) root.getChildAt(3))
					.setUserObject(new myLabel(myLabel.RATIOSTEP, String
							.valueOf(myCoord.ratioStep)));

			if (myCanvas._dxf._u.currLayer != null)
				((DefaultMutableTreeNode) root.getChildAt(4))
						.setUserObject(new myLabel(myLabel.CUR_LAYER, String
								.valueOf(myCanvas._dxf._u.currLayer._nom)));

			if (myCanvas._dxf._u.currBlock != null)
				((DefaultMutableTreeNode) root.getChildAt(5))
						.setUserObject(new myLabel(myLabel.CUR_BLOCK,
								myCanvas._dxf._u.currBlock._name));

			((DefaultTreeModel) getModel()).nodeStructureChanged(root
					.getChildAt(0));
			((DefaultTreeModel) getModel()).nodeStructureChanged(root
					.getChildAt(1));
			((DefaultTreeModel) getModel()).nodeStructureChanged(root
					.getChildAt(2));
			((DefaultTreeModel) getModel()).nodeStructureChanged(root
					.getChildAt(3));
			((DefaultTreeModel) getModel()).nodeStructureChanged(root
					.getChildAt(4));
			((DefaultTreeModel) getModel()).nodeStructureChanged(root
					.getChildAt(5));
		}

		return;
	}

	@Override
	public void autoscroll(Point p) {
		int realrow = getRowForLocation(p.x, p.y);
		Rectangle outer = getBounds();
		realrow = (p.y + outer.y <= margin ? realrow < 1 ? 0 : realrow - 1
				: realrow < getRowCount() - 1 ? realrow + 1 : realrow);
		scrollRowToVisible(realrow);
	}

	@Override
	public Insets getAutoscrollInsets() {
		Rectangle outer = getBounds();
		Rectangle inner = getParent().getBounds();
		return new Insets(inner.y - outer.y + margin, inner.x - outer.x
				+ margin, outer.height - inner.height - inner.y + outer.y
				+ margin, outer.width - inner.width - inner.x + outer.x
				+ margin);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

	}

	class myDragGestureListener implements DragGestureListener {

		@Override
		public void dragGestureRecognized(DragGestureEvent dge) {
			try {
				dge.startDrag(
						DragSource.DefaultMoveNoDrop,
						((myTreeRenderer) myCanvas._dxf.tree.getCellRenderer()),
						dragSourceListener);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
