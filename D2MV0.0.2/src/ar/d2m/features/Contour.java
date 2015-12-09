package ar.d2m.features;

import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.util.Hashtable;

import fr.epsi.dxf.DXF_Loader;
import fr.epsi.dxf.Entities.myLine;
import fr.epsi.dxf.Entities.myPoint;
import fr.epsi.dxf.Graphics.DXF_Color;
import fr.epsi.dxf.Graphics.myCanvas;
import fr.epsi.dxf.Graphics.myCoord;
import fr.epsi.dxf.Header.myLineType;

import ar.d2m.data.Entities;

public class Contour {

	protected static Hashtable contourEntities;
	protected static int colorContour;
	
	public Contour() {
		super();
	}
	
	public static Hashtable getContourEntities(){
		colorContour=170;
		contourEntities= Entities.getFeature(colorContour);
		System.out.println(contourEntities.size());
		return contourEntities;
	}
	
	public static void drawContour(DXF_Loader dxf){
	dxf._mc.big.drawLine(10, 10, 100, 100);
	myLine l = new myLine(new myPoint(new Point2D.Double(
			myCoord.javaToDXF_X(10),
			myCoord.javaToDXF_Y(10))), new myPoint(
			new Point2D.Double(myCoord.javaToDXF_X(100),
					myCoord.javaToDXF_Y(100))),
			DXF_Color.getColor(dxf._jcc.getColor()),
			dxf._u.currLayer,
			(myLineType) dxf._comboLineType.getSelectedItem(),
			dxf._u.currThickness, 0);
	dxf._u.currLayer._myEnt.addElement(l);
	dxf.tree.addEntity(l);
	dxf._mc.repaint();
	System.out.println("Se ha dibujado la linea");
	}

}