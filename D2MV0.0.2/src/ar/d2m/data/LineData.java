package ar.d2m.data;

import fr.epsi.dxf.Entities.myLine;

public class LineData extends Data {
	public double xi;
	public double yi;
	public double xf;
	public double yf;
	public LineData(myLine element) {
		super(element);
		xi= element._a.X();
		yi= element._a.Y();
		xf= element._b.X();
		yf= element._b.Y();
		// TODO Auto-generated constructor stub
	}

}
