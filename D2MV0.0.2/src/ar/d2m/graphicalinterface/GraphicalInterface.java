package ar.d2m.graphicalinterface;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JCheckBox;


import ar.d2m.tools.Tool;

public class GraphicalInterface{
	public static ResourceBundle res = ResourceBundle
			.getBundle("ar.d2m.resources.machines.Mill");
	public FeaturesSettings set;
	public FeaturesSelection sel;
	public Tool tool;
	public JButton generate;
	
	
}
