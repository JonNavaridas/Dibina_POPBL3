package elementos;

import java.util.HashMap;
import java.util.Map;

public class Pedido {

	String id;
	String destino;
	Map<Tipo, Integer> listaProductos;
	
	public Pedido(String id, String destino) {
		this.id = id;
		this.destino = destino;
		listaProductos = new HashMap<>();
	}
}