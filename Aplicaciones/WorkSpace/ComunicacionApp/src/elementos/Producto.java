package elementos;

import java.io.Serializable;
import java.util.Date;

public class Producto implements Serializable {

	private static final long serialVersionUID = 2299343732612703163L;

	Tipo tipo; // Que clase de producto es.
	Date fecha; // Fecha en la que entro el ultimo producto de este tipo.
	Integer cantidad; // Numero de unidades.
	Procedencia procedencia; // Enpresa que proporciono el producto.
	
	public Producto(Tipo tipo, Date fecha, int cantidad, Procedencia procedencia) {
		this.tipo = tipo;
		this.fecha = fecha;
		this.cantidad = cantidad;
		this.procedencia = procedencia;
	}
	
	public int getCantidad() {
		return cantidad;
	}
	
	public void addElements(int cantidad) {
		this.cantidad += cantidad;
	}

	@Override
	public boolean equals(Object obj) {
		Producto p = (Producto)obj;
		if (p.tipo == tipo && p.procedencia == procedencia) return true;
		return false;
	}
	
}