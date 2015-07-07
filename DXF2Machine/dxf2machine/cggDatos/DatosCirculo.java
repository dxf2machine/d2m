/*------------------------------------------------------------------------------------------ 
Copyright 2014, Celeste Gabriela Guagliano. 

This file is part of DXF2Machine project. 

DXF2Machine is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License. 

DXF2Machine is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. 

You should have received a copy of the GNU General Public License along with DXF2Machine. If not, see <http://www.gnu.org/licenses/>.
  --------------------------------------------------------------------------------------------*/

package cggDatos;

import java.util.Hashtable;

import cggColeccion.ColeccionFunciones;
import cggGCode.GCode;
import cggGCode.compensacionContorno;
import cggTablas.Tabla;

/**
 * This class defines the structure of DatosCirculo.
 * DatosCirculo is a data structure that stores the parameters needed to generate a Circle.
 * @author: Celeste G. Guagliano
 * @version: 13/01/15
 * 
 */ 

public class DatosCirculo extends datos {

	public double CentroX, CentroY, Radio;

	public DatosCirculo(double d, double e, double f, double g, double h,
			double i, double j, int _color, boolean ubicado, int posicion, int o) {
		super(d, e, f, g, _color, ubicado, posicion, o);
		CentroX = h;
		CentroY = i;
		Radio = j;
	}

	public String mecanizate() {
		String linea = GCode.avanceCicular(this);
		return linea;
	}

	public String taladrate() {
		String linea = GCode.coordenadasTaladro(this);
		return linea;
	}

	public EcuacionCircunferencia calculaTuEcuacion(double radioHerramienta){
		EcuacionCircunferencia circunferencia=compensacionContorno.calcularEcuacionCircunferencia(this,radioHerramienta);
		return circunferencia;
	
}
    public EcuacionEntidad compensaTuEcuacion(EcuacionEntidad ecuacion,double radioHerramienta, datos elemento) {
		EcuacionCircunferencia compensada=(EcuacionCircunferencia)ecuacion;
	    compensada.Radio=compensada.Radio+radioHerramienta;
		return compensada;
	}

	public static DatosCirculo obtenerCentro() {
        Hashtable ListaCentro = new Hashtable();
		int colorCentro = 1;
		DatosCirculo centro=null;
			ListaCentro = ColeccionFunciones.ObtenerSubconjunto(
			Tabla.ListaEntidades, colorCentro);
			if(ListaCentro.size()==1 && (ListaCentro.get(1) instanceof DatosCirculo) ){
				centro=(DatosCirculo)ListaCentro.get(1);
			}
			return centro;
	}
	public DatosCirculo desplazateAlCentro(DatosCirculo centro) {
		this.ComienzoX=(double) FormatoNumeros.formatearNumero(this.ComienzoX-centro.CentroX);
		this.FinalX=(double) FormatoNumeros.formatearNumero(this.FinalX-centro.CentroX);
		this.ComienzoY=(double) FormatoNumeros.formatearNumero(this.ComienzoY-centro.CentroY);
		this.FinalY=(double) FormatoNumeros.formatearNumero(this.FinalY-centro.CentroY);
		this.CentroX=(double) FormatoNumeros.formatearNumero(this.CentroX-centro.CentroX);
		this.CentroY=(double) FormatoNumeros.formatearNumero(this.CentroY-centro.CentroY);
		return this;
	}
}
