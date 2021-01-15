package comunicacionServer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import elementos.Pedido;
import elementos.User;

public class GestorElementos extends Thread {

	private static final String FICHERO_LOG = "Files/log.txt";
	private static final String FICHERO_USUARIOS = "Files/users.csv";
	private static final String FICHERO_PEDIDOS = "Files/pedidos.dat";
	private static final String FICHERO_PRODUCTOS = "Files/productos.dat";
	public static final String FICHERO_CREAR_USUARIOS = "Files/CreateUsers.csv";

	Queue<ElementoEnCola> listaDeEspera;
	boolean running;
	
	public GestorElementos() {
		listaDeEspera = new LinkedList<>();
		running = false;
	}
	
	public void addElementoACola(int operacion, String line) {
		listaDeEspera.add(new ElementoEnCola(operacion, line));
		if (!running) running = true;
	}
	
	public boolean getRunning() {
		return running;
	}
	
	public int elementosEnLista() {
		return listaDeEspera.size();
	}
	
	private void realizarAccion() throws ParseException, IOException {
		ElementoEnCola elemento = listaDeEspera.remove();
		
		switch(elemento.getOperacion()) {
		case 1: addPedido(elemento.transformToPedido());
			break;
		case 2: removePedido(elemento.transformToPedido());
			break;
		case 3: a�adirUsuario(elemento.transformToUser());
			break;
		case 4: cambiarContrase�a(elemento.transformToUser(), Integer.parseInt(elemento.getElemento().split("[_]")[1]));
			break;
		default:
			System.out.println("Operacion no disponible"); 
			return;
		}
		if (listaDeEspera.size() == 0) running = false;
		writeLog(elemento);
	}

	@Override
	public void run() {
		do {
			try {
				realizarAccion();
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
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
	
	@SuppressWarnings("unchecked")
	private void removePedido(Pedido p) {
		List<Pedido> listaPedidos = null;
		
		try(ObjectInputStream in  = new ObjectInputStream(new FileInputStream(FICHERO_PEDIDOS))) {
			listaPedidos = (List<Pedido>) in.readObject();
			if (listaPedidos != null) {
				ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FICHERO_PEDIDOS));
				listaPedidos.remove(p);
				
				out.writeObject(listaPedidos);
				out.close();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (EOFException e){
			
		}catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void a�adirUsuario(User user) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(FICHERO_USUARIOS, true));
		
		String output = user.getID() + "," + user.getName() + "," + user.getFullName() + "," +
						user.getContrase�a() + "," + user.getPermisos().getInicial();
		out.write(output + "\n");
		out.close();
		
		File file = new File(FICHERO_CREAR_USUARIOS);
		boolean ficheroVacio = !file.exists();
		out = new BufferedWriter(new FileWriter(file, true));
		
		if (ficheroVacio) out.write("GivenName,Surname,Name,DisplayName,SamAccountName\n");
		out.write(user.createUserString() + "\n");
		out.close();
	}
	
	private void cambiarContrase�a(User user, int newPassword) throws IOException { // Cambia la contrase�a del usuario asignado.
        String oldLine = user.getID() + "," + user.getName() + "," + user.getContrase�a();
        String newLine = user.getID() + "," + user.getName() + "," + newPassword;
        
        List<String> usuarios = new ArrayList<>();
        BufferedReader in = new BufferedReader(new FileReader(FICHERO_USUARIOS));
        
		String linea;
		while ((linea = in.readLine()) != null) usuarios.add(linea);
		in.close();
		
		usuarios.remove(oldLine);
		usuarios.add(newLine);
		
		usuarios = usuarios.stream().sorted().collect(Collectors.toList());
		
		BufferedWriter out = new BufferedWriter(new FileWriter(FICHERO_USUARIOS));
		usuarios.stream().forEach((a)->{
			try {
				out.write(a + "\n");
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		out.close();
	}
	
	private void writeLog(ElementoEnCola elemento) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(FICHERO_LOG, true));
			try {
				switch(elemento.getOperacion()) {
				case 1:
				case 2:
					System.out.println(elemento.transformarOperacion() + ": Pedido " + elemento.getElemento().split("#")[0]);
					out.write(elemento.transformarOperacion() + ": Pedido " + elemento.getElemento().split("#")[0] + "\n");
					break;
				case 3:
				case 4:
					System.out.println(elemento.transformarOperacion() + ": User " + elemento.getElemento().split("[$]")[0]
							+ " " + elemento.getElemento().split("[$]")[2]);
					out.write(elemento.transformarOperacion() + ": User " + elemento.getElemento().split("[$]")[0]
							+ " " + elemento.getElemento().split("[$]")[2] + "\n");
					break;
				default:
					System.out.println("Error");
					break;
				}
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
			out.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeLog(String mensaje) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(FICHERO_LOG, true));
			try {
				out.write(mensaje);
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
			out.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}