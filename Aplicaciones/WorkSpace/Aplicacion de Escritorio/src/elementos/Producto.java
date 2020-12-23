package elementos;

import java.io.Serializable;
import java.util.Date;

import gestionPaquetes.PedidoException;

public class Producto implements Serializable {

	private static final long serialVersionUID = 2299343732612703163L;

	Tipo tipo;
	Date fecha;
	int cantidad;
	Procedencia procedencia;
	
	public Producto(Tipo tipo, Date fecha, int cantidad, Procedencia procedencia) {
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
	
	public void addElements(int cantidad) {
		this.cantidad += cantidad;
	}

	public void removeElements(int cantidad) throws PedidoException {
		if (cantidad > this.cantidad) throw new PedidoException("No hay suficiente stock.");
		this.cantidad -= cantidad;
	}
	
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
	
	@SuppressWarnings("deprecation")
	public String getFechaExacta() {
		return fecha.getYear() + " " + fecha.getMonth() + " " + fecha.getDay() + " " +
			   fecha.getHours() + " " + fecha.getMinutes() + " " + fecha.getSeconds();
	}

	@Override
	public String toString() {
		return tipo + " " + procedencia;
	}

	@Override
	public boolean equals(Object obj) {
		Producto p = (Producto) obj;
		return (this.tipo.equals(p.getTipo()) && this.procedencia.equals(p.getProcedencia())) ? true : false;
	}
}