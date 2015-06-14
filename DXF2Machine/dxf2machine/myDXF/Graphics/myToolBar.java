/*------------------------------------------------------------------------------------------ 
Copyright 2007-2009, Marc Paris, Stephan Soulard and Edouard Vanhauwaert.
Copyright 2014, Celeste Gabriela Guagliano. 

This file was originaly part of DXF project and then modified by Celeste Gabriela Guagliano for DXF2Machine project.

DXF2Machine is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License. 

DXF2Machine is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. 

You should have received a copy of the GNU General Public License along with DXF2Machine. If not, see <http://www.gnu.org/licenses/>.
  --------------------------------------------------------------------------------------------*/

/*
 * Initials     Name
 * -------------------------------------
 * CeGu         Celeste Guagliano. 
 */

/*
 * modification history (new versions first)
 * -----------------------------------------------------------
 * 20150113 v0.0.2 CeGu modified myToolbar class
 * 20140428 v0.0.1 CeGu fork from DXF project

*/    
package myDXF.Graphics;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import myDXF.DXF_Loader;

public class myToolBar extends JToolBar implements ActionListener {

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

/*	private static final long serialVersionUID = 1L;
	public int selectedIndex = 0;

    public static final int toolPoint=0;
	public static final int toolLine = 1;
    public static final int toolArc = 2;
	public static final int toolPoly = 3;
	public static final int toolCircle = 4;
	public static final int toolEllipse = 5;
	public static final int toolSel = 6;
	public static final int toolDeplace = 7;
	public static final int toolZoom = 8;
	public static final int toolZoomCentre = 9;
	public static final int toolPipette = 10;
	public static final int toolTrace = 11;
	public static final int toolTxt = 12;
	public static final int toolSolid = 13;
	public static final int toolSelX = 14;
	public static final int toolNone = 0;
	/**
	 * @uml.property name="lastTool"
	 * @uml.associationEnd multiplicity="(1 1)"
	 
	private JButton lastTool;

	public myToolBar() {
		super(DXF_Loader.res.getString("myToolBar.0"));
	//	this.setLayout(new GridLayout(1, 10));
		this.setMinimumSize(new Dimension(350, 30));
		this.setPreferredSize(new Dimension(350, 30));

		JButton selection = new JButton();
		selection.addActionListener(this);
		selection.setActionCommand("selection");
		selection.setToolTipText("Definir mecanizado");
		selection.setText("Definir Mecanizados");
		this.add(selection);
	}

	public void reset(boolean s) {
		if (s) {
			for (int i = 0; i < DXF_Loader._mc.vectClickOn.size(); i++)
				DXF_Loader._mc.vectClickOn.elementAt(i).setSelected(false);
			DXF_Loader._mc.vectClickOn.removeAllElements();
		}

		DXF_Loader._mc.selecting = false;
		DXF_Loader._mc.moving = false;
		DXF_Loader._mc.zooming = false;
		DXF_Loader._mc.drawingCircle = false;
		DXF_Loader._mc.drawingPolyLineStart = false;
		DXF_Loader._mc.drawingPolyLineEnd = false;
		DXF_Loader._mc.drawingArc = false;
		DXF_Loader._mc.drawingArcAngleStart = false;
		DXF_Loader._mc.drawingArcAngleEnd = false;
		DXF_Loader._mc.drawingEllipse = false;
		DXF_Loader._mc.drawingTrace = false;
		DXF_Loader._mc.drawingTxt = false;
		DXF_Loader._mc.drawingSolid = false;

		myCanvas._dxf.sel.setText(myCanvas._dxf.defSelTxtA
				+ DXF_Loader._mc.vectClickOn.size() + myCanvas._dxf.txtB);
		myCanvas._dxf.clipB.setText(myCanvas._dxf.defClipTxtA
				+ DXF_Loader._mc.clipBoard.size() + myCanvas._dxf.txtB);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		setCursor(Cursor.getDefaultCursor());
		DXF_Loader._mc.setCursor(Cursor
				.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		boolean s = true;

		if (e.getActionCommand() == "selection") {

			if (selectedIndex == toolSel) {
				DXF_Loader._mc.setCursor(Cursor
						.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				selectedIndex = toolNone;
				s = true;
			} else {
				DXF_Loader._mc.setCursor(Cursor
						.getPredefinedCursor(Cursor.HAND_CURSOR));
				selectedIndex = toolSel;
				s = false;
			}

		}
		reset(s);

		/*
		 * lastTool.setEnabled(true); lastTool=(JButton) e.getSource();
		 * lastTool.setEnabled(false);
		 
	}

	public int getSelectedIndex() {
		return selectedIndex;
	}
*/
}
