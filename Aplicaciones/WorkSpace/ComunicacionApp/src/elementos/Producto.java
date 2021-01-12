package elementos;

import java.io.Serializable;
import java.util.Date;

public class Producto implements Serializable {

	private static final long serialVersionUID = 2299343732612703163L;

	Tipo tipo; // Que clase de producto es.
	Date fecha; // Fecha en la que entro el ultimo producto de este tipo.
	int cantidad; // Numero de unidades.
	Procedencia procedencia; // Enpresa que proporciono el producto.
	
}