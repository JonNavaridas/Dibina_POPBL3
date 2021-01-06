package gestionFicheros;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import elementos.Pedido;
import elementos.Producto;
import usuarios.User;

public class EscritorDeElementos {

	public static final String FICHERO_USUARIOS = "Files/Users.csv";
	public static final String FICHERO_PEDIDOS = "Files/Pedidos.dat";
	public static final String FICHERO_PRODUCTOS = "Files/Productos.dat";
	public static final String FICHERO_CREAR_USUARIOS = "Files/CreateUsers.csv";
	
	public void escribirPedidos(List<Pedido> pedidos) { // Guardar los pedidos sin resolver en el fichero
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FICHERO_PEDIDOS))) {
			out.writeObject(pedidos);
			out.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void escribirProductos(List<Producto> productos) { // Guardar los productos.
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FICHERO_PRODUCTOS))) {
			out.writeObject(productos);
			out.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void cambiarContraseña(User user, int newPassword) throws IOException { // Cambia la contraseña del usuario asignado.
        String oldLine = user.getID() + "," + user.getName() + "," + user.getContraseña();
        String newLine = user.getID() + "," + user.getName() + "," + newPassword;
        
        List<String> usuarios = new ArrayList<>();
        BufferedReader in = new BufferedReader(new FileReader(FICHERO_USUARIOS));
		
        System.out.println(newLine);
        
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
}
