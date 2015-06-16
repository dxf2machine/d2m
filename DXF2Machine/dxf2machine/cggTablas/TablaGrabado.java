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
 * @author: Celeste G. Guagliano
 * @version: 13/01/15
 * 
 */ 

public class TablaGrabado {
	public static Hashtable ListaGrabado = new Hashtable();
	public static int colorGrabado = 4;

	public static Hashtable ObtenerTabla() {
		resetearTablas();
		ListaGrabado = ColeccionFunciones.ObtenerSubconjunto(
				Tabla.ListaEntidades, colorGrabado);
		// GCode.GenerarGrabado(ListaGrabado);
		return ListaGrabado;

	}

	public static void resetearTablas() {
		ListaGrabado.clear();
		GCode.grabado.setText("");

	}

	/*
	 * REVISAR TODO
	 * 
	 * public static void ordenarDatos(){ datos elemento1=null; datos
	 * elemento2=null; double xfinal=elementoInicial.FinalX; double
	 * yfinal=elementoInicial.FinalY; double xinicial=0; double yinicial=0; do{
	 * for (Enumeration e=ListaGrabado.elements();e.hasMoreElements();){
	 * elemento1=(datos)e.nextElement(); xinicial= elemento1.ComienzoX;
	 * yinicial= elemento1.ComienzoY; /*xfinal=((datosLinea) elemento1).FinalX;
	 * yfinal=((datosLinea) elemento1).FinalY; if(elemento1.ubicado==false){
	 * if(xinicial==xfinal&&yinicial==yfinal){ posicion+=1;
	 * ListaGrabadoOrdenada.add(elemento1); elemento1.ubicado=true;
	 * elemento1.posicion=posicion; xfinal=elemento1.FinalX;
	 * yfinal=elemento1.FinalY;
	 * 
	 * }else{ for(Iterator at=ListaGrabado.iterator();at.hasNext();){
	 * elemento1=(datos)at.next(); if(elemento1.ubicado==false){ xinicial=
	 * elemento1.ComienzoX; yinicial= elemento1.ComienzoY;
	 * if(xfinal==xinicial&&yfinal==yinicial){ posicion+=1;
	 * ListaGrabadoOrdenada.add(elemento1); (elemento1).ubicado=true;
	 * (elemento1).posicion=posicion; xfinal=(elemento1).FinalX;
	 * yfinal=(elemento1).FinalY;} } } } } }
	 * 
	 * 
	 * 
	 * if(ListaGrabadoOrdenada.size()<ListaGrabado.size()){
	 * obtenerElementoInicial(); xfinal=elementoInicial.FinalX;
	 * yfinal=elementoInicial.FinalY; }
	 * 
	 * }while(ListaGrabado.size()>ListaGrabadoOrdenada.size()); }
	 * 
	 * 
	 * 
	 * 
	 * 
	 * public static void AccederALaLista(){ double xfinal=0; double yfinal=0;
	 * datos d1=null; boolean inicial=true; int posi=1; do{ for( Iterator it =
	 * ListaGrabadoOrdenada.iterator(); it.hasNext(); ) { datos elemento1
	 * =(datos) it.next(); coincidencia=false;
	 * 
	 * switch (elemento1.posicion){ case 1: if (inicial==true){ if(pasada==0){
	 * EnsambladoGCode.coordenadaInicial(elemento1);
	 * EnsambladoGCode.inicializarGCode(elemento1);
	 * EnsambladoGCode.primerAvance(); d1=elemento1; } else{
	 * EnsambladoGCode.saltarASiguiente(elemento1,true); }
	 * EnsambladoGCode.agregarGCode(elemento1,true); xfinal=(elemento1).FinalX;
	 * yfinal=(elemento1).FinalY; posi=2; inicial=false; d1=elemento1; }
	 * 
	 * default: if (inicial==false){ if((elemento1).posicion==posi){
	 * EnsambladoGCode.comprobarCoordenadaCoincidente(d1,elemento1); if
	 * (coincidencia==true){ xfinal=(elemento1).FinalX;
	 * yfinal=(elemento1).FinalY; }else if (coincidencia==false){
	 * EnsambladoGCode.saltarASiguiente(elemento1,true);
	 * EnsambladoGCode.agregarGCode(elemento1,true); xfinal=(elemento1).FinalX;
	 * yfinal=(elemento1).FinalY;
	 * 
	 * } posi+=1; d1=elemento1; } } }
	 * 
	 * 
	 * 
	 * //EnsambladoGCode.agregarGCode(((datosLinea)elemento1).ComienzoX,((datosLinea
	 * )elemento1).ComienzoY);} //ventana.append(((datosLinea)
	 * elemento).ComienzoX+ " "+((datosLinea)elemento).ComienzoY +
	 * "  "+((datosLinea
	 * )elemento).FinalX+" "+((datosLinea)elemento).FinalY+"\n ");
	 * 
	 * }
	 * 
	 * }while(posi<=ListaGrabadoOrdenada.size());
	 * 
	 * if(EnsambladoGCode.z<EnsambladoGCode.profuZ){ if
	 * (EnsambladoGCode.z+EnsambladoGCode.pasadaZ<EnsambladoGCode.profuZ){
	 * EnsambladoGCode.z+=EnsambladoGCode.pasadaZ; }else {
	 * EnsambladoGCode.z=EnsambladoGCode.profuZ; } pasada+=1; SiguientePasada();
	 * } EnsambladoGCode.finalizarGCode(); }
	 * 
	 * 
	 * private static void SiguientePasada() { // TODO Auto-generated method
	 * stub AccederALaLista();
	 * 
	 * }
	 * 
	 * public static void obtenerElementoInicial(){ boolean inicial=true; double
	 * distanciaAlCero1=0; double distanciaAlCero2=0; //double posicion=0; for (
	 * Iterator it = ListaGrabado.iterator(); it.hasNext(); ){ datos
	 * elemento1=(datos) it.next(); double xinicial=0; double yinicial=0;
	 * if(elemento1.ubicado==false){ if (inicial==true){ elementoInicial=
	 * elemento1;
	 * 
	 * distanciaAlCero1=Math.sqrt(Math.pow(xinicial,2)+Math.pow(yinicial,2));
	 * inicial=false; } else{
	 * distanciaAlCero2=Math.sqrt(Math.pow(xinicial,2)+Math.pow(yinicial,2)); if
	 * (distanciaAlCero2<distanciaAlCero1){ elementoInicial=elemento1;
	 * distanciaAlCero1=distanciaAlCero2; } } }
	 * 
	 * } //System.out.println("X "+elementoInicial.ComienzoX+
	 * " Y "+elementoInicial.ComienzoY);
	 * ListaGrabadoOrdenada.add(elementoInicial); if (posicion<1){ posicion=1;}
	 * else{ posicion=posicion+1; } elementoInicial.posicion=posicion;
	 * for(Iterator iter=ListaGrabado.iterator();iter.hasNext();){ datos ele2=
	 * (datos)iter.next(); if (elementoInicial.ComienzoX==(ele2).ComienzoX &&
	 * elementoInicial.ComienzoY==(ele2).ComienzoY){ (ele2).ubicado=true; } }
	 * 
	 * }
	 * 
	 * public static void resetearTablas(){ for(Iterator
	 * ite=ListaGrabadoOrdenada.iterator();ite.hasNext();){ ite.next();
	 * ite.remove(); } for(Iterator ite=ListaGrabado.iterator();ite.hasNext();){
	 * ite.next(); ite.remove(); } posicion=0; pasada=0;
	 * EnsambladoGCode.z=Double.parseDouble(setMecanizados.pasadaZ.getText());
	 * EnsambladoGCode
	 * .profuZ=Double.parseDouble(setMecanizados.profuZ.getText()); if
	 * (EnsambladoGCode.pasadaZ>EnsambladoGCode.profuZ){
	 * EnsambladoGCode.z=EnsambladoGCode.profuZ; } else{
	 * EnsambladoGCode.z=EnsambladoGCode.pasadaZ; }
	 * 
	 * }
	 */
}
