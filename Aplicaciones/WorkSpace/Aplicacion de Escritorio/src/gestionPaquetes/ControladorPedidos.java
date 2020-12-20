package gestionPaquetes;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import elementos.Procedencia;
import elementos.Producto;
import elementos.Tipo;

public class ControladorPedidos {

	List<Procedencia> listaProcedencias;
	List<Producto> listaProductos;
	List<Tipo> listaTipos;
	
	Set<Integer> setID;
	Random generador;
	
	public ControladorPedidos(List<Procedencia> listaProcedencias, List<Tipo> listaTipos) {
		this.listaProcedencias = listaProcedencias;
		this.listaTipos = listaTipos;
		
		generador = new Random();
		setID = new HashSet<>();
	}

	public List<Tipo> getListaTipos() {
		return listaTipos;
	}
	
	public List<Procedencia> getListaProcedencias() {
		return listaProcedencias;
	}

	public String[] getTipoArray() {
		String[] tipoArray = new String[listaTipos.size()];
		
		for (int i = 0; i < listaTipos.size(); i++) {
			tipoArray[i] = listaTipos.get(i).toString();
		}
		
		return tipoArray;
	}

	public void crearPaquete(String procedencia, String cantidad, String tipo) throws PaqueteException {
		try {
			if (Integer.parseInt(cantidad) >= 1000) 
				throw new PaqueteException("No esta permitida esa cantidad");
			//Producto paquete = new Producto();
			//listaProductos.add(paquete);
		}
		catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
	}
	
	private Tipo getTipo(String nombre) {
		for (Tipo t : listaTipos) {
			if (t.getNombre().equals(nombre)) return t;
		}
		return null;
	}
	
	private Procedencia getProcedencia(String nombre) {
		for (Procedencia t : listaProcedencias) {
			if (t.getNombre().equals(nombre)) return t;
		}
		return null;
	}

	
}