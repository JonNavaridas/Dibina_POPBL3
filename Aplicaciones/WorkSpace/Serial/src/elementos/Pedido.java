package elementos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import exceptions.PedidoException;

public class Pedido implements Serializable{
	
	private static final long serialVersionUID = 1L;
	Long id; // Numero determinado por la fecha en la que se hizo el pedido
	User user; // La persona que ha hecho el pedido
	Date fecha; // Fecha en la que se hizo el pedido
	Estado estado; // Situación del pedido
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
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public String getUserString() {
		return user.getName() + " (" + user.getID() + ")";
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

	public Estado getEstado() {
		return estado;
	}
	
	public String getEstadoString() {
		return estado.toString().toLowerCase();
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	
	public Integer getNumElements() {
		int cantidad = 0;
		for (Producto p : listaProductos) cantidad += p.getCantidad();
		return cantidad;
	}
	
	@SuppressWarnings("deprecation")
	public String getDia() {
		String[] valores = fecha.toString().split(" ");
		return valores[5] + "/" + (fecha.getMonth() + 1) + "/" + valores[2];
	}

	public Object getFieldAt(int column) {
		switch(column) {
		case 0: return new String(user.toString());
		case 1: return getNumElements();
		case 2: return  new String(destino);
		case 3: return fecha;
		case 4: return estado;
		default: return null;
		}
	}

	// Añadir un producto a la lista de productos, como si de una lista de la compra se tratase.
	public void addProducto(Producto producto) {
		boolean encontrado = false;
		
		for (Producto p : listaProductos) {
			// Si encontramos el producto en la lista, añadimos la cantidad a esta.
			if (p.equals(producto)) {
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

	@Override
	public String toString() {
		return "Pedido [id=" + id + ", user=" + user + ", fecha=" + fecha + ", estado=" + estado + ", destino="
				+ destino + ", listaProductos=" + listaProductos + "]";
	}	
	
	public boolean compareProductList(List<Producto> productos) {
		if(!(productos.size() == listaProductos.size())) return false;
		for(Producto producto: productos) {
			Producto tmp = listaProductos.stream().filter(f->f.getCantidad()==producto.getCantidad()&&
												 f.getTipo().getNombre().equals(producto.getTipo().getNombre())&&
												 f.getProcedencia().getNombre().equals(producto.getProcedencia().getNombre()))
										 .findAny().orElse(null);
			if(tmp==null)return false;
		}
		return true;
	}
	
	public String transformToString() {
		String output = "";

		output += id + "#";
		output += user.transformToString() + "#";
		output += fecha.toString() + "#";
		output += estado.toString().toLowerCase() + "#";
		output += destino + "#";

		for (Producto p : listaProductos) {
			output += p.transformToString() + "&";
		}

		return output.substring(0, output.length() - 1);
	}
}