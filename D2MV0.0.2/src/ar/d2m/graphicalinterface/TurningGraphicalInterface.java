/*---------------------------------------------------------------------------------------- 
Copyright 2015, Celeste Gabriela Guagliano. 

This file is part of DXF2Machine project. 

DXF2Machine is free software: you can redistribute it and/or modify it under the terms of 
the GNU General Public License as published by the Free Software Foundation, either 
version 2 of the License. 

DXF2Machine is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
See the GNU General Public License for more details. 

You should have received a copy of the GNU General Public License along with DXF2Machine. 
If not, see <http://www.gnu.org/licenses/>.

For more information, contact us at: dxf2machine@gmail.com
  ----------------------------------------------------------------------------------------*/

package ar.d2m.graphicalinterface;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import ar.d2m.features.Contour;
import ar.d2m.features.ContourMilling;
import ar.d2m.features.ContourOutTurning;
import ar.d2m.features.Drilling;
import ar.d2m.features.Engraving;
import ar.d2m.features.FaceMilling;
import ar.d2m.loader.D2MLoader;
import ar.d2m.machines.TurningMachine;

import ar.d2m.tools.Tool;

import fr.epsi.dxf.DXF_Loader;

/**
 * @author CGG
 *
 */
public class TurningGraphicalInterface extends GraphicalInterface implements ActionListener{
	
	
    public TurningGraphicalInterface(DXF_Loader DXF, TurningMachine turningMachine) {
    	res = ResourceBundle
    			.getBundle("ar.d2m.resources.machines.Turn");
    DXF.frame.setTitle("D2M: DXF2Turn");
    JPanel SetUp= new JPanel();
    JPanel Tools= new JPanel();
    DXF.GCode.setLayout(new GridLayout(3,0));
	SetUp.setLayout(new GridLayout(2,1));
	Tools.setLayout(new BorderLayout());
	set= new FeaturesSettings();
	sel=new FeaturesSelection(turningMachine.colores);
	tool= new Tool();
	generate= new JButton("Generate G-Code");
	SetUp.add(sel,"Selection of Features");
	SetUp.add(set,"Configuration");
	Tools.add(tool.Scroll,BorderLayout.CENTER);
	Tools.add(generate,BorderLayout.SOUTH);
	DXF.GCode.setSize(300,700);
	DXF.GCode.add(SetUp);
	DXF.GCode.add(Tools);
	DXF.setVisible(true);
	generate.addActionListener(this);
	
	set.setFeature1.addActionListener(this);
	set.setFeature2.addActionListener(this);
	set.setFeature3.addActionListener(this);
	set.setFeature4.addActionListener(this);
   }
/*
 * Esta clase va a empaquetar todos los elementos de la interfaz grafica del torno y creara una 
 * nueva instancia de la pantalla.
 */

	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==generate){
			if(set.feature1.isSelected()==true){
				new FaceMilling();
			}
			if(set.feature2.isSelected()==true){
				new ContourOutTurning();
				Contour.drawContour(D2MLoader.DXF);
			}
			if(set.feature3.isSelected()==true){
				new Engraving();
			}
			if(set.feature4.isSelected()==true){
				new Drilling();
			}
		}
		if (e.getSource()==set.setFeature1){
			D2MLoader.machine.first.setEntitiesTable();
					
		}
	}
		
	}

