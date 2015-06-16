/*------------------------------------------------------------------------------------------ 
Copyright 2014, Celeste Gabriela Guagliano. 

This file is part of DXF2Machine project. 

DXF2Machine is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License. 

DXF2Machine is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. 

You should have received a copy of the GNU General Public License along with DXF2Machine. If not, see <http://www.gnu.org/licenses/>.
  --------------------------------------------------------------------------------------------*/


package cggDatos;

//import javax.swing.JTextArea;

import cggGCode.GCode;

/**
 * This class defines the structure of datos.
 * datos is a data structure that store the basic data of any entity.
 * @author: Celeste G. Guagliano
 * @version: 13/01/15
 * 
 */ 


public class datos {

	public double ComienzoX;
	public double ComienzoY;
	public double FinalX;
	public double FinalY;
	public int Color;
	public boolean ubicado = false;
	public int posicion = 0;
	public int orientacion = 0;
//	public JTextArea ventana = myDXF.GCode.VentanaGCodeJFrame.ventanaTexto;

	public datos(double d, double e, double f, double g, int _color,
			boolean ubicado, int posicion, int orientacion) {

		this.ComienzoX = d;
		this.ComienzoY = e;
		this.FinalX = f;
		this.FinalY = g;
		this.Color = _color;
		this.ubicado = ubicado;
		this.posicion = posicion;
		this.orientacion = orientacion;
	}

	public String mecanizate() {
		return null;
		// TODO Auto-generated method stub

	}

	public String saltarASiguiente() {
		String linea = GCode.avanceRapido(this);
		return linea;
	}

	public String taladrate() {
		// TODO Auto-generated method stub
		return null;
	}

	public datos compensate() {
		// TODO Auto-generated method stub
		return null;
	}

	public EcuacionEntidad calculaTuEcuacion(double radioHerramienta) {
		// TODO Auto-generated method stub
		return null;
	}

	public EcuacionEntidad compensaTuEcuacion(EcuacionEntidad ecuacion, double radioHerramienta, datos elemento) {
		// TODO Auto-generated method stub
	
		return null;
	}

	public datos cambiarCoordenadaFinal(Coordenadas interseccion) {
		if(this.orientacion==0){
			this.FinalX=interseccion.x;
			this.FinalY=interseccion.y;
		}else{
			this.ComienzoX=interseccion.x;
			this.ComienzoY=interseccion.y;
		}
		return this;
	}

	public datos cambiarCoordenadaInicial(Coordenadas interseccion) {
		if(this.orientacion==0){
			this.ComienzoX=interseccion.x;
			this.ComienzoY=interseccion.y;
		}else{
			this.FinalX=interseccion.x;
			this.FinalY=interseccion.y;
			
		}
		return this;
		
	}

	public EcuacionEntidad calculaTuEcuacionDesplazada(double radioHerramienta) {
		// TODO Auto-generated method stub
		return null;
	}

	public datos calculaTusCoordenadasCompensadas(double radioHerramienta) {
		// TODO Auto-generated method stub
		return null;
	}

	public Coordenadas ObtenerPuntoSobreRectaCompensada(double herramienta,
			EcuacionRecta original) {
		// TODO Auto-generated method stub
		return null;
	}

	public datos desplazateAlCentro(DatosCirculo centro) {
		// TODO Auto-generated method stub
	return null;
	}

}
