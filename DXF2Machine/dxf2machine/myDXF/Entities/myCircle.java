/*--------------------------------------------------------------------------------------- 
Copyright 2007, Stephan Soulard and Edouard Vanhauwaert.
Copyright 2014, Celeste Gabriela Guagliano. 

This file was originaly part of DXF project and then modified by 
Celeste Gabriela Guagliano for DXF2Machine project.

DXF2Machine is free software: you can redistribute it and/or modify it under the terms of 
the GNU General Public License as published by the Free Software Foundation, either 
version 2 of the License. 

DXF2Machine is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
See the GNU General Public License for more details. 

You should have received a copy of the GNU General Public License along with DXF2Machine. 
If not, see <http://www.gnu.org/licenses/>.
  ---------------------------------------------------------------------------------------*/

/*
 * Initials     Name
 * -------------------------------------
 * CeGu         Celeste Guagliano. 
 */

/*
 * modification history (new versions first)
 * -----------------------------------------------------------
 * 20141028 v0.0.2 CeGu add obtenerDatos() method
 * 20150428 v0.0.1 CeGu fork from DXF project

*/    

package myDXF.Entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;

import cggDatos.DatosCirculo;
import cggDatos.FormatoNumeros;
import cggDatos.datos;
import cggTablas.Tabla;
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
 * Class to manage the circle's data
 * @author: Stephan Soulard, Edouard Vanhauwaert
 * @version: 13/01/15 by Celeste Guagliano
 * 
 */ 
public class myCircle extends myEntity {

	private static final long serialVersionUID = 1L;
	private Ellipse2D.Double _e = new Ellipse2D.Double();
	public myPoint _point = new myPoint();
	public double _radius = 0;
	public double xinicial = 0;
	public double yinicial = 0;
	public double xfinal = 0;
	public double yfinal = 0;
	public double angI = 0;
	public double angF = 0;


	public myCircle(myPoint p, double r, myLineType lineType, int c, myLayer l,
			int visibility, double thickness) {
		super(c, l, visibility, lineType, thickness);
		_point = p;
		_radius = r;
		_color = c;

		myStats.nbCercle += 1;
	}

	@Override
	public void obtenerDatos() {

		xinicial = _point.X() - _radius;
		yinicial = _point.Y();
		xfinal = xinicial + 2 * _radius;
		yfinal = yinicial;
		double centroX = _point.X();
		double centroY = _point.Y();
		boolean ubicado = false;
		int posicion = 0;
		int orientacion = 0;
		xinicial=(double) FormatoNumeros.formatearNumero(xinicial);
		yinicial=(double) FormatoNumeros.formatearNumero(yinicial);
		xfinal=(double) FormatoNumeros.formatearNumero(xfinal);
		yfinal=(double) FormatoNumeros.formatearNumero(yfinal);
		centroX=(double) FormatoNumeros.formatearNumero(centroX);
		centroY=(double) FormatoNumeros.formatearNumero(centroY);
		double Radio=(double) FormatoNumeros.formatearNumero(_radius);
		datos datos = new DatosCirculo(xinicial,yinicial,xfinal,yfinal,centroX,centroY, Radio, _color, ubicado, posicion, orientacion);
		Tabla.agregarDatoATabla(datos);
	}

	public myCircle() {
		super(0, null, 0, null, myTable.defaultThickness);

		myStats.nbCercle += 1;
	}

	public myCircle(myCircle orig) {
		super(orig._color, orig._refLayer, 0, orig._lineType, orig._thickness);
		_point = new myPoint(orig._point);
		_radius = orig._radius;

	}

	@Override
	public void draw(Graphics g) {
		double x = myCoord.dxfToJava_X(_point.X());
		double y = myCoord.dxfToJava_Y(_point.Y());

		super.draw(g);

		_e.setFrameFromCenter(x, y, (x - (_radius * myCoord.Ratio)),
				(y - (_radius * myCoord.Ratio)));
		/*
		 * =new Ellipse2D.Double(myCoord.dxfToJava_X(_point.X()-_radius),
		 * myCoord.dxfToJava_Y(_point.Y()+_radius), (_radius*2*myCoord.Ratio),
		 * (_radius*2*myCoord.Ratio) );
		 */
		((Graphics2D) g).draw(_e);
	}

	public static myCircle read(myBufferedReader br, myUnivers univers)
			throws NumberFormatException, IOException {

		String ligne, ligne_temp;
		int visibility = 0, color = 0;
		double x = 0, y = 0, r = 0, thickness = 1;
		myLayer l = null;
		myLineType lineType = null;

		myLog.writeLog("> new myCircle");
		while ((ligne = br.readLine()) != null && !ligne.equalsIgnoreCase("0")) {
			ligne_temp = ligne;
			ligne = br.readLine();

			if (ligne_temp.equalsIgnoreCase("8")) {
				l = univers.findLayer(ligne);
			} else if (ligne_temp.equalsIgnoreCase("60")) {
				visibility = Integer.parseInt(ligne);
			} else if (ligne_temp.equalsIgnoreCase("62")) {
				color = Integer.parseInt(ligne);
			} else if (ligne_temp.equalsIgnoreCase("6")) {
				lineType = univers.findLType(ligne);
			} else if (ligne_temp.equalsIgnoreCase("40")) {
				r = Double.parseDouble(ligne);
			} else if (ligne_temp.equalsIgnoreCase("10")) {
				x = Double.parseDouble(ligne);
			} else if (ligne_temp.equalsIgnoreCase("39")) {
				thickness = Double.parseDouble(ligne);
			} else if (ligne_temp.equalsIgnoreCase("20")) {
				y = Double.parseDouble(ligne);
			} else {
				myLog.writeLog("Unknown :" + ligne_temp + "(" + ligne + ")");
			}
		}
		return new myCircle(new myPoint(x, y, color, l, visibility, 1), r,
				lineType, color, l, visibility, thickness);
	}

	@Override
	public void write(FileWriter out) throws IOException {
		out.write("CIRCLE\n");
		super.writeCommon(out);
		out.write("40\n");
		out.write((_radius) + "\n");
		out.write("10\n");
		out.write(_point.X() + "\n");
		out.write("20\n");
		out.write(_point.Y() + "\n");
		out.write("39\n");
		out.write(_thickness + "\n");
		out.write("0\n");
	}

	@Override
	public String toString() {
		return DXF_Loader.res.getString("myCircle.0");
	}

	@Override
	public DefaultMutableTreeNode getNode() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode(this);
		root.add(new DefaultMutableTreeNode(new myLabel(myLabel.X, String
				.valueOf(_point.X()))));
		root.add(new DefaultMutableTreeNode(new myLabel(myLabel.Y, String
				.valueOf(_point.Y()))));
		root.add(new DefaultMutableTreeNode(new myLabel(myLabel.RADIUS, String
				.valueOf(_radius))));
		root.add(new DefaultMutableTreeNode(new myLabel(myLabel.THICKNESS,
				String.valueOf(_thickness))));

		Vector<DefaultMutableTreeNode> v = super.getCommonNode();
		for (int i = 0; i < v.size(); i++)
			root.add(v.get(i));
		// System.out.println(myLabel.X);
		// System.out.println(root);
		// System.out.println(String.valueOf(_point.X()));
		return root;
	}

	@Override
	public myLabel getNewLabel(String code, Object newValue)
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
	public Ellipse2D.Double getSelectedEntity() {
		return _e;
	}

	@Override
	public void translate(double x, double y) {
		this._point._point.x -= myCoord.getTransalation(x);
		this._point._point.y += myCoord.getTransalation(y);
	}

	@Override
	public double getMinX(double min) {
		if ((_point.X() - _radius) < min)
			return _point.X() - _radius;
		return min;
	}

	@Override
	public double getMaxX(double max) {
		if ((_point.X() + _radius) > max)
			return _point.X() + _radius;
		return max;
	}

	@Override
	public double getMinY(double min) {
		if ((_point.Y() - _radius) < min)
			return _point.Y() - _radius;
		return min;
	}

	@Override
	public double getMaxY(double max) {
		if ((_point.Y() + _radius) > max)
			return _point.Y() + _radius;
		return max;
	}

	public static Ellipse2D.Double getSmallerGraphicEntity(Ellipse2D.Double orig) {
		Ellipse2D.Double ret = new Ellipse2D.Double();
		ret.setFrameFromCenter(orig.getCenterX(), orig.getCenterY(),
				orig.getMaxX() - 10, orig.getMaxY() - 10);
		return ret;
	}

}
