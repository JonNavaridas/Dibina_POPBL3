package elementos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gestionPaquetes.PedidoException;

public class Pedido {

	Long id; // Numero generado aleatoriamente
	Date fecha; // Fecha en la que se hizo el pedido
	String destino; // Lugar donde se va a recoger el pedido
	List<Producto> listaProductos; // Productos que se lleva el usuario en el pedido
	
	public Pedido() {
		listaProductos = new ArrayList<>();
	}
	
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getDestino() {
		return destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public List<Producto> getListaProductos() {
		return listaProductos;
	}

	public void addProducto(Producto producto) {
		boolean encontrado = false;
		
		for (Producto p : listaProductos) {
			if (p.equals(producto)) {
				p.addElements(producto.getCantidad());
				encontrado = true;
			}
		}
		if (!encontrado) listaProductos.add(producto);
	}
	
	public void removeProducto(Tipo tipo, Procedencia procedencia, int cantidad) throws PedidoException {
		Producto producto = new Producto(tipo, null, cantidad, procedencia);
		
		for (Producto p : listaProductos) {
			if (p.equals(producto)) {
				p.removeElements(producto.getCantidad());
				return;
			}
		}
		throw new PedidoException("No se ha podido encontrar ese producto.");
	}
	
	// Reducimos el stock que ha pedido el usuario, descontandolo del stock total. Si no hay suficiente stock lanza una excepción.
	public void removeStock(List<Producto> stock) throws PedidoException {
		
	}
}