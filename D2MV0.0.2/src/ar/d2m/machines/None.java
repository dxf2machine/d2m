package ar.d2m.machines;

import fr.epsi.dxf.DXF_Loader;

public class None extends Machine {
	
	public None(DXF_Loader DXF){
		DXF.GCode.removeAll();
		DXF.frame.setTitle("DXF2Machine: Transforming draws into machine code");
	}

}
