package elementos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gestionPaquetes.PedidoException;

public class Pedido {

	Long id; // Numero determinado por la fecha en la que se hizo el pedido
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

	// A�adir un producto a la lista de productos, como si de una lista de la compra se tratase.
	public void addProducto(Producto producto) {
		boolean encontrado = false;
		
		for (Producto p : listaProductos) {
			if (p.equals(producto)) { // Si encontramos el producto en la lista, a�adimos la cantidad a esta.
				p.addElements(producto.getCantidad());
				encontrado = true;
			}
		}
		// Si no encontramos el elemnto en la lista significa que es la primera vez que introducimos ese elemento.
		if (!encontrado) listaProductos.add(producto);
	}
	
	public void removeProducto(Tipo tipo, Procedencia procedencia, int cantidad) throws PedidoException {
		Producto producto = new Producto(tipo, null, cantidad, procedencia);
		
		for (Producto p : listaProductos) {
			if (p.equals(producto)) { // Eliminamos el elemento de la lista
				listaProductos.remove(p);
				return;
			}
		}
		// Si no hemos encontrado el elemento a eliminar significa que un error ha ocurrido en el programa.
		throw new PedidoException("No se ha podido encontrar ese producto.");
	}
	
	// Reducimos el stock que ha pedido el usuario, descontandolo del stock total. Si no hay suficiente stock lanza una excepci�n.
	public void removeStock(List<Producto> stock) throws PedidoException {
		// No estoy seguro de si va a tener que ser utilizada, de momento aqui se queda.
	}
}