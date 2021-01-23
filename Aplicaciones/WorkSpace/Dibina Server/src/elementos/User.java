package elementos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class User implements Serializable {

	private static final long serialVersionUID = -6662751635722310099L;

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
	
	public String getName() {
		return name;
	}
	
	public String getFullName() {
		return fullName;
	}
	
	public String getID() {
		return id;
	}
	
	public Permisos getPermisos() {
		return permisos;
	}
	
	public int getContraseña() {
		return password;
	}
	
	public String createUserString() {
		String[] values = this.fullName.split(" ");
		String output = "";
		
		output += values[0] + ","; // Nombre
		output += values[1] + ","; // Apellido
		output += this.fullName + ","; // Nombre completo
		output += this.fullName + ","; // Nombre de visualización
		output += this.name + ","; // Nombre de la cuenta
		
		return output;
	}
}