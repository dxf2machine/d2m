/*------------------------------------------------------------------------------------------ 
Copyright 2014, Celeste Gabriela Guagliano. 

This file is part of DXF2Machine project. 

DXF2Machine is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License. 

DXF2Machine is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. 

You should have received a copy of the GNU General Public License along with DXF2Machine. If not, see <http://www.gnu.org/licenses/>.
  --------------------------------------------------------------------------------------------*/

package cggTablas;

import java.util.Hashtable;

import cggGCode.GCode;

/**
 * This class generate a table of admitted postprocessors.
 * @author: Celeste G. Guagliano
 * @version: 13/01/15
 * 
 */ 
public class TablaPostprocesadores {

	public static Hashtable ObtenerTabla() {
		Hashtable postprocesadores = new Hashtable();
		postprocesadores.put("Sinumerik 810/820M",
				"myDXF.i18n.GCode_Sinumerik820");
		postprocesadores.put("HAAS/FANUC", "myDXF.i18n.GCode_Hass");
		return postprocesadores;
	}

	public static String ObtenerPostprocesador(String postprocesador) {
		String parametro = null;
		Hashtable maquinas = ObtenerTabla();
		parametro = (String) maquinas.get(postprocesador);
		return parametro;
	}
}
