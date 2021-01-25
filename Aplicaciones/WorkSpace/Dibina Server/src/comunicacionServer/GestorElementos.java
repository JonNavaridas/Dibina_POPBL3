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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import elementos.Estado;
import elementos.Pedido;
import elementos.Producto;
import elementos.User;

public class GestorElementos extends Thread {

	private static final String FICHERO_LOG = "../Files/log.txt";
	private static final String FICHERO_USUARIOS = "../Files/users.csv";
	private static final String FICHERO_PEDIDOS = "../Files/pedidos.dat";
	private static final String FICHERO_PRODUCTOS = "../Files/Productos.dat";
	public static final String FICHERO_CREAR_USUARIOS = "../Files/CreateUsers.csv";

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
		if (!listaDeEspera.isEmpty()) {
			ElementoEnCola elemento = listaDeEspera.remove();
			
			switch(elemento.getOperacion()) {
			case 1: addPedido(elemento.transformToPedido());
				break;
			case 2: removePedido(elemento.transformToPedidos());
				break;
			case 3: añadirUsuario(elemento.transformToUser());
				break;
			case 4: cambiarContraseña(elemento.transformToUser(), Integer.parseInt(elemento.getElemento().split("[_]")[1]));
				break;
			case 5: cambiarEstadoPedido(elemento.transformToPedidos(), Estado.ACEPTADO);
				break;
			case 6: cambiarEstadoPedido(elemento.transformToPedidos(), Estado.DENEGADO);
				break;
			case 7: cambiarEstadoPedido(elemento.transformToPedidos(), Estado.RECOGIDO);
				break;
			case 8: cambiarEstadoPedido(elemento.transformToPedidos(), Estado.ACEPTADO);
				break;
			case 9: informarSalidaPedido(elemento.transformToPedido());
				break;
			case 10: añadirStock(elemento.transformToProducto());
				break;
			default:
				System.out.println("Operacion no disponible"); 
				return;
			}
			if (listaDeEspera.size() == 0) running = false;
			writeLog(elemento);
		}
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
			
		} catch (FileNotFoundException | EOFException e){
			if (listaPedidos == null) listaPedidos = new ArrayList<>();
			
			ObjectOutputStream out;
			try {
				out = new ObjectOutputStream(new FileOutputStream(FICHERO_PEDIDOS));
				listaPedidos.add(p);
				
				out.writeObject(listaPedidos);
				out.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void removePedido(List<Long> listID) {
		List<Pedido> listaEliminar = getPedidosByID(listID);
		List<Pedido> listaPedidos = null;
		
		try(ObjectInputStream in  = new ObjectInputStream(new FileInputStream(FICHERO_PEDIDOS))) {
			listaPedidos = (List<Pedido>) in.readObject();
			if (listaPedidos != null) {
				ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FICHERO_PEDIDOS));
				for (Pedido p : listaEliminar) {
					listaPedidos.remove(p);
				}
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
	
	@SuppressWarnings("unchecked")
	public void cambiarEstadoPedido(List<Long> listaID, Estado estado) {
		List<Pedido> listaPedidos = null;
		List<Pedido> listaPedidosCambiar = new ArrayList<>();
		
		try(ObjectInputStream in  = new ObjectInputStream(new FileInputStream(FICHERO_PEDIDOS))) {
			listaPedidos = (List<Pedido>) in.readObject();
			if (listaPedidos != null) {
				ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FICHERO_PEDIDOS));
				for (Pedido p : listaPedidos) {
					if (listaID.contains(p.getID())) {
						p.setEstado(estado);
						listaPedidosCambiar.add(p);
					}
				}
				
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
		} finally {
			if (estado == Estado.ACEPTADO && listaPedidosCambiar.size() > 0) {
				reducirStock(listaPedidosCambiar);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private List<Pedido> getPedidosByID(List<Long> listID) {
		List<Pedido> listaPedidos = null;
		
		try(ObjectInputStream in  = new ObjectInputStream(new FileInputStream(FICHERO_PEDIDOS))) {
			listaPedidos = (List<Pedido>) in.readObject();
			in.close();
			return listaPedidos;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (EOFException e){
			
		}catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return listaPedidos;
	}
	
	@SuppressWarnings("unchecked")
	private void reducirStock(List<Pedido> listaPedidos) {
		List<Producto> listaReducir = new ArrayList<>();
		List<Producto> listaProductos = null;
		
		// Mirar cada pedido que productos contiene y meterlos en una lista
		for (Pedido p : listaPedidos) {
			List<Producto> list = p.getProducto();
			for (int i = 0; i < list.size(); i++) { // Si contiene suma
				if (listaReducir.contains(list.get(i))) {
					int index = listaReducir.indexOf(list.get(i));
					listaReducir.get(index).addElements(list.get(i).getCantidad());
				}
				else { // Si no contiene resta
					listaReducir.add(list.get(i));
				}
			}
		}
		
		// Leer todos los productos
		try(ObjectInputStream in  = new ObjectInputStream(new FileInputStream(FICHERO_PRODUCTOS))) {
			listaProductos = (List<Producto>) in.readObject();
			Iterator<Producto> it = listaProductos.iterator();
			
			while(it.hasNext()) {
				Producto p = it.next();
				if (listaReducir.contains(p)) {
					// Si la cantidad es la misma borramos todo
					if (p.getCantidad() == listaReducir.get(listaReducir.indexOf(p)).getCantidad()) {
						it.remove();
					}
					// Si es distinta reducimos
					else if (p.getCantidad() > listaReducir.get(listaReducir.indexOf(p)).getCantidad()) {
						listaProductos.get(listaProductos.indexOf(p)).addElements(-listaReducir.get(listaReducir.indexOf(p)).getCantidad());
					}
				}
			}
			in.close();
			
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FICHERO_PRODUCTOS));
			out.writeObject(listaProductos);
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
	
	public void añadirUsuario(User user) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(FICHERO_USUARIOS, true));
		
		String output = user.getID() + "," + user.getName() + "," + user.getFullName() + "," +
						user.getContraseña() + "," + user.getPermisos().getInicial();
		out.write(output + "\n");
		out.close();
		
		File file = new File(FICHERO_CREAR_USUARIOS);
		boolean ficheroVacio = !file.exists();
		out = new BufferedWriter(new FileWriter(file, true));
		
		if (ficheroVacio) out.write("GivenName,Surname,Name,DisplayName,SamAccountName\n");
		out.write(user.createUserString() + "\n");
		out.close();
	}
	
	private void cambiarContraseña(User user, int newPassword) throws IOException { // Cambia la contraseña del usuario asignado.
        String oldLine = user.getID() + "," + user.getName() + "," + user.getContraseña();
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
	
	private void informarSalidaPedido(Pedido pedido) {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(FICHERO_LOG, true));
			String output = "";
			
			for (Producto p : pedido.getProducto()) {				
				output += "\t[";
				output += "Tipo=" + p.getTipo() + ",";
				output += "Fecha=" + p.getTime() + ",";
				output += "Cantidad=" + p.getCantidad() + ",";
				output += "Procedencia=" + p.getProcedencia();
				output += "]\n";
			}
			
			out.write("Pedido retirado: " + pedido.getID() + output);
			out.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void añadirStock(Producto producto) {
		try(ObjectInputStream in  = new ObjectInputStream(new FileInputStream(FICHERO_PRODUCTOS))) {
			List<Producto> listaProductos = (List<Producto>) in.readObject();
			in.close();
			
			if (listaProductos.contains(producto)) {
				listaProductos.get(listaProductos.indexOf(producto)).addElements(producto.getCantidad());
			}
			else {
				listaProductos.add(producto);
			}
			
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FICHERO_PRODUCTOS));
			out.writeObject(listaProductos);
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
	
	private void writeLog(ElementoEnCola elemento) throws ParseException {
		try {
			Thread.sleep(1000);
			BufferedWriter out = new BufferedWriter(new FileWriter(FICHERO_LOG, true));
			try {
				switch(elemento.getOperacion()) {
				case 1:
					System.out.println(elemento.transformarOperacion() + ": Pedido " + elemento.getElemento().split("[#]")[0]);
					out.write(elemento.transformarOperacion() + ": Pedido " + elemento.getElemento().split("[#]")[0] 
							+ " --> " + elemento.getElemento().split("[#]")[3] + "\n" + sacarProductos(elemento.getElemento().split("[#]")[5]));
					break;
				case 3:
				case 4:
					System.out.println(elemento.transformarOperacion() + ": User " + elemento.getElemento().split("[$]")[0]
							+ " " + elemento.getElemento().split("[$]")[2]);
					out.write(elemento.transformarOperacion() + ": User " + elemento.getElemento().split("[$]")[0]
							+ " " + elemento.getElemento().split("[$]")[2] + "\n");
					break;
				case 2:
				case 5:
				case 6:
				case 7:
				case 8:
					String output = "";
					List<Long> valores = elemento.transformToPedidos();
					for(Long l : valores) output += "\n\t->Pedido " + String.valueOf(l);
					
					System.out.println(elemento.transformarOperacion() + ":" + output);
					out.write(elemento.transformarOperacion() + ":" + output + "\n");
					break;
				case 9: break;
				case 10:
					System.out.println("Producto añadido: Unidades añadidas" + elemento.transformToProducto().getCantidad());
					out.write("Producto añadido: Unidades añadidas" + elemento.transformToProducto().getCantidad());
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
		} catch (InterruptedException e1) {	
			e1.printStackTrace();
		}
	}
	
	public String sacarProductos(String productoString) {
		String[] productos = productoString.split("[&]");
		String output = "";
		String[] valores;
		
		for (int i = 0; i < productos.length; i++) {
			valores = productos[i].split("[$]");
			
			output += "\t[";
			output += "Tipo=" + valores[0] + ",";
			output += "Fecha=" + valores[1] + ",";
			output += "Cantidad=" + valores[2] + ",";
			output += "Procedencia=" + valores[3];
			output += "]\n";
		}
		
		return output;
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
