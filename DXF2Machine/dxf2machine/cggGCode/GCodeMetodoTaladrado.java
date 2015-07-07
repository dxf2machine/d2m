/*------------------------------------------------------------------------------------------ 
Copyright 2014, Celeste Gabriela Guagliano. 

This file is part of DXF2Machine project. 

DXF2Machine is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License. 

DXF2Machine is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. 

You should have received a copy of the GNU General Public License along with DXF2Machine. If not, see <http://www.gnu.org/licenses/>.
  --------------------------------------------------------------------------------------------*/

package cggGCode;

import java.util.Hashtable;

import javax.swing.JTextArea;

import cggColeccion.ColeccionFunciones;
import cggDatos.Coordenadas;
import cggDatos.Herramienta;
import cggDatos.datos;

/**
 * This class implements the necessary algorithms to generate the G-Code from a list of holes.  
 * @author: Celeste G. Guagliano
 * @version: 13/01/15
 * 
 */ 

public class GCodeMetodoTaladrado extends GCode {

	/**
	 * Method to initialize the direct drilling process
	 * @param centro is the first hole's center
	 * @param profuRasgo is the total depth of the feature.
	 * @param velocidad2 is the spindle speed.
	 * @param avance2 is the tool's feed rate.
	 * @param principal is the main program's code.
	 * @param zseg is the safety height.
	 * @param zcambio is the safety tool change's height.
	 */
	private static void inicializarTaladradoDirecto(Coordenadas centro,
			double profuRasgo, int velocidad2, double avance2,
			JTextArea principal, double zseg,double zcambio) {
		    String linea=taladrado.replace("referencia", Double.toString(zseg));
		    linea=linea.replace("profundidad",Double.toString(profuRasgo));
		    linea=linea.replace("planoRetraccion",Double.toString(zcambio));
		    linea=linea.replace("avance",Double.toString(avance2));
		    principal.append(linea);
		}
	/**
	 * Method to initialize the drilling process
	 * @param datos is the first hole's data.
	 * @param herramientas is the tool's list.
	 * @param principal is the main program code.
	 * @param profuRasgo is the total depth of the feature.
	 */
	static void inicializarTaladrado(datos datos,
			Hashtable herramientas, JTextArea principal, double profuRasgo) {
		Herramienta taladro=(Herramienta) herramientas.get("Taladrado");
		double pasada= taladro.Pasada;
		int velocidadT=(int)taladro.Velocidad;
		double avanceT=taladro.Avance;
		double Zcambio=taladro.Zcambio;
		int nroHerramienta = (int) taladro.Numero;
		Coordenadas centro = (Coordenadas) ColeccionFunciones.ObtenerCoordenadaCentroEntidad(datos);
		String cambioTool=cambioHerramienta.replaceAll("herramienta", Integer.toString(nroHerramienta));
		cambioTool=cambioTool.replace("velocidad",Integer.toString(velocidadT));
		cambioTool=cambioTool.replace("x",Double.toString(centro.x));
		cambioTool=cambioTool.replace("y",Double.toString(centro.y));
		cambioTool=cambioTool.replace("planoRetraccion", Double.toString(Zcambio));
		principal.append(cambioTool);
		double zseg=taladro.Zseguro;
		if (pasada<profuRasgo){
			inicializarTaladradoProfundo(centro,pasada,profuRasgo,velocidadT,avanceT,principal,zseg,Zcambio);
		}else{
			inicializarTaladradoDirecto(centro,profuRasgo,velocidadT,avanceT,principal,zseg,Zcambio);
		}
		}

	/**
	 * Method to initialize the deep drilling process
	 * @param centro is the first hole's center.
	 * @param pasada is the partiaL depth of the feature.
	 * @param profuRasgo is the total depth of the feature.
	 * @param velocidad2 is the spindle speed.
	 * @param avance2 is the tool's feed rate.
	 * @param principal is the main's program code.
	 * @param zseg is the safety height.
	 * @param zcambio is the safety tool change's height.
	 */
	private static void inicializarTaladradoProfundo(Coordenadas centro,
			double pasada, double profuRasgo, int velocidad2, double avance2,
			JTextArea principal, double zseg,double zcambio) {
	    String linea=taladradoProfundo.replace("referencia", Double.toString(zseg));
	    linea=linea.replace("profundidad",Double.toString(profuRasgo));
	    linea=linea.replace("planoRetraccion",Double.toString(zcambio));
	    linea=linea.replace("pasada",Double.toString(pasada));
	    linea=linea.replace("avance",Double.toString(avance2));
	    principal.append(linea);

		
	}


}
