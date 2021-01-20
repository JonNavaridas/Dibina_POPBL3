package gestionPedidos;

import java.util.Calendar;
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
	
	public ControladorPedidos(List<Procedencia> listaProcedencias, List<Tipo> listaTipos, List<Producto> listaProductos) {
		this.listaProcedencias = listaProcedencias;
		this.listaProductos = listaProductos;
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
	
	public List<Producto> getListaProductos() {
		return listaProductos;
	}
	
	public Long generarID() { // Crear un identificador a partir de la fecha en la que le pedido se ha realizado
		String[] valores = Calendar.getInstance().getTime().toString().split("[ ]");
		
		if (valores.length == 6) {
			String[] hora = valores[3].split("[:]");
			return Long.parseLong(valores[5] + getMonth() + valores[2] + hora[0] + hora[1] + hora[2]);
		}
		
		return null;
	}
	
	@SuppressWarnings("deprecation")
	private String getMonth() {
		return String.valueOf(Calendar.getInstance().getTime().getMonth() + 1);
	}

	public String[] getTipoArray() { // Pasar el list de tipos a array
		return listaTipos.toArray(new String[0]);
	}
	
	// Obtener el tipo utilizando el nombre
	public Tipo getTipo(String nombre) {
		for (Tipo t : listaTipos) {
			if (t.getNombre().equals(nombre)) return t;
		}
		return null;
	}
	
	// Obtener la procedencia utilizando la abreviatura
	public Procedencia getProcedencia(String nombre) {
		for (Procedencia t : listaProcedencias) {
			if (t.getAbreviatura().equals(nombre)) return t;
		}
		return null;
	}
}