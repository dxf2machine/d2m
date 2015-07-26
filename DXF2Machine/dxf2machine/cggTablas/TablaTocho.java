/*------------------------------------------------------------------------------------------ 
Copyright 2014, Celeste Gabriela Guagliano. 

This file is part of DXF2Machine project. 

DXF2Machine is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License. 

DXF2Machine is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. 

You should have received a copy of the GNU General Public License along with DXF2Machine. If not, see <http://www.gnu.org/licenses/>.
  --------------------------------------------------------------------------------------------*/

package cggTablas;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JTextArea;

import cggColeccion.ColeccionFunciones;
import cggDatos.datos;
import cggGCode.GCode;

/**
 * This class resets the stock table.
 * @author: Celeste G. Guagliano
 * @version: 13/01/15
 * 
 */ 
public class TablaTocho {
	public static Hashtable ListaTocho = new Hashtable();

	/**
	 * Method to reset the stock's list and the text area were the code is generated.
	 */
	public static void resetearTablas() {
		ListaTocho.clear();
		GCode.plano.setText("");
	}

	/**
	 * Method to get the stock list.
	 * @return a stock list.
	 */
	public static Hashtable ObtenerTabla() {
		resetearTablas();
		return ListaTocho;
	}
}
