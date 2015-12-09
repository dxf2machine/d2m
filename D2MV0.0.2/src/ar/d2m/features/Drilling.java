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

import java.util.Enumeration;
import java.util.Hashtable;

import ar.d2m.data.Entities;
import fr.epsi.dxf.Entities.myEntity;
import fr.epsi.dxf.Entities.myLine;

/**
 * @author CGG
 *
 */
public class Drilling extends Feature {
	
	private static Hashtable drillingEntities;
	private static int colorContour;
	public Drilling(){
		drillingEntities= new Hashtable<String,myEntity>();
		drillingEntities=getDrillingEntities();
		Enumeration<myEntity> elements = drillingEntities.elements();
		while(elements.hasMoreElements()){
			myEntity element= elements.nextElement();
			if (element instanceof myLine){
				System.out.println("X"+ ((myLine) element)._a.X()+ " Y"+((myLine) element)._a.Y());
			}
		}
	}
	
	public static Hashtable getDrillingEntities(){
		colorContour=90;
		drillingEntities= Entities.getFeature(colorContour);
		System.out.println(drillingEntities.size());
		return drillingEntities;
		}



}
