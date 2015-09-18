/*---------------------------------------------------------------------------------------- 
Copyright 2007, Stephan Soulard and Edouard Vanhauwaert.
Copyright 2014, Celeste Gabriela Guagliano. 

This file was originally part of DXF project and then modified by 
Celeste Gabriela Guagliano for DXF2Machine project.

DXF2Machine is free software: you can redistribute it and/or modify it under the terms of 
the GNU General Public License as published by the Free Software Foundation, either 
version 2 of the License. 

DXF2Machine is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
See the GNU General Public License for more details. 

You should have received a copy of the GNU General Public License along with DXF2Machine. 
If not, see <http://www.gnu.org/licenses/>.

For more information, contact us at: dxf2machine@gmail.com
  ---------------------------------------------------------------------------------------*/

/*
 * Initials     Name
 * -------------------------------------
 * CeGu         Celeste Guagliano. 
 */

/*
 * modification history (new versions first)
 * -----------------------------------------------------------
 * 20141002 v0.0.1 CeGu add obtenerDatos() method
 * 20141002 v0.0.1 CeGu improve draw() method
 * 20140828 v0.0.1 CeGu fork from DXF project

*/    

package myDXF.Entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;

import d2mDatos.DatosArcos;
import d2mDatos.FormatoNumeros;
import d2mDatos.datos;
import d2mTablas.Tabla;
import myDXF.DXF_Loader;
import myDXF.myUnivers;
import myDXF.Graphics.myCoord;
import myDXF.Graphics.myLabel;
import myDXF.Graphics.myLog;
import myDXF.Header.myLayer;
import myDXF.Header.myLineType;
import myDXF.Header.myStats;
import myDXF.Header.myTable;

/**
 * This class to manage the arc's data
 * @author: Stephan Soulard, Edouard Vanhauwaert
 * @version: 0.0.1 by Celeste Guagliano
 * 
 */ 
public class myArc extends myEntity {

	private static final long serialVersionUID = 1L;
	public myPoint _point = new myPoint();
	public double _radius = 0;
	protected double _angle1 = 0;
	protected double _angle2 = 0;
	protected double _2angle2 = 0;
	public double _xang1 = 0;
	public double _yang1 = 0;
	public double _xang2 = 0;
	public double _yang2 = 0;

	private Arc2D.Double _a = new Arc2D.Double();
	

	public myArc(double a1, double a2, myPoint p, double r,
			myLineType lineType, int c, myLayer l, int visibility,
			double thickness) {
		super(c, l, visibility, lineType, thickness);
		_point = p;
		_radius = r;
		_angle1 = a1;
		_angle2 = a2;
		_thickness = thickness;
		_color = c;

		/*
		 * datosArco datos = new datosArco(myCoord.dxfToJava_X(_point.X()),
		 * myCoord.dxfToJava_Y(_point.Y()), r, a1, a2);
		 * myDXF.Entities.TablaArcos.Tabla(datos);
		 */
		myStats.nbArc += 1;
	}

	@Override
	public void obtenerDatos() {
		_xang1 = _radius * Math.cos(_angle1 * Math.PI / 180) + _point.X();
		_yang1 = _radius * Math.sin(_angle1 * Math.PI / 180) + _point.Y();
		_xang2 = _radius * Math.cos(_angle2 * Math.PI / 180) + _point.X();
		_yang2 = _radius * Math.sin(_angle2 * Math.PI / 180) + _point.Y();
		_xang1=(double) FormatoNumeros.formatearNumero(_xang1);
		_xang2=(double) FormatoNumeros.formatearNumero(_xang2);
		_yang1=(double) FormatoNumeros.formatearNumero(_yang1);
		_yang2=(double) FormatoNumeros.formatearNumero(_yang2);
		double CentroX=(double) FormatoNumeros.formatearNumero(_point.X());
		double CentroY=(double) FormatoNumeros.formatearNumero(_point.Y());
		double Radio=(double) FormatoNumeros.formatearNumero(Math.abs(_radius));
		double ang1=(double) FormatoNumeros.formatearNumero(_angle1);
		double ang2=(double) FormatoNumeros.formatearNumero(_angle2);
		datos datos = new DatosArcos(_xang1,_yang1,_xang2,_yang2, CentroX,CentroY,Radio, ang1,ang2, _color, false, 0, 0);
		Tabla.agregarDatoATabla(datos);

	}

	public myArc() {
		super(-1, null, 0, null, myTable.defaultThickness);
		_point = new myPoint();
		_radius = 0;

		myStats.nbArc += 1;
	}

	public myArc(myArc orig) {
		super(orig._color, orig._refLayer, 0, orig._lineType, orig._thickness);
		_point = new myPoint(orig._point);
		_radius = orig._radius;
		_angle1 = orig._angle1;
		_angle2 = orig._angle2;
	}

	@Override
	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		super.draw(g);
		if (_angle2 < _angle1) {
			_2angle2 = _angle2 + 360 - _angle1;
		} else {
			_2angle2 = _angle2 - _angle1;
		}
		_a.setArcByCenter(myCoord.dxfToJava_X(_point.X()),
				myCoord.dxfToJava_Y(_point.Y()), _radius * myCoord.Ratio,
				(_angle1), _2angle2, Arc2D.OPEN);

		g2d.draw(_a);
	}

	public static myArc read(myBufferedReader br, myUnivers univers)
			throws NumberFormatException, IOException {

		String ligne, ligne_temp = "";
		double x = 0, y = 0, r = 0, a1 = 0, a2 = 0, thickness = 0;
		int visibility = 0, c = 0;
		myLineType lineType = null;
		myLayer l = null;
		myLog.writeLog("> new myArc");

		while ((ligne = br.readLine()) != null && !ligne.equalsIgnoreCase("0")) {
			ligne_temp = ligne;
			ligne = br.readLine();

			if (ligne_temp.equalsIgnoreCase("8")) {
				l = univers.findLayer(ligne);
			} else if (ligne_temp.equalsIgnoreCase("62")) {
				c = Integer.parseInt(ligne);
			} else if (ligne_temp.equalsIgnoreCase("6")) {
				lineType = univers.findLType(ligne);
			} else if (ligne_temp.equalsIgnoreCase("40")) {
				r = Double.parseDouble(ligne);
			} else if (ligne_temp.equalsIgnoreCase("10")) {
				x = Double.parseDouble(ligne);
			} else if (ligne_temp.equalsIgnoreCase("20")) {
				y = Double.parseDouble(ligne);
			} else if (ligne_temp.equalsIgnoreCase("50")) {
				a1 = Double.parseDouble(ligne);
			} else if (ligne_temp.equalsIgnoreCase("51")) {
				a2 = Double.parseDouble(ligne);
			} else if (ligne_temp.equalsIgnoreCase("60")) {
				visibility = Integer.parseInt(ligne);
			} else if (ligne_temp.equalsIgnoreCase("39")) {
				thickness = Double.parseDouble(ligne);
			}
		}
		return new myArc(a1, a2, new myPoint(x, y, c, null, visibility, 1), r,
				lineType, c, l, visibility, thickness);
	}

	@Override
	public void write(FileWriter out) throws IOException {
		out.write("ARC\n");
		super.writeCommon(out);
		out.write("10\n");
		out.write(_point.X() + "\n");
		out.write("20\n");
		out.write(_point.Y() + "\n");
		out.write("40\n");
		out.write(_radius + "\n");
		out.write("50\n");
		out.write(_angle1 + "\n");
		out.write("51\n");
		out.write(_angle2 + "\n");
		out.write("0\n");
	}

	@Override
	public String toString() {
		return DXF_Loader.res.getString("myArc.0");
	}

	@Override
	public DefaultMutableTreeNode getNode() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(this);
		root.add(new DefaultMutableTreeNode(new myLabel(myLabel.X, String
				.valueOf(_point.X()))));
		root.add(new DefaultMutableTreeNode(new myLabel(myLabel.Y, String
				.valueOf(_point.Y()))));
		root.add(new DefaultMutableTreeNode(new myLabel(myLabel.ANGLE1, String
				.valueOf(_angle1))));
		root.add(new DefaultMutableTreeNode(new myLabel(myLabel.ANGLE2, String
				.valueOf(_angle2))));
		root.add(new DefaultMutableTreeNode(new myLabel(myLabel.RADIUS, String
				.valueOf(_radius))));
		root.add(new DefaultMutableTreeNode(new myLabel(myLabel.THICKNESS,
				String.valueOf(_thickness))));
		// System.out.println(root);
		Vector<DefaultMutableTreeNode> v = super.getCommonNode();
		for (int i = 0; i < v.size(); i++)
			root.add(v.get(i));

		return root;
	}

	@Override
	public myLabel getNewLabel(String code, Object newValue)
			throws NumberFormatException {
		myLabel l = null;

		if (code.equals(myLabel.ANGLE1)) {
			_angle1 = Double.parseDouble(newValue.toString());
			l = new myLabel(myLabel.ANGLE1, newValue.toString());
		} else if (code.equals(myLabel.ANGLE2)) {
			_angle2 = Double.parseDouble(newValue.toString());
			l = new myLabel(myLabel.ANGLE2, newValue.toString());
		} else {
			l = getNewArcLabel(code, newValue);
		}

		return l;
	}

	public myLabel getNewArcLabel(String code, Object newValue)
			throws NumberFormatException {
		myLabel l = null;

		if (code.equals(myLabel.X)) {
			_point.setX(Double.parseDouble(newValue.toString()));
			l = new myLabel(myLabel.X, newValue.toString());
		} else if (code.equals(myLabel.Y)) {
			_point.setY(Double.parseDouble(newValue.toString()));
			l = new myLabel(myLabel.Y, newValue.toString());
		} else if (code.equals(myLabel.RADIUS)) {
			_radius = Double.parseDouble(newValue.toString());
			l = new myLabel(myLabel.RADIUS, newValue.toString());
		} else if (code.equals(myLabel.THICKNESS)) {
			_thickness = Double.parseDouble(newValue.toString());
			l = new myLabel(myLabel.THICKNESS, newValue.toString());
		} else {
			l = super.getCommonLabel(code, newValue);
		}

		return l;
	}

	@Override
	public Arc2D.Double getSelectedEntity() {
		return _a;
	}

	@Override
	public void translate(double x, double y) {
		this._point._point.x -= myCoord.getTransalation(x);
		this._point._point.y += myCoord.getTransalation(y);
	}

	@Override
	public double getMinX(double min) {
		if ((_point.X() - _radius) < min)
			return _point.X();
		return min;
	}

	@Override
	public double getMaxX(double max) {
		if ((_point.X() + _radius) > max)
			return _point.X();
		return max;
	}

	@Override
	public double getMinY(double min) {
		if ((_point.Y() - _radius) < min)
			return _point.Y();
		return min;
	}

	@Override
	public double getMaxY(double max) {
		if ((_point.Y() + _radius) > max)
			return _point.Y();
		return max;
	}
}
