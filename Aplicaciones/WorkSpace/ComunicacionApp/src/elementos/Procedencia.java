package elementos;

import java.io.Serializable;
import java.util.List;

public class Procedencia implements Serializable {

	private static final long serialVersionUID = -8495386230097032575L;

	String nombre; // Nombre de la empresa.
	String abreviatura; // Abreviatura de la empresa.
	List<Tipo> listaProductos; // Lista de productos que nos proporciona dicha empresa (Utilizado para comporbaciones).
	
}