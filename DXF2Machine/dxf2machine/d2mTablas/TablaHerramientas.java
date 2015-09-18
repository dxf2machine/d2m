/*------------------------------------------------------------------------------------------ 
Copyright 2014, Celeste Gabriela Guagliano. 

This file is part of DXF2Machine project. 

DXF2Machine is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License. 

DXF2Machine is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. 

You should have received a copy of the GNU General Public License along with DXF2Machine. If not, see <http://www.gnu.org/licenses/>.

For more information, contact us at: dxf2machine@gmail.com
  --------------------------------------------------------------------------------------------*/

package d2mTablas;

import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;

import d2mDatos.Herramienta;

/**
 * This class generate a table of tools.
 * @author: Celeste G. Guagliano
 * @version: 0.0.1
 * 
 */ 


public class TablaHerramientas {
	static Hashtable TablaHerramientas = new Hashtable();

	/**
	 * Method to get the tool's list
	 * @param herramientas is the tool's table.
	 * @return a tool's list.
	 */
	public static Hashtable ObtenerTabla(JTable herramientas) {
		for (int i = 1; i < 5; i++) {
			double Numero = (double) herramientas.getValueAt(0, i);
			double Diametro = (double) herramientas.getValueAt(1, i);
			double Velocidad = (double) herramientas.getValueAt(3, i);
			double Avance = (double) herramientas.getValueAt(2, i);
			double Pasada = (double) herramientas.getValueAt(4, i);
			double Zseguro= (double) herramientas.getValueAt(5,i);
			double Zcambio=(double) herramientas.getValueAt(6, i);
			Herramienta dato = new Herramienta(Numero, Diametro, Velocidad,
					Avance, Pasada,Zseguro,Zcambio);
			TablaHerramientas.put(herramientas.getColumnName(i), dato);
		}
		return TablaHerramientas;
		}
	
	 }

