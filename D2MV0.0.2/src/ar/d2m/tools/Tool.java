package ar.d2m.tools;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ar.d2m.graphicalinterface.GraphicalInterface;

public class Tool extends JPanel{
	public JTable toolTable;
	public JScrollPane Scroll;
	String[] columnNames = { "Tool", GraphicalInterface.res.getString("feature1"),GraphicalInterface.res.getString("feature2"),
			GraphicalInterface.res.getString("feature3"), GraphicalInterface.res.getString("feature4") };
	Object[][] data = {
			{ "Tool Nr", (double) 1, (double) 2, (double) 3, (double) 4 },
			{ "Diameter", (double) 25, (double) 12, (double) 4, (double) 6 },
			{ "Speed", (double) 1000, (double) 2000, (double) 2000,
					(double) 2000 },
			{ "Feed Rate", (double) 400, (double) 200, (double) 200,
					(double) 100 },
			{ "Step", (double) 0.5, (double) 0.5, (double) 0.5,
					(double) 0.5 },
			{ "Z safe",(double)5,	(double)5,	(double)5,	(double)5},
			{ "Z Tool change",(double)50,(double)50,(double)50,(double)50}			
					};
	public Tool(){
	DefaultTableModel modelo = new DefaultTableModel(data, columnNames) {
		@Override
		public Class getColumnClass(int columna) {
			if (columna == 0)
				return String.class;
			return Double.class;
		}

		public boolean isCellEditable(int row, int column) {
			if (column == 0) {
				return false;
			} else {
				return true;
			}
		}
	};
		

	toolTable = new JTable(modelo);
	toolTable.setRowHeight(30);
	toolTable.setPreferredScrollableViewportSize(new Dimension(370,
			220));
	 Scroll = new JScrollPane(toolTable);
	 Scroll.setVisible(true);
	}

}
