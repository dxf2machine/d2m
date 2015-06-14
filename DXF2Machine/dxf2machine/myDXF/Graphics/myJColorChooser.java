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
 * 20150113 v0.0.2 CeGu improve actionPerformed() method
 * 20150113 v0.0.2 CeGu improve myJColorChooser() method
 * 20140428 v0.0.1 CeGu fork from DXF project

*/    
package myDXF.Graphics;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import cggGCode.SelectorDeDirectorio;
import cggTablas.Tabla;
import myDXF.DXF_Loader;

public class myJColorChooser extends JPanel implements ActionListener {
	public int selectedIndex = 0;

   
	public static final int toolSel = 6;
	public static final int toolNone = 0;

	private static final long serialVersionUID = 1L;
	public static JButton col;
	public static JButton colLayer;
	public static Hashtable colores=new Hashtable();
    public static JLabel color= new JLabel();
    public static JButton rojo=new JButton();
    public static JButton verde=new JButton();
    public static JButton cian=new JButton();
    public static JButton azul=new JButton();
    public static JButton nada=new JButton();
    public int indice=0;
	public myJColorChooser() {
		colores.put(1,"Origen");
		colores.put(3, "Taladrado");
		colores.put(4, "Grabado");
		colores.put(5, "Contorno");
		colores.put(0,"Ningun Rasgo");
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(200, 20));
		JPanel p = new JPanel(new GridLayout(0, 10));
		color.setText((String)colores.get(0));
		rojo.setCursor(Cursor
				.getPredefinedCursor(Cursor.HAND_CURSOR));
		rojo.setPreferredSize(new Dimension(30,15));
		rojo.addActionListener(this);
		rojo.setActionCommand("selection");
		rojo.setBackground(DXF_Color.ColorMap[1]);
		rojo.setToolTipText((String) colores.get(1));
		p.add(rojo);
		rojo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == rojo) {
					color.setText((String) colores.get(1));
			}}}
		);
		verde.setCursor(Cursor
				.getPredefinedCursor(Cursor.HAND_CURSOR));
		verde.setPreferredSize(new Dimension(8, 8));
		verde.addActionListener(this);
		verde.setActionCommand("selection");
		verde.setBackground(DXF_Color.ColorMap[3]);
		verde.setToolTipText((String) colores.get(3));
		p.add(verde);
		verde.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == verde) {
					color.setText((String) colores.get(3));
			}}}
		);
		cian.setCursor(Cursor
				.getPredefinedCursor(Cursor.HAND_CURSOR));
		cian.setPreferredSize(new Dimension(8, 8));
		cian.addActionListener(this);
		cian.setActionCommand("selection");
		cian.setBackground(DXF_Color.ColorMap[4]);
		cian.setToolTipText((String) colores.get(4));
		p.add(cian);
		cian.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == cian) {
					color.setText((String) colores.get(4));
			}}}
		);
		azul.setCursor(Cursor
				.getPredefinedCursor(Cursor.HAND_CURSOR));
		azul.setPreferredSize(new Dimension(8, 8));
		azul.addActionListener(this);
		azul.setActionCommand("selection");
		azul.setBackground(DXF_Color.ColorMap[5]);
		azul.setToolTipText((String) colores.get(5));
		p.add(azul);
		azul.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == azul) {
					color.setText((String) colores.get(5));
			}}}
		);
		
		nada.setCursor(Cursor
				.getPredefinedCursor(Cursor.HAND_CURSOR));
		nada.setPreferredSize(new Dimension(8, 8));
		nada.addActionListener(this);
		nada.setActionCommand("nada");
		nada.setBackground(DXF_Color.ColorMap[0]);
		nada.setToolTipText((String) colores.get(0));
		p.add(nada);
		nada.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == nada) {
					color.setText((String) colores.get(0));
			}}}
		);
		
/*		for (int i = 3; i < 6; i++) {
			tmpJButton = new JButton();
			tmpJButton.setActionCommand("Selection");
			tmpJButton.setCursor(Cursor
					.getPredefinedCursor(Cursor.HAND_CURSOR));
			tmpJButton.setPreferredSize(new Dimension(8, 8));
			tmpJButton.addActionListener(this);
			tmpJButton.setBackground(DXF_Color.ColorMap[i]);
			tmpJButton.setToolTipText((String) colores.get(i));
			p.add(tmpJButton);
		}*/
		this.add(p, BorderLayout.NORTH);
		this.setPreferredSize(new Dimension(200, 20));

		this.setMinimumSize(new Dimension(200, 20));

		JPanel p_current = new JPanel();

	/*	p_current
				.add(new JLabel(DXF_Loader.res.getString("myJColorChooser.0")));

	   colLayer = new JButton();
		colLayer.setMinimumSize(new Dimension(50, 9));
		colLayer.setPreferredSize(new Dimension(50, 9));
		colLayer.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(final MouseEvent e) {
				col.setBackground(colLayer.getBackground());
			}
		});
		colLayer.setBackground(DXF_Color.getDefaultColor());
		colLayer.setEnabled(true);
		colLayer.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		colLayer.setToolTipText("0");

		p_current.add(colLayer);
*/
		//color=new JLabel((String) colores.get(0));
		p_current.add(color);
		col = new JButton("");
		col.setMinimumSize(new Dimension(50, 20));
		col.setPreferredSize(new Dimension(50, 20));
		if (DXF_Loader._mc == null || myCanvas._dxf == null
				|| myCanvas._dxf._u == null
				|| myCanvas._dxf._u.currLayer == null)
			col.setBackground(DXF_Color.getDefaultColor());
		else
			col.setBackground(DXF_Color
					.getColor(myCanvas._dxf._u.currLayer._color));

		col.setEnabled(false);
		col.setToolTipText(DXF_Loader.res.getString("myJColorChooser.4"));

		p_current.add(col);
		this.add(p_current, BorderLayout.EAST);

	}

	public Color getColor() {
		return col.getBackground();
	}

	@Override
	public void actionPerformed(ActionEvent a) {
		col.setBackground(((JButton) a.getSource()).getBackground());
		
		setCursor(Cursor.getDefaultCursor());
		DXF_Loader._mc.setCursor(Cursor
				.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		boolean s = true;

		

			if (a.getActionCommand() == "nada") {
				DXF_Loader._mc.setCursor(Cursor
						.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
				selectedIndex = toolNone;
				s = true;
			}
				if (a.getActionCommand() == "selection") {
				DXF_Loader._mc.setCursor(Cursor
						.getPredefinedCursor(Cursor.HAND_CURSOR));
				selectedIndex = toolSel;
				s = false;
			}

		
	
		reset(s);
		
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

	public int getSelectedIndex() {
		return selectedIndex;
	}

}
