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
 * This class implements the necessary algorithms to generate the G-Code from any list of entities.  
 * @author: Celeste G. Guagliano
 * @version: 13/01/15
 * 
 */ 

public class GCodeMetodoGeneral extends GCode {
	String nume=null;
	double profundidad=0;
	String RapidoZ;
	
	/**
	 * Method to generate the general translation of features.
	 * @param lista is the list of entities.
	 * @param rasgo is the text area were the code is generated.
	 * @param pasada is the partial depth of the contouring.
	 * @param repeticiones is the number of times that the subprogram must be called if the code is generated in a subprogram.
	 * @param herramienta is the tool involved in the process.
	 */
	public JTextArea mecanizarLista(Hashtable lista, JTextArea rasgo,
			double pasada, int repeticiones, Herramienta herramienta) {
		profundidad=inicializarProfundidad(pasada);
		double zsafe=herramienta.Zseguro;
		if(repeticiones>0){
			rasgo.append(coordenadaAbsoluta);
			for (int i = 1; i <= lista.size(); i++) {
				datos elemento1 = (datos) lista.get(i - 1);
				datos elemento2 = (datos) lista.get(i);
				boolean continuidad = true;
				if (elemento1 != null) {
					continuidad = ColeccionFunciones.compartenCoordenada(
							elemento1, elemento2);
				}
			  if (continuidad == true) {
					String linea = elemento2.mecanizate();
					linea=linea+("\r\n");
					rasgo.append(linea);
					
				} else {
					String RapidoZ=avanceRapidoZ.replace("z", Double.toString(zsafe));
					rasgo.append(RapidoZ);
					String salto = elemento2.saltarASiguiente();
					rasgo.append(salto);
					String linea=avanceLinealZ.replace("z",Double.toString(profundidad));
					rasgo.append(linea);
					linea = elemento2.mecanizate();
					linea=linea+("\r\n");
					rasgo.append(linea);
				}
			}
			rasgo.append(RapidoZ);
			if (repeticiones > 1) {
				String linea=avanceRapidoZ.replace("z", Double.toString(zsafe));
				rasgo.append(linea);
				datos elemento = (datos) lista.get(1);
				Coordenadas iniciales = ColeccionFunciones.ObtenerCoordenadaInicioEntidad(elemento);
				linea=avanceRapido.replace("x",Double.toString(iniciales.x));
				linea=linea.replace("y", Double.toString(iniciales.y));
				rasgo.append(linea);
				profundidad=profundidad-pasada;
				linea=avanceLinealZ.replace("z", Double.toString(profundidad));
				rasgo.append(linea);
				}
			
			mecanizarLista(lista,rasgo,pasada,--repeticiones,herramienta);
		}
		return rasgo;
	}
	
	/**
	 * Method to get the first depth of the process
	 * @param pasada is the partial depth of the feature.
	 * @return the first depth.
	 */
	private double inicializarProfundidad(double pasada) {
		if(profundidad==0){
			profundidad-=pasada;
		}
		return profundidad;
	}

}
