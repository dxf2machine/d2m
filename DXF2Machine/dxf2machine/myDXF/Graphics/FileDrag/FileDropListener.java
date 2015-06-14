package myDXF.Graphics.FileDrag;

public interface FileDropListener extends java.util.EventListener {
	public abstract void filesDropped(FileDropEvent evt);
}
