package ar.d2m.graphicalinterface;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.InternationalFormatter;

import fr.epsi.dxf.Entities.myEntity;
import ar.d2m.features.Feature;
import ar.d2m.loader.D2MLoader;

public class DefineContour extends JFrame implements ActionListener{

	public JButton addLine, addArc, accept, cancel;
	public JLabel xini, yini, xfin, yfin;
	public JFormattedTextField xinicial, yinicial, xfinal, yfinal;
	public Hashtable<Integer,myEntity> contour;
	public JPanel panel;
	public JPanel line;
	public JPanel arc;
	public DefineContour(){
		contour= new Hashtable<Integer,myEntity>();
		addLine= new JButton("add Line");
		addArc= new JButton("add Arc");
		accept= new JButton("accept");
		cancel= new JButton("cancel");
		panel= new JPanel();
		panel.setLayout(new BorderLayout());
		this.add(panel);
		panel.add(addLine);
		panel.add(addArc);
		line= new JPanel();
		arc= new JPanel();
		InternationalFormatter formato = new InternationalFormatter();
		formato.setMaximum(new Integer(1000));
		formato.setMinimum(new Integer((int) 0.001));
		xinicial= new JFormattedTextField(formato);
		xinicial.setValue(new Integer(0));
		yinicial= new JFormattedTextField(formato);
		xfinal= new JFormattedTextField(formato);
		yfinal= new JFormattedTextField(formato);
		line.add(xinicial);
		line.add(yinicial);
		line.add(xfinal);
		line.add(yfinal);
		panel.add(accept);
		panel.add(cancel);
		this.setVisible(true);
		addLine.addActionListener(this);
		this.setMinimumSize(new Dimension(200,200));
		this.setPreferredSize(new Dimension(200,200));
		}
	public Hashtable setContour(){
		accept.addActionListener(this);
		return contour;
	}
	
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource()==accept){
			contour.put(contour.size()+1,Feature.drawLine(D2MLoader.DXF,(Integer)xinicial.getValue(),(Integer)yinicial.getValue(),(Integer)xfinal.getValue(),(Integer)yfinal.getValue()));
			System.out.println(contour.size());
		}
		if(arg0.getSource()==addLine){
			panel.add(line);
		}
	}
}
