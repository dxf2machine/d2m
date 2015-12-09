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

import java.util.Hashtable;

import ar.d2m.data.Entities;
import fr.epsi.dxf.Entities.myEntity;

/**
 * @author CGG
 *
 */
public class Engraving extends Feature {
	
	private static Hashtable engravingEntities;
	private static int color;
	public Engraving(){
		engravingEntities= new Hashtable<String,myEntity>();
		engravingEntities=getEngravingEntities();
		
	}
	
	public static Hashtable getEngravingEntities(){
		color=130;
		engravingEntities= Entities.getFeature(color);
		System.out.println(engravingEntities.size());
		return engravingEntities;
	}



}
