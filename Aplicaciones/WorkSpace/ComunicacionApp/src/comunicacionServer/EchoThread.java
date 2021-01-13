package comunicacionServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class EchoThread extends Thread {
	
    protected Socket socket;
    GestorElementos gestor;

    public EchoThread(Socket clientSocket, GestorElementos gestor) {
        this.socket = clientSocket;
        this.gestor = gestor;
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
                	if (numeroOperacion == -1) {
                		numeroOperacion = confirmarOperacion(line);
                		if (numeroOperacion != 0) {
                			out.write("Operacion aceptada\n");
                			
                		}
                		else {
                			out.write("Operacion denegada\n");
                			return;
                		}
                	}
                	else {
                		gestor.addElementoACola(numeroOperacion, line);
                		out.write("Operacion realizada\n");
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
		default: numeroOperacion = 0; break;
		}
		
		return numeroOperacion;
	}
}