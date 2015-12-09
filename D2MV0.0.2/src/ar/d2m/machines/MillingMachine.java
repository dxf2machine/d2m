/*---------------------------------------------------------------------------------------- 
Copyright 2015, Celeste Gabriela Guagliano. 

This file is part of DXF2Machine project. 

DXF2Machine is free software: you can redistribute it and/or modify it under the terms of 
the GNU General Public License as published by the Free Software Foundation, either 
version 2 of the License. 

DXF2Machine is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
See the GNU General Public License for more details. 

You should have received a copy of the GNU General Public License along with DXF2Machine. 
If not, see <http://www.gnu.org/licenses/>.

For more information, contact us at: dxf2machine@gmail.com
  ----------------------------------------------------------------------------------------*/

package ar.d2m.machines;

import ar.d2m.features.ContourMilling;
import ar.d2m.features.Drilling;
import ar.d2m.features.Engraving;
import ar.d2m.features.FaceMilling;
import ar.d2m.graphicalinterface.GraphicalInterface;
import ar.d2m.graphicalinterface.MillingGraphicalInterface;
import fr.epsi.dxf.DXF_Loader;

/**
 * @author CGG
 *
 */
public class MillingMachine extends Machine {
	
	
	public MillingMachine(DXF_Loader DXF){
		DXF.GCode.removeAll();
		first= new FaceMilling();
		second= new ContourMilling();
		third= new Engraving();
		fourth= new Drilling();
	}
	
	public void setIface(DXF_Loader DXF){
		colores.put(1,"Origen");
		colores.put(3, "Drilling");
		colores.put(4, "Engraving");
		colores.put(5, "Contour Milling");
		colores.put(0,"None");
		iface=new MillingGraphicalInterface(DXF, this);
	}
	
}
