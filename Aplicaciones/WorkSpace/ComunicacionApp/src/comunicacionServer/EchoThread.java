package comunicacionServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class EchoThread extends Thread {
	
    protected Socket socket;

    public EchoThread(Socket clientSocket) {
        this.socket = clientSocket;
    }

    public void run() {
        InputStream inp = null;
        BufferedReader in = null;
        OutputStreamWriter out = null;
        
        try {
            inp = socket.getInputStream();
            in = new BufferedReader(new InputStreamReader(inp));
            out = new OutputStreamWriter(socket.getOutputStream());
        } catch (IOException e) {
            return;
        }
        
        Integer numeroOperacion = -1;
        String line;
        
        while (true) {
            try {
                line = in.readLine();
                if ((line == null) || line.equalsIgnoreCase("QUIT")) {
                    socket.close();
                    return;
                } else {
                	if (numeroOperacion != -1) {
                		numeroOperacion = confirmarOperacion(line);
                		if (numeroOperacion != 0) {
                			out.write("Operacion aceptada");
                		}
                		else {
                			out.write("Operacion denegada");
                			return;
                		}
                	}
                	else {
                		aplicarOperacion(numeroOperacion, line);
                		out.write("Operacion realizada");
                	}
                	
                    out.write(line + "\n\r");
                    out.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
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