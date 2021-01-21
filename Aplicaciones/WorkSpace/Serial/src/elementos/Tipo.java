package elementos;

import java.io.Serializable;
import java.util.Comparator;

public class Tipo implements Serializable, Comparator<Tipo> {

	private static final long serialVersionUID = 5559020248449778815L;

	Integer ID; // Numero identificador del elemento
	String nombre; // Nombre
	
	public Tipo(int id, String nombre) {
		this.ID = id;
		this.nombre = nombre;
	}
	
	public String getNombre() {
		return nombre;
	}
	
	public Integer getID() {
		return ID;
	}

	@Override
	public String toString() {
		return nombre.substring(0, 1).toUpperCase() + nombre.substring(1).toLowerCase();
	}

	@Override
	public int compare(Tipo o1, Tipo o2) {
		return o1.getNombre().compareTo(o2.getNombre());
	}

	@Override
	public boolean equals(Object obj) {
		return ((Tipo)obj).getNombre().equals(this.nombre);
	}
	
	public String transformToString() {
		return ID + "/" + nombre;
	}
}