/*------------------------------------------------------------------------------------------ 
Copyright 2014, Celeste Gabriela Guagliano. 

This file is part of DXF2Machine project. 

DXF2Machine is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License. 

DXF2Machine is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. 

You should have received a copy of the GNU General Public License along with DXF2Machine. If not, see <http://www.gnu.org/licenses/>.
  --------------------------------------------------------------------------------------------*/
//Esto es una prueba de subida desde otra compu a git
//La prueba funciona
package cggColeccion;


import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;

import cggDatos.Coordenadas;
import cggDatos.DatosCirculo;
import cggDatos.DatosLinea;
import cggDatos.FormatoNumeros;
import cggDatos.Herramienta;
import cggDatos.datos;
import myDXF.DXF_Loader;

/**
 * This class defines util methods for colections.
 * @author: Celeste G. Guagliano
 * @version: 13/01/15
 * 
 */ 


public class ColeccionFunciones {

	/** Method to select a sub-group of elements from a list. 
	 * @param listaEntidades is an entities hashtable.
	 * @param condicion is the selection criteria. 
	 * @return a list of entities. 
	 */
	
	public static Hashtable ObtenerSubconjunto(HashSet listaEntidades,
			int condicion) {
		Hashtable aux = new Hashtable();
		for (Iterator it = listaEntidades.iterator(); it.hasNext();) {
			datos elemento = (datos) it.next();
			if (elemento.Color == condicion) {
				aux.put(aux.size() + 1, elemento);

			}
		}
		return aux;
	}

	/** Method to get the initial coordinate of the first element in a list. 
	 * @param lista is an ordered entities hashtable. 
	 * @return the value of the initial coordinate of the first element of the list. 
	 */
	
	public static Coordenadas ObtenerCoordenadaInicial(Hashtable lista) {
		datos inicial = (datos) lista.get(0);
		double x1 = 0, y1 = 0;
		Coordenadas coordInicial = new Coordenadas(0, 0);
		if (inicial.orientacion == 0) {
			coordInicial = new Coordenadas(inicial.ComienzoX, inicial.ComienzoY);
		} else {
			coordInicial = new Coordenadas(inicial.FinalX, inicial.FinalY);
		}

		return coordInicial;
	}
	
	/** Method to initialize an ordered list 
	 * @param lista is an ordered entities hashtable. 
	 * @return the key of the first ordered element. 
  	 */
	public static int ObtenerElementoInicial(Hashtable lista) {
		Hashtable iniciales = new Hashtable();
		int llave = 0;
		Coordenadas comparativa = new Coordenadas(0, 0);
		comparativa = ObtenerCoordenadaMinima(lista);
		iniciales = MinimaDistancia(lista, comparativa);
		llave = SeleccionarUnInicial(iniciales, comparativa, lista);
		return llave;
	}
	
	/** Method to decide between two possible first elements of an ordered list. 
	 * @param iniciales is an ordered entities hashtable.
	 * @param comparativa it's the criteria for decision
	 * @param lista it's an ordered list of entities.
	 * @return the key of the selected element. 
	 */
	private static int SeleccionarUnInicial(Hashtable iniciales,
			Coordenadas comparativa, Hashtable lista) {
		// TODO Teniendo una lista de posibles entidades iniciales, este metodo aplica un criterio de selección para decidir cual es el inicial que se utiliza. 
		datos elemento = null;
		if (iniciales.size() > 1) {
			Coordenadas coinciden = new Coordenadas(0, 0);
			coinciden = ObtenerCoincidente(iniciales);
			double alfa1 = 0;
			double alfa2 = 0;
			datos elemento1 = (datos) iniciales.get(1);
			datos elemento2 = (datos) iniciales.get(2);
			double x1 = elemento1.ComienzoX;
			double y1 = elemento1.ComienzoY;
			double x2 = elemento2.ComienzoX;
			double y2 = elemento2.ComienzoY;
			if (x1 == coinciden.x && y1 == coinciden.y) {
				x1 = elemento1.FinalX;
				y1 = elemento1.FinalY;
			} else {
				elemento1.orientacion = -1;
			}
			if (x2 == coinciden.x && y2 == coinciden.y) {
				x2 = elemento2.FinalX;
				y2 = elemento2.FinalY;
			} else {
				elemento2.orientacion = -1;
			}
			double Xmin = comparativa.x;
			double Ymin = comparativa.y;
			alfa1 = Math.atan((y1 - Ymin) / (x1 - Xmin));
			alfa2 = Math.atan((y2 - Ymin) / (x2 - Xmin));
			if (alfa2 < alfa1) {
				elemento = elemento1;
			} else {
				elemento = elemento2;
			}

		} else {
			elemento = (datos) iniciales.get(1);
			elemento = ComprobarOrdenCoordenada(elemento, comparativa);
		}
		int clave = 0;
		for (Enumeration k = lista.keys(); k.hasMoreElements();) {
			int llave = (int) k.nextElement();
			datos valor = (datos) lista.get(llave);
			if (valor == elemento) {
				clave = llave;
			}
		}
		return clave;
	}

	/**
	 *  Method to define the orientation of an entity analyzing it's coordinates in the context of the list. 
	 * @param elemento it's an entity.
	 * @param comparativa it's the evaluation criteria. 
	 * @return an oriented element. 
	 */
	
	private static datos ComprobarOrdenCoordenada(datos elemento,
			Coordenadas comparativa) {
		//TODO Este método decide si la entidad en cuestión se encuentra orientada en forma directa o inversa
		// es decir, si sus coordenadas iniciales son las correctas en el conjunto o debe tomarse sus coordenadas finales como iniciales y viceversa.
		double x1 = elemento.ComienzoX;
		double y1 = elemento.ComienzoY;
		double x2 = elemento.FinalX;
		double y2 = elemento.FinalY;
		double distancia1 = Math.sqrt(Math.pow(x1 - comparativa.x, 2)
				+ Math.pow(y1 - comparativa.y, 2));
		double distancia2 = Math.sqrt(Math.pow(x2 - comparativa.x, 2)
				+ Math.pow(y2 - comparativa.y, 2));
		if (distancia1 < distancia2) {
			elemento.orientacion = 0;
		} else {
			elemento.orientacion = -1;
		}
		return elemento;
	}
	
	/** Method to compare coordinates and get the matched pair. 
	 * @param iniciales it's a list of two entities.
	 * @return the matched coordinate. 
	 */
	private static Coordenadas ObtenerCoincidente(Hashtable iniciales) {
		//TODO Este método compara las coordenas de dos entidades y obtiene un par coincidente.
		Coordenadas coincidentes = new Coordenadas(0, 0);
		datos elemento1 = (datos) iniciales.get(1);
		datos elemento2 = (datos) iniciales.get(2);
		double x1 = elemento1.ComienzoX;
		double y1 = elemento1.ComienzoY;
		double x2 = elemento2.ComienzoX;
		double y2 = elemento2.ComienzoY;
		if (x1 == x2 && y1 == y2) {
			coincidentes = new Coordenadas(x1, y1);
		} else {
			x1 = elemento1.FinalX;
			y1 = elemento1.FinalY;
			if (x1 == x2 && y1 == y2) {
				coincidentes = new Coordenadas(x1, y1);
			} else {
				x2 = elemento2.FinalX;
				y2 = elemento2.FinalY;
				if (x1 == x2 && y1 == y2) {
					coincidentes = new Coordenadas(x1, y1);
				} else {
					x1 = elemento1.ComienzoX;
					y1 = elemento1.ComienzoY;
					coincidentes = new Coordenadas(x1, y1);
				}
			}
		}
		return coincidentes;
	}

	/** Method to calculate the minimun distance between the elements of a list and a pair of comparative coordinates. 
	 * @param lista a list of entities.
	 * @param comparativa the comparative coordinates.
	 * @return a list of maximum two entities that meet the criteria. 
	 */
	private static Hashtable MinimaDistancia(Hashtable lista,
			Coordenadas comparativa) {
		//TODO Este método recibe una lista de entidades y calcula cual es la entidad que posee un par de coordenadas de distancia minima a un punto de referencia dado.
		Hashtable elementosIniciales = new Hashtable();
		datos inicial1 = null;
		for (Enumeration e = lista.keys(); e.hasMoreElements();) {
			int clave = (int) e.nextElement();
			inicial1 = (datos) lista.get(clave);
		}
		double distancia1 = CalcularDistanciaAReferencia(comparativa, inicial1);
		int clave = 0;
		int clave2 = 0;
		datos inicial2 = null;
		double distancia2 = 0;
		for (Enumeration e = lista.keys(); e.hasMoreElements();) {
			int key = (int) e.nextElement();
			datos pinicial = (datos) lista.get(key);
			double distancia = CalcularDistanciaAReferencia(comparativa,
					pinicial);
			if (distancia < distancia1) {
				clave = key;
				inicial1 = pinicial;
				distancia1 = distancia;
			} else if (distancia == distancia1 && pinicial != inicial1) {
				clave2 = key;
				inicial2 = pinicial;
			}
		}
		elementosIniciales.put(1, inicial1);
		if (inicial2 != null) {
			elementosIniciales.put(2, inicial2);
		}

		return elementosIniciales;

	}

	/** Method to compare coordinates of entities 
	 * @param inicial1 it's the data of an entity.
	 * @param inicial2 it's the data of an entity.
	 * @return true if there's a coincidence, false otherwise. 
	 */
		private static boolean compararEntidades(datos inicial1, datos inicial2) {
		//TODO Este metodo compara entidades para saber si comparten un punto, es decir un par de coordenadas.
		//mal puesto el nombre, debería ser "compararCoordenadasEntidades" o algo similar.
		double x1 = inicial1.ComienzoX;
		double x2 = inicial2.ComienzoX;
		double y1 = inicial1.ComienzoY;
		double y2 = inicial2.ComienzoY;
		if (x1 == x2 && y1 == y2) {
			return true;
		} else {
			x1 = inicial1.FinalX;
			y1 = inicial1.FinalY;
			if (x1 == x2 && y1 == y2) {
				return true;
			} else {
				x2 = inicial2.FinalX;
				y2 = inicial2.FinalY;
				if (x1 == x2 && y1 == y2) {
					return true;
				} else {
					x1 = inicial1.ComienzoX;
					y1 = inicial1.ComienzoY;
					if (x1 == x2 && y1 == y2) {
						return true;
					} else {
						// TODO Auto-generated method stub
						return false;
					}
				}
			}
		}
	}

		/** Method to calculate the distance between a couple of pair coordinates. 
		 * @param comparativa it's the reference pair coordinate.
		 * @param inicial1 it's the element whose coordinates are analyzed 
		 * @return the calculated distance.
		 */
		private static double CalcularDistanciaAReferencia(Coordenadas comparativa,
			datos inicial1) {
		//TODO Este método calcula la distancia entre un par de coordenadas y un punto de referencia, 
		//compara el valor obtenido calculando con la coordenada inicial y final de la entidad y devuelve el menor.
		double x1 = inicial1.ComienzoX;
		double y1 = inicial1.ComienzoY;
		double x2 = inicial1.FinalX;
		double y2 = inicial1.FinalY;
		double distancia1 = Math.sqrt(Math.pow(x1 - comparativa.x, 2)
				+ Math.pow(y1 - comparativa.y, 2));
		double distancia2 = Math.sqrt(Math.pow(x2 - comparativa.x, 2)
				+ Math.pow(y2 - comparativa.y, 2));
		if (distancia1 < distancia2) {
			return distancia1;
		} else {
			return distancia2;
		}

	}

		/** Method to get the minimum value of the x and y coordinates regardless of whether these values are from the same entity or not 
		 * @param lista it's a list of entities.
		 * @return the minimun coordinates. 
		 */
		private static Coordenadas ObtenerCoordenadaMinima(Hashtable lista) {
		//TODO recibe una lista y busca entre las coordenadas de sus entidades el menor valor de X y de Y, le resta un valor de seguridad y devuelve este par de coordenadas.
		Coordenadas minimas = new Coordenadas(0, 0);
		minimas = InicializarCoordenadas(lista);
		double x = minimas.x;
		double y = minimas.y;
		for (Enumeration e = lista.elements(); e.hasMoreElements();) {
			datos elemento = (datos) e.nextElement();
			double nuevoX = elemento.ComienzoX;
			double nuevoY = elemento.ComienzoY;
			x = ChequearCoordenada(x, nuevoX);
			y = ChequearCoordenada(y, nuevoY);
			nuevoX = elemento.FinalX;
			nuevoY = elemento.FinalY;
			x = ChequearCoordenada(x, nuevoX);
			y = ChequearCoordenada(y, nuevoY);
		}
		minimas = new Coordenadas(x - 2, y - 2);

		return minimas;
	}

		/** Method to get a random pair of coordinates from a list   
		 * @param lista it's a list of entities.
		 * @return a random pair of coordinates. 
		 */
			
	private static Coordenadas InicializarCoordenadas(Hashtable lista) {
		//TODO Este metodo toma un elemento cualquiera de una tabla y devuelve sus coordenadas iniciales.
		datos elemento = new datos(0, 0, 0, 0, 0, false, 0, 0);
		Coordenadas iniciales = new Coordenadas(0, 0);
		for (Enumeration e = lista.elements(); e.hasMoreElements();) {
			elemento = (datos) e.nextElement();
		}
		iniciales = new Coordenadas(elemento.ComienzoX, elemento.ComienzoY);
		return iniciales;
	}

	/** Method to compare two numbers. 
	 * @param valor it's a number.
	 * @param nuevoValor it's a number.
	 * @return the minimun number. 
	 */
	private static double ChequearCoordenada(double valor, double nuevoValor) {
		//TODO compara el valor de dos coordenadas y devuelve el menor.
		if (nuevoValor < valor) {
			return nuevoValor;
		} else {
			return valor;
		}
	}

	/** Method for generating an ordered list. 
	 * @param ordenados it's a list of ordered entities.
	 * @param lista it's a list of entities to be ordered.
	 * @return an ordered list.
	 */
	public static Hashtable ObtenerElementosOrdenados(Hashtable ordenados,
			Hashtable lista) {
		//TODO Toma las entidades de una tabla, las ordena y las coloca en una tabla siguiendo el orden.
		//Devuelve la tabla ordenada.
		ordenados = ObtenerSiguienteElemento(ordenados, lista);
		return ordenados;
	}

	/** Method to get the next element for an ordered list fulfilling certain criteria
	 * @param ordenados it's a list of ordered entities.
	 * @param lista it's a list of entities to be ordered.
	 * @return an ordered list.
	 */
	private static Hashtable ObtenerSiguienteElemento(Hashtable ordenados,
			Hashtable listaContorno) {
		//TODO Este método toma el último valor de la tabla de elementos ordenados, busca el siguiente en la tabla no ordenada según el criterio de ordenamiento
		// lo coloca en la tabla ordenada y lo saca de la tabla original.
		do {
			datos elemento = ComprobarCoordenadaInicial(ordenados,
					listaContorno);
			if (elemento == null) {
				elemento = ComprobarCoordenadaFinal(ordenados, listaContorno);
			}
			if (elemento == null) {
				elemento = ObtenerElementoAleatorio(listaContorno);
			}
			int clave = ordenados.size() + 1;
			ordenados.put(clave, elemento);
			for (Enumeration e = listaContorno.keys(); e.hasMoreElements();) {
				int key = (int) e.nextElement();
				datos ele = (datos) listaContorno.get(key);
				if (ele == elemento) {
					listaContorno.remove(key);
				}
			}
		} while (listaContorno.size() > 1);
		for (Enumeration e = listaContorno.keys(); e.hasMoreElements();) {
			int key = (int) e.nextElement();
			datos ele = (datos) listaContorno.get(key);
			listaContorno.remove(key);
			ordenados.put(ordenados.size() + 1, ele);
		}
		return ordenados;
	}

	/** Method to get a random element of a list.
	 * @param lista it's a list of entities 
	 * @return an element.
	 */
	private static datos ObtenerElementoAleatorio(Hashtable listaContorno) {
		//TODO devuelve un elemento al azar de la lista recibida.
		int llave = 0;
		llave = ObtenerElementoInicial(listaContorno);
		datos elemento = (datos) listaContorno.get(llave);
		return elemento;
	}

	/** Method to compare if the final coordinate of the last item in an ordered list matches with the final coordinate of any other element of the orginal list.
	 * @param ordenados it's a list of ordered entities.
	 * @param lista it's a list of entities to be ordered.
	 * @return an element.
	 */
	private static datos ComprobarCoordenadaFinal(Hashtable ordenados,
			Hashtable lista) {
		//TODO chequea si la coordenada final del ultimo elemento de una lista ordenada coincide con la coordenada final de algun otro elemento de la lista original.
		datos elemento = null;
		boolean elementoEncontrado = false;
		Coordenadas FinalUltimoElementoOrdenado = ObtenerCoordenadaFinal(ordenados);
		for (Enumeration e = lista.elements(); e.hasMoreElements();) {
			datos siguiente = (datos) e.nextElement();
			Coordenadas SiguienteElemento = new Coordenadas(siguiente.FinalX,
					siguiente.FinalY);
			elementoEncontrado = CompararCoordenadas(
					FinalUltimoElementoOrdenado, SiguienteElemento);
			if (elementoEncontrado == true) {
				elemento = siguiente;
				elemento.orientacion = -1;
			}
		}
		return elemento;
	}

	/** Method to compare a couple of pair coordinates.
	 * @param dato1 a pair coordinates.
	 * @param dato2 a pair coordinates
	 * @return true if the coordinates match, false otherwise.
	 */
	private static boolean CompararCoordenadas(Coordenadas dato1,
			Coordenadas dato2) {
	//TODO comprueba si un par de coordenadas coinciden, si es así devuelve true, caso contrario devuelve false.
		boolean coincidencia = false;
		if (dato1.x == dato2.x && dato1.y == dato2.y) {
			coincidencia = true;
		}
		return coincidencia;
	}

	/** Method to compare if the final coordinate of the last item in an ordered list matches with the initial coordinate of any other element of the orginal list.
	 * @param ordenados it's a list of ordered entities.
	 * @param lista it's a list of entities to be ordered.
	 * @return an element.
	 */
	private static datos ComprobarCoordenadaInicial(Hashtable ordenados,
			Hashtable listaContorno) {
		//TODO Chequea si la coordenada final del ultimo elemento de la tabla ordenada coincide con la coordenada inicial de algun elemento de la tabla original.
		datos elemento = null;
		boolean elementoEncontrado = false;
		Coordenadas FinalUltimoElementoOrdenado = ObtenerCoordenadaFinal(ordenados);
		for (Enumeration e = listaContorno.elements(); e.hasMoreElements();) {
			datos siguiente = (datos) e.nextElement();
			Coordenadas SiguienteElemento = new Coordenadas(
					siguiente.ComienzoX, siguiente.ComienzoY);
			elementoEncontrado = CompararCoordenadas(
					FinalUltimoElementoOrdenado, SiguienteElemento);
			if (elementoEncontrado == true) {
				elemento = siguiente;
				elemento.orientacion = 0;
				return elemento;
			}
		}
		
		return elemento;
	}

	
	/** Method to determine whether a list of elements form a closed contour.
	 * @param listaContornoOrdenada it's a list of ordered entities.
	 * @return true if the contour is closed, false otherwise.
	 */
	 public static boolean EvaluarContornoCerrado(Hashtable listaContornoOrdenada) {
		// TODO Evalúa si hay continuidad entre los elementos de la tabla, es decir que todos los elementos se toquen entre si, incluyendo el ultimo con el primero.
		Coordenadas iniciales = new Coordenadas(0, 0);
		Coordenadas finales = new Coordenadas(0, 0);
		int clave = 1;
		boolean comparteCoordenada = true;
		for (Enumeration k = listaContornoOrdenada.keys(); k.hasMoreElements();) {
			clave = (int) k.nextElement();
			datos elemento1 = (datos) listaContornoOrdenada.get(clave);
			if (k.hasMoreElements()) {
				clave = (int) k.nextElement();
				datos elemento2 = (datos) listaContornoOrdenada.get(clave);
				if (comparteCoordenada == true) {
					comparteCoordenada = compararEntidades(elemento1, elemento2);
				}
			}
		}
		if (comparteCoordenada == true) {
			datos elemento1 = (datos) listaContornoOrdenada.get(1);
			datos elemento2 = (datos) listaContornoOrdenada
					.get(listaContornoOrdenada.size());
			comparteCoordenada = compararEntidades(elemento1, elemento2);
		}
		return comparteCoordenada;
	}

	 /** Method to orientate a closed contour.
	  * @param listaOrdenada it's a list of ordered entities.
	  * @return a list of orientated elements.
	 */
		 
	public static Hashtable OrientarContornoCerrado(Hashtable listaOrdenada) {
		//TODO revisa la orientación de los elementos ordenados en la tabla y la corrije si es necesario.
		int clave = 1;
		if(listaOrdenada.size()>1){
		do {
			datos elemento1 = (datos) listaOrdenada.get(clave);
			clave += 1;
			datos elemento2 = (datos) listaOrdenada.get(clave);
			listaOrdenada.remove(clave);
			elemento2 = OrientarElemento(elemento1, elemento2);
			listaOrdenada.put(clave, elemento2);
		  } while (clave < listaOrdenada.size());
		}
		return listaOrdenada;
	}

/*	private static datos OrientarElementoFinal(datos elemento1, datos elemento2) {
		// TODO Auto-generated method stub
		return null;
	}*/

	/** Method to orientate an element using another element as a criterion.
	 * @param elemento1 the element to be oriented
	 * @param elemento2 the criteria of orientation.
	 * @return an oriented element.
	 */
	private static datos OrientarElemento(datos elemento1, datos elemento2) {
		//TODO Chequea cuales son las coordenadas que se tocan entre dos elementos y en base a esto orienta los mismos.
		double x1 = 0, y1 = 0, x2 = 0, y2 = 0;
		if (elemento1.orientacion == 0) {
			x1 = elemento1.FinalX;
			y1 = elemento1.FinalY;
		} else {
			x1 = elemento1.ComienzoX;
			y1 = elemento1.ComienzoY;
		}
		x2 = elemento2.ComienzoX;
		y2 = elemento2.ComienzoY;
		if (x1 == x2 && y2 == y1) {
			elemento2.orientacion = 0;
		} else {
			elemento2.orientacion = -1;
		}
		return elemento2;
	}


	/** Method to orientate an element using another element as a criterion.
	 * @param elemento1 the element to be oriented
	 * @param elemento2 the criteria of orientation.
	 * @return an oriented element.
	 */
	private static Coordenadas ObtenerCoordenadaFinal(
			Hashtable listaContornoOrdenada) {
		//TODO Devuelve la coordenada final de un elemento teniendo en cuenta su orientación.
		Coordenadas coordFinales = new Coordenadas(0, 0);
		datos elementoFinal = (datos) listaContornoOrdenada
				.get(listaContornoOrdenada.size());
		if (elementoFinal.orientacion == 0) {
			coordFinales = new Coordenadas(elementoFinal.FinalX,
					elementoFinal.FinalY);
		} else {
			coordFinales = new Coordenadas(elementoFinal.ComienzoX,
					elementoFinal.ComienzoY);
		}

		return coordFinales;
	}

	/** Method to select the entity in the original list that meets the criteria to initialize the ordered list. 
	 * @param datos is an entities list.
	 * @return a list of ordered entities. 
	 */
	public static Hashtable InicializarTablaOrdenada(Hashtable datos) {
		//TODO coloca el elemento inicial en la tabla ordenada.
		Hashtable ordenada = new Hashtable();
		int elementoInicial = ObtenerElementoInicial(datos);
		datos elemento = (datos) datos.get(elementoInicial);
		ordenada.put(1, elemento);
		return ordenada;
	}

	/** Method to check if two entities share any pair of coordinates. 
	 * @param elemento1 is an entity.
	 * @param elemento2 is an entity.
	 * @return true if any coordinates match, false otherwise. 
	 */
	public static boolean compartenCoordenada(datos elemento1, datos elemento2) {
		//TODO chequea si dos elementos comparten coordenadas.		
		boolean comparten = false;
		double x1 = 0, y1 = 0, x2 = 0, y2 = 0;
		if (elemento1.orientacion == 0) {
			x1 = elemento1.FinalX;
			y1 = elemento1.FinalY;
		} else {
			x1 = elemento1.ComienzoX;
			y1 = elemento1.ComienzoY;
		}
		if (elemento2.orientacion == 0) {
			x2 = elemento2.ComienzoX;
			y2 = elemento2.ComienzoY;
		} else {
			x2 = elemento2.FinalX;
			y2 = elemento2.FinalY;
		}
		if (x1 == x2 && y1 == y2) {
			comparten = true;
		}
		return comparten;
	}

	/** Method to remove unneeded elements of a list. 
	 * @param lista is the original list
	 * @param listaOptimizada is the resultant list.
	 * @return a new list.
	 */
	public static Hashtable ObtenerNuevaLista(Hashtable lista,
			Hashtable listaOptimizada) {
		//TODO elimina de la tabla original los elementos que ya fueron incluidos en la tabla ordenada.
		for (Enumeration k = lista.keys(); k.hasMoreElements();) {
			int clave = (int) k.nextElement();
			datos elemento = (datos) lista.get(clave);
			if (listaOptimizada.contains(elemento)) {
				lista.remove(clave);
			}
		}
		return lista;
	}

	public static Hashtable ObtenerTocho(Hashtable listaContorno,
			Hashtable herramientas) {
		// TODO Encuentra las coordenadas maximas y minimas del contorno, y teniendo en cuenta estas coordenadas y la herramienta a utilizar calcula la tabla de trayectoria del planeado
		Hashtable tocho = new Hashtable();
		Coordenadas inicial = ObtenerCoordenadaMinima(listaContorno);
		Coordenadas finales = ObtenerCoordenadaMaxima(listaContorno);
		DatosCirculo elemento = chequearElementos(listaContorno);
		if (elemento != null) {
			inicial.x =(double) FormatoNumeros.formatearNumero(elemento.CentroX - elemento.Radio);
			inicial.y = (double) FormatoNumeros.formatearNumero(elemento.CentroY - elemento.Radio);
			finales.x = (double) FormatoNumeros.formatearNumero(elemento.CentroX + elemento.Radio);
			finales.y = (double) FormatoNumeros.formatearNumero(elemento.CentroY + elemento.Radio);
		}
		Herramienta planeado = (Herramienta) herramientas.get("Planeado");
		double diametro = planeado.Diametro;
		tocho = GenerarTablaTocho(inicial, finales, diametro);
		return tocho;
	}

	private static DatosCirculo chequearElementos(Hashtable listaContorno) {
		// TODO chequea si los elementos de la lista son circulos.
		DatosCirculo elemento = null;
		for (Enumeration e = listaContorno.elements(); e.hasMoreElements();) {
			datos ele = (datos) e.nextElement();
			if (ele instanceof DatosCirculo) {
				elemento = (DatosCirculo) ele;
			}
		}
		return elemento;
	}

	private static Hashtable GenerarTablaTocho(Coordenadas inicial,
			Coordenadas finales, double diametro) {
		//TODO genera la tabla de trayectorias del planeado
		Hashtable tocho = new Hashtable();
		inicial.x =(double) FormatoNumeros.formatearNumero(inicial.x	- diametro);
		finales.x =(double) FormatoNumeros.formatearNumero(finales.x	+ diametro);
		DatosLinea primerLinea = new DatosLinea(inicial.x, inicial.y,
				finales.x, inicial.y, 0, false, 0, 0);
		tocho.put(1, primerLinea);
		DatosLinea linea = new DatosLinea(0, 0, 0, 0, 0, false, 0, 0);
		DatosLinea anterior = new DatosLinea(0, 0, 0, 0, 0, false, 0, 0);
		DatosLinea arriba = new DatosLinea(0, 0, 0, 0, 0, false, 0, 0);
		int key = 1;
		do {
			anterior = (DatosLinea) tocho.get(key);
			arriba = DesplazateVertical(anterior, diametro);
			linea = DesplazateHorizontal(arriba, diametro, inicial, finales);
			key += 1;
			tocho.put(key, arriba);
			key += 1;
			tocho.put(key, linea);
		} while (linea.FinalY < finales.y);
		return tocho;
	}

	private static DatosLinea DesplazateHorizontal(DatosLinea arriba,
			double diametro, Coordenadas inicial, Coordenadas finales) {
		//TODO genera una linea horizontal
		DatosLinea horizontal = new DatosLinea(0, 0, 0, 0, 0, false, 0, 0);
		horizontal.ComienzoX = arriba.FinalX;
		horizontal.ComienzoY = arriba.FinalY;
		horizontal.FinalY = arriba.FinalY;
		if (arriba.FinalX == inicial.x) {
			horizontal.FinalX = finales.x;
		} else {
			horizontal.FinalX = inicial.x;
		}
		return horizontal;
	}

	private static DatosLinea DesplazateVertical(DatosLinea anterior,
			double diametro) {
		//TODO genera una linea vertical
		DatosLinea arriba = new DatosLinea(0, 0, 0, 0, 0, false, 0, 0);
		arriba.ComienzoX = anterior.FinalX;
		arriba.FinalX = anterior.FinalX;
		arriba.ComienzoY = anterior.FinalY;
		arriba.FinalY = (double) FormatoNumeros.formatearNumero(anterior.FinalY+ .7 * diametro);
		return arriba;
	}

	private static Coordenadas ObtenerCoordenadaMaxima(Hashtable listaContorno) {
		// TODO obtiene el maximo valor de X e Y entre las coordenadas de la lista recibida
		Coordenadas maximas = new Coordenadas(0, 0);
		maximas = InicializarCoordenadas(listaContorno);
		double x = maximas.x;
		double y = maximas.y;
		for (Enumeration e = listaContorno.elements(); e.hasMoreElements();) {
			datos elemento = (datos) e.nextElement();
			double nuevoX = elemento.ComienzoX;
			double nuevoY = elemento.ComienzoY;
			x = ChequearCoordenadaMax(x, nuevoX);
			y = ChequearCoordenadaMax(y, nuevoY);
			nuevoX = elemento.FinalX;
			nuevoY = elemento.FinalY;
			x = ChequearCoordenadaMax(x, nuevoX);
			y = ChequearCoordenadaMax(y, nuevoY);
		}
		maximas = new Coordenadas(x, y);

		return maximas;
	}

	private static double ChequearCoordenadaMax(double x, double nuevoX) {
		//TODO compara el par de dos coordenadas y devuelve el mayor
		if (nuevoX > x) {
			return nuevoX;
		} else {
			return x;
			// TODO Auto-generated method stub
		}

	}

	public static Coordenadas ObtenerCoordenadaFinEntidad(datos inicial) {
		//TODO devuelve el valor de la coordenada final de una entidad teniendo en cuenta su orientacion
		Coordenadas finales = new Coordenadas(0, 0);
		if (inicial.orientacion == 0) {
			finales.x = inicial.FinalX;
			finales.y = inicial.FinalY;
		} else {
			finales.x = inicial.ComienzoX;
			finales.y = inicial.ComienzoY;
					}
		return finales;
	}

	public static Coordenadas ObtenerCoordenadaInicioEntidad(datos inicial) {
		//TODO devuelve el valor de la coordenada inicial de una entidad teniendo en cuenta su orientacion
		Coordenadas iniciales = new Coordenadas(0, 0);
		if (inicial.orientacion == 0) {
			iniciales.x = inicial.ComienzoX;
			iniciales.y = inicial.ComienzoY;
		} else {
			iniciales.x = inicial.FinalX;
			iniciales.y = inicial.FinalY;
		}
		return iniciales;
	}

	public static Coordenadas ObtenerCoordenadaCentroEntidad(datos datos) {
		//TODO Obtiene la coordenada del centro de una entidad siempre que se trate de un circulo.
		Coordenadas centro= new Coordenadas(0,0);
		if(datos instanceof DatosCirculo){
			centro= new Coordenadas(((DatosCirculo)datos).CentroX,((DatosCirculo)datos).CentroY);
		}
		return centro;
	}

	public static Hashtable ObtenerCirculos(Hashtable lista) {
		// TODO extrae los circulos de una lista dada y los devuelve en otra lista.
		Hashtable seleccion= new Hashtable();
		for (Enumeration e = lista.elements(); e.hasMoreElements();){
			datos elemento = (datos) e.nextElement();
			if(elemento instanceof DatosCirculo){
				seleccion.put(seleccion.size()+1, elemento);
			}
			
		}
			return seleccion;	
	}
	
}