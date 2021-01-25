package gestionFicheros;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import elementos.Pedido;
import elementos.Procedencia;
import elementos.Producto;
import elementos.Tipo;

public class LectorElementos {

	public static final String FICHERO_TIPOS = "../Files/Tipos.txt";
	public static final String FICHERO_LOGIN_ESP = "../Files/Language/ESP_Login.txt";
	public static final String FICHERO_LOGIN_EUS = "../Files/Language/EUS_Login.txt";
	public static final String FICHERO_LOGIN_ENG = "../Files/Language/ENG_Login.txt";
	public static final String FICHERO_DIBINA_ESP = "../Files/Language/ESP_Dibina.txt";
	public static final String FICHERO_DIBINA_EUS = "../Files/Language/EUS_Dibina.txt";
	public static final String FICHERO_DIBINA_ENG = "../Files/Language/ENG_Dibina.txt";
	public static final String FICHERO_PANELES_ESP = "../Files/Language/ESP_Paneles.txt";
	public static final String FICHERO_PANELES_EUS = "../Files/Language/EUS_Paneles.txt";
	public static final String FICHERO_PANELES_ENG = "../Files/Language/ENG_Paneles.txt";
	public static final String FICHERO_TABLAS_ESP = "../Files/Language/ESP_Tablas.txt";
	public static final String FICHERO_TABLAS_EUS = "../Files/Language/EUS_Tablas.txt";
	public static final String FICHERO_TABLAS_ENG = "../Files/Language/ENG_Tablas.txt";
	public static final String FICHERO_PASSUSER_ESP = "../Files/Language/ESP_PasswordUser.txt";
	public static final String FICHERO_PASSUSER_EUS = "../Files/Language/EUS_PasswordUser.txt";
	public static final String FICHERO_PASSUSER_ENG = "../Files/Language/ENG_PasswordUser.txt";
	public static final String FICHERO_PEDIDOS = "../Files/pedidos.dat";
	public static final String FICHERO_DESTINOS = "../Files/Destinos.txt";
	public static final String FICHERO_PRODUCTOS = "../Files/Productos.dat";
	public static final String FICHERO_PROCEDENCIAS = "../Files/Procedencias.txt";
	
	public static List<Tipo> leerTipos() { // Leer via texto los distintos tipos disponibles.
		try {
			List<Tipo> listaTipos = new ArrayList<>();
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
		try {
			List<Procedencia> listaProcedencias = new ArrayList<>();
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
	public static List<Pedido> leerPedidos() { // Leer todo los pedidos sin resolver
		List<Pedido> listaPedidos = new ArrayList<>();
		
		try(ObjectInputStream in  = new ObjectInputStream(new FileInputStream(FICHERO_PEDIDOS))) {
			do {
				listaPedidos = (List<Pedido>) in.readObject();
			} while(listaPedidos != null);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}catch (EOFException e){
			return listaPedidos;
		}catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return listaPedidos;
	}

	@SuppressWarnings("unchecked")
	public static List<Producto> leerProductos() { // Leer el stock que se encuentra en el almacen
		List<Producto> listaProductos = new ArrayList<>();
		try(ObjectInputStream in  = new ObjectInputStream(new FileInputStream(FICHERO_PRODUCTOS))) {
			do {
				listaProductos = (List<Producto>) in.readObject();
				listaProductos = listaProductos.stream().sorted(Comparator.comparing(Producto::toString)).collect(Collectors.toList());
			} while(listaProductos != null);
			
		} catch (FileNotFoundException e) {
			e.getStackTrace();
			return null;
		}catch (EOFException e){
			return listaProductos;
		}catch (IOException e) {
			e.getStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
		return listaProductos;
	}

	public static String[] leerDestinos() { // Obtener la información sobre los diferentes lugares donde pueden recogerse los pedidos.
		try {
			List<String> destinos = new ArrayList<>();
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
	
	public static String[] leerLogin(int language) { // Obtener palablas usadas en el dialogo de login
		try {
			List<String> login = new ArrayList<>();
			BufferedReader in;
			switch(language) {
			case 1: in = new BufferedReader(new InputStreamReader(new FileInputStream(FICHERO_LOGIN_EUS), "utf-8")); break;
			case 2: in = new BufferedReader(new InputStreamReader(new FileInputStream(FICHERO_LOGIN_ENG), "utf-8")); break;
			case 0:
			default: in = new BufferedReader(new InputStreamReader(new FileInputStream(FICHERO_LOGIN_ESP), "utf-8")); break;
			}
			
			String linea;
			while ((linea = in.readLine()) != null) {
				login.add(linea);
			}
			in.close();
			
			return login.toArray(new String[0]);
		} 
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String[] leerDibinaMenus(int language) { // Obtener palablas usadas en el menu y pantalla inicial
		try {
			List<String> menus = new ArrayList<>();
			BufferedReader in;
			switch(language) {
			case 1: in = new BufferedReader(new InputStreamReader(new FileInputStream(FICHERO_DIBINA_EUS), "utf-8")); break;
			case 2: in = new BufferedReader(new InputStreamReader(new FileInputStream(FICHERO_DIBINA_ENG), "utf-8")); break;
			case 0:
			default: in = new BufferedReader(new InputStreamReader(new FileInputStream(FICHERO_DIBINA_ESP), "utf-8")); break;
			}
			
			String linea;
			while ((linea = in.readLine()) != null) {
				menus.add(linea);
			}
			in.close();
			
			return menus.toArray(new String[0]);
		} 
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String[] leerPaneles(int language) { // Obtener palablas usadas en los paneles que no son el inicial
		try {
			List<String> paneles = new ArrayList<>();
			BufferedReader in;
			switch(language) {
			case 1: in = new BufferedReader(new InputStreamReader(new FileInputStream(FICHERO_PANELES_EUS), "utf-8")); break;
			case 2: in = new BufferedReader(new InputStreamReader(new FileInputStream(FICHERO_PANELES_ENG), "utf-8")); break;
			case 0:
			default: in = new BufferedReader(new InputStreamReader(new FileInputStream(FICHERO_PANELES_ESP), "utf-8")); break;
			}
			
			String linea;
			while ((linea = in.readLine()) != null) {
				paneles.add(linea);
			}
			in.close();
			
			return paneles.toArray(new String[0]);
		} 
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String[] leerTablaPedido(int language) {
		try {
			List<String> listTabla = new ArrayList<>();
			BufferedReader in;
			switch(language) {
			case 1: in = new BufferedReader(new InputStreamReader(new FileInputStream(FICHERO_TABLAS_EUS), "utf-8")); break;
			case 2: in = new BufferedReader(new InputStreamReader(new FileInputStream(FICHERO_TABLAS_ENG), "utf-8")); break;
			case 0:
			default: in = new BufferedReader(new InputStreamReader(new FileInputStream(FICHERO_TABLAS_ESP), "utf-8")); break;
			}
			
			String linea;
			while ((linea = in.readLine()) != null) {
				listTabla.add(linea);
			}
			in.close();
			
			return Arrays.copyOfRange(listTabla.toArray(new String[0]), 0, 5);
		} 
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String[] leerTablaProducto(int language) {
		try {
			List<String> listTabla = new ArrayList<>();
			BufferedReader in;
			switch(language) {
			case 1: in = new BufferedReader(new InputStreamReader(new FileInputStream(FICHERO_TABLAS_EUS), "utf-8")); break;
			case 2: in = new BufferedReader(new InputStreamReader(new FileInputStream(FICHERO_TABLAS_ENG), "utf-8")); break;
			case 0:
			default: in = new BufferedReader(new InputStreamReader(new FileInputStream(FICHERO_TABLAS_ESP), "utf-8")); break;
			}
			
			String linea;
			while ((linea = in.readLine()) != null) {
				listTabla.add(linea);
			}
			in.close();
			
			return Arrays.copyOfRange(listTabla.toArray(new String[0]), 3, 7);
		} 
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String[] leerPasswordUser(int language) {
		try {
			List<String> passUser = new ArrayList<>();
			BufferedReader in;
			switch(language) {
			case 1: in = new BufferedReader(new InputStreamReader(new FileInputStream(FICHERO_PASSUSER_EUS), "utf-8")); break;
			case 2: in = new BufferedReader(new InputStreamReader(new FileInputStream(FICHERO_PASSUSER_ENG), "utf-8")); break;
			case 0:
			default: in = new BufferedReader(new InputStreamReader(new FileInputStream(FICHERO_PASSUSER_ESP), "utf-8")); break;
			}
			
			String linea;
			while ((linea = in.readLine()) != null) {
				passUser.add(linea);
			}
			in.close();
			
			return passUser.toArray(new String[0]);
		} 
		catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}