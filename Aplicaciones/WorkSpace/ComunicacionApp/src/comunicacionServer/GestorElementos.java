package comunicacionServer;

import java.util.ArrayList;
import java.util.List;

import elementos.Pedido;
import elementos.Producto;
import elementos.User;

public class GestorElementos {

	List<User> listaUsuarios;
	List<Pedido> listaPedidos;
	List<Producto> listaProductos;
	
	public GestorElementos() {
		listaUsuarios = new ArrayList<>();
		listaPedidos = new ArrayList<>();
		listaProductos = new ArrayList<>();
	}
	
	public void addUsuario(User u) {
		listaUsuarios.add(u);
	}
	
	public void addPedido(Pedido p) {
		listaPedidos.add(p);
	}
	
	public void addProducto(Producto p) {
		listaProductos.add(p);
	}
}
