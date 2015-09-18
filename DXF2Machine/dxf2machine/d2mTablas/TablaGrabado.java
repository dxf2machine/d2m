/*------------------------------------------------------------------------------------------ 
Copyright 2014, Celeste Gabriela Guagliano. 

This file is part of DXF2Machine project. 

DXF2Machine is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License. 

DXF2Machine is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. 

You should have received a copy of the GNU General Public License along with DXF2Machine. If not, see <http://www.gnu.org/licenses/>.

For more information, contact us at: dxf2machine@gmail.com
  --------------------------------------------------------------------------------------------*/

package d2mTablas;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;

import javax.swing.JTextArea;

import d2mColeccion.ColeccionFunciones;
import d2mDatos.datos;
import d2mGCode.GCode;

/**
 * This class access the general table of entities of DXF2Machine and collect the entities matching a color code in a new table.
 * @author: Celeste G. Guagliano
 * @version: 0.0.1
 * 
 */ 

public class TablaGrabado {
	public static Hashtable ListaGrabado = new Hashtable();
	public static int colorGrabado = 4;

	/**
	 * Method to get the engraving list.
	 * @return an entities list.
	 */
	public static Hashtable ObtenerTabla() {
		resetearTablas();
		ListaGrabado = ColeccionFunciones.ObtenerSubconjunto(
		Tabla.ListaEntidades, colorGrabado);
		return ListaGrabado;

	}

	/**
	 * Method to reset the engraving list and the text area were the code is generated.
	 */
	public static void resetearTablas() {
		ListaGrabado.clear();
		GCode.grabado.setText("");

	}

}
