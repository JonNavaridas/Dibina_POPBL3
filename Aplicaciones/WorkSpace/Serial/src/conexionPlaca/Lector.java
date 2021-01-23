package conexionPlaca;


import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import elementos.Pedido;
import elementos.Procedencia;
import elementos.Tipo;


public class Lector {
	
	public static final String FICHERO_PEDIDOS = "../Files/Pedidos.dat";
	public static final String FICHERO_DESTINOS = "../Files/Destinos.txt";
	public static final String FICHERO_TIPOS = "../Files/Tipos.txt";
	public static final String FICHERO_PRODUCTOS = "../Files/Productos.dat";
	public static final String FICHERO_PROCEDENCIAS = "../Files/Procedencias.txt";

	
	public static List<Tipo> leerTipos() { // Leer via texto los distintos tipos disponibles.
		List<Tipo> listaTipos = new ArrayList<>();
		try {
			BufferedReader in = new BufferedReader(new FileReader(FICHERO_TIPOS));
			
			String linea;
			while ((linea = in.readLine()) != null) {
				String[] valores = linea.split("[&]");
				listaTipos.add(new Tipo(Integer.parseInt(valores[0]), valores[1]));
			}
			in.close();
			return listaTipos;
		} 
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static List<Procedencia> leerProcedencias() { // Leer via texto las distintas empresas que proporcionan productos
		List<Procedencia> listaProcedencias = new ArrayList<>();
		try {
			BufferedReader in = new BufferedReader(new FileReader(FICHERO_PROCEDENCIAS));
			
			String linea;
			while ((linea = in.readLine()) != null) {
				String[] valores = linea.split("[&]");
				Procedencia procedencia = new Procedencia(valores[0], valores[1]);
				
				String[] tipos = valores[2].split("[$]");
				for(String s : tipos) {
					String[] valor = s.split("[#]");
					procedencia.getListTipo().add(new Tipo(Integer.parseInt(valor[0]), valor[1]));
				}
				
				listaProcedencias.add(procedencia);
			}
			in.close();
			return listaProcedencias;
		} 
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String[] leerDestinos() { // Obtener la información sobre los diferentes lugares donde pueden recogerse los pedidos.
		List<String> destinos = new ArrayList<>();
		try {
			BufferedReader in = new BufferedReader(new FileReader(FICHERO_DESTINOS));
			
			String linea;
			while ((linea = in.readLine()) != null) {
				destinos.add(linea);
			}
			in.close();			
			return destinos.toArray(new String[0]);
		} 
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static List<Pedido> leerPedidos(String path) {
		List<Pedido> listaPedidos = new ArrayList<>();
		try(ObjectInputStream in  = new ObjectInputStream(new FileInputStream(path))) {
			do {
				listaPedidos = (List<Pedido>) in.readObject();
			} while(listaPedidos != null);
			in.close();
		} catch (FileNotFoundException e) {
			return null;
		}catch (EOFException e){
			return listaPedidos;
		}catch (IOException e) {
			return null;
		} catch (ClassNotFoundException e) {
			return null;
		}
		return listaPedidos;
	}
	
	public static void escribirPedidos(List<Pedido> pedidos) { // Guardar los pedidos sin resolver en el fichero
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FICHERO_PEDIDOS))) {
			out.writeObject(pedidos);
			out.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
