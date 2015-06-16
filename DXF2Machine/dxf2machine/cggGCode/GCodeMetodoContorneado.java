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
 * This class implements the necessary algorithms to generate the G-Code from contours.  
 * @author: Celeste G. Guagliano
 * @version: 13/01/15
 * 
 */ 
public class GCodeMetodoContorneado extends GCode {
/*
 * Esta clase hará la optimización del tipo de mecanizado contorneado
 * 
   */
	public static double tramo=0;
	public static double incrementoTramo=0;
	
	public JTextArea mecanizarLista(Hashtable lista, JTextArea rasgo,
			double pasada, int P, Herramienta herramienta) {
			if(P>0){
				--P;
				incrementoTramo=pasada/lista.size();
				rasgo.append(coordenadaAbsoluta);
				if(tramo==0){
					tramo-=incrementoTramo;
				}
				for (int i = 1; i <= lista.size(); i++) {
					datos elemento1 = (datos) lista.get(i);
					String linea = elemento1.mecanizate();
					linea= linea+rampaZ.replace("z",Double.toString(tramo));
					rasgo.append(linea);
					tramo-=incrementoTramo;
						}
				mecanizarLista(lista,rasgo,pasada,P,herramienta);
				
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
				
							
					
			//}
			return rasgo;
	}
	/*		if(P>0){
				String linea=avanceLinealZ.replace("z", "-"+Double.toString(pasada));
				rasgo.append(coordenadaRelativa+linea);
			}
				if (subprograma == false) {
					mecanizarLista(lista,rasgo,pasada,P,herramienta);	
			}
		}
		
	}*/	
}
