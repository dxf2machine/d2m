/*------------------------------------------------------------------------------------------ 
Copyright 2014, Celeste Gabriela Guagliano. 

This file is part of DXF2Machine project. 

DXF2Machine is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License. 

DXF2Machine is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. 

You should have received a copy of the GNU General Public License along with DXF2Machine. If not, see <http://www.gnu.org/licenses/>.
  --------------------------------------------------------------------------------------------*/

package cggDatos;

import cggGCode.GCode;
import cggGCode.compensacionContorno;

/**
 * This class defines the structure of DatosArcos
 * DatosArcos is a data structure that stores the parameters needed to generate an arc.
 * @author: Celeste G. Guagliano
 * @version: 13/01/15
 * 
 */ 

public class DatosArcos extends datos {
	public double radio, Xcentro, Ycentro, AngI, AngF;

	public DatosArcos(double d, double e, double f, double g, double h,
			double i, double j, double k, double l, int _color,
			boolean ubicado, int posicion, int o) {
		super(d, e, f, g, _color, ubicado, posicion, o);
		this.Xcentro = h;
		this.Ycentro = i;
		this.radio = j;
		this.AngI = k;
		this.AngF = l;
		// TODO Auto-generated constructor stub
	}

	public String mecanizate() {
		// hace tu gracia.
		String linea = GCode.avanceSegmentoCircular(this);
		return linea;
	}
	
	public EcuacionCircunferencia calculaTuEcuacion(double radioHerramienta){
		EcuacionCircunferencia circunferencia=compensacionContorno.calcularEcuacionCircunferencia(this,radioHerramienta);
		return circunferencia;
	}
	
	public EcuacionEntidad compensaTuEcuacion(EcuacionEntidad ecuacion,double radioHerramienta, datos elemento) {
		EcuacionCircunferencia compensada=(EcuacionCircunferencia)ecuacion;
		return ecuacion;
	}

	public double obtenerAnguloInicial() {
		double angulo=0;
		if (this.orientacion==0){
			angulo=this.AngI;
		}else
			angulo=this.AngF;
		// TODO Auto-generated method stub
		return angulo;
	}

	public double obtenerAnguloFinal() {
		double angulo=0;
		if (this.orientacion==0){
			angulo=this.AngF;
		}else
			angulo=this.AngI;
		// TODO Auto-generated method stub
		return angulo;
	}
	
	public DatosArcos desplazateAlCentro(DatosCirculo centro) {
		this.ComienzoX=(double) FormatoNumeros.formatearNumero(this.ComienzoX-centro.CentroX);
		this.FinalX=(double) FormatoNumeros.formatearNumero(this.FinalX-centro.CentroX);
		this.ComienzoY=(double) FormatoNumeros.formatearNumero(this.ComienzoY-centro.CentroY);
		this.FinalY=(double) FormatoNumeros.formatearNumero(this.FinalY-centro.CentroY);
		this.Xcentro=(double) FormatoNumeros.formatearNumero(this.Xcentro-centro.CentroX);
		this.Ycentro=(double) FormatoNumeros.formatearNumero(this.Ycentro-centro.CentroY);
		return this;
	}
	
}
