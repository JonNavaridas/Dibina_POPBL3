package comunicacionServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	public static void main(String[] args) throws IOException {
		ServerSocket server;
		Socket socket;
		int port;
		
		if (args.length < 1) {
			System.out.println("Sintaxis de llamada: java EcoServ <puerto>");
			System.exit(-1);
		}
		else {
			port = Integer.valueOf(args[0]).intValue();
			server = new ServerSocket(port);
			
			while (true) {
				socket = server.accept();
				new EchoThread(socket).start();
			}
		}
	}
}