package gestionFicheros;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import elementos.Procedencia;
import elementos.Producto;
import elementos.Tipo;

public class CrearFichero {

	public static final String FICHERO_TIPOS = "Files/Tipos.txt";
	public static final String FICHERO_PROCEDENCIAS = "Files/Procedencias.txt";
	
	Random generador;
	List<Tipo> listaTipos;
	List<Procedencia> listaProcedencias;
	
	public CrearFichero() {
		generador = new Random();
		listaTipos = leerTipos();
		listaProcedencias = leerProcedencias();
	}

	public static void main(String[] args) {
		List<Producto> productos = new ArrayList<>();
		int index = 0;
		
		CrearFichero app = new CrearFichero();
		
		for (int i = 0; i < 100; i++) {
			Producto p = app.generarProductoRandom();
			
			if ((index = app.comprobarProducto(productos, p)) != -1) {
				productos.get(index).addElements(p.getCantidad());
			}
			else {
				productos.add(p);
			}
		}
		
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("Files/Productos.dat"))) {
			out.writeObject(productos);
			out.close();
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	private int comprobarProducto(List<Producto> productos, Producto producto) {
		int cont = 0;
		
		for (Producto p : productos) {
			if (p.getTipo().equals(producto.getTipo()) && p.getProcedencia().equals(producto.getProcedencia()))
				return cont;
			cont++;
		}
		
		return -1;
	}

	public Producto generarProductoRandom() {
		return new Producto(listaTipos.get(generador.nextInt(listaTipos.size())), Calendar.getInstance().getTime(),
							generador.nextInt(1000), listaProcedencias.get(generador.nextInt(listaProcedencias.size())));
	}
	
	public List<Tipo> leerTipos() { // Leer via texto los distintos tipos disponibles.
		try {
			BufferedReader in = new BufferedReader(new FileReader(FICHERO_TIPOS));
			listaTipos = new ArrayList<>();
			
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

	public List<Procedencia> leerProcedencias() { // Leer via texto las distintas empresas que proporcionan productos
		try {
			BufferedReader in = new BufferedReader(new FileReader(FICHERO_PROCEDENCIAS));
			listaProcedencias = new ArrayList<>();
			
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
}