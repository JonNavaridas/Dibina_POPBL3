package gestionUsuarios;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import elementos.Permisos;
import elementos.User;

public class Identificador {

	private static final String FICHERO_USUARIOS = "../Files/Users.csv";
	
	List<User> listaUsuarios;
	User usuario;

	public Identificador() {
		getUsers();
	}
	
	public void getUsers() {
		listaUsuarios = new ArrayList<>();
		readUsuarios();
	}
	
	public User getUser(String id) {
		listaUsuarios.stream().filter((u)->u.getID().equals(id)).findAny().ifPresent((u)->usuario = u);
		if (usuario == null) System.out.println("No encontrado");
		return usuario;
	}
	
	public User getUser(String nombre, Integer contraseña) {
		listaUsuarios.stream().filter((u)->u.getName().equals(nombre) && u.getContraseña() == contraseña).findAny().ifPresent((u)->usuario = u);
		if (usuario == null) System.out.println("No encontrado");
		return usuario;
	}
	
	private void readUsuarios() {
		try {
			BufferedReader in = new BufferedReader(new FileReader(FICHERO_USUARIOS));
			String linea;
			while ((linea = in.readLine()) != null) {
				String[] valores = linea.split("[,]");
				listaUsuarios.add(new User(valores[0], valores[1], valores[2], Integer.parseInt(valores[3]), Permisos.getPermiso(valores[4])));
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}