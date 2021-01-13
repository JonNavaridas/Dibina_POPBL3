package comunicacionServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	GestorElementos gestor;
	
	@SuppressWarnings("resource")
	public void administrarConecsiones(String puerto) throws IOException {
		ServerSocket server;
		Socket socket;
		int port;
		
		port = Integer.valueOf(puerto).intValue();
		gestor = new GestorElementos();
		server = new ServerSocket(port);
		
		while (true) {
			socket = server.accept();
			new EchoThread(socket, gestor).start();
		}
	}
	
	public static void main(String[] args) throws IOException {		
		if (args.length < 1) {
			System.out.println("Sintaxis de llamada: java EcoServ <puerto>");
			System.exit(-1);
		}
		else {
			Server server = new Server();
			server.administrarConecsiones(args[0]);
		}
	}
}