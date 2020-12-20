package gestionFicheros;

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
import elementos.Producto;
import elementos.Tipo;

public class LectorElementos {

	public static final String FICHERO_TIPOS = "Files/Tipos.txt";
	public static final String FICHERO_PEDIDOS = "Files/Pedidos.dat";
	public static final String FICHERO_PRODUCTOS = "Files/Productos.dat";
	public static final String FICHERO_PROCEDENCIAS = "Files/Procedencias.txt";
	
	List<Tipo> listaTipos;
	List<Pedido> listaPedidos;
	List<Producto> listaProductos;
	List<Procedencia> listaProcedencias;
	
	public LectorElementos() {
		listaTipos = new ArrayList<>();
		listaPedidos = new ArrayList<>();
		listaProcedencias = new ArrayList<>();
	}
	
	public List<Tipo> leerTipos() {
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

	public List<Procedencia> leerProcedencias() {
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

	@SuppressWarnings("unchecked")
	public List<Pedido> leerPedidos() {
		try(ObjectInputStream in  = new ObjectInputStream(new FileInputStream(FICHERO_PEDIDOS))) {
			do {
				listaPedidos = (List<Pedido>) in.readObject();
			} while(listaPedidos != null);
			
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
	
	public void escribirPedidos(List<Pedido> pedidos) {
		
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FICHERO_PEDIDOS))) {
			out.writeObject(pedidos);
			out.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Producto> leerProductos() {
		try(ObjectInputStream in  = new ObjectInputStream(new FileInputStream(FICHERO_PRODUCTOS))) {
			do {
				listaProductos = (List<Producto>) in.readObject();
			} while(listaPedidos != null);
			
		} catch (FileNotFoundException e) {
			return null;
		}catch (EOFException e){
			return listaProductos;
		}catch (IOException e) {
			return null;
		} catch (ClassNotFoundException e) {
			return null;
		}
		return listaProductos;
	}
	
	public void escribirProductos(List<Producto> productos) {
		
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FICHERO_PRODUCTOS))) {
			out.writeObject(productos);
			out.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}