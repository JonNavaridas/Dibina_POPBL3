package elementos;

import java.util.Date;
import java.util.List;

public class Pedido {
	
	Long id; // Numero determinado por la fecha en la que se hizo el pedido
	User user; // La persona que ha hecho el pedido
	Date fecha; // Fecha en la que se hizo el pedido
	Estado estado; // Situaci�n del pedido
	String destino; // Lugar donde se va a recoger el pedido
	List<Producto> listaProductos; // Productos que se lleva el usuario en el pedido
	
}