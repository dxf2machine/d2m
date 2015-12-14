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
		JPanel selector= new JPanel(new GridLayout(1,2));
		JPanel okcancel= new JPanel(new GridLayout(1,2));
		selector.add(addLine);
		selector.add(addArc);
		panel.add(selector,BorderLayout.NORTH);
		//panel.add(addArc,BorderLayout.NORTH);
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
		line.setLayout(new GridLayout(2,4));
		line.add(new JLabel("Start (X,Y)"));
		line.add(xinicial);
		line.add(new JLabel(" , "));
		line.add(yinicial);
		line.add(new JLabel("End (X,Y)"));
		line.add(xfinal);
		line.add(new JLabel(" , "));
		line.add(yfinal);
		okcancel.add(accept);
		okcancel.add(cancel);
		panel.add(okcancel,BorderLayout.SOUTH);
		panel.add(line,BorderLayout.CENTER);
		line.setVisible(false);
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
			xinicial.setValue(xfinal.getValue());
			yinicial.setValue(yfinal.getValue());
			xinicial.setEnabled(false);
			yinicial.setEnabled(false);
			xfinal.setValue(0);
			yfinal.setValue(0);
		}
		if(arg0.getSource()==addLine){
			line.setVisible(true);
		}
	}
}
