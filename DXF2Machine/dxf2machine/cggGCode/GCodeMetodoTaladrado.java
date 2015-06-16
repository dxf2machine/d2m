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
	
	private static void inicializarTaladradoDirecto(Coordenadas centro,
			double profuRasgo, int velocidad2, double avance2,
			JTextArea principal, double zseg,double zcambio) {
		//String numeLinea=null;
		//if(planoSeguridad=="No"){
		//	numeLinea=incrementarLinea(++nroLineaPrincipal);
		    String linea=taladrado.replace("referencia", Double.toString(zseg));
		    linea=linea.replace("profundidad",Double.toString(profuRasgo));
		    linea=linea.replace("planoRetraccion",Double.toString(zcambio));
		    linea=linea.replace("avance",Double.toString(avance2));
		    principal.append(linea);
		 //   linea=definirAvance.replace("avance",Double.toString(avance2));
		 //   linea=linea.replace("z", Double.toString(zseg));
		 //   principal.append(linea);
		    
	/*	}else{
			numeLinea=incrementarLinea(++nroLineaPrincipal);
			principal.append(numeLinea+" "+agujereado+" "+planoSeguridad+"25."+" "+comienzoTaladrado+zseg+" "+profundidadAgujereado+profuRasgo+
					X+centro.x+" "+Y+centro.y+Y+avance+avance2+EOL);
		}*/
		// TODO Auto-generated method stub
		}
	
	static void inicializarTaladrado(datos datos,
			Hashtable herramientas, JTextArea principal, double profuRasgo) {
		// TODO Auto-generated method stub
		Herramienta taladro=(Herramienta) herramientas.get("Taladrado");
		double pasada= taladro.Pasada;
		int velocidadT=(int)taladro.Velocidad;
		double avanceT=taladro.Avance;
		double Zcambio=taladro.Zcambio;
		//String nume=incrementarLinea(++nroLineaPrincipal);
		int nroHerramienta = (int) taladro.Numero;
		Coordenadas centro = (Coordenadas) ColeccionFunciones.ObtenerCoordenadaCentroEntidad(datos);
		//nroLineaPrincipal = (int) principal.getLineCount() +numeracionReferencia+1 ;
		//String numeLinea = ("N" + nroLineaPrincipal);
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

	private static void inicializarTaladradoProfundo(Coordenadas centro,
			double pasada, double profuRasgo, int velocidad2, double avance2,
			JTextArea principal, double zseg,double zcambio) {
		// TODO Auto-generated method stub
	    String linea=taladradoProfundo.replace("referencia", Double.toString(zseg));
	    linea=linea.replace("profundidad",Double.toString(profuRasgo));
	    linea=linea.replace("planoRetraccion",Double.toString(zcambio));
	    linea=linea.replace("pasada",Double.toString(pasada));
	    linea=linea.replace("avance",Double.toString(avance2));
	    principal.append(linea);

		
	}


}
