/*--------------------------------------------------------------------------------------- 
Copyright 2007, Stephan Soulard and Edouard Vanhauwaert.
Copyright 2014, Celeste Gabriela Guagliano. 

This file was originally part of DXF project and then modified by 
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
 * 20150328 v0.0.5 CeGu add itemStateChanged() method
 * 20150328 v0.0.4 CeGu add isCellEditable() method
 * 20141120 v0.0.3 CeGu improve DXF_Loader() method
 * 20141002 v0.0.2 CeGu improve DXF_Loader() method
 * 20140828 v0.0.1 CeGu fork from DXF project

*/    

package myDXF;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Point;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

//import javafx.scene.control.ComboBox;

/*
public void actionPerformed(ActionEvent e) {
	int sel=((JComboBox)e.getSource()).getSelectedIndex();
	switch (sel) {
		case 0 : res = ResourceBundle.getBundle("myDXF.i18n.Ressources_EN");
				locale = new Locale("en", "US");
			    Locale.setDefault(locale);
			break;
		case 1 : res = ResourceBundle.getBundle("myDXF.i18n.Ressources_FR");
				locale = new Locale("fr", "FR");
			    Locale.setDefault(locale);
			break;
		case 2 : res = ResourceBundle.getBundle("myDXF.i18n.Ressources_ES");
				locale = new Locale("es", "ES");
			    Locale.setDefault(locale);
			break;
		default : res = ResourceBundle.getBundle("myDXF.i18n.Ressources_EN");
				locale = new Locale("en", "US");
				Locale.setDefault(locale);
			break;
	}
	doInternational();
	
}
});

*/





import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneLayout;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;

import cggGCode.GCode;
import cggGCode.SelectorDeDirectorio;
import cggTablas.Tabla;
import myDXF.Graphics.myCanvas;
import myDXF.Graphics.myCoord;
import myDXF.Graphics.myHistory;
import myDXF.Graphics.myJColorChooser;
import myDXF.Graphics.myJMenu;
import myDXF.Graphics.myLog;
import myDXF.Graphics.myToolBar;
import myDXF.Graphics.FileDrag.FileDrop;
import myDXF.Graphics.TreeView.myJTree;
import myDXF.Header.myHeader;
import myDXF.Header.myLineType;
import myDXF.Header.myStats;
import myDXF.Header.myTable;

/**
 * This class is the main class of the DXF2Machine project
 * @author: Stephan Soulard, Edouard Vanhauwaert
 * @version: 28/03/15 by Celeste Guagliano
 * 
 */ 
public class DXF_Loader extends JPanel implements ActionListener {
	public static ResourceBundle res = ResourceBundle
			.getBundle("myDXF.i18n.Ressources_ES");

	public static final long serialVersionUID = 1L;
	public static JTabbedPane tabPane;
	public static String Ruta = null;
	public myToolBar _typeOutil;
	public JComboBox _comboLineType;
	public myJColorChooser _jcc;
	public JSplitPane _sp;
	public JScrollPane _dxfScrollPane;
	public JScrollPane _toolScrollPane;
	public myJTree tree;
	public myUnivers _u;
	public myJMenu treeMenu;
	public JFrame frame;
	public String lastOpenDXF = "";
	public String lastSaveDXF = "";
	public String lastSaveAsImg = "";
	protected boolean init = false;
	public String postprocesador = null;

	public static DefaultMutableTreeNode defaultLayer;
	public static JTextField[] textFields;
	public static JButton back;
	public static JButton fwd;
	public static TextArea logText;
	public static myCanvas _mc;

	public static boolean checkLineType = false;

	public static Locale locale;

	public Label txtLine = new Label();
	public Label txtPoint = new Label();
	public Label txtArc = new Label();
	public Label txtCircle = new Label();
	public Label txtDimension = new Label();
	public Label GCodigo = new Label();

	public Checkbox chkWriteLog;
	public JLabel info;
	public JLabel clipB;
	public JLabel sel;
	public JLabel coordXY;
	public JProgressBar memoryProgress;
	public JLabel ram;
	public boolean proximitySelection = false;
	public final String defClipTxtA = res.getString("defClipTxtA");
	public final String defSelTxtA = res.getString("defSelTxtA");
	public final String txtB = "                                                      ";

	public Checkbox proximity;
	private Checkbox chk;
	private Checkbox ltype;
	private JLabel lbl_ltype;
	private JLabel lbl_thick;

	private Label lbl_mem;

	private Label i18n;

	private JMenuItem menuItemHelp;

	private JMenuItem menuItemAbout;

	public JMenu menu;
	public JMenu menuAide;

	public JButton jbClearLogs;
	public static boolean taladro;
	public static boolean contorno;
	public static boolean grabo;
	public static boolean plano;
	public static JTable TablaHerramientas = new JTable();
	public static JTextField profPlano = new JTextField("0.5");
	public static JTextField profContorno = new JTextField("0.5");
	public static JTextField profGrabo = new JTextField("0.5");
	public static JTextField profTaladro = new JTextField("0.5");
	public JComboBox postproce;
	public JButton GenerarCodigo;
	public JPanel botonDefineMecanizado;

	public DXF_Loader() {
		super();
 
		this.frame = new JFrame("DXF2Machine");
		this.frame.setIconImage(new ImageIcon(ClassLoader
				.getSystemResource("images/dxf.jpg")).getImage());
		this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.frame.setContentPane(this.createContentPane());

		new FileDrop(this.frame, new FileDrop.Listener() {
			@Override
			public void filesDropped(java.io.File[] files) {
				try {
					if (files[files.length - 1].getName().toLowerCase()
							.lastIndexOf(".dxf") != -1) {
						load(files[files.length - 1].getAbsoluteFile());
						lastOpenDXF = files[files.length - 1].getAbsoluteFile()
								.getParent();

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		this.frame.addComponentListener(new ComponentListener() {
			@Override
			public void componentHidden(ComponentEvent arg0) {
			}

			@Override
			public void componentMoved(ComponentEvent arg0) {
			}

			@Override
			public void componentResized(ComponentEvent arg0) {
				if (DXF_Loader.this.init)
					DXF_Loader.this.cadrer();
			}

			@Override
			public void componentShown(ComponentEvent arg0) {
			}
		});

		Dimension minimumSize = new Dimension(400, 300);

		tabPane = new JTabbedPane();
        JTabbedPane tab2Pane=new JTabbedPane();
		JPanel globalPanel = new JPanel(new GridLayout(0, 1));
		JPanel definirMecanizado= new JPanel(new GridLayout(0,1));
		JPanel botonDefineMeca= new JPanel(new GridLayout(1,1));
		JPanel toolPanel = new JPanel(new GridLayout(0, 1));
		JPanel optionPanel = new JPanel(new GridLayout(0, 1));
		JPanel statsPanel = new JPanel(new GridLayout(0, 1));

		// OPTION
		JPanel mecanizados = new JPanel(new GridLayout(0, 1));
		JPanel postProcesado = new JPanel(new GridLayout(0, 1));
		JPanel TiposyProfu = new JPanel(new GridLayout(0, 3));
		statsPanel.add(mecanizados, BorderLayout.PAGE_START);
		// mecanizados.setBorder(BorderFactory.createEmptyBorder(25, 15, 75,
		// 5));
		String postprocesadores[] = { "Sinumerik 810/820M", "HAAS/FANUC" };
		postproce = new JComboBox(postprocesadores);
		postprocesador = (String) postproce.getSelectedItem();
		postProcesado.add(postproce);
		Checkbox planeado = new Checkbox(res.getString("Planeado"));//"Planeado");
		Checkbox contorneado = new Checkbox(res.getString("Contorneado"));
		Checkbox grabado = new Checkbox(res.getString("Grabado"));
		Checkbox taladrado = new Checkbox(res.getString("Taladrado"));

		TiposyProfu.add(planeado);
		TiposyProfu.add(new JLabel("Prof Planeado:"));
		TiposyProfu.add(profPlano);
		TiposyProfu.add(contorneado);
		TiposyProfu.add(new JLabel("Prof Contorno:"));
		TiposyProfu.add(profContorno);
		TiposyProfu.add(grabado);
		TiposyProfu.add(new JLabel("Prof Grabado:"));
		TiposyProfu.add(profGrabo);
		TiposyProfu.add(taladrado);
		TiposyProfu.add(new JLabel("Prof Taladrado:"));
		TiposyProfu.add(profTaladro);

		JPanel generadorG = new JPanel(new GridLayout(0, 1));
		GenerarCodigo = new JButton("Generar Codigo");
		generadorG.add(GenerarCodigo);
		statsPanel.add(mecanizados);
		postProcesado
				.setBorder(BorderFactory.createEmptyBorder(15, 35, 30, 15));
		TiposyProfu.setSize(150, 150);
		TiposyProfu.setBorder(BorderFactory.createEmptyBorder(0, 35, 10, 15));
		generadorG.setBorder(BorderFactory.createEmptyBorder(15, 35, 35, 15));
		GenerarCodigo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == GenerarCodigo) {
					Ruta = SelectorDeDirectorio.SeleccionarDirectorio();
					Tabla.ObtenerTablas(postprocesador, Ruta);
				}
			}
		});
		postproce.addActionListener(this);

		mecanizados.add(postProcesado, BorderLayout.PAGE_START);
		mecanizados.add(TiposyProfu, BorderLayout.PAGE_START);
		mecanizados.add(generadorG, BorderLayout.PAGE_START);

		contorneado.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent item) {
				int status = item.getStateChange();
				if (status == ItemEvent.SELECTED)
					contorno = true;
				else
					contorno = false;
			}

		});
		planeado.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent item) {
				int status = item.getStateChange();
				if (status == ItemEvent.SELECTED)
					plano = true;
				else
					plano = false;
			}

		});
		grabado.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent item) {
				int status = item.getStateChange();
				if (status == ItemEvent.SELECTED)
					grabo = true;
				else
					grabo = false;
			}

		});

		taladrado.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent item) {
				int status = item.getStateChange();
				if (status == ItemEvent.SELECTED)
					taladro = true;
				else
					taladro = false;
			}

		});

		/*
		 * proximity = new Checkbox(res.getString("CHK_PROXIMITY"));
		 * proximity.addItemListener(new ItemListener(){
		 * 
		 * @Override public void itemStateChanged(ItemEvent item) { int status =
		 * item.getStateChange(); if (status == ItemEvent.SELECTED)
		 * DXF_Loader.this.proximitySelection = true; else
		 * DXF_Loader.this.proximitySelection = false; }
		 * 
		 * });
		 * 
		 * chk = new Checkbox(res.getString("DXF_Loader.32"));
		 * chk.addItemListener(new ItemListener(){
		 * 
		 * @Override public void itemStateChanged(ItemEvent item) { int status =
		 * item.getStateChange(); if (status == ItemEvent.SELECTED)
		 * myUnivers.antialiasing = true; else myUnivers.antialiasing = false; }
		 * 
		 * });
		 * 
		 * ltype = new Checkbox(res.getString("CHK_LINE_STYLE"));
		 * ltype.addItemListener(new ItemListener(){
		 * 
		 * @Override public void itemStateChanged(ItemEvent item) { int status =
		 * item.getStateChange(); if (status == ItemEvent.SELECTED)
		 * checkLineType = true; else { checkLineType = false;
		 * myCanvas.big.setStroke(myTable.defaultStroke); } }
		 * 
		 * });
		 */
		i18n = new Label(res.getString(res.getString("DXF_Loader.9")));

		String[] columnNames = { "Herramienta", "Planeado", "Contorneado",
				"Grabado", "Taladrado" };
		Object[][] data = {
				{ "Nro", (double) 1, (double) 2, (double) 3, (double) 4 },
				{ "Diametro", (double) 25, (double) 12, (double) 4, (double) 6 },
				{ "Velocidad", (double) 1000, (double) 2000, (double) 2000,
						(double) 2000 },
				{ "Avance", (double) 400, (double) 200, (double) 200,
						(double) 100 },
				{ "Pasada", (double) 0.5, (double) 0.5, (double) 0.5,
						(double) 0.5 },
				{ "Z seguro",(double)5,	(double)5,	(double)5,	(double)5},
				{ "Z cambio",(double)50,(double)50,(double)50,(double)50}			
						};
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

		TablaHerramientas = new JTable(modelo);
		TablaHerramientas.setRowHeight(30);
		TablaHerramientas.setPreferredScrollableViewportSize(new Dimension(300,
				300));
		JScrollPane Scroll = new JScrollPane(TablaHerramientas);
		optionPanel.add(Scroll);

		String[] listeCAP = { res.getString("DXF_Loader.54"),
				res.getString("DXF_Loader.55"), res.getString("DXF_Loader.56") };
		JComboBox comboCAP = new JComboBox(listeCAP);
		/*
		 * comboCAP.addActionListener(new ActionListener(){
		 * 
		 * @Override public void actionPerformed(ActionEvent e) { JComboBox cb =
		 * (JComboBox)e.getSource(); if(cb.getSelectedIndex() == 0)
		 * myTable.CAP=BasicStroke.CAP_ROUND; else if(cb.getSelectedIndex() ==
		 * 1) myTable.CAP=BasicStroke.CAP_BUTT; else if(cb.getSelectedIndex() ==
		 * 2) myTable.CAP=BasicStroke.CAP_SQUARE; if
		 * (e.getSource=="Generar Codigo"){
		 * 
		 * } } } );
		 */
		String[] listeJOIN = { res.getString("DXF_Loader.58"),
				res.getString("DXF_Loader.59"), res.getString("DXF_Loader.60") };
		// JComboBox comboJOIN = new JComboBox(listeJOIN);
		// comboJOIN.addActionListener(new ActionListener(){

	/*	String[] listeBGColor = { res.getString("DXF_Loader.62"),
				res.getString("DXF_Loader.63") };
		JComboBox comboBGColor = new JComboBox(listeBGColor);
		/*
		 * comboBGColor.addActionListener(new ActionListener(){
		 * 
		 * @Override public void actionPerformed(ActionEvent e) { JComboBox cb =
		 * (JComboBox)e.getSource(); if(cb.getSelectedIndex() == 0)
		 * fmyUnivers._bgColor=Color.BLACK; else myUnivers._bgColor=Color.WHITE;
		 * } });
		 */

		// TOOLS

		// MOVE TOOLS
		JPanel movePanel = new JPanel(new GridLayout(1, 10));
		movePanel.setMinimumSize(new Dimension(90, 30));
		movePanel.setPreferredSize(new Dimension(90, 30));
		JToolBar moveBar = new JToolBar(res.getString("DXF_Loader.8"));

		JButton zoomp = new JButton();
		zoomp.setActionCommand("zoom+");
		zoomp.addActionListener(this);
		zoomp.setToolTipText(res.getString("DXF_Loader.67"));
		zoomp.setIcon(new ImageIcon(ClassLoader
				.getSystemResource("images/zoomin.gif")));
		movePanel.add(zoomp, BorderLayout.SOUTH);

		JButton zoomm = new JButton();
		zoomm.setActionCommand("zoom-");
		zoomm.setToolTipText(res.getString("DXF_Loader.70"));
		zoomm.addActionListener(this);
		zoomm.setIcon(new ImageIcon(ClassLoader
				.getSystemResource("images/zoomout.gif")));
		movePanel.add(zoomm, BorderLayout.SOUTH);

		back = new JButton();
		back.setActionCommand("back");
		back.setToolTipText(res.getString("DXF_Loader.73"));
		back.addActionListener(this);
		back.setIcon(new ImageIcon(ClassLoader
				.getSystemResource("images/undo.gif")));
		back.setEnabled(false);
		movePanel.add(back, BorderLayout.SOUTH);

		fwd = new JButton();
		fwd.setActionCommand("fwd");
		fwd.setToolTipText(res.getString("DXF_Loader.76"));
		fwd.addActionListener(this);
		fwd.setIcon(new ImageIcon(ClassLoader
				.getSystemResource("images/redo.gif")));
		fwd.setEnabled(false);
		movePanel.add(fwd);
		

		JButton left = new JButton("");
		left.setActionCommand("left");
		left.setToolTipText(res.getString("DXF_Loader.80"));
		left.setIcon(new ImageIcon(ClassLoader
				.getSystemResource("images/gauche.gif")));
		left.addActionListener(this);
		movePanel.add(left);

		JButton right = new JButton("");
		right.setActionCommand("right");
		right.setToolTipText(res.getString("DXF_Loader.84"));
		right.setIcon(new ImageIcon(ClassLoader
				.getSystemResource("images/droite.gif")));
		right.addActionListener(this);
		movePanel.add(right);

		JButton up = new JButton();
		up.setActionCommand("up");
		up.setToolTipText(res.getString("DXF_Loader.87"));
		up.setIcon(new ImageIcon(ClassLoader
				.getSystemResource("images/haut.gif")));
		up.addActionListener(this);
		movePanel.add(up);

		JButton down = new JButton("");
		down.setActionCommand("down");
		down.setToolTipText(res.getString("DXF_Loader.91"));
		down.setIcon(new ImageIcon(ClassLoader
				.getSystemResource("images/bas.gif")));
		down.addActionListener(this);
		movePanel.add(down);

		JButton centrer = new JButton("");
		centrer.setIcon(new ImageIcon(ClassLoader
				.getSystemResource("images/cadrer.jpg")));
		centrer.setActionCommand("cadrer");
		centrer.setToolTipText(res.getString("DXF_Loader.96"));
		centrer.addActionListener(this);
		movePanel.add(centrer);

		JButton btnResetSize = new JButton("");
		btnResetSize.setIcon(new ImageIcon(ClassLoader
				.getSystemResource("images/reset.gif")));
		btnResetSize.addActionListener(this);
		btnResetSize.setToolTipText(res.getString("DXF_Loader.99"));
		btnResetSize.setActionCommand("reset_size");
		movePanel.add(btnResetSize);

		movePanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		moveBar.add(movePanel);
        
		
		// TYPE LINE
		JToolBar lineBar = new JToolBar(res.getString("DXF_Loader.101"));

		this._comboLineType = new JComboBox();
		this._comboLineType.setAutoscrolls(true);

		// THICKNESS
		SpinnerModel spinnerModel = new SpinnerNumberModel(1, 1, 256, 1);
		JSpinner spinnerThickness = new JSpinner(spinnerModel);
		spinnerThickness.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent event) {
				JSpinner source = (JSpinner) event.getSource();
				DXF_Loader.this._u.currThickness = Integer.parseInt(source
						.getValue().toString());
			}

		});
		lbl_ltype = new JLabel(res.getString("LBL_CURR_LTYPE"));

		logText = new myThreadedLogWriter();

		JPanel iLoveJavaGUI = new JPanel();

		this.chkWriteLog = new Checkbox(res.getString("CHK_LOG"));
		this.chkWriteLog.setState(false);
		this.chkWriteLog.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent item) {
				int status = item.getStateChange();
				if (status == ItemEvent.SELECTED)
					myLog.setActiv(true);
				else
					myLog.setActiv(false);
			}
		});

		iLoveJavaGUI.add(this.chkWriteLog, BorderLayout.WEST);

		JLabel jl = new JLabel("                          ");
		iLoveJavaGUI.add(jl, BorderLayout.CENTER);

		jbClearLogs = new JButton(res.getString("CLR_LOG"));
		jbClearLogs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logText.setText("");
			}
		});
		iLoveJavaGUI.add(jbClearLogs, BorderLayout.EAST);

		// COLOR
		this._jcc = new myJColorChooser();
		this._jcc.setBorder(BorderFactory.createTitledBorder("Definir Rasgos Mecanizables"));
		JToolBar colorBar = new JToolBar(res.getString("DXF_Loader.106"));
		colorBar.setBounds(20,20,200,300);
	//	_typeOutil = new myToolBar();
	//	_typeOutil.setBounds(50,50,10,20);
	//	botonDefineMeca.add(_typeOutil);
		_jcc.setBounds(200,200,50,50);
		//colorBar.add(botonDefineMeca);
		colorBar.add(this._jcc);
       // TREEVIEW
		JToolBar treeBar = new JToolBar(res.getString("DXF_Loader.107"));

		// DO NOT MOVE THIS LINE
		_mc = new myCanvas(this);
		this.tree = new myJTree(_mc);
		// La survie du monde en d�pend !

		//JPanel treeView = new JPanel();
		//treeView.setLayout(null);
	
	/*	minimumSize = new Dimension(400, 500);
		treeBar.setMinimumSize(minimumSize);
		treeBar.setPreferredSize(minimumSize);

	//	treeView.setPreferredSize(new Dimension(100, 100));

		treeBar.setMinimumSize(new Dimension(50, 50));
		treeBar.setPreferredSize(new Dimension(50, 50));
		// MISE EN FORME*/

		// toolPanel.add(this._typeOutil);
	    
		toolPanel.add(moveBar, BorderLayout.PAGE_START);
		moveBar.setBorder(BorderFactory.createEmptyBorder(25, 10, 50, 15));
		//toolPanel.add(lineBar);
		toolPanel.add(colorBar, BorderLayout.PAGE_START);
		colorBar.setBorder(BorderFactory.createEmptyBorder(0, 0, 50, 0));

		tabPane.add("Configuraciones", toolPanel);
		tabPane.add("Herramientas", optionPanel);
		tabPane.add("GCode", statsPanel);

		globalPanel.add(tabPane, BorderLayout.PAGE_START);
		tabPane.setBorder(BorderFactory.createEmptyBorder(15, 5, 50, 5));
		definirMecanizado.setBorder(BorderFactory.createEmptyBorder(0,5, 75, 5));
		

		JScrollPane mos=new JScrollPane(Tabla.consola);
		tab2Pane.add("Consola",mos);
        globalPanel.add(tab2Pane);
		this.tree._refCanva = _mc;
		this.treeMenu = new myJMenu(_mc);
		this._toolScrollPane = new JScrollPane(globalPanel);

		this._sp = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, globalPanel, _mc);
		this._sp.setOneTouchExpandable(true);
		this._sp.setDividerLocation(420);
		this._sp.setPreferredSize(new Dimension((int) (globalPanel
				.getPreferredSize().getWidth() + 802), 660));

		JPanel _infoBar = new JPanel(new BorderLayout());
		_infoBar.setSize(1, 50);

		JPanel memoryUsage = new JPanel(new BorderLayout());
		this.ram = new JLabel(res.getString("DXF_Loader.112"));
		this.ram.setOpaque(true);
		this.ram.setBackground(Color.DARK_GRAY);
		this.ram.setForeground(Color.WHITE);

		memoryUsage.add(this.ram, BorderLayout.EAST);
		new myThreadedRam(this);
		memoryUsage.add(this.memoryProgress, BorderLayout.CENTER);
		memoryUsage.setPreferredSize(new Dimension(384, 10));
		_infoBar.add(memoryUsage, BorderLayout.WEST);

		JPanel _labels = new JPanel(new BorderLayout());
		_labels.setSize(1, 50);

		this.coordXY = new JLabel();
		this.coordXY.setOpaque(true);
		this.coordXY.setBackground(Color.BLACK);
		this.coordXY.setForeground(Color.WHITE);
		_infoBar.add(this.coordXY, BorderLayout.CENTER);

		this.clipB = new JLabel(defClipTxtA + "0" + txtB);
		this.clipB.setOpaque(true);
		this.clipB.setBackground(Color.BLACK);
		this.clipB.setForeground(Color.WHITE);
		_labels.add(this.clipB, BorderLayout.CENTER);

		this.sel = new JLabel(defSelTxtA + "0" + txtB);
		this.sel.setOpaque(true);
		this.sel.setBackground(Color.BLACK);
		this.sel.setForeground(Color.WHITE);
		_labels.add(this.sel, BorderLayout.WEST);

		this.info = new JLabel(DXF_Loader.res.getString("READY"));
		this.info.setOpaque(true);
		this.info.setBackground(Color.BLACK);
		this.info.setForeground(Color.WHITE);
		_labels.add(this.info, BorderLayout.EAST);

		_infoBar.add(_labels, BorderLayout.EAST);

		_infoBar.setOpaque(true);
		_infoBar.setBackground(Color.BLACK);
		_infoBar.setForeground(Color.WHITE);

		this.frame.setLocation(10, 10);
		this.frame.setJMenuBar(this.createMenuBar());
		this.frame.getContentPane().add(this._sp, BorderLayout.NORTH);
		this.frame.getContentPane().add(_infoBar, BorderLayout.SOUTH);
		this.frame.pack();
		this.frame.setVisible(true);

		this._u = new myUnivers(new myHeader());
		this.updateLineTypeCombo();
		this.tree.createNodes();
	}

	public void doInternational() {
		proximity.setLabel(res.getString("CHK_PROXIMITY"));
		ltype.setLabel(res.getString("CHK_LINE_STYLE"));
		tabPane.setTitleAt(0, res.getString("TP_TOOLS"));
		tabPane.setTitleAt(1, res.getString("TP_LOGS"));
		tabPane.setTitleAt(2, res.getString("TP_OPTIONS"));
		tabPane.setTitleAt(3, res.getString("TP_STATS"));
		lbl_ltype.setText(res.getString("LBL_CURR_LTYPE"));
		lbl_thick.setText(res.getString("LBL_CURR_THICK"));
		chkWriteLog.setLabel(res.getString("CHK_LOG"));
		jbClearLogs.setText(res.getString("CLR_LOG"));
		lbl_mem.setText(res.getString("LBL_MEM"));
		sel.setText(res.getString("defSelTxtA") + _mc.vectClickOn.size() + txtB);
		clipB.setText(res.getString("defClipTxtA") + _mc.clipBoard.size()
				+ txtB);
		i18n.setText(res.getString("INTL"));
		myJMenu.file_filter = res.getString("FILE_FILTER");
		myJMenu.dialogNAME = DXF_Loader.res.getString("NEW_NAME");
		info.setText(res.getString("READY"));
		menuItemHelp.setText(res.getString("menuItemHelp"));
		menuItemAbout.setText(res.getString("menuItemAbout"));

		menu.setText(DXF_Loader.res.getString("menuItemFichier"));

		myJMenu.menuItemNouveau.setText(DXF_Loader.res
				.getString("menuItemNouveau"));
		myJMenu.menuItemOuvrir.setText(DXF_Loader.res
				.getString("menuItemOuvrir"));
		myJMenu.menuItemEnregistrer.setText(DXF_Loader.res
				.getString("menuItemEnregistrer"));
		myJMenu.menuItemEnregistrerSous.setText(DXF_Loader.res
				.getString("menuItemEnregistrerSous"));
		myJMenu.menuItemRenommer.setText(DXF_Loader.res
				.getString("menuItemRenommer"));
		myJMenu.menuItemExporter.setText(DXF_Loader.res
				.getString("menuItemExporter"));
		myJMenu.menuItemImprimer.setText(DXF_Loader.res
				.getString("menuItemImprimer"));
		myJMenu.menuItemQuitter.setText(DXF_Loader.res
				.getString("menuItemQuitter"));
	}

	public void updateLineTypeCombo() {
		Vector lt = this._u.getLTypes();
		this._comboLineType.removeAllItems();

		for (int i = 0; i < lt.size(); i++)
			this._comboLineType.addItem((lt.get(i)));
	}

	public void updateStats() {
		this.txtPoint.setText(String.valueOf(myStats.nbPoint));
		this.txtLine.setText(String.valueOf(myStats.nbLine));
		this.txtArc.setText(String.valueOf(myStats.nbArc));
		this.txtCircle.setText(String.valueOf(myStats.nbCercle));
		this.txtDimension.setText(String.valueOf(myStats.nbDimension));
	}

	public JMenuBar createMenuBar() {

		// JMenuItem menuItem = null;
		JMenuBar menuBar = new JMenuBar();
		Vector<JMenuItem> vm = myJMenu.getFileMenuItems(_mc, false);

		menu = new JMenu(res.getString("menuItemFichier"));
		menu.setMnemonic(KeyEvent.VK_A);

		for (int i = 0; i < vm.size(); i++) {
			if (vm.elementAt(i).getText().equals("Separateur")) {
				menu.addSeparator();
			} else {
				menu.add(vm.elementAt(i));
			}
		}
		menuBar.add(menu);

		menuAide = new JMenu(res.getString("DXF_Loader.147"));

		menuItemHelp = new JMenuItem(res.getString("menuItemHelp"));
		menuItemHelp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
				ActionEvent.CTRL_MASK));
		menuItemHelp.setIcon(new ImageIcon(ClassLoader
				.getSystemResource("images/aide.png")));
		menuItemHelp.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					// TODO --> 2

					Runtime.getRuntime().exec(
							"rundll32 url.dll,FileProtocolHandler "
									+ res.getString("URL_HELP"));

				} catch (IOException e) {
					myLog.writeLog(e.getMessage());
				}
			}
		});
		menuAide.add(menuItemHelp);

		menuAide.addSeparator();
		menuItemAbout = new JMenuItem(res.getString("menuItemAbout"));
		menuItemAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,
				ActionEvent.CTRL_MASK));
		menuItemAbout.setIcon(new ImageIcon(ClassLoader
				.getSystemResource("images/award.png")));
		menuItemAbout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFrame frame = new JFrame(res.getString("ABOUT_VERS"));
				frame.setLocation(200, 200);
				frame.setIconImage(new ImageIcon(ClassLoader
						.getSystemResource("images/award.png")).getImage());
				frame.setMinimumSize(new Dimension(400, 150));
				frame.getContentPane().setLayout(new GridLayout(0, 1));
				JLabel labelImage = new JLabel(new ImageIcon(ClassLoader
						.getSystemResource("images/about.jpg")));
				frame.getContentPane().add(labelImage);
				frame.pack();
				frame.setVisible(true);
			}
		});
		menuAide.add(menuItemAbout);
		menuBar.add(menuAide);

		return menuBar;
	}

	public Container createContentPane() {
		JPanel contentPane = new JPanel(new BorderLayout());
		JPanel menuPane = new JPanel(new BorderLayout());
		contentPane.setOpaque(true);
		menuPane.setVisible(true);
		contentPane.add(menuPane);
		return contentPane;
	}

	public void Center(Point double1) {
		if (double1.getX() < (myCoord.Max / 2)) {
			if (double1.getY() < (myCoord.Max / 2)) {
				myCoord.decalageX += ((myCoord.Max / 2) - double1.getX());
				myCoord.decalageY += ((myCoord.Max / 2) - double1.getY());
			} else if (double1.getY() > (myCoord.Max / 2)) {
				myCoord.decalageX += ((myCoord.Max / 2) - double1.getX());
				myCoord.decalageY -= (double1.getY() - (myCoord.Max / 2));
			}
		} else if (double1.getY() < (myCoord.Max / 2)) {
			myCoord.decalageX -= (double1.getX() - (myCoord.Max / 2));
			myCoord.decalageY += ((myCoord.Max / 2) - double1.getY());
		} else if (double1.getY() > (myCoord.Max / 2)) {
			myCoord.decalageX -= (double1.getX() - (myCoord.Max / 2));
			myCoord.decalageY -= (double1.getY() - (myCoord.Max / 2));
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "zoom+") {
			myCoord.Ratio += myCoord.ratioStep;
			myHistory.saveHistory(true);
		} else if (e.getActionCommand() == "zoom-") {
			if ((myCoord.Ratio - myCoord.ratioStep) < 1) {
				myCoord.ratioStep /= 2;
				myCoord.Ratio -= myCoord.ratioStep;
			} else
				myCoord.Ratio -= myCoord.ratioStep;
			myHistory.saveHistory(true);

		} else if (e.getActionCommand() == "back")
			myHistory.backToThePast();
		else if (e.getActionCommand() == "fwd")
			myHistory.backToTheFuture();
		else if (e.getActionCommand() == "left") {
			myCoord.decalageX -= 10;
			myHistory.saveHistory(true);
		} else if (e.getActionCommand() == "right") {
			myCoord.decalageX += 10;
			myHistory.saveHistory(true);
		} else if (e.getActionCommand() == "up") {
			myCoord.decalageY -= 10;
			myHistory.saveHistory(true);
		} else if (e.getActionCommand() == "down") {
			myCoord.decalageY += 10;
			myHistory.saveHistory(true);
		} else if (e.getActionCommand() == "cadrer")
			this.cadrer();
		else if (e.getActionCommand() == "reset_size") {
			myCoord.reset();
			myHistory.saveHistory(true);
		}
		this._u._header.setLIM(_mc.getWidth(), _mc.getHeight());

		this.tree.updateEnv();
		_mc.repaint();
		if (e.getSource() == postproce) {
			postprocesador = (String) postproce.getSelectedItem();
		}
		if (e.getSource() == GenerarCodigo) {
			Tabla.ObtenerTablas(postprocesador, Ruta);
		}
	}

	private static void createAndShowGUI() {
		new DXF_Loader();
	}

	public void newDXF() {

		if (_mc != null) {
			myCanvas._dxf.tree.createNodes();
			myCoord.reset();
			myHistory.resetHistory();
			myStats.reset();
			myCanvas._dxf.tree.updateEnv();

			_mc.selecting = false;
			_mc.moving = false;
			_mc.zooming = false;
			_mc.drawingCircle = false;
			_mc.drawingPolyLineStart = false;
			_mc.drawingPolyLineEnd = false;
			_mc.drawingLwPolyLineStart = false;
			_mc.drawingLwPolyLineEnd = false;
			_mc.drawingArc = false;
			_mc.drawingArcAngleStart = false;
			_mc.drawingArcAngleEnd = false;
			_mc.drawingEllipse = false;
			_mc.drawingTrace = false;
			_mc.drawingTxt = false;
			_mc.drawingSolid = false;
		}

		if (this._u != null)
			this._u.reset();

	}

	public void write(String nomFichier) throws Exception {
		FileWriter out = null;
		File f = null;

		try {
			f = new File(nomFichier);
			out = new FileWriter(f);

			if (this._u._header == null)
				this._u._header = new myHeader();

			this._u.writeHeader(out);
			this._u.writeTables(out);
			this._u.writeBlocks(out);
			this._u.writeEntities(out);

			out.write("EOF\n");
			out.close();
		} catch (IOException e) {
			myLog.writeLog(res.getString("DXF_Loader.168") + e.getMessage());
		}
		myLog.writeLog(res.getString("DXF_Loader.169"));

	}

	public void load(File Fichier) throws IOException, Exception {
		myLog.writeLog(" <------ START " + Fichier.getName() + " ------>");
		new myThreadedLoad(this, Fichier);
	}

	public void cadrer() {
		myCoord.Max = this._u.getMaxSpan();
		int w = DXF_Loader._mc.getWidth() - 10;
		int h = DXF_Loader._mc.getHeight() - 10;

		if (w < h)
			myCoord.Ratio = (w) / myCoord.Max;
		else
			myCoord.Ratio = (h) / myCoord.Max;
		myCoord.decalageX -= myCoord.dxfToJava_X(_u.lastView.getMinX())
				- myCoord.javaToDXF_X(0) - _u.strayX;
		myCoord.decalageY -= myCoord.dxfToJava_Y(_u.lastView.getMaxY())
				- _u.strayY;

		myHistory.saveHistory(true);
		this.tree.updateEnv();
	}

	public static void main(final String[] args) {

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JPopupMenu.setDefaultLightWeightPopupEnabled(false);
				createAndShowGUI();
				if ((args.length != 0)
						&& (args[0].toLowerCase().lastIndexOf(".dxf") != -1)) {
					File f = new File(args[0]);
					if (f.exists() && f.canRead()) {
						try {
							myCanvas._dxf.load(f);
						} catch (Exception e) {
							e.printStackTrace();
						}
						;
					}
				}
			}
		});

	}

	public boolean isCellEditable(int i, int j) {
		if (j == 1) {
			return false;
		}
		return true;

	}

	public void itemStateChanged(ItemEvent e) {
		if (e.getSource() == postproce) {
			postprocesador = (String) postproce.getSelectedItem();

		}
	}

}
