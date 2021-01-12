package comunicacionSockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

public class Cliente extends Thread {

	private static final String PUERTO = "8888";
	private static final String IP = "10.0.2.5";
	
	public String mensaje;
	public String operacion;
	public JScrollPane panel;
	
	public Cliente(String mensaje, String operacion, JScrollPane panel) {
		this.mensaje = mensaje;
		this.operacion = operacion;
	}
	
	@Override
	public void run() {
		Socket sock = null;
		PrintWriter out = null;
		BufferedReader in = null;
		
		int port = Integer.valueOf(PUERTO).intValue();
		
		try {
			sock = new Socket(IP, port);
			out = new PrintWriter(sock.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		}
		catch (UnknownHostException e) {
			System.err.println("No se nada del host " + IP);
		}
		catch (IOException e) {
			System.err.println("No pude conectarme a " + IP + ":8000");
		}
		
		try {
			// Place holders
			operacion = "add pedido";
			mensaje = "pedido nuevo";
			
			out.println(operacion);
			String respuesta = in.readLine();
			
			if (respuesta.equals("Operacion aceptada")) {
				out.println(mensaje);
				respuesta = in.readLine();
				
				if (!respuesta.equals("Operacion realizada"))
					throw new ComunicationException("Ha ocurrido un error con la conexion.");
			}
			else {
				throw new ComunicationException("Ha ocurrido un error con la conexion.");
			}
			// Terminar la conexion
			out.println("QUIT");
			
			out.close();
			in.close();
			sock.close();
		}
		catch (ComunicationException e) {
			JOptionPane.showMessageDialog(panel, e.getMessage(), "Error de Conexion", JOptionPane.ERROR_MESSAGE);
			out.println("QUIT");
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
