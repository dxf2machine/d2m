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

public class GCodeMetodoGeneral extends GCode {
	String nume=null;
	double profundidad=0;
	String RapidoZ;
	public JTextArea mecanizarLista(Hashtable lista, JTextArea rasgo,
			double pasada, int P, Herramienta herramienta) {
		profundidad=inicializarProfundidad(profundidad,pasada);
		double zsafe=herramienta.Zseguro;
		if(P>0){
			//nume = incrementarLinea(++nroLineaSubprog);
			rasgo.append(coordenadaAbsoluta);
			for (int i = 1; i <= lista.size(); i++) {// chequeo con la coordenada anterior si hay continuidad,si no la hay salto a la siguiente)
				datos elemento1 = (datos) lista.get(i - 1);
				datos elemento2 = (datos) lista.get(i);
				boolean continuidad = true;
				if (elemento1 != null) {
					continuidad = ColeccionFunciones.compartenCoordenada(
							elemento1, elemento2);
				}
				//nume = incrementarLinea(++nroLineaSubprog);
				if (continuidad == true) {
					String linea = elemento2.mecanizate();
					linea=linea+("\r\n");
					rasgo.append(linea);
					
				} else {
					String RapidoZ=avanceRapidoZ.replace("z", Double.toString(zsafe));
					rasgo.append(RapidoZ);
					//nume = incrementarLinea(++nroLineaSubprog);
					String salto = elemento2.saltarASiguiente();
					rasgo.append(salto);
					//nume = incrementarLinea(++nroLineaSubprog);
					String linea=avanceLinealZ.replace("z",Double.toString(profundidad));
					rasgo.append(linea);
					//nume = incrementarLinea(++nroLineaSubprog);
					linea = elemento2.mecanizate();
					linea=linea+("\r\n");
					rasgo.append(linea);
				}
			}
			//nume = incrementarLinea(++nroLineaSubprog);
			rasgo.append(RapidoZ);
			if (P > 1) {
				//nume = incrementarLinea(++nroLineaSubprog);
				String linea=avanceRapidoZ.replace("z", Double.toString(zsafe));
				rasgo.append(linea);
				datos elemento = (datos) lista.get(1);
				Coordenadas iniciales = ColeccionFunciones.ObtenerCoordenadaInicioEntidad(elemento);
				linea=avanceRapido.replace("x",Double.toString(iniciales.x));
				linea=linea.replace("y", Double.toString(iniciales.y));
				rasgo.append(linea);
				//nume = incrementarLinea(++nroLineaSubprog);
				profundidad=profundidad-pasada;
				linea=avanceLinealZ.replace("z", Double.toString(profundidad));
				rasgo.append(linea);
				}
			
			mecanizarLista(lista,rasgo,pasada,--P,herramienta);
		}
		return rasgo;
	}
	
	
	private double inicializarProfundidad(double profundidad2, double pasada) {
		// TODO Auto-generated method stub
		if(profundidad==0){
			profundidad-=pasada;
		}
		return profundidad;
	}

}
