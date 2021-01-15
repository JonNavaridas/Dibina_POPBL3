package pruebasSinConexion;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import elementos.Estado;
import elementos.Pedido;
import elementos.Permisos;
import elementos.Procedencia;
import elementos.Producto;
import elementos.Tipo;
import elementos.User;

public class Main {
	List<Pedido> lista;
	Pedido pedidoExiste;
	Pedido pedidoNoExiste;
	
	public Main() {
		inicializar();
		crearPedidosParaBuscar();
		start();
		Pedido pedido = encontrarPedido(pedidoExiste);
		System.out.println((pedido==null)? "ERROR":"OK\n"+pedido);
		pedido = encontrarPedido(pedidoNoExiste);
		System.out.println((pedido==null)? "ERROR":"OK\n"+pedido);
	}
	
	private void crearPedidosParaBuscar() {
		pedidoExiste = lista.get(0);
		pedidoExiste.setEstado(null);
		pedidoExiste.setUser(null);
		pedidoExiste.setFecha(null);
		
		pedidoNoExiste = crearPedido(0);
		pedidoNoExiste.setEstado(null);
		pedidoNoExiste.setUser(null);
		pedidoNoExiste.setFecha(null);
	}

	private void inicializar() {
		lista = new ArrayList<>();
		
		for(int i  = 0; i < 4; i++) {
			Pedido pedido = null;
			pedido = crearPedido(i);
			lista.add(pedido);
		}
		Lector.escribirPedidos(lista);
	}

	private Pedido crearPedido(int i) {
		String[] destinos = Lector.leerDestinos();
		Procedencia[] procedencias = Lector.leerProcedencias().toArray(new Procedencia[0]);
		Tipo[] tipos = Lector.leerTipos().toArray(new Tipo[0]);
		Pedido pedido = new Pedido();
		List<User> users = crearUsers();
		
		pedido.setDestino(destinos[(int)(Math.random()*destinos.length)]);
		pedido.setEstado(Estado.ACEPTADO);
		pedido.setFecha(Calendar.getInstance().getTime());
		pedido.setId((long) 11111);
		pedido.setUser(users.get(i));
		for(int j = 0; j < 3; j++) {
			pedido.addProducto(new Producto(
					tipos[(int)(Math.random()*tipos.length)], 
					Calendar.getInstance().getTime(), 
					(int)(Math.random()*100), 
					procedencias[(int)(Math.random()*procedencias.length)]));
		}
		return pedido;
	}

	private List<User> crearUsers() {
		List<User> users = new ArrayList<>();
		users.add(new User("11111", "1", "11", 1, Permisos.TOTAL));
		users.add(new User("22222", "2", "22", 2, Permisos.TOTAL));
		users.add(new User("33333", "3", "33", 3, Permisos.TOTAL));
		users.add(new User("44444", "4", "44", 4, Permisos.TOTAL));		
		return users;
	}

	private void start(){
		lista = null;
		boolean opened = false;
		int i=0;
		
		while(!opened) {
			if(i==8)lista = Lector.leerPedidos("Files/Pedidos.dat"); //PARA COMPROBAR QUE FUNCIONA EL WHILE
			else lista = Lector.leerPedidos("Files/Pedidos2.dat");
			
			if(lista != null) opened = true;
			else {
				System.out.println("esperando...");
				try {
					Thread.sleep(300);					
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			i++;
		}
		for(Pedido pedido: lista)System.out.println(pedido.toString());
		System.out.println(pedidoExiste);
		System.out.println(pedidoNoExiste);
	}

	private Pedido encontrarPedido(Pedido pedido) {
		Pedido tmp = lista.stream().filter(f->f.getDestino().equals(pedido.getDestino())&& 
					  f.compareProductList(pedido.getListaProductos())&&
					  f.getEstado()==Estado.ACEPTADO).findFirst().orElse(null);
		
		return tmp;
	}
	
	public static void main(String[] args) {
		Main programa = new Main();
	}

}
