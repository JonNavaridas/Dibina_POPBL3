package usuarios;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Identificador {

	Scanner teclado;
	List<User> listaUsuarios;
	User usuario;

	public Identificador() {
		getUsers();
	}
	
	public void getUsers() {
		teclado = new Scanner(System.in);
		listaUsuarios = new ArrayList<>();
		
		readUsuarios();
	}
	
	public User getUser(String id) {
		listaUsuarios.stream().filter((u)->u.getID().equals(id)).findAny().ifPresent((u)->usuario = u);
		System.out.println((usuario != null)? usuario: "No encontrado");
		return usuario;
	}
	
	public User getUser(String nombre, Integer contraseña) {
		listaUsuarios.stream().filter((u)->u.getName().equals(nombre) && u.getContraseña() == contraseña).findAny().ifPresent((u)->usuario = u);
		System.out.println((usuario != null)? usuario: "No encontrado");
		return usuario;
	}
	
	private void saveUsuarios() {
		
	}
	
	private void readUsuarios() {
		try {
			int i = 0;
			BufferedReader inSinHash = new BufferedReader(new FileReader("Files/UserPassword.txt"));
			BufferedReader in = new BufferedReader(new FileReader("Files/UserName.txt"));
			FileWriter destino= new FileWriter ("Files/Users.txt",false);
			
			String linea;
			linea = inSinHash.readLine();
			String[] contraseñas = linea.split("[$]");		//contraseñas originales
			
			while ((linea = in.readLine()) != null) {
				String[] valores = linea.split("[$]");		//leer id, nombre

				destino.write(valores[0]+"$"+valores[1]+"$"+contraseñas[i].hashCode());
				destino.write('\n');
				i++;
			}
			in.close();
			inSinHash.close();
			destino.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		try {
			BufferedReader in = new BufferedReader(new FileReader("Files/Users.txt"));
			String linea;
			while ((linea = in.readLine()) != null) {
				String[] valores = linea.split("[$]");
				listaUsuarios.add(new User(valores[0], valores[1], Integer.parseInt(valores[2])));
			}
			in.close();
		} catch (Exception e) {e.printStackTrace();}
	}
}