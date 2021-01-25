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

	@Override
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
                		if (!gestor.isAlive()) gestor.start();
                		out.write("Operacion realizada\n");
                	}
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
		case "Añadir pedido": numeroOperacion = 1; break; // Añadir un pedido a la lista de pedidos
		case "Eliminar pedido": numeroOperacion = 2; break; // Retirar n numero de pedidos (entregados o denegados)
		case "Crear usuario": numeroOperacion = 3; break; // Añadir un nuevo usuario
		case "Cambiar contraseña": numeroOperacion = 4; break; // Cambiar la contraseña de un usuario ya existente
		case "Aceptar pedido": numeroOperacion = 5; break; // Marcar un pedido procesado como aceptado
		case "Denegar pedido": numeroOperacion = 6; break; // Marcar un pedido procesando com denegado
		case "Entregar pedido": numeroOperacion = 7; break; // Marcar un pedido aceptado como entregado
		case "Rehacer pedido": numeroOperacion = 8; break; // Marcar un pedido denegado como procesando
		case "Sacar pedido de almacen": numeroOperacion = 9; break; // Informar de que un operario ha retirado stock del almacen
		case "Añadir stock": numeroOperacion = 10; break; // El operario ha registrado la entrada de productos en el almacen
		default: numeroOperacion = 0; break;
		}
		
		return numeroOperacion;
	}
}