/*------------------------------------------------------------------------------------------ 
Copyright 2014, Celeste Gabriela Guagliano. 

This file is part of DXF2Machine project. 

DXF2Machine is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License. 

DXF2Machine is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. 

You should have received a copy of the GNU General Public License along with DXF2Machine. If not, see <http://www.gnu.org/licenses/>.

For more information, contact us at: dxf2machine@gmail.com
  --------------------------------------------------------------------------------------------*/

package d2mDatos;


import d2mColeccion.ColeccionFunciones;
import d2mGCode.GCode;
import d2mGCode.compensacionContorno;

/**
 * This class defines the structure of DatosLinea
 * DatosLinea is a data structure that stores the parameters needed to generate a line.
 * @author: Celeste G. Guagliano
 * @version: 0.0.1
 * 
 */ 

public class DatosLinea extends datos {

	public DatosLinea(double d, double e, double f, double g, int _color,
			boolean ubicado, int posicion, int o) {

		super(d, e, f, g, _color, ubicado, posicion, o);
	}

	public String mecanizate() {
		String linea = GCode.avanceLineal(this);
		return linea;
	}
	public EcuacionEntidad calculaTuEcuacion(double herramienta){
		EcuacionRecta ecuacion=compensacionContorno.calcularEcuacionRecta(this,herramienta);
		return ecuacion;
	}
	
	public Coordenadas ObtenerPuntoSobreRectaCompensada(double radioHerramienta,
			EcuacionRecta original) {
		double A=0;
		double B=0;
		double C=0;
		double d=radioHerramienta;
		Coordenadas iniciales=ColeccionFunciones.ObtenerCoordenadaInicioEntidad(this);
		Coordenadas finales=ColeccionFunciones.ObtenerCoordenadaFinEntidad(this);
		Coordenadas punto=new Coordenadas(iniciales.x,iniciales.y);
		if(iniciales.x!=finales.x){
			A=original.B*original.B;
			B=2*original.B*(original.A*punto.x+original.C);
			C=Math.pow((original.A*punto.x+original.C),2)-d*d*(original.A*original.A+original.B*original.B);
			double y1=(-B+Math.sqrt(B*B-4*A*C))/2*A;
			double y2=(-B-Math.sqrt(B*B-4*A*C))/2*A;
			if(finales.x>iniciales.x){
				if(y1>y2){
					punto.y=y1;
				}else{
					punto.y=y2;
				}
			}else{
				if(y1>y2){
					punto.y=y2;
				}else{
					punto.y=y1;
				}
			}
					
		}else{
			A=original.A*original.A;
			B=2*original.A*(original.B*punto.y+original.C);
			C=Math.pow((original.B*punto.y+original.C), 2)-d*d*(original.A*original.A+original.B*original.B);
			double x1=(-B+Math.sqrt(B*B-4*A*C))/2*A;
			double x2=(-B-Math.sqrt(B*B-4*A*C))/2*A;
			if(finales.y>iniciales.y){
				if(x1<x2){
					punto.x=x1;
				}else{
					punto.x=x2;
				}
			}else{
				if(x1<x2){
					punto.x=x2;
				}else{
					punto.x=x1;
				}
			}
			
		}
		return punto;
		}


	public DatosLinea desplazateAlCentro(DatosCirculo centro) {
	this.ComienzoX=(double) FormatoNumeros.formatearNumero(this.ComienzoX-centro.CentroX);
	this.FinalX=(double) FormatoNumeros.formatearNumero(this.FinalX-centro.CentroX);
	this.ComienzoY=(double) FormatoNumeros.formatearNumero(this.ComienzoY-centro.CentroY);
	this.FinalY=(double) FormatoNumeros.formatearNumero(this.FinalY-centro.CentroY);
	return this;
	}
	

}
