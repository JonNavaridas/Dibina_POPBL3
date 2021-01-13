package elementos;

import java.util.ArrayList;
import java.util.List;

public class User {

	String id;
	String name;
	String fullName;
	Integer password;
	Permisos permisos;
	List<Pedido> listaPedidos;
	
	public User(String id, String name, String fullName, Integer password, Permisos permisos) {
		this.id = id;
		this.name = name;
		this.fullName = fullName;
		this.password = password;
		this.permisos = permisos;
		this.listaPedidos = new ArrayList<>();
	}
}