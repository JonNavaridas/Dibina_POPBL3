package comunicacionServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
	
	public static final int PUERTO = 8888;
	GestorElementos gestor;
	
	@SuppressWarnings("resource")
	public void administrarConexiones() throws IOException {
		ServerSocket server;
		Socket socket;
		int port;
		
		port = Integer.valueOf(PUERTO).intValue();
		server = new ServerSocket(port);
		gestor = null;
		
		new KeyboardListener().start();
		System.out.println("############################################");
		System.out.println("               SERVER RUNNING               ");
		System.out.println("############################################");
		GestorElementos.writeLog("Server on\n");
		
		while (true) {
			socket = server.accept();
			if (gestor == null) gestor = new GestorElementos();
			
			new EchoThread(socket, gestor).start();
			if (gestor.elementosEnLista() == 0 && !gestor.getRunning())
				gestor = null;
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
					GestorElementos.writeLog("Server off\n");
					
					running = false;
					teclado.close();
					System.exit(0);
				}
			} while (running);
		}
	}
	
	public static void main(String[] args) throws IOException {		
		Server server = new Server();
		server.administrarConexiones();
	}
}