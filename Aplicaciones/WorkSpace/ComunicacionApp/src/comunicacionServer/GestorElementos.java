package comunicacionServer;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import elementos.Pedido;

public class GestorElementos extends Thread {

	private static final String FICHERO_USUARIOS = "Files/users.csv";
	private static final String FICHERO_PEDIDOS = "Files/pedidos.dat";
	private static final String FICHERO_PRODUCTOS = "Files/productos.dat";

	Queue<ElementoEnCola> listaDeEspera;
	private boolean running;
	
	public GestorElementos() {
		listaDeEspera = new LinkedList<>();
		running = false;
	}
	
	public void addElementoACola(int operacion, String line) {
		listaDeEspera.add(new ElementoEnCola(operacion, line));
		if (!running) {
			running = true;
			this.start();
		}
	}
	
	private void realizarAccion() throws ParseException {
		ElementoEnCola elemento = listaDeEspera.remove();
		
		switch(elemento.getOperacion()) {
		case 1: addPedido(elemento.transformToPedido());
			break;
		case 2:
			break;
		case 3:
			break;
		default:
			System.out.println("Operacion no disponible"); 
			return;
		}
		if (listaDeEspera.size() == 0) running = false;
	}

	@Override
	public void run() {
		do {
			try {
				realizarAccion();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} while (running);
	}
	
	@SuppressWarnings("unchecked")
	private void addPedido(Pedido p) {
		List<Pedido> listaPedidos = null;
		
		try(ObjectInputStream in  = new ObjectInputStream(new FileInputStream(FICHERO_PEDIDOS))) {
			listaPedidos = (List<Pedido>) in.readObject();
			if (listaPedidos == null) listaPedidos = new ArrayList<>();
			
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FICHERO_PEDIDOS));
			listaPedidos.add(p);
			
			out.writeObject(listaPedidos);
			out.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (EOFException e){
			
		}catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
