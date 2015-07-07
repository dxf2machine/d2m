/*------------------------------------------------------------------------------------------ 
Copyright 2014, Celeste Gabriela Guagliano. 

This file is part of DXF2Machine project. 

DXF2Machine is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License. 

DXF2Machine is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. 

You should have received a copy of the GNU General Public License along with DXF2Machine. If not, see <http://www.gnu.org/licenses/>.
  --------------------------------------------------------------------------------------------*/

package cggTablas;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;

import javax.swing.JTextArea;

import cggColeccion.ColeccionFunciones;
import cggDatos.datos;
import cggGCode.GCode;

/**
 * This class access the general table of entities of DXF2Machine and collect the entities matching a color code in a new table.
 * This kind of optimization just returns the same list that receives.  
 * @author: Celeste G. Guagliano
 * @version: 13/01/15
 * 
 */ 

public class TablaContorno {
	public static Hashtable ListaContorno = new Hashtable();
	public static int colorContorno = 5;
	
	/**
	 * Method to get the entities of the contour list.
	 * @return an entities list.
	 */

	public static Hashtable obtenerTabla() {
		resetearTablas();
		ListaContorno = ColeccionFunciones.ObtenerSubconjunto(
		Tabla.ListaEntidades, colorContorno);
		return ListaContorno;
	}

	/**
	 *  Method to reset the content of the list and the text area were the code is generated.
	 */
	public static void resetearTablas() {
		ListaContorno.clear();
		GCode.contorneado.setText("");
	}

}
