/*------------------------------------------------------------------------------------------ 
Copyright 2014, Celeste Gabriela Guagliano. 

This file is part of DXF2Machine project. 

DXF2Machine is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License. 

DXF2Machine is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. 

You should have received a copy of the GNU General Public License along with DXF2Machine. If not, see <http://www.gnu.org/licenses/>.
  --------------------------------------------------------------------------------------------*/

package cggDatos;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import javax.swing.JOptionPane;

/**
 * This class defines the number's format.
 * @author: Celeste G. Guagliano
 * @version: 13/01/15
 * 
 */ 
public class FormatoNumeros {
	
	
	public static Object formatearNumero(double numero){
		DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
		simbolos.setDecimalSeparator('.');
		DecimalFormat formateador = new DecimalFormat("#####.###",simbolos);
		try{
		numero=Double.parseDouble(formateador.format(numero));
		return numero;
		}catch(NumberFormatException ex){
			 JOptionPane.showMessageDialog(null, "Imposible generar el mecanizado solicitado","Error" ,JOptionPane.ERROR_MESSAGE);
			
        	//System.out.println("Imposible mecanizar el rasgo seleccionado");
			};
		return null;
}
}
