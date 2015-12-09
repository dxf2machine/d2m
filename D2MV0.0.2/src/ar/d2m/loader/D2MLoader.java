package ar.d2m.loader;

import javax.swing.JComboBox;

import ar.d2m.graphicalinterface.FeaturesSelection;
import ar.d2m.graphicalinterface.FeaturesSettings;
import ar.d2m.graphicalinterface.MachineSelection;
import ar.d2m.machines.Machine;
import ar.d2m.machines.MillingMachine;
import ar.d2m.machines.None;
import fr.epsi.dxf.DXF_Loader;

public class D2MLoader{
	
	private static final long serialVersionUID = 1L;
	public static DXF_Loader DXF;
	public static Machine machine;
	public static FeaturesSelection selection;
	public static FeaturesSettings settings;
	public static boolean changeColor=false;
	public static int selected;
	public static JComboBox machines;
	
	public static void main(final String[] args){
		DXF=new DXF_Loader();
		machine= new None(DXF);
		DXF.toolBar.add(new MachineSelection(DXF,machine));
		}

	public static void updateMachine(Machine setMachine) {
		DXF.GCode.removeAll();
		setMachine.setIface(DXF);
		
	}

}