/*--------------------------------------------------------------------------------------- 
Copyright 2014, Celeste Gabriela Guagliano. 

This file is part of DXF2Machine project. 

DXF2Machine is free software: you can redistribute it and/or modify it under the terms 
of the GNU General Public License as published by the Free Software Foundation, either 
version 2 of the License. 

DXF2Machine is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
See the GNU General Public License for more details. 

You should have received a copy of the GNU General Public License along with DXF2Machine. 
If not, see <http://www.gnu.org/licenses/>.
-----------------------------------------------------------------------------------------*/


package cggDatos;



import cggGCode.GCode;

/**
 * This class defines the structure of datos and it's methods.
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

	/** This method translate the entity into machine code.
	 * @return a string.
	 */
	public String mecanizate() {
		return null;
	

	}

	/** This method generates a jump between two non connected entities.
	 * @return a string.
	 */
	public String saltarASiguiente() {
		String linea = GCode.avanceRapido(this);
		return linea;
	}

	/** This method translate an entity into drill code.
	 * @return a string.
	 */
	public String taladrate() {
	
		return null;
	}

	/** This method calculates the compensation of an entity
	 * @return another entity.
	 */
	public datos compensate() {
	
		return null;
	}
	/** This method calculates the equation that defines an entity.
	 * @param radioHerramienta is the tool diameter
	 * @return an equation.
	 */
	public EcuacionEntidad calculaTuEcuacion(double radioHerramienta) {
	
		return null;
	}

	/** This method compensates a given equation.
	 * @param ecuacion is an equation
	 * @param radioHerramienta is the tool radius.
	 * @param elemento is an entity.
	 * @return an equation.
	 */
	public EcuacionEntidad compensaTuEcuacion(EcuacionEntidad ecuacion, double radioHerramienta, datos elemento) {
	
	
		return null;
	}

	/** This method replaces the end point of an entity.
	 * @param interseccion 
	 * @return an entity.
	 */
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
	/** This method replaces the start point of an entity.
	 * @param interseccion is a point.
	 * @return an entity.
	 */
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

	/** This method calculates the offset of an equation
	 * @param radioHerramienta is the tool radius.
	 * @return an equation.
	 */
	public EcuacionEntidad calculaTuEcuacionDesplazada(double radioHerramienta) {
		
		return null;
	}

	/** This method calculates the new start and end point of an entity with compensation.
	 * @param radioHerramienta is the tool radius.
	 * @return an entity.
	 */
	public datos calculaTusCoordenadasCompensadas(double radioHerramienta) {
	
		return null;
	}

	/** This method calculates a point over an offset line
	 * @param radioHerramienta is the tool radius.
	 * @param original is an equation..
	 * @return a point.
	 */
	public Coordenadas ObtenerPuntoSobreRectaCompensada(double radioHerramienta,
			EcuacionRecta original) {
		
		return null;
	}

	/** This method calculates the displacement of an entity 
	 * @param centro is a point.
	 * @return an entity.
	 */
	public datos desplazateAlCentro(DatosCirculo centro) {
	
	return null;
	}

}
