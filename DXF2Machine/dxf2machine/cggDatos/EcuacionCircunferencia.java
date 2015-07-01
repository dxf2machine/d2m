/*------------------------------------------------------------------------------------------ 
Copyright 2014, Celeste Gabriela Guagliano. 

This file is part of DXF2Machine project. 

DXF2Machine is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License. 

DXF2Machine is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. 

You should have received a copy of the GNU General Public License along with DXF2Machine. If not, see <http://www.gnu.org/licenses/>.
  --------------------------------------------------------------------------------------------*/

package cggDatos;

/**
 * This class defines the structure of EcuacionCircunferencia.
 * EcuacionCircunferencia is a data structure that stores the parameters of the equation of a given circle.
 * @author: Celeste G. Guagliano
 * @version: 13/01/15
 * 
 */ 

public class EcuacionCircunferencia extends EcuacionEntidad{
	public double centroX;  
	public double centroY;  
	public double Radio;
	

	/*
	 * a es el parametro que acompania a y
	 * b es el parametro que acompania a x
	 * c es el termino independiente
	 * la ordenada al origen sera c/a
	 * la pendiente de la recta sera b/a
	 * salvo el caso particular en que a=0 y tenemos una recta paralela al eje y
	 */
    public EcuacionCircunferencia(double cx, double cy,double r) {
			this.centroX = cx;
			this.centroY = cy;
			this.Radio = r;
	
		}
	
	
}
