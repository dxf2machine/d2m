package ar.d2m.graphicalinterface;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ar.d2m.machines.Machine;
import ar.d2m.machines.MillingMachine;
import ar.d2m.machines.None;
import ar.d2m.machines.TurningMachine;
import ar.d2m.loader.D2MLoader;
import fr.epsi.dxf.DXF_Loader;


public class MachineSelection extends JPanel implements ActionListener{

	private JLabel machines;
	private JLabel selectedMachine;
	private JLabel postProcessor;
	private JComboBox machineSelector;
	private Hashtable<String,Machine> machineList;
	private JButton select;
	private JComboBox postProcessorSelector;
	private JLabel space;
	public MachineSelection(DXF_Loader DXF, Machine machine){
	this.setLayout(new GridLayout(0,7));
	machines= new JLabel("Machines");
	machineList= new Hashtable<String,Machine>();
	machineList.put("None", new None(DXF));
	machineList.put("Turn", new TurningMachine(DXF));
	machineList.put("Mill", new MillingMachine(DXF));
	machineSelector= new JComboBox();
	Enumeration e= machineList.keys();
	while(e.hasMoreElements()){
		String key= (String) e.nextElement();
		machineSelector.addItem(key);
	}
	select= new JButton("Select Machine");
	this.add(machines);
	this.add(machineSelector);
	this.add(select);
	select.addActionListener(this);
	postProcessorSelector= new JComboBox();
	postProcessorSelector.setVisible(false);
	postProcessor= new JLabel("PostProcessor:");
	postProcessor.setAlignmentX(CENTER_ALIGNMENT);
	postProcessor.setVisible(false);
	space= new JLabel("   ");
	selectedMachine= new JLabel("None");
	selectedMachine.setAlignmentX(RIGHT_ALIGNMENT);
	this.add(space);
	this.add(selectedMachine);
	this.add(postProcessor);
	this.add(postProcessorSelector);
	this.setBorder(BorderFactory.createLineBorder(Color.black));
	}

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getSource()==select){
			Machine setMachine=machineList.get(machineSelector.getSelectedItem());
			D2MLoader.updateMachine(setMachine);
			selectedMachine.setText(machineSelector.getSelectedItem().toString());
			postProcessor.setVisible(true);
			postProcessorSelector.setVisible(true);
			if(selectedMachine.getText()=="None"){
				postProcessor.setVisible(false);
				postProcessorSelector.setVisible(false);
			}
		}
		
	}
}
