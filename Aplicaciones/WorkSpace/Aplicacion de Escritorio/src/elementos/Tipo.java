package elementos;

import java.io.Serializable;

public class Tipo implements Serializable {

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
}