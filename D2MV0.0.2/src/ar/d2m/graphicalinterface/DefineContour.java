package ar.d2m.graphicalinterface;

import java.awt.Color;
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
	public DefineContour(){
		contour= new Hashtable<Integer,myEntity>();
		addLine= new JButton("add Line");
		addArc= new JButton("add Arc");
		accept= new JButton("accept");
		cancel= new JButton("cancel");
		JPanel panel= new JPanel();
		panel.setLayout(new GridLayout(4,2));
		this.add(panel);
		panel.add(addLine);
		panel.add(addArc);
		InternationalFormatter formato = new InternationalFormatter();
		formato.setMaximum(new Integer(1000));
		formato.setMinimum(new Integer((int) 0.01));
		xinicial= new JFormattedTextField(formato);
		xinicial.setValue(new Integer(0));
		yinicial= new JFormattedTextField(formato);
		xfinal= new JFormattedTextField(formato);
		yfinal= new JFormattedTextField(formato);
		panel.add(xinicial);
		panel.add(yinicial);
		panel.add(xfinal);
		panel.add(yfinal);
		panel.add(accept);
		panel.add(cancel);
		this.setVisible(true);
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
	}
}
