package usuarios;

import java.util.ArrayList;
import java.util.List;

import elementos.Pedido;

public class User {

	String id;
	String name;
	Integer password;
	List<Pedido> listaPedidos;
	
	public User(String id, String name, int password) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.listaPedidos = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}
	
	public String getID() {
		return id;
	}
	
	public int getContraseña() {
		return password;
	}
	
	public void addPedido(Pedido p) {
		listaPedidos.add(p);
	}
	
	public void removePedido(Pedido p) {
		listaPedidos.remove(p);
	}
	
	public List<Pedido> getListaPedidos() {
		return this.listaPedidos;
	}

	@Override
	public String toString() {
		return name + " (" + id + ")";
	}
}