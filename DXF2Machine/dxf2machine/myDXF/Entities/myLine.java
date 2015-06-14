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
 * 20141002 v0.0.2 CeGu add obtenerDatos() method
 * 20140428 v0.0.1 CeGu fork from DXF project

*/    

package myDXF.Entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;

import cggDatos.DatosLinea;
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

public class myLine extends myEntity {

	private static final long serialVersionUID = 1L;
	public myPoint _a = new myPoint();
	public myPoint _b = new myPoint();
	private Line2D.Double _l = new Line2D.Double();
	public boolean ubicado = false;
	public int posicion = 0;
	static DecimalFormat formatoNumero = (DecimalFormat) DecimalFormat.getNumberInstance();

	public myLine(myPoint a, myPoint b, int c, myLayer l, myLineType lineType,
			double thickness, int visibility) {
		super(c, l, visibility, lineType, thickness);
		_a = a;
		_b = b;
		// myDXF.Entities.TablaLineas.Tabla(datos);
		myStats.nbLine += 1;

	}

	@Override
	public void obtenerDatos() {
		double Xinicial = _a.X();
		double Yinicial = _a.Y();
		double Xfinal = _b.X();
		double Yfinal = _b.Y();
		int orientacion = 0;
		Xinicial=(double) FormatoNumeros.formatearNumero(Xinicial);
		Yinicial=(double) FormatoNumeros.formatearNumero(Yinicial);
		Xfinal=(double) FormatoNumeros.formatearNumero(Xfinal);
		Yfinal=(double) FormatoNumeros.formatearNumero(Yfinal);
		datos datos = new DatosLinea(Xinicial,Yinicial,Xfinal,Yfinal, _color, ubicado, posicion, orientacion);
		Tabla.agregarDatoATabla(datos);
	}

	public myLine() {
		super(-1, null, 0, null, myTable.defaultThickness);
		myStats.nbLine += 1;
	}

	public myLine(myLine original) {
		super(original._color, original._refLayer, 0, original._lineType,
				original._thickness);
		_a = new myPoint(original._a);
		_b = new myPoint(original._b);

		myStats.nbLine += 1;
	}

	@Override
	public void draw(Graphics g) {

		_l.setLine(myCoord.dxfToJava_X(_a.X()), myCoord.dxfToJava_Y(_a.Y()),
				myCoord.dxfToJava_X(_b.X()), myCoord.dxfToJava_Y(_b.Y()));

		super.draw(g);
		((Graphics2D) g).draw(_l);
	}

	@Override
	public Line2D.Double getSelectedEntity() {
		return _l;
	}

	public static myLine read(myBufferedReader br, myUnivers univers)
			throws IOException {
		String ligne = "", ligne_temp = "";
		myLayer l = null;
		double x1 = 0, y1 = 0, x2 = 0, y2 = 0, thickness = 0;
		myLineType lineType = null;
		int visibility = 0, c = -1;
		myLog.writeLog("> new myLine");

		while ((ligne = br.readLine()) != null
				&& !ligne.trim().equalsIgnoreCase("0")) {
			ligne_temp = ligne;
			ligne = br.readLine();

			if (ligne_temp.equalsIgnoreCase("10")) {
				x1 = Double.parseDouble(ligne);
			} else if (ligne_temp.equalsIgnoreCase("20")) {
				y1 = Double.parseDouble(ligne);
			} else if (ligne_temp.equalsIgnoreCase("11")) {
				x2 = Double.parseDouble(ligne);
			} else if (ligne_temp.equalsIgnoreCase("21")) {
				y2 = Double.parseDouble(ligne);
			} else if (ligne_temp.equalsIgnoreCase("8")) {
				l = univers.findLayer(ligne);
			} else if (ligne_temp.equalsIgnoreCase("62")) {
				c = Integer.parseInt(ligne);
			} else if (ligne_temp.equalsIgnoreCase("6")) {
				lineType = univers.findLType(ligne);
			} else if (ligne_temp.equalsIgnoreCase("39")) {
				thickness = Double.parseDouble(ligne);
				;
			} else if (ligne_temp.equalsIgnoreCase("60")) {
				visibility = Integer.parseInt(ligne);
			} else {
				myLog.writeLog("Unknown :" + ligne_temp + "(" + ligne + ")");
			}
		}
		return new myLine(new myPoint(x1, y1, c, l, visibility, 1),
				new myPoint(x2, y2, c, l, visibility, 1), c, l, lineType,
				thickness, visibility);
	}

	@Override
	public void write(FileWriter out) throws IOException {
		out.write("LINE\n");
		out.write("10\n");
		out.write(_a.X() + "\n");
		out.write("20\n");
		out.write(_a.Y() + "\n");
		out.write("11\n");
		out.write(_b.X() + "\n");
		out.write("21\n");
		out.write(_b.Y() + "\n");
		out.write("6\n");
		out.write(_lineType + "\n");
		out.write("39\n");
		out.write(_thickness + "\n");
		super.writeCommon(out);
		out.write("0\n");
	}

	@Override
	public String toString() {
		return DXF_Loader.res.getString("myLine.0");
	}

	@Override
	public DefaultMutableTreeNode getNode() {

		DefaultMutableTreeNode root = new DefaultMutableTreeNode(this);

		root.add(new DefaultMutableTreeNode(new myLabel(myLabel.XA, String
				.valueOf(this._a.X()))));
		root.add(new DefaultMutableTreeNode(new myLabel(myLabel.YA, String
				.valueOf(this._a.Y()))));
		root.add(new DefaultMutableTreeNode(new myLabel(myLabel.XB, String
				.valueOf(this._b.X()))));
		root.add(new DefaultMutableTreeNode(new myLabel(myLabel.YB, String
				.valueOf(this._b.Y()))));
		root.add(new DefaultMutableTreeNode(new myLabel(myLabel.THICKNESS,
				String.valueOf(this._thickness))));

		Vector<DefaultMutableTreeNode> v = super.getCommonNode();

		for (int i = 0; i < v.size(); i++)
			root.add(v.get(i));
		return root;
	}

	@Override
	public myLabel getNewLabel(String code, Object newValue)
			throws NumberFormatException {
		myLabel l = null;

		if (code.equals(myLabel.XA)) {
			_a.setX(Double.parseDouble(newValue.toString()));
			l = new myLabel(myLabel.XA, newValue.toString());
		} else if (code.equals(myLabel.YA)) {
			_a.setY(Double.parseDouble(newValue.toString()));
			l = new myLabel(myLabel.YA, newValue.toString());
		} else if (code.equals(myLabel.XB)) {
			_b.setX(Double.parseDouble(newValue.toString()));
			l = new myLabel(myLabel.XB, newValue.toString());
		} else if (code.equals(myLabel.YB)) {
			_b.setY(Double.parseDouble(newValue.toString()));
			l = new myLabel(myLabel.YB, newValue.toString());
		} else if (code.equals(myLabel.THICKNESS)) {
			_thickness = Double.parseDouble(newValue.toString());
			l = new myLabel(myLabel.THICKNESS, newValue.toString());
		} else {
			l = super.getCommonLabel(code, newValue);
		}
		return l;
	}

	@Override
	public void translate(double x, double y) {
		this._a._point.x -= myCoord.getTransalation(x);
		this._a._point.y += myCoord.getTransalation(y);
		this._b._point.x -= myCoord.getTransalation(x);
		this._b._point.y += myCoord.getTransalation(y);
	}

	@Override
	public double getMinX(double min) {
		if (_a.X() < min)
			min = _a.X();
		if (_b.X() < min)
			min = _b.X();
		return min;
	}

	@Override
	public double getMaxX(double max) {
		if (_a.X() > max)
			max = _a.X();
		if (_b.X() > max)
			max = _b.X();
		return max;
	}

	@Override
	public double getMinY(double min) {
		if (_a.Y() < min)
			min = _a.Y();
		if (_b.Y() < min)
			min = _b.Y();
		return min;
	}

	@Override
	public double getMaxY(double max) {
		if (_a.Y() > max)
			max = _a.Y();
		if (_b.Y() > max)
			max = _b.Y();

		return max;
	}

	@Override
	protected void finalize() throws Throwable {
		myStats.nbLine -= 1;
		super.finalize();
	}

}
