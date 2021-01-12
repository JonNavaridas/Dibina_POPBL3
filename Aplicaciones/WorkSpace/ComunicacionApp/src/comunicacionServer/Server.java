package comunicacionServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	public static void main(String[] args) throws IOException {
		OutputStreamWriter out;
		BufferedReader in;
		ServerSocket s;
		Socket c;
		int port;
		
		String mensaje;
		String operacion;
		Server operador;
		
		if(args.length < 1) {
			System.out.println("Sintaxis de llamada: java EcoServ <puerto>");
			System.exit(-1);
		}
		else {
			port = Integer.valueOf(args[0]).intValue();
			s= new ServerSocket(port);
			operador = new Server();
			c = s.accept();
			
			in = new BufferedReader(new InputStreamReader( c.getInputStream()));
			out = new OutputStreamWriter(c.getOutputStream());
			
			while((operacion=in.readLine())!=null) {
				System.out.println("Operacion a realizar: " + operacion);
				
				if (operador.confirmarOperacion(operacion) != 0) {
					out.write("Operacion confirmada\n");
					mensaje = in.readLine();

					out.write("Operacion realizada" + '\n');
					out.write(mensaje + '\n');
					out.flush();	
				}
			}
		
			out.close();
			in.close();
			c.close();
			s.close();
		}
	}
	
	public int confirmarOperacion(String operacion) {
		int numeroOperacion;
		
		switch(operacion) {
		case "add pedido": numeroOperacion = 1; break;
		case "remove productos": numeroOperacion = 2; break;
		case "new user": numeroOperacion = 3; break;
		default: numeroOperacion = 0; break; // Sino devolvemos false.
		}
		
		return numeroOperacion;
	}
	
	public void aplicarOperacion(int operacion, String elemento) {
		
		switch(operacion) {
		case 1: 
			break;
		case 2:
			break;
		case 3:
			break;
		default: System.out.println("Operacion no conocida.");
			break;
		}
		
	}
}