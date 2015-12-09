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

package ar.d2m.features;

import java.awt.Color;
import java.util.Hashtable;

import javax.swing.JFrame;

import ar.d2m.graphicalinterface.DefineContour;
import ar.d2m.loader.D2MLoader;
import fr.epsi.dxf.Entities.myEntity;
import fr.epsi.dxf.Graphics.myJColorChooser;

/**
 * @author CGG
 *
 */
public class ContourOutTurning extends Contour {

	private static Hashtable contourEntities;
	private static int colorContour;
	public JFrame selector;
	public ContourOutTurning(){
		contourEntities= new Hashtable<Integer,myEntity>();
		contourEntities=getContourEntities();
	}
	
	public void setEntitiesTable() {
		// TODO Auto-generated method stub
	D2MLoader.DXF._jcc.changingColor=new Color(0xff, 0x00, 0x00);
	myJColorChooser.col.setBackground(new Color (0xff,0x00,0x00));
	selector=new DefineContour();
	contourEntities=((DefineContour) selector).setContour();
	}
}
