package ar.d2m.features;

import java.awt.Color;
import java.awt.geom.Point2D;

import ar.d2m.loader.D2MLoader;
import fr.epsi.dxf.DXF_Loader;
import fr.epsi.dxf.Entities.myLine;
import fr.epsi.dxf.Entities.myPoint;
import fr.epsi.dxf.Graphics.DXF_Color;
import fr.epsi.dxf.Graphics.myCoord;
import fr.epsi.dxf.Header.myLineType;

public class Feature{

	public static int color;
	public void setEntitiesTable() {
		// TODO Auto-generated method stub
		
	}
	public static myLine drawLine(DXF_Loader dxf, int xi, int yi, int xf, int yf){
		dxf._mc.big.drawLine(xi, yi,xf , yf);
		myLine l = new myLine(new myPoint(new Point2D.Double(
				myCoord.javaToDXF_X(xi),
				myCoord.javaToDXF_Y(yi))), new myPoint(
				new Point2D.Double(myCoord.javaToDXF_X(xf),
						myCoord.javaToDXF_Y(yf))),
				DXF_Color.getColor( dxf._jcc.getColor()),
				dxf._u.currLayer,
				(myLineType) dxf._comboLineType.getSelectedItem(),
				dxf._u.currThickness, 0);
		dxf._u.currLayer._myEnt.addElement(l);
		dxf.tree.addEntity(l);
		dxf._mc.repaint();
		return l;
	}
	
	public void drawArc(){
		
	}
}
