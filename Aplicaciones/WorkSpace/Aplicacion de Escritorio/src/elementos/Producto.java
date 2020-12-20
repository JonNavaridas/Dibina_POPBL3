package elementos;

import java.util.Date;

public class Producto {

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

	public Object getFieldAt(int column) {
		switch(column) {
		case 0: return new String(tipo.getNombre());
		case 1: return cantidad;
		case 2: return new String(procedencia.getNombre() + " (" + procedencia.getAbreviatura() + ")");
		case 3: return fecha;
		default: return null;
		}
	}
}