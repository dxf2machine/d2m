/*
 * [ 1719398 ] First shot at LWPOLYLINE
 * Peter Hopfgartner - hopfgartner
 *  
*/


package myDXF.Entities;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Arc2D;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import javax.swing.tree.DefaultMutableTreeNode;

import myDXF.DXF_Loader;
import myDXF.myUnivers;
import myDXF.Graphics.myCoord;
import myDXF.Graphics.myLabel;
import myDXF.Graphics.myLog;
import myDXF.Header.myLayer;
import myDXF.Header.myLineType;
import myDXF.Header.myStats;
import myDXF.Header.myTable;

public class myLwPolyline extends myEntity {

	private static final long serialVersionUID = 1L;
	public String _name = DXF_Loader.res.getString("myLwPolyline.0");
	public int _flag = 0;
	public Vector<myLwVertex> _myVertex = new Vector<myLwVertex>();
	GeneralPath poly = new GeneralPath();

	public myLwPolyline(String name, int flag, int c, myLayer l,
			Vector<myLwVertex> v, int visibility, myLineType lineType,
			double thickness) {
		super(c, l, visibility, lineType, thickness);
		_name = name;

		if (v == null) {
			v = new Vector<myLwVertex>();
		}
		_myVertex = v;
		_flag = flag;

		myStats.nbLwPolyline += 1;
	}

	public myLwPolyline(myLayer l) {
		super(-1, l, 0, null, myTable.defaultThickness);
		myStats.nbLwPolyline += 1;
	}

	public myLwPolyline() {
		super(-1, null, 0, null, myTable.defaultThickness);
		myStats.nbLwPolyline += 1;
	}

	public myLwPolyline(myLwPolyline orig) {
		super(orig._color, orig._refLayer, 0, orig._lineType, orig._thickness);
		_name = orig._name;

		for (int i = 0; i < orig._myVertex.size(); i++) {
			_myVertex.add(new myLwVertex(orig._myVertex.elementAt(i), true));
		}
		_flag = orig._flag;
	}

	@Override
	public void draw(Graphics g) {
		if (_myVertex.size() <= 0 || _myVertex.firstElement() == null)
			return;

		myDXF.Graphics.myCanvas tmp = myDXF.DXF_Loader._mc;

		double max_y = tmp.getHeight();
		double px, py;
		double cx, cy;
		double bulge = _myVertex.firstElement()._bulge;
		double alfa, beta;

		double x1 = (_myVertex.firstElement().X());
		double y1 = max_y - _myVertex.firstElement().Y();
		double x2 = 0, y2 = 0;
		double p, r = 0, k = 0;
		Shape obj = null;
		poly = new GeneralPath();

		for (int j = 1; j < _myVertex.size(); j++) {
			x2 = (_myVertex.elementAt(j).X());
			y2 = max_y - _myVertex.elementAt(j).Y();

			if (Math.abs(bulge) > 0.01) {
				px = (x1 + x2) / 2;
				py = (y1 + y2) / 2;
				alfa = Math.atan(bulge) * 4;
				p = Math.sqrt(((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2)));
				r = Math.abs(p / 2 / Math.sin(alfa / 2));
				k = r * Math.cos(alfa / 2);
				if (bulge > 0) {
					cy = py - k * (x2 - x1) / p;
					cx = px - k * (y1 - y2) / p;
				} else {
					cy = py + k * (x2 - x1) / p;
					cx = px + k * (y1 - y2) / p;
				}

				if (((x1 - cx) / r) > 1.0)
					beta = 0;
				else if (((x1 - cx) / r) < -1)
					beta = 180;
				else if (y1 < cy)
					beta = Math.acos((x1 - cx) / r) * 180 / Math.PI;
				else
					beta = 360 - (Math.acos((x1 - cx) / r)) * 180 / Math.PI;

				if (beta < 0)
					beta += 360.0;

				obj = new Arc2D.Double(myCoord.dxfToJava_X(cx - r),
						myCoord.dxfToJava_Y(max_y - cy + r), 2.0 * r
								* myCoord.Ratio, 2.0 * r * myCoord.Ratio, beta,
						alfa * 180 / Math.PI, Arc2D.OPEN);

			} else {
				obj = new Line2D.Double(myCoord.dxfToJava_X(x1),
						myCoord.dxfToJava_Y(max_y - y1),
						myCoord.dxfToJava_X(x2),
						myCoord.dxfToJava_Y(max_y - y2));
			}

			(poly).append(obj, true);
			bulge = _myVertex.elementAt(j)._bulge;
			x1 = x2;
			y1 = y2;
		}
		if ((_myVertex.lastElement()._bulge != 0) && (_flag == 1)) {
			bulge = _myVertex.lastElement()._bulge;

			x1 = (_myVertex.lastElement().X());
			y1 = max_y - _myVertex.lastElement().Y();
			x2 = (_myVertex.firstElement().X());
			y2 = max_y - _myVertex.firstElement().Y();

			if (Math.abs(bulge) > 0.01) {
				px = (x1 + x2) / 2;
				py = (y1 + y2) / 2;
				alfa = Math.atan(bulge) * 4;
				p = Math.sqrt(((x1 - x2) * (x1 - x2)) + ((y1 - y2) * (y1 - y2)));
				r = Math.abs(p / 2 / Math.sin(alfa / 2));
				k = r * Math.cos(alfa / 2);
				if (bulge > 0) {
					cy = py - k * (x2 - x1) / p;
					cx = px - k * (y1 - y2) / p;
				} else {
					cy = py + k * (x2 - x1) / p;
					cx = px + k * (y1 - y2) / p;
				}

				if (((x1 - cx) / r) > 1.0)
					beta = 0;
				else if (((x1 - cx) / r) < -1)
					beta = 180;
				else if (y1 < cy)
					beta = Math.acos((x1 - cx) / r) * 180 / Math.PI;
				else
					beta = 360 - (Math.acos((x1 - cx) / r)) * 180 / Math.PI;

				if (beta < 0)
					beta += 360.0;

				obj = new Arc2D.Double(myCoord.dxfToJava_X(cx - r),
						myCoord.dxfToJava_Y(max_y - cy + r), 2.0 * r
								* myCoord.Ratio, 2.0 * r * myCoord.Ratio, beta,
						alfa * 180 / Math.PI, Arc2D.OPEN);
			} else {
				obj = new Line2D.Double(myCoord.dxfToJava_X(x1),
						myCoord.dxfToJava_Y(max_y - y1),
						myCoord.dxfToJava_X(x2),
						myCoord.dxfToJava_Y(max_y - y2));
			}
			(poly).append(obj, true);
		} else if (_flag == 1 && _myVertex.size() > 1)
			poly.closePath();

		super.draw(g);

		((Graphics2D) g).draw(poly);
	}

	public static myLwPolyline read(myBufferedReader br, myUnivers univers)
			throws IOException {
		String ligne, ligne_temp;
		String name = "";
		int visibility = 0, flag = 0, color = -1;
		myLineType lineType = null;
		Vector<myLwVertex> lv = new Vector<myLwVertex>();
		// myLwPolyline p = null;
		myLayer l = null;

		myLog.writeLog("> new myLwPolyline");
		while ((ligne = br.readLine()) != null
				&& !ligne.equalsIgnoreCase("SEQEND")) {
			ligne_temp = ligne;
			ligne = br.readLine();
			if (ligne_temp.equals("10")) {
				double x = Double.parseDouble(ligne);
				double bulge = 0.0, y = 0.0;
				while ((ligne_temp = br.readLine()) != null) {
					if (ligne_temp.equals("20")) {
						ligne = br.readLine();
						y = Double.parseDouble(ligne);
					} else if (ligne_temp.equals("40")) {
						ligne = br.readLine();
						// FIXME: Not used
					} else if (ligne_temp.equals("41")) {
						ligne = br.readLine();
						// FIXME: Not used
					}
					if (ligne_temp.equals("42")) {
						ligne = br.readLine();
						bulge = Double.parseDouble(ligne);
					} else {
						break;
					}
				}
				lv.addElement(new myLwVertex(x, y, bulge));
			}
			if (ligne_temp.equalsIgnoreCase("2")) {
				name = ligne;
			} else if (ligne_temp.equalsIgnoreCase("8")) {
				l = univers.findLayer(ligne);
			} else if (ligne_temp.equalsIgnoreCase("6")) {
				lineType = univers.findLType(ligne);
			} else if (ligne_temp.equalsIgnoreCase("62")) {
				color = Integer.parseInt(ligne);
			} else if (ligne_temp.equalsIgnoreCase("70")) {
				flag = Integer.parseInt(ligne);
			} else if (ligne_temp.equalsIgnoreCase("60")) {
				visibility = Integer.parseInt(ligne);
			} else if (ligne_temp.equalsIgnoreCase("0")) {
				break;
			} else {
				myLog.writeLog("Unknown :" + ligne_temp + "(" + ligne + ")");
			}
		}
		/*
		 * if(l==null) univers.findLayer("default");
		 */

		return new myLwPolyline(name, flag, color, l, lv, visibility, lineType,
				myTable.defaultThickness);
	}

	@Override
	public void write(FileWriter out) throws IOException {
		out.write("LWPOLYLINE\n");

		super.writeCommon(out);

		out.write("90\n");
		out.write(_myVertex.size() + "\n");

		out.write("70\n");
		out.write(_flag + "\n");

		out.write("2\n");
		out.write(_name + "\n");
		out.write("0\n");

		for (int i = 0; i < _myVertex.size(); i++) {
			out.write("10\n");
			out.write(_myVertex.elementAt(i).X() + "\n");
			out.write("20\n");
			out.write(_myVertex.elementAt(i).Y() + "\n");
			if (_myVertex.elementAt(i)._bulge > 0.0) {
				out.write("42\n");
				out.write(_myVertex.elementAt(i).Y() + "\n");
			}
		}

		out.write("SEQEND\n");
		out.write("0\n");
	}

	@Override
	public String toString() {
		return DXF_Loader.res.getString("myLwPolyline.1");
	}

	@Override
	public DefaultMutableTreeNode getNode() {

		DefaultMutableTreeNode root = new DefaultMutableTreeNode(this);
		root.add(new DefaultMutableTreeNode(new myLabel(myLabel.VALUE, _name)));
		root.add(new DefaultMutableTreeNode(new myLabel(myLabel.FLAG, String
				.valueOf(_flag))));

		Vector<DefaultMutableTreeNode> v = super.getCommonNode();
		for (int i = 0; i < v.size(); i++)
			root.add(v.get(i));

		for (int i = 0; i < _myVertex.size(); i++) {
			root.add(_myVertex.get(i).getNode());
		}

		return root;
	}

	@Override
	public myLabel getNewLabel(String code, Object newValue)
			throws NumberFormatException {
		myLabel l = null;

		if (code.equals(myLabel.FLAG)) {
			_flag = Integer.parseInt(newValue.toString());
			l = new myLabel(myLabel.FLAG, newValue.toString());
		} else if (code.equals(myLabel.VALUE)) {
			_name = newValue.toString();
			l = new myLabel(myLabel.VALUE, newValue.toString());
		} else {
			l = super.getCommonLabel(code, newValue);
		}

		return l;
	}

	@Override
	public GeneralPath getSelectedEntity() {
		return poly;
	}

	@Override
	public void translate(double x, double y) {
		for (int i = 0; i < _myVertex.size(); i++) {
			_myVertex.elementAt(i).translate(x, y);
		}
	}

	@Override
	public double getMinX(double min) {
		for (int k = 0; k < _myVertex.size(); k++) {
			min = _myVertex.elementAt(k).getMinX(min);
		}
		return min;
	}

	@Override
	public double getMaxX(double max) {
		for (int k = 0; k < _myVertex.size(); k++) {
			max = _myVertex.elementAt(k).getMaxX(max);
		}
		return max;
	}

	@Override
	public double getMinY(double min) {
		for (int k = 0; k < _myVertex.size(); k++) {
			min = _myVertex.elementAt(k).getMinY(min);
		}
		return min;
	}

	@Override
	public double getMaxY(double max) {
		for (int k = 0; k < _myVertex.size(); k++) {
			max = _myVertex.elementAt(k).getMaxY(max);
		}
		return max;
	}
}
