package elementos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Procedencia implements Serializable {

	private static final long serialVersionUID = -8495386230097032575L;

	String nombre; // Nombre de la empresa.
	String abreviatura; // Abreviatura de la empresa.
	List<Tipo> listaProductos; // Lista de productos que nos proporciona dicha empresa (Utilizado para comporbaciones).
	
	public Procedencia(String nombre, String abreviatura) {
		this.nombre = nombre;
		this.abreviatura = abreviatura;
		listaProductos = new ArrayList<>();
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public List<Tipo> getListTipo() {
		return listaProductos;
	}

	@Override
	public String toString() {
		return abreviatura;
	}

	public String getAbreviatura() {
		return abreviatura;
	}

	public String transformToString() {
		return nombre + "/" + abreviatura;
	}
}