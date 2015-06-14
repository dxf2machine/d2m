/*------------------------------------------------------------------------------------------ 
Copyright 2014, Celeste Gabriela Guagliano. 

This file is part of DXF2Machine project. 

DXF2Machine is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License. 

DXF2Machine is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. 

You should have received a copy of the GNU General Public License along with DXF2Machine. If not, see <http://www.gnu.org/licenses/>.
  --------------------------------------------------------------------------------------------*/

package cggDatos;

public class EcuacionCircunferencia extends EcuacionEntidad{
	public double centroX;  
	public double centroY;  
	public double Radio;
	

	/*
	 * a es el parametro que acompaña a y
	 * b es el parametro que acompaña a x
	 * c es el termino independiente
	 * la ordenada al origen será c/a
	 * la pendiente de la recta será b/a
	 * salvo el caso particular en que a=0 y tenemos una recta paralela al eje y
	 */
    public EcuacionCircunferencia(double cx, double cy,double r) {
			this.centroX = cx;
			this.centroY = cy;
			this.Radio = r;
	
		}
	
	
}
