/*------------------------------------------------------------------------------------------ 
Copyright 2014, Celeste Gabriela Guagliano. 

This file is part of DXF2Machine project. 

DXF2Machine is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License. 

DXF2Machine is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. 

You should have received a copy of the GNU General Public License along with DXF2Machine. If not, see <http://www.gnu.org/licenses/>.
  --------------------------------------------------------------------------------------------*/


package cggDatos;
/**
 * This class defines the structure of Herramienta.
 * Herramienta is a data structure that stores the parameters of a given tool.
 * @author: Celeste G. Guagliano
 * @version: 13/01/15
 * 
 */ 
public class Herramienta {
	public double Diametro = 0;
	public double Avance = 0;
	public double Velocidad = 0;
	public double Pasada = 0;
	public double Numero = 0;
	public double Zseguro=0;
	public double Zcambio=0;

	public Herramienta(double num, double dia, double ava, double velo,
			double pasa,double zseg,double zcam) {
		this.Numero = num;
		this.Diametro = dia;
		this.Velocidad = velo;
		this.Avance = ava;
		this.Pasada = pasa;
		this.Zseguro=zseg;
		this.Zcambio=zcam;
		// TODO Auto-generated constructor stub
	}

}
