package comunicacionSockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import elementos.Pedido;

public class Cliente extends Thread {

	public String[] args;
	public Pedido mensaje;
	
	public Cliente(Pedido mensaje) {
		args = new String[2];
		args[0] = "169.254.247.245";
		args[1] = "8888";
		this.mensaje = mensaje;
	}
	
	@Override
	public void run() {
		Socket sock = null;
		PrintWriter out = null;
		BufferedReader in=null;
		int port;

		if(args.length < 2) {
			System.out.println("Sintaxis de llamada: java EcoCliente <host> <puerto>");
			System.exit(-1);
		}
		port=Integer.valueOf(args[1]).intValue();
		
		try {
			sock = new Socket(args[0], port);
			out = new PrintWriter(sock.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		}
		catch (UnknownHostException e) {
			System.err.println("No se nada del host "+args[0]);
			System.exit(1);
		}
		catch (IOException e) {
			System.err.println("No pude conectarme a "+args[0]+":8000");
			System.exit(1);
		}
		
		try {
			//out.println(mensaje);
			System.out.println("Estoy aqui");
			out.println("Hola");
			out.print(mensaje);
			System.out.println("eco: " + in.readLine());
			out.close();
			in.close();
			sock.close();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
