package conexionSockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Cliente extends Thread {

	private static final String PUERTO = "8888";
	private static final String IP = "127.0.0.1";
	
	public String mensaje;
	public String operacion;
	
	public Cliente(String mensaje, String operacion) {
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
			System.out.println("No se nada del host " + IP);
		}
		catch (IOException e) {
			System.out.println("No pude conectarme a " + IP);
		}
		
		try {
			if (out != null && in != null) {
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
		}
		catch (ComunicationException e) {
			System.out.println("Error de Conexion");
			out.println("QUIT");
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
