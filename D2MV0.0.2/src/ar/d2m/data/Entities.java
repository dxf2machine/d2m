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

package ar.d2m.data;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;

import fr.epsi.dxf.Entities.myEntity;
import fr.epsi.dxf.Entities.myLine;
import fr.epsi.dxf.Graphics.myCanvas;


/**
 * @author CGG
 *
 */
public class Entities {
	private static Hashtable<String,myEntity> entities;
	private static int l;
	
	public static Hashtable getFeature(int color){
		entities= new Hashtable<String,myEntity>();
		for (int i = 0; i < myCanvas._dxf._u._myTables.size(); i++) {
			for (int j = 0; j < myCanvas._dxf._u._myTables.elementAt(i)._myLayers
					.size(); j++) {
				for (int k = 0; k < myCanvas._dxf._u._myTables.elementAt(i)._myLayers
						.elementAt(j)._myEnt.size(); k++) {
					if((myCanvas._dxf._u._myTables.elementAt(i)._myLayers
							.elementAt(j)._myEnt.elementAt(k))._color== color){
						++l;
						entities.put(Integer.toString(l),myCanvas._dxf._u._myTables.elementAt(i)._myLayers
							.elementAt(j)._myEnt.elementAt(k));
						}
						;

					}

		
				}

			}
		return entities;
	}
	
	public Hashtable getData(Hashtable entities){
		Hashtable data= new Hashtable<Integer,Data>();
		Enumeration<myEntity> elements = entities.elements();
		while(elements.hasMoreElements()){
			myEntity element= elements.nextElement();
			if (element instanceof myLine){
				LineData line= new LineData((myLine)element);
				
				}
		}
		return data;
	}
}

