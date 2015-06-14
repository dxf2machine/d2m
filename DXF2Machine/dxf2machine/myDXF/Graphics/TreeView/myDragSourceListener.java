package myDXF.Graphics.TreeView;

import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;

class myDragSourceListener implements DragSourceListener {
	@Override
	public void dragEnter(DragSourceDragEvent dsde) {
		// System.out.println("myDragSourceListener : drag Enter");
	}

	@Override
	public void dragOver(DragSourceDragEvent dsde) {
		// System.out.println("myDragSourceListener : drag Over");
	}

	@Override
	public void dropActionChanged(DragSourceDragEvent dsde) {
		// System.out.println("myDragSourceListener : drop Action Changed");
	}

	@Override
	public void dragExit(DragSourceEvent dse) {
		// System.out.println("myDragSourceListener : drag Exit");
	}

	@Override
	public void dragDropEnd(DragSourceDropEvent dsde) {
		// System.out.println("myDragSourceListener : dragDropEnd");
	}

}
