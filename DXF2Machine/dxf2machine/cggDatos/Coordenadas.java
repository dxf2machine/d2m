/*------------------------------------------------------------------------------------------ 
Copyright 2014, Celeste Gabriela Guagliano. 

This file is part of DXF2Machine project. 

DXF2Machine is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License. 

DXF2Machine is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. 

You should have received a copy of the GNU General Public License along with DXF2Machine. If not, see <http://www.gnu.org/licenses/>.
  --------------------------------------------------------------------------------------------*/

package cggDatos;

/**
 * This class defines structure of Coordenadas.
 * Coordenadas is a data structure that stores a pair of coordinates.
 * @author: Celeste G. Guagliano
 * @version: 13/01/15
 * 
 */ 

public class Coordenadas {
	public double x = 0;
	public double y = 0;

	public Coordenadas(double x1, double y1) {
		this.x = x1;
		this.y = y1;
	}

}
