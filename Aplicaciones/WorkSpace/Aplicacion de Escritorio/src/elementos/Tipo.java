package elementos;

public class Tipo {

	Integer ID;
	String nombre;
	
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
		//return nombre.substring(0, 1).toUpperCase() + nombre.substring(1).toLowerCase();
		return nombre;
	}
}