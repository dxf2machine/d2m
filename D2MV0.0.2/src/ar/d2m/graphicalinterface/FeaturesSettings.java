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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * @author CGG
 *
 */
public class FeaturesSettings  extends JPanel implements ActionListener{
	
	public JCheckBox feature1;
	public JCheckBox feature2;
	public JCheckBox feature3;
	public JCheckBox feature4;
	public JTextField textf1;
	public JTextField textf2;
	public JTextField textf3;
	public JTextField textf4;
	public JLabel space;
	public FeaturesSettings(){
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(370, 50));
		this.setLayout(new GridLayout(7,3));
		JLabel feature= new JLabel("Feature");
		JLabel depth= new JLabel("Z depth");
		JLabel space= new JLabel("          ");
		this.add(feature);
		this.add(depth);
		this.add(space);
		feature1= new JCheckBox(GraphicalInterface.res.getString("feature1"));
		feature2= new JCheckBox(GraphicalInterface.res.getString("feature2"));
		feature3= new JCheckBox(GraphicalInterface.res.getString("feature3"));
		feature4= new JCheckBox(GraphicalInterface.res.getString("feature4"));
		textf1= new JTextField();
		textf2= new JTextField();
		textf3= new JTextField();
		textf4= new JTextField();
		this.add(feature1);
		this.add(textf1);
		this.add(feature2);
		this.add(textf2);
		this.add(feature3);
		this.add(textf3);
		this.add(feature4);
		this.add(textf4);
		this.add(space);
		this.add(space);
		this.add(space);
		this.add(space);
		this.setVisible(true);
	}

	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
