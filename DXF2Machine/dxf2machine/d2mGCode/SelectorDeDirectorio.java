/*------------------------------------------------------------------------------------------ 
Copyright 2014, Celeste Gabriela Guagliano. 

This file is part of DXF2Machine project. 

DXF2Machine is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License. 

DXF2Machine is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. 

You should have received a copy of the GNU General Public License along with DXF2Machine. If not, see <http://www.gnu.org/licenses/>.

For more information, contact us at: dxf2machine@gmail.com
  --------------------------------------------------------------------------------------------*/

package d2mGCode;

import javax.swing.*;

import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.util.*;

/**
 * This class implements a directory chooser to save files  
 * @author: Celeste G. Guagliano
 * @version: 0.0.1
 * 
 */ 

public class SelectorDeDirectorio extends JPanel implements ActionListener {
	JButton go;

	static JFileChooser chooser;
	static String choosertitle;

	public static String SeleccionarDirectorio() {
		String directorio = null;

		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle(choosertitle);
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		chooser.setAcceptAllFileFilterUsed(false);
		if (chooser.showOpenDialog(chooser) == JFileChooser.APPROVE_OPTION) {
			System.out.println("getCurrentDirectory(): "
					+ chooser.getCurrentDirectory());
			System.out.println("getSelectedFile() : "
					+ chooser.getSelectedFile());

		} else {
			System.out.println("No Selection ");
		}
		return directorio = (chooser.getSelectedFile().getAbsolutePath() + File.separator);
	}

	public Dimension getPreferredSize() {
		return new Dimension(200, 200);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

}