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

import cggDatos.Coordenadas;
import cggDatos.FormatoNumeros;
import cggDatos.Herramienta;
import cggDatos.datos;
import d2mColeccion.ColeccionFunciones;
/**
 * This class implements the necessary algorithms to generate the G-Code from contours.  
 * @author: Celeste G. Guagliano
 * @version: 13/01/15
 * 
 */ 
public class GCodeMetodoContorneado extends GCode {
	public static double tramo=0;
	public static double incrementoTramo=0;
	
/**
 * Method to generate the contouring.
 * @param lista is the list of entities.
 * @param rasgo is the text area were the code is generated.
 * @param pasada is the partial depth of the contouring.
 * @param repeticiones is the number of times that the subprogram must be called if the code is generated in a subprogram.
 * @param herramienta is the tool involved in the process.
 */
	public JTextArea mecanizarLista(Hashtable lista, JTextArea rasgo,
			double pasada, int repeticiones, Herramienta herramienta) {
			if(repeticiones>0){
				--repeticiones;
				incrementoTramo=pasada/lista.size();
				rasgo.append(coordenadaAbsoluta);
				if(tramo==0){
					tramo-=incrementoTramo;
				}
				for (int i = 1; i <= lista.size(); i++) {
					datos elemento1 = (datos) lista.get(i);
					String linea = elemento1.mecanizate();
					tramo=(double) FormatoNumeros.formatearNumero(tramo);
					linea= linea+rampaZ.replace("z",Double.toString(tramo));
					rasgo.append(linea);
					tramo-=incrementoTramo;
						}
				mecanizarLista(lista,rasgo,pasada,repeticiones,herramienta);
			}
				else{
					for (int i = 1; i <= lista.size(); i++) {
						datos elemento1 = (datos) lista.get(i);
						String linea = elemento1.mecanizate();
						linea=linea+("\r\n");
						rasgo.append(linea);
						   }
					    tramo=0;
				        }
			return rasgo;
	}
}
