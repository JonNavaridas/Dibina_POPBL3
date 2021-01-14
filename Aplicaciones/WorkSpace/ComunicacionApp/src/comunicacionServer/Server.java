package comunicacionServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

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
		
		new KeyboardListener().start();
		System.out.println("############################################");
		System.out.println("               SERVER RUNNING               ");
		System.out.println("############################################");
		
		while (true) {
			socket = server.accept();
			new EchoThread(socket, gestor).start();
		}
	}
	
	public class KeyboardListener extends Thread {

		@Override
		public void run() {
			Scanner teclado = new Scanner(System.in);
			boolean running = true;
			
			do {
				if (teclado.nextLine().toLowerCase().equals("quit")) {
					System.out.println("Closing server...");
					running = false;
					teclado.close();
					System.exit(0);
				}
			} while (running);
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