/*------------------------------------------------------------------------------------------ 
Copyright 2014, Celeste Gabriela Guagliano. 

This file is part of DXF2Machine project. 

DXF2Machine is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License. 

DXF2Machine is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. 

You should have received a copy of the GNU General Public License along with DXF2Machine. If not, see <http://www.gnu.org/licenses/>.
  --------------------------------------------------------------------------------------------*/

package cggGCode;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.swing.JTextArea;

import cggDatos.DatosArcos;
import cggDatos.DatosCirculo;
import cggDatos.DatosLinea;
import cggDatos.datos;
import cggTablas.TablaAgujeros;
import cggTablas.TablaContorno;
import cggTablas.TablaGrabado;

public class EnsambladoGCode {
//	public static JTextArea ventanaTexto = myDXF.GCode.VentanaGCodeJFrame.ventanaTexto;
	public static String finalizar = "off";
	public static double avance;
	public static double profuZ;
	public static int velocidad;
	public static double pasadaZ;
	public static double radio;
	public static double z;
	public static double xinicio = 0;
	public static double yinicio = 0;
	public static double x1 = 0;
	public static double y1 = 0;
	public static double x2 = 0;
	public static double y2 = 0;

	public static void inicializarGCode(datos d) {
/*		avance = Double.parseDouble(setMecanizados.avance.getText());
		profuZ = Double.parseDouble(setMecanizados.profuZ.getText());
		pasadaZ = Double.parseDouble(setMecanizados.pasadaZ.getText());
		velocidad = Integer.parseInt(setMecanizados.velocidad.getText());
		radio = Double.parseDouble(setMecanizados.diaHerram.getText());
		if (pasadaZ > profuZ) {
			z = profuZ;
		} else {
			z = pasadaZ;
		}
		ventanaTexto.append(// "% \n"
				// + "o00001\n"
				// + "G00 G90 G49 G80 G21 \n"
				// + "T01 M06\n"
				"S" + velocidad + " M03\n" + "G00 G54 X" + xinicio + " Y"
						+ yinicio + "\n"
						// + "G43 H01 Z50.\n"
						+ "G00 Z5.\n");
		finalizar = "on";
	}

	public static void primerAvance() {
		ventanaTexto.append("G01 Z-" + z + " F" + avance + "\n");
	}

	public static void agregarGCode(datos d, boolean directo) {
		if (d instanceof DatosLinea) {
			if (directo == true) {
				ventanaTexto
						.append("G01 X" + d.FinalX + " Y" + d.FinalY + "\n");
			} else {
				ventanaTexto.append("G01 X" + d.ComienzoX + " Y" + d.ComienzoY
						+ "\n");
			}
		} else if (d instanceof DatosCirculo) {
			// se trata de una circunferencia se recorre con G03 y el radio
			ventanaTexto.append("G03 X"
					+ (d.FinalX + 2 * ((DatosCirculo) d).Radio) + " Y"
					+ d.FinalY + " U" + ((DatosCirculo) d).Radio + "\n");
			ventanaTexto.append("G03 X" + d.FinalX + " Y"
					+ ((DatosCirculo) d).FinalY + " U"
					+ ((DatosCirculo) d).Radio + "\n");
		}

		else {// se trata de un arco para saber si se recorre con G02 o G03 se
				// debe saber si AngI>AngF o al reves
				// if(directo==true){

			/*
			 * }else{ x1=d.ComienzoX; y1=d.ComienzoY; x2=d.FinalX; y2=d.FinalY;
			 * }
			 
			if (directo == false) {
				x1 = d.ComienzoX;
				y1 = d.ComienzoY;
				x2 = d.FinalX;
				y2 = d.FinalY;
				ventanaTexto.append("G02 X" + x1 + " Y" + y1 + " I"
						+ (((DatosArcos) d).Xcentro - x2) + " J"
						+ (((DatosArcos) d).Ycentro - y2) + "\n");
			} else {
				x1 = d.FinalX;
				y1 = d.FinalY;
				x2 = d.ComienzoX;
				y2 = d.ComienzoY;
				ventanaTexto.append("G03 X" + x1 + " Y" + y1 + " I"
						+ (((DatosArcos) d).Xcentro - x2) + " J"
						+ (((DatosArcos) d).Ycentro - y2) + "\n");
			}
		}
	}

	public static void inicializarAgujereado() {
		ventanaTexto.append("G90 F" + avance + "\n");
		if (pasadaZ > profuZ || pasadaZ == 0) {
			ventanaTexto.append("G81 R02=5. R03=-" + profuZ + " R10=25.\n");
		} else {
			ventanaTexto.append("G83 R02=5. R03=-" + profuZ + " R10=25. R05="
					+ pasadaZ + "\n");
		}

	}

	@SuppressWarnings("deprecation")
	public static void finalizarGCode() {
		if (finalizar == "on") {
			if (ventanaTexto.getText() != null) {
				ventanaTexto.append("M30\n "
				// + "%"
						);
				finalizar = "off";
			}
		}
	}

	public static void resetearGCode() {
		// TODO Auto-generated method stub
		finalizar = "off";
		TablaGrabado.resetearTablas();
		TablaContorno.resetearTablas();
		TablaAgujeros.resetearTablas();

	}

	/*
	 * public static void comprobarCoordenadaCoincidente(datos d1, datos d2) {
	 * // TODO Auto-generated method stub if
	 * ((d1.FinalX==d2.ComienzoX)&&(d1.FinalY==d2.ComienzoY)){ if
	 * (TablaGrabado.coincidencia==false){
	 * //agregarGCode(comienzoX2,comienzoY2); agregarGCode(d2,true);
	 * TablaGrabado.coincidencia=true;} }
	 * 
	 * }
	 
	public static void saltarASiguiente(datos d1, boolean directo) {
		// TODO Auto-generated method stub
		double xi = 0;
		double yi = 0;
		if (directo == true) {
			xi = d1.ComienzoX;
			yi = d1.ComienzoY;
		} else {
			xi = d1.FinalX;
			yi = d1.FinalY;
		}
		ventanaTexto.append("G00 Z50.\n" + "Z5.\n" + "X" + xi + " Y" + yi
				+ "\n" + "G01 Z-" + z + "\n");

	}

	/*
	 * public static void comprobarOrdenCoordenada(datos d1,datos elemento1) {
	 * if (TablaContorno.directo==true){
	 * 
	 * if(Math.abs(d1.FinalX-elemento1.ComienzoX)<0.001&&Math.abs(d1.FinalY-
	 * elemento1.ComienzoY)<0.001) { TablaContorno.directo=true;} else{
	 * TablaContorno.directo=false; } } else{
	 * if(Math.abs(d1.ComienzoX-elemento1.
	 * ComienzoX)<0.001&&Math.abs(d1.ComienzoY-elemento1.ComienzoY)<0.001){
	 * TablaContorno.directo=true; } else{ TablaContorno.directo=false;} }
	 * 
	 * } public static void agregarAgujero(datos elemento){
	 * ventanaTexto.append("X"
	 * +((DatosCirculo)elemento).CentroX+" Y"+((DatosCirculo
	 * )elemento).CentroY+" \n"); }
	 * 
	 * public static void comprobarOrdenCoordenadaInicio(datos elemento1) {
	 * if(Math
	 * .sqrt(Math.pow(elemento1.ComienzoX-TablaContorno.xmin,2)+Math.pow(elemento1
	 * .ComienzoY-TablaContorno.ymin,2))<Math.sqrt(Math.pow(elemento1.FinalX-
	 * TablaContorno.xmin,2)+Math.pow(elemento1.FinalY-TablaContorno.ymin,2))){
	 * TablaContorno.directo=true; xinicio=elemento1.ComienzoX;
	 * yinicio=elemento1.ComienzoY; }else { TablaContorno.directo=false;
	 * xinicio=elemento1.FinalX; yinicio=elemento1.FinalY; } } public static
	 * void coordenadaInicial(datos elemento1){ xinicio=elemento1.ComienzoX;
	 * yinicio=elemento1.ComienzoY; } public static void finalizarAgujereado() {
	 * ventanaTexto.append("G80\n");
	 * 
	 * } public static void GenerarCodigoG(Hashtable listaOrdenada) { // TODO
	 * Auto-generated method stub
	 * 
	 * }
	 
	public static void Mecanizate(Hashtable listaOptimizada, GCode post) {
		// tenemos que recorrer la lista y decirle a cada entidad que se
		// mecanice
		System.out.println("\n\n\nLas entidades de la tabla son:");
		for (Enumeration k = listaOptimizada.keys(); k.hasMoreElements();) {
			int key = (int) k.nextElement();
			datos elemento = (datos) listaOptimizada.get(key);
			System.out.println("Clave:" + key + " elemento:" + elemento + "\n");
		}
	}
*/
}
}