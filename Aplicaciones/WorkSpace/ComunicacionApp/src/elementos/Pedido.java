package elementos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Pedido implements Serializable {
	
	private static final long serialVersionUID = 2176420357777145249L;
	
	Long id; // Numero determinado por la fecha en la que se hizo el pedido
	User user; // La persona que ha hecho el pedido
	Date fecha; // Fecha en la que se hizo el pedido
	Estado estado; // Situación del pedido
	String destino; // Lugar donde se va a recoger el pedido
	List<Producto> listaProductos; // Productos que se lleva el usuario en el pedido
	
	public Pedido(Long id, User user, Date fecha, Estado estado, String destino, List<Producto> listaProductos) {
		this.id = id;
		this.user = user;
		this.fecha = fecha;
		this.estado = estado;
		this.destino = destino;
		this.listaProductos = listaProductos;
	}
	
	public List<Producto> getProducto() {
		return listaProductos;
	}
	
	public Long getID() {
		return id;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
}