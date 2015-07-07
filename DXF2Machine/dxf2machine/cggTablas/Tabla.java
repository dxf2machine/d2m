/*------------------------------------------------------------------------------------------ 
Copyright 2014, Celeste Gabriela Guagliano. 

This file is part of DXF2Machine project. 

DXF2Machine is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License. 

DXF2Machine is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. 

You should have received a copy of the GNU General Public License along with DXF2Machine. If not, see <http://www.gnu.org/licenses/>.
  --------------------------------------------------------------------------------------------*/

package cggTablas;

import java.io.File;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;

import javax.swing.JEditorPane;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import cggColeccion.ColeccionFunciones;
import cggDatos.DatosCirculo;
import cggDatos.datos;
import cggGCode.GCode;
import cggGCode.GCodeMetodoContorneado;
import cggGCode.GCodeMetodoGeneral;
import cggGCode.GCodeMetodoTaladrado;
import myDXF.DXF_Loader;
//import myDXF.GCode.areaTexto;
import myDXF.Graphics.myCanvas;

;
/**
 * This class access the general table of entities of DXF project and collect the admitted entities for DXF2Machine in a new table.
 * @author: Celeste G. Guagliano
 * @version: 13/01/15
 * 
 */ 
public class Tabla {

	public static HashSet ListaEntidades = new HashSet();
	public static JTextArea principal = new JTextArea();
	public static JTextPane consola=new JTextPane();

	/**
	 * Method to get access to the original list of entities.
	 */
	public static void AccederALaLista() {

		for (int i = 0; i < myCanvas._dxf._u._myTables.size(); i++) {
			for (int j = 0; j < myCanvas._dxf._u._myTables.elementAt(i)._myLayers
					.size(); j++) {
				for (int k = 0; k < myCanvas._dxf._u._myTables.elementAt(i)._myLayers
						.elementAt(j)._myEnt.size(); k++) {
					(myCanvas._dxf._u._myTables.elementAt(i)._myLayers
							.elementAt(j)._myEnt.elementAt(k)).obtenerDatos();

				}

			}

		}

	}

	/**
	 * Method to clear the list's content.
	 */
	public static void resetearTabla() {
		ListaEntidades.clear();
		principal.setText("");
		
	}

	/**
	 * Method to add entities into a list.
	 * @param entidad is the entity to add.
	 */
	public static void agregarDatoATabla(datos entidad) {
		ListaEntidades.add(entidad);
	}

	/**
	 * Method to collect entities by a certain criteria.
	 * @param postprocesador is the machine's post processor.
	 * @param ruta is a selected directory.
	 */
	public static void ObtenerTablas(String postprocesador, String ruta) {

		Tabla.resetearTabla();
		Tabla.AccederALaLista();
		DatosCirculo centro=DatosCirculo.obtenerCentro();
		if (centro!=null){
		Hashtable ListaContorno = TablaContorno.obtenerTabla();
		ListaContorno=desplazarCentro(ListaContorno,centro);
		Hashtable ListaPlaneado = TablaTocho.ObtenerTabla();
		ListaPlaneado=desplazarCentro(ListaPlaneado,centro);
		Hashtable ListaGrabado = TablaGrabado.ObtenerTabla();
		ListaGrabado=desplazarCentro(ListaGrabado,centro);
		Hashtable ListaAgujeros = TablaAgujeros.ObtenerTablaAgujereado();
		ListaAgujeros=desplazarCentro(ListaAgujeros,centro);
		Hashtable herramientas = TablaHerramientas.ObtenerTabla(DXF_Loader.TablaHerramientas);
		double profPlano = Double.parseDouble(DXF_Loader.profPlano.getText());
		double profConto = Double.parseDouble(DXF_Loader.profContorno.getText());
		double profGrabo = Double.parseDouble(DXF_Loader.profGrabo.getText());
		double profTaladro = Double.parseDouble(DXF_Loader.profTaladro.getText());
		String postProce = (String) TablaPostprocesadores.ObtenerPostprocesador(postprocesador);
		GCode.prepararPostprocesador(postProce, ruta);
		principal = GCode.EncabezarPrograma(principal);
		String texto=principal.getText();
		if (DXF_Loader.plano == true) {
			if (ListaContorno.size() != 0) {
				ListaPlaneado = ColeccionFunciones.ObtenerTocho(ListaContorno,
						herramientas);
				
				principal =(new GCodeMetodoGeneral()).GenerarPlaneado(new GCodeMetodoGeneral(),ListaPlaneado, herramientas,
						profPlano, principal);
			}
		}
		if (DXF_Loader.contorno == true) {
			if (ListaContorno.size() != 0) {
				GCode mecanizarRasgo= new GCodeMetodoContorneado();
				principal = mecanizarRasgo.GenerarContorneado(mecanizarRasgo,ListaContorno,
						herramientas, profConto, principal);
				
			}
		}
		if (DXF_Loader.grabo == true) {
			if (ListaGrabado.size() != 0) {
				GCode mecanizarRasgo=new GCodeMetodoGeneral();
				principal = mecanizarRasgo.GenerarGrabado(mecanizarRasgo,ListaGrabado, herramientas,
						profGrabo, principal);
			}
		}
		if (DXF_Loader.taladro == true) {
			if (ListaAgujeros.size() != 0) {
				GCode mecanizarRasgo= new GCodeMetodoTaladrado();
				principal = GCode.GenerarTaladrado(mecanizarRasgo,ListaAgujeros, herramientas,
						profTaladro, principal);

			}
		}
		GCode.archivarMecanizado(principal,consola);
		GCode.formatearConsola(principal,consola);
	}else{
		 JOptionPane.showMessageDialog(null, "Debe seleccionar un punto de origen de pieza unico", "Error", JOptionPane.ERROR_MESSAGE);
	}
}

	/**
	 * Method to set the reference point of a map.
	 * @param lista is the entities's list
	 * @param centro is the center of the reference point.
	 * @return a list of entities.
	 */
	private static Hashtable desplazarCentro(Hashtable lista,
			DatosCirculo centro) {
		Hashtable listaDesplazada=new Hashtable();
		for(Enumeration e=lista.elements();e.hasMoreElements();){
			datos elemento=(datos) e.nextElement();
			elemento=elemento.desplazateAlCentro(centro);
			listaDesplazada.put(listaDesplazada.size()+1,elemento);
		}
		return listaDesplazada;
	}
	 	

}
