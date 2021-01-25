package elementos;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import exceptions.PedidoException;

public class Producto implements Serializable {

	private static final long serialVersionUID = 2299343732612703163L;

	Tipo tipo; // Que clase de producto es.
	Date fecha; // Fecha en la que entro el ultimo producto de este tipo.
	Integer cantidad; // Numero de unidades.
	Procedencia procedencia; // Enpresa que proporciono el producto.
	
	public Producto(Tipo tipo, Date fecha, Integer cantidad, Procedencia procedencia) {
		this.tipo = tipo;
		this.fecha = fecha;
		this.cantidad = cantidad;
		this.procedencia = procedencia;
	}
	
	public Tipo getTipo() {
		return tipo;
	}

	public Date getFecha() {
		return fecha;
	}

	public int getCantidad() {
		return cantidad;
	}

	public Procedencia getProcedencia() {
		return procedencia;
	}
	
	// Añadimos elementos y actualizamos la fecha de entrada.
	public void addElements(int cantidad) {
		this.cantidad += cantidad;
		this.fecha = Calendar.getInstance().getTime();
	}

	// Reducimos la cantidad de elementos disponibles. Si no hay suficientes elementos disponibles lanza una excepción.
	public void removeElements(int cantidad) throws PedidoException {
		if (cantidad > this.cantidad) throw new PedidoException("No hay suficiente stock.");
		this.cantidad -= cantidad;
	}
	
	// Obtener los parametros para mostrar en la tabla.
	public Object getFieldAt(int column) {
		switch(column) {
		case 0: return new String(tipo.getNombre());
		case 1: return cantidad;
		case 2: return new String(procedencia.getNombre() + " (" + procedencia.getAbreviatura() + ")");
		case 3: return fecha;
		default: return null;
		}
	}
	
	// Conocer los nombres de los tipos para facilitar su ordenación
	public String getNombreTipo() {
		return tipo.getNombre().toLowerCase();
	}
	
	// Conocer las procedencias para facilitar la ordenación
	public String getNombreProcedencia() {
		return (procedencia.getNombre() + " " + procedencia.getAbreviatura()).toLowerCase();
	}
	
	public String transformToString() {
		String output = "";
		
		output += tipo.transformToString() + "$";
		output += fecha.toString() + "$";
		output += cantidad + "$";
		output += procedencia.transformToString();
		
		return output;
	}

	@Override
	public String toString() {
		return tipo + " " + procedencia;
	}

	@Override
	public boolean equals(Object obj) {
		Producto p = (Producto) obj; // Si el tipo y la procedencia es el mismo significa que el producto ees el mismo.
		return (this.tipo.equals(p.getTipo()) && this.procedencia.equals(p.getProcedencia())) ? true : false;
	}
}