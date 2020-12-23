package elementos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Procedencia implements Serializable {

	private static final long serialVersionUID = -8495386230097032575L;

	String nombre;
	String abreviatura;
	List<Tipo> listaProductos;
	
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
}