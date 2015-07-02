/*------------------------------------------------------------------------------------------ 
Copyright 2014, Celeste Gabriela Guagliano. 

This file is part of DXF2Machine project. 

DXF2Machine is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 2 of the License. 

DXF2Machine is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. 

You should have received a copy of the GNU General Public License along with DXF2Machine. If not, see <http://www.gnu.org/licenses/>.
  --------------------------------------------------------------------------------------------*/

package cggGCode;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Hashtable;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

import cggColeccion.*;
import cggDatos.Coordenadas;
import cggDatos.DatosArcos;
import cggDatos.DatosCirculo;
import cggDatos.DatosLinea;
import cggDatos.FormatoNumeros;
import cggDatos.Herramienta;
import cggDatos.datos;
import cggOptimizacion.*;
import cggTablas.Tabla;
import cggTablas.TablaAgujeros;
import cggTablas.TablaContorno;
import cggTablas.TablaGrabado;
import cggTablas.TablaTocho;
import myDXF.DXF_Loader;
/**
 * This class implements the necessary algorithms to translate all the collected data into G-Code  
 * @author: Celeste G. Guagliano
 * @version: 13/01/15
 * 
 */ 

public class GCode {
	public static String nombreA;
    public static String nombreArchivo;
    public static String encabezado;
    public static String cambioHerramienta;
    public static String definirAvance;
    public static String avanceRapido;
    public static String avanceRapidoZ;
    public static String avanceLineal;
    public static String avanceSegmentoCircularHorario;
    public static String avanceSegmentoCircularAntihorario;
    public static String avanceCircularHorario;
    public static String avanceCircularAntihorario;
    public static String taladrado;
    public static String taladradoProfundo;
    public static String coordenadas;
    public static String cancelarCicloFijo;
    public static String terminacion;
    public static String llamadaSubprograma;
    public static String retornoSubprograma;
    public static String rampaZ;
    public static boolean subprograma;
    public static String avanceLinealZ;
    public static String nombreSubprograma;
    public static String coordenadaAbsoluta;
    public static String coordenadaRelativa;
    public static String Ruta;
    public static String properties;
    public static int nroSubprograma=0;
    public static String llamadaSubprog;
    public static JTextArea grabado = new JTextArea();
    public static JTextArea contorneado = new JTextArea();
    public static JTextArea taladro = new JTextArea();
    public static JTextArea plano=new JTextArea();
        
//	public GCode() {
		// Tabla.ObtenerTablas();
		// TODO Auto-generated constructor stub
//	}

	/** Method to generate the drill process.
	 * @param mecanizarRasgo 
	 * @param Lista
	 * @param Herramientas
	 * @param profu
	 * @param principal
	 * @return a point.
	 */
	public static JTextArea GenerarTaladrado(GCode mecanizarRasgo, Hashtable Lista,
			Hashtable Herramientas, double profu, JTextArea principal) {
		Hashtable TaladradoOptimizado = ObtenerTaladradoOptimizado(Lista);
		int P = 1;
		datos elemento = (datos) TaladradoOptimizado.get(1);
		double profuRasgo = Double.parseDouble(DXF_Loader.profTaladro.getText());
		Herramienta herramienta = (Herramienta) Herramientas.get("Taladrado");
		double zcambio=herramienta.Zcambio;
		double zsafe=herramienta.Zseguro;
		GCodeMetodoTaladrado.inicializarTaladrado(elemento,Herramientas,principal,profuRasgo);
		for (int i = 2; i <= TaladradoOptimizado.size(); i++) {
			elemento = (datos) TaladradoOptimizado.get(i);
			String linea = elemento.taladrate();
			taladro.append(linea);
		}
		finalizarTaladrado(taladro);
		principal = finalizar(taladro, principal, P,zcambio);
		return principal;

	}


	private static void finalizarTaladrado(JTextArea taladrado) {
		taladrado.append(cancelarCicloFijo);
		// TODO Auto-generated method stub
	}

	



	public JTextArea GenerarContorneado(GCode mecanizarRasgo, Hashtable Lista,
			Hashtable Herramientas, double profu, JTextArea principal) {
		Herramienta herramienta = (Herramienta) Herramientas.get("Contorneado");
		Hashtable ContornoOptimizado=new Hashtable();
		ContornoOptimizado = ObtenerContorneadoOptimizado(Lista,herramienta);
		double Zcambio=herramienta.Zcambio;
		double pasada = herramienta.Pasada;
	//	inicializar((datos) ContornoOptimizado.get(1), herramienta, contorno);
		double profuRasgo = Double.parseDouble(DXF_Loader.profContorno
				.getText());
		int P = 0;
		if (pasada > profuRasgo) {
			pasada = profuRasgo;
		} else {
			pasada =(double) FormatoNumeros.formatearNumero(ObtenerPasada(pasada, profuRasgo));
		}
			P = ObtenerRepeticiones(pasada, profuRasgo);
            inicializarRasgo(principal, herramienta,
			(datos) ContornoOptimizado.get(1), profuRasgo,0);
			contorneado = mecanizarRasgo.mecanizarLista(ContornoOptimizado, contorneado, pasada, P,
				herramienta);
		principal = finalizar(contorneado, principal, 1,Zcambio);
		return principal;

	}

	private static int ObtenerRepeticiones(double pasada, double profuRasgo) {
		// TODO Auto-generated method stub
		int P = (int) (profuRasgo / pasada);
		return P;
	}

	private static double ObtenerPasada(double pasada, double profuRasgo) {
		// TODO Auto-generated method stub
		double cantidad = 0;
		if (pasada <= profuRasgo) {
			cantidad = profuRasgo / pasada;
			if (cantidad - (int) cantidad > 0) {
				pasada = profuRasgo / ((int) cantidad);
			}
		} else {
			pasada = profuRasgo;
		}
        pasada=(double) FormatoNumeros.formatearNumero(pasada);
        return pasada;
	}

	public JTextArea GenerarGrabado(GCode mecanizarRasgo,Hashtable Lista,
		Hashtable Herramientas, double profu, JTextArea principal) {
		Hashtable GrabadoOptimizado = ObtenerGrabadoOptimizado(Lista);
		
		Herramienta herramienta = (Herramienta) Herramientas.get("Grabado");
		double pasada = herramienta.Pasada;
		double zcambio=herramienta.Zcambio;
		int P = 0;
		double profuRasgo = Double.parseDouble(DXF_Loader.profGrabo.getText());
		if (pasada > profuRasgo) {
			pasada = profuRasgo;
		} else {
			pasada = ObtenerPasada(pasada, profuRasgo);
		}
			P = ObtenerRepeticiones(pasada, profuRasgo);
		inicializarRasgo(principal, herramienta,
				(datos) GrabadoOptimizado.get(1), profuRasgo,pasada);
		grabado = mecanizarRasgo.mecanizarLista(GrabadoOptimizado, grabado, pasada, P,
				herramienta);
		P=1;
		principal = finalizar(grabado, principal, P,zcambio);
		return principal;

	}

	public JTextArea mecanizarLista(Hashtable lista, JTextArea rasgo,
			double pasada, int P, Herramienta herramienta) {
		// Subclase
		return null;
	}

	private static void agregarPrimerAvance(JTextArea grabado,
			Herramienta herramienta2, datos datos) {
		String linea = datos.mecanizate();
		//grabado.append(linea + avance + herramienta2.Avance + EOL);
		// TODO Auto-generated method stub

	}

	public static JTextArea GenerarPlaneado(GCode mecanizarRasgo,Hashtable Lista,
			Hashtable herramientas, double profu, JTextArea principal) {
		Hashtable PlaneadoOptimizado = ObtenerPlaneadoOptimizado(Lista);
		// primero inicializamos el mecanizado
		// recorremos la tabla diciendole a cada entidad que se mecanice y le
		// pasamos el postprocesador
		Herramienta herramienta = (Herramienta) herramientas.get("Planeado");
		double zcambio=herramienta.Zcambio;
		double pasada = herramienta.Pasada;
		int P = 0;
	//	inicializar((datos) PlaneadoOptimizado.get(1), herramienta, planeado);
		double profuRasgo = Double.parseDouble(DXF_Loader.profPlano.getText());
		if (pasada > profuRasgo) {
			pasada = profuRasgo;
			} else {
			pasada = ObtenerPasada(pasada, profuRasgo);
			}
			P = ObtenerRepeticiones(pasada, profuRasgo);
		inicializarRasgo(principal, herramienta,
				(datos) PlaneadoOptimizado.get(1), profuRasgo,pasada);
		
		plano = mecanizarRasgo.mecanizarLista(PlaneadoOptimizado, plano, pasada, P,
				herramienta);
		P=1;
		principal = finalizar(plano, principal, P,zcambio);
		return principal;
	}

	
	private static JTextArea finalizar(JTextArea rasgo, JTextArea principal,
			int p, double zcambio) {
		
		// TODO Auto-generated method stub
		rasgo=chequearContenido(rasgo);
		if (subprograma == false) {
			principal.append(rasgo.getText());
		} else {
			++nroSubprograma;
			llamadaSubprog = llamadaSubprograma.replace("programa", Integer.toString(nroSubprograma));
			llamadaSubprog=llamadaSubprog.replace("repeticiones",Integer.toString(p));
			String nombreSubprog=nombreSubprograma.replace("nombre",Integer.toString(nroSubprograma));
			String ruta = new String(Ruta + nombreSubprog);
			//System.out.println(ruta);
			//String numero=incrementarLinea(++nroLineaSubprog);
     		rasgo.append(retornoSubprograma);
			try {
				BufferedWriter out = new BufferedWriter(new FileWriter(ruta));
				out.write(rasgo.getText());
				out.close();
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(null, ex.getMessage());
			}
			//String nume = ("N" + (++nroLineaPrincipal));
			principal.append(llamadaSubprog);
			//nume = ("N" + (++nroLineaPrincipal));
			
		
		}
		String linea=coordenadaAbsoluta+avanceRapidoZ.replace("z",Double.toString(zcambio));
		principal.append(linea);
		return principal;
	}

	
	private static JTextArea chequearContenido(JTextArea rasgo) {
		// TODO Auto-generated method stub
		if(rasgo.getText()==null){
			rasgo.setText("Lo siento, algunos de los elementos que desea mecanizar no son admitidos a�n por DXF2GCode,"
					+ "Por favor, revise las entidades seleccionadas o modifique el archivo en su editor CAD para proseguir.");
		}
		return rasgo;
	}

	public static Hashtable ObtenerPlaneadoOptimizado(Hashtable listaTocho) {
		Hashtable ListaTochoOptimizada = new Hashtable();
		ListaTochoOptimizada = Optimizacion.Optimizacion(listaTocho);
		return ListaTochoOptimizada;
	}

	
	public static Hashtable ObtenerContorneadoOptimizado(Hashtable listaContorno,Herramienta contorneado) {
		Hashtable ListaContornoOptimizada = new Hashtable();
		ListaContornoOptimizada = OptimizacionMetodo2.Optimizacion(TablaContorno.ListaContorno,contorneado.Diametro/2);
		return ListaContornoOptimizada;
	}

	public static Hashtable ObtenerGrabadoOptimizado(Hashtable listaGrabado) {
		Hashtable ListaGrabadoOptimizada = new Hashtable();
		ListaGrabadoOptimizada = OptimizacionMetodo1
				.Optimizacion(TablaGrabado.ListaGrabado);
		return ListaGrabadoOptimizada;
	}

	public static Hashtable ObtenerTaladradoOptimizado(Hashtable listaAgujeros) {
		Hashtable ListaAgujerosOptimizada = new Hashtable();
		ListaAgujerosOptimizada = Optimizacion
				.Optimizacion(TablaAgujeros.ListaAgujeros);
		return ListaAgujerosOptimizada;
	}

	public static JTextArea EncabezarPrograma(JTextArea principal) {
		String nroProg = "0001";
		//nroLineaPrincipal = 1;
		//String nroLinea = ("N" + nroLineaPrincipal);
		//if (nombreEnEncabezado == true) {
		String linea=encabezado.replace("numero", nroProg);
			principal.append(linea);
		//	nroLineaPrincipal += 1;
	//	} 
		
		return principal;
	}

	public static void archivarMecanizado(JTextArea mecanizado, JTextPane consola) {
		
		//nroLineaPrincipal = mecanizado.getLineCount() + numeracionReferencia;
		//String nume = incrementarLinea(++nroLineaPrincipal);
		mecanizado.append(terminacion);
		int numero=0;
		File f;
		String ruta;
		do{
		++numero;
		nombreA=nombreArchivo.replace("nombre",Integer.toString(numero));
		ruta = new String(Ruta +nombreA);
		f = new File(ruta);
		}while(f.exists());
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(ruta));
			out.write(mecanizado.getText());
			out.close();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage());
		}
	}

	public static JTextArea inicializarRasgo(JTextArea principal,
			Herramienta tool, datos inicial, double profRasgo, double pasada) {
		int nroHerramienta = (int) tool.Numero;
		int velocidadHerramienta = (int) tool.Velocidad;
		double referencia=tool.Zseguro;
		double ZcambioHerramienta=tool.Zcambio;
		double avanceT= tool.Avance;
		Coordenadas inicio=new Coordenadas(0,0);
		inicio =ColeccionFunciones.ObtenerCoordenadaInicioEntidad(inicial);
		String linea=cambioHerramienta.replaceAll("herramienta",Integer.toString(nroHerramienta));
		linea=linea.replace("velocidad", Integer.toString(velocidadHerramienta));
		linea=linea.replace("x",Double.toString(inicio.x));
		linea=linea.replace("y",Double.toString(inicio.y));
		linea=linea.replace("planoRetraccion", Double.toString(referencia));
		principal.append(linea);
		linea=definirAvance.replace("referencia",Double.toString(referencia));
		linea=linea.replace("avance",Double.toString(avanceT));
		principal.append(linea);
		linea=avanceLinealZ.replace("z","-"+Double.toString(pasada));
		principal.append(linea);
		return principal;
	}

		public static String incrementarLinea(int numero) {
		String linea = null;
		linea = ("N" + numero);
		return linea;
	}

	public static void prepararPostprocesador(String postprocesador,
			String ruta2) {
		Ruta = ruta2;
		properties = postprocesador;
		ResourceBundle maquina = ResourceBundle.getBundle(properties);
		nombreArchivo=maquina.getString("nombreArchivo");
		encabezado=maquina.getString("encabezado");
		cambioHerramienta=maquina.getString("cambioHerramienta");
		definirAvance=maquina.getString("definirAvance");
		avanceRapido=maquina.getString("avanceRapidoXY");
		avanceRapidoZ=maquina.getString("avanceRapidoZ");
		avanceLineal=maquina.getString("avanceLineal");
		avanceSegmentoCircularHorario=maquina.getString("avanceSegmentoCircularHorario");
		avanceSegmentoCircularAntihorario=maquina.getString("avanceSegmentoCircularAntihorario");
		avanceCircularHorario=maquina.getString("avanceCircularHorario");
		avanceCircularAntihorario=maquina.getString("avanceCircularAntihorario");
		taladrado=maquina.getString("taladrado");
		taladradoProfundo=maquina.getString("taladradoProfundo");
		coordenadas=maquina.getString("coordenadas");
		cancelarCicloFijo=maquina.getString("finalizarCicloFijo");
		terminacion=maquina.getString("terminacion");
	    llamadaSubprograma=maquina.getString("llamadaASubprog");
		retornoSubprograma=maquina.getString("retornoDeSubprograma");
		subprograma=Boolean.parseBoolean(maquina.getString("subprogramas"));
		avanceLinealZ=maquina.getString("avanceLinealZ");
		nombreSubprograma=maquina.getString("nombreSubprograma");
		coordenadaAbsoluta=maquina.getString("coordenadaAbsoluta");
		coordenadaRelativa=maquina.getString("coordenadaRelativa");
		rampaZ=maquina.getString("rampaZ");
	}


	public static String avanceLineal(DatosLinea elemento) {
		String linea = null;
        Coordenadas dato=ColeccionFunciones.ObtenerCoordenadaFinEntidad(elemento);
		linea = avanceLineal.replace("x",Double.toString(dato.x));
		linea=linea.replace("y",Double.toString(dato.y));
		return linea;
	}

	public static String avanceRapido(datos elemento) {
		String linea = null;
		Coordenadas dato=ColeccionFunciones.ObtenerCoordenadaInicioEntidad(elemento);
		linea = avanceRapido.replace("x", Double.toString(dato.x));
		linea=linea.replace("y",Double.toString(dato.y));
		return linea;
	}

	public static String avanceSegmentoCircular(DatosArcos elemento) {
		String linea = null;
		Coordenadas dato=ColeccionFunciones.ObtenerCoordenadaFinEntidad(elemento);
		Coordenadas dato2=ColeccionFunciones.ObtenerCoordenadaInicioEntidad(elemento);
		if (elemento.orientacion == 0) {
			linea=avanceSegmentoCircularAntihorario;
		}else{
			linea=avanceSegmentoCircularHorario;
		}
			linea=linea.replace("x",Double.toString(dato.x));
			linea=linea.replace("y", Double.toString(dato.y));
			linea=linea.replace("i", Double.toString((double) FormatoNumeros.formatearNumero(elemento.Xcentro-dato2.x)));
			linea=linea.replace("j", Double.toString((double) FormatoNumeros.formatearNumero(elemento.Ycentro-dato2.y)));
			return linea;
	}

	
	public static String avanceCicular(DatosCirculo elemento) {
		String linea1=null;
		String linea2=null;
		String linea=null;
		Coordenadas dato=ColeccionFunciones.ObtenerCoordenadaFinEntidad(elemento);
		Coordenadas dato2=ColeccionFunciones.ObtenerCoordenadaInicioEntidad(elemento);
		if(elemento.orientacion==0){
			linea1=avanceCircularHorario;
			linea2=avanceCircularHorario;
		}else{
			linea1=avanceCircularAntihorario;
			linea2=avanceCircularAntihorario;
		}
		linea1=linea1.replace("x", Double.toString(dato.x));
		linea1=linea1.replace("y",Double.toString(dato.y));
		linea1=linea1.replace("radio", Double.toString(elemento.Radio));
		linea1=linea1.concat("\r\n");
		linea2=linea2.replace("x", Double.toString(dato2.x));
		linea2=linea2.replace("y", Double.toString(dato2.y));
		linea2=linea2.replace("radio", Double.toString(elemento.Radio));
		linea=linea1.concat(linea2);
		return linea;
	}

	public static String coordenadasTaladro(DatosCirculo elemento) {
		String linea = coordenadas.replace("x",Double.toString(elemento.CentroX));
		linea=linea.replace("y", Double.toString(elemento.CentroY));
		return linea;
	}

	public static void formatearConsola(JTextArea principal,JTextPane consola) {
		consola.setContentType("text/html");
		String allText = principal.getText() ;
		String formateo="";
		StringTokenizer st = new StringTokenizer(allText,"\r\n") ;
		formateo= formateo+"<font color=\"black\">(CODIGO DEL PROGRAMA PRINCIPAL</font><br>";
		formateo= formateo+"<font color=\"black\">UBICACION: "+Ruta+nombreA+")</font><br>";
		while (st.hasMoreTokens()) {
		     String line = st.nextToken();
		     formateo= formateo+"<font color=\"black\">"+line+"</font><br>";
		 	}
		allText=plano.getText();
		if(allText.isEmpty()!=true){
		st = new StringTokenizer(allText,"\r\n") ;
		formateo= formateo+"<font color=\"#87828B\">(CODIGO DE PLANEADO)</font><br>";
		while (st.hasMoreTokens()) {
		     String line = st.nextToken();
		     formateo= formateo+"<font color=\"#87828B\">"+line+"</font><br>";
		 	}
		}
		allText=contorneado.getText();
		if(allText.isEmpty()!=true){
		st = new StringTokenizer(allText,"\r\n") ;
		 formateo= formateo+"<font color=\"blue\">(CODIGO DE CONTORNEADO)</font><br>";
		while (st.hasMoreTokens()) {
		     String line = st.nextToken();
		     formateo= formateo+"<font color=\"blue\">"+line+"</font><br>";
		 	}
		}
		allText=grabado.getText();
		if(allText.isEmpty()!=true){
		st = new StringTokenizer(allText,"\r\n") ;
		formateo= formateo+"<font color=\"#0CB7F2\">(CODIGO DE GRABADO)</font><br>";
		while (st.hasMoreTokens()) {
		     String line = st.nextToken();
		     formateo= formateo+"<font color=\"#0CB7F2\">"+line+"</font><br>";
		 	}
		}
		allText=taladro.getText();
		if(allText.isEmpty()!=true){
		st = new StringTokenizer(allText,"\r\n") ;
		formateo= formateo+"<font color=\"#00ff00\">(CODIGO DE TALADRADO)</font><br>";
		while (st.hasMoreTokens()) {
		     String line = st.nextToken();
		     formateo= formateo+"<font color=\"#00ff00\">"+line+"</font><br>";
		 	}
		}
		consola.setText(formateo);
		
        
       //consola.add(contorneado);
       //consola.add(grabado);
		}
		// TODO Auto-generated method stub
		
	}

