/*------------------------------------------------------------------------------------------ 
Copyright 2014, Celeste Gabriela Guagliano. 

This file is part of DXF2Machine project. 

DXF2Machine is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License. 

DXF2Machine is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. 

You should have received a copy of the GNU General Public License along with DXF2Machine. If not, see <http://www.gnu.org/licenses/>.
  --------------------------------------------------------------------------------------------*/

package cggOptimizacion;

import java.util.Hashtable;

import cggColeccion.ColeccionFunciones;
import cggDatos.Coordenadas;
import cggDatos.DatosArcos;
import cggDatos.DatosLinea;
import cggDatos.EcuacionCircunferencia;
import cggDatos.EcuacionEntidad;
import cggDatos.EcuacionRecta;
import cggDatos.datos;
import cggGCode.compensacionContorno;

public class OptimizacionMetodo2 extends Optimizacion {

	public static Hashtable Optimizacion(Hashtable lista,double RadioHerramienta) {
		Hashtable listaOptimizada = new Hashtable();
		Hashtable listaEcuaciones= new Hashtable();
		Hashtable listaCompensada= new Hashtable();
		Hashtable listaEntidadesCompensadas= new Hashtable();
		listaOptimizada = ColeccionFunciones.InicializarTablaOrdenada(lista);
		lista = ColeccionFunciones.ObtenerNuevaLista(lista, listaOptimizada);
		if(lista.size()>0){
		listaOptimizada = ColeccionFunciones.ObtenerElementosOrdenados(
				listaOptimizada, lista);
		}
		boolean cerrado = ColeccionFunciones
				.EvaluarContornoCerrado(listaOptimizada);
		if (cerrado == true) {
			listaOptimizada = ColeccionFunciones.OrientarContornoCerrado(listaOptimizada);
			//listaEntidadesCompensadas=compensacionContorno.GenerarListaEntidadesCompensadas(listaOptimizada, RadioHerramienta);
			listaEcuaciones=compensacionContorno.ObtenerListaEcuaciones(listaOptimizada,RadioHerramienta);
			listaCompensada= compensacionContorno.intersectarEcuacionesCompensadas(listaOptimizada,listaEcuaciones);
			return listaCompensada;
		} else {
			return lista;
			// TODO Auto-generated constructor stub
		}
	}

	public static Hashtable intersectarEntidades(int i,int j, EcuacionEntidad ecuacion1,
			datos elemento1, EcuacionEntidad ecuacion2, datos elemento2, Hashtable listaCompensada) {
		//DatosLinea lineaCompensada=new DatosLinea(0, 0, 0, 0, 0, false, 0, 0);
		//DatosArcos arcoCompensado=new DatosArcos(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, false, 0, 0);
		Coordenadas interseccion=new Coordenadas(0,0);
		double centroY=0;
		if(ecuacion1 instanceof EcuacionRecta){
			if(ecuacion2 instanceof EcuacionRecta){
				interseccion=compensacionContorno.intersectarRectas((EcuacionRecta)ecuacion1,(EcuacionRecta)ecuacion2);
				
			}else{
				interseccion=compensacionContorno.intersectarRectaYArco((EcuacionRecta)ecuacion1,(EcuacionCircunferencia)ecuacion2,(DatosArcos)elemento2);
				
			}
		}else{
			if(ecuacion2 instanceof EcuacionRecta){
				
				interseccion=compensacionContorno.intersectarArcoYRecta((EcuacionRecta)ecuacion2,(EcuacionCircunferencia)ecuacion1,(DatosArcos)elemento1);
				}
			else{
				Coordenadas FinArco= ColeccionFunciones.ObtenerCoordenadaFinEntidad(elemento2);
				if(FinArco.y<((DatosArcos)elemento1).Ycentro){
					centroY=-((DatosArcos)elemento1).Ycentro;
					}else{
						centroY=((DatosArcos)elemento1).Ycentro;
					}
				interseccion=compensacionContorno.intersectarArcos((EcuacionCircunferencia)ecuacion1,(EcuacionCircunferencia)ecuacion2,(DatosArcos)elemento1,(DatosArcos)elemento2);
			}
			
		}
		elemento1=elemento1.cambiarCoordenadaFinal(interseccion);
		elemento2=elemento2.cambiarCoordenadaInicial(interseccion);
		listaCompensada.put(i, elemento1);
		listaCompensada.put(j, elemento2);
	return listaCompensada;	
	}
	
}
