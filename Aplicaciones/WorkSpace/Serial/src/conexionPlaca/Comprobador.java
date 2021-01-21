package conexionPlaca;


import java.util.Calendar;
import java.util.List;

import conexionSockets.Cliente;
import elementos.Estado;
import elementos.Pedido;
import elementos.Procedencia;
import elementos.Producto;
import elementos.Tipo;

public class Comprobador {

	List<Pedido> lista;
	String[] destinos;
	Procedencia[] procedencias;
	Tipo[] tipos;
	
	public Comprobador() {		
		lista = leerListadoPedidos();
		destinos = Lector.leerDestinos();
		procedencias = Lector.leerProcedencias().toArray(new Procedencia[0]);
		tipos = Lector.leerTipos().toArray(new Tipo[0]);
	}
		
	public boolean procesarMensaje(Byte[] mensaje) {
		int i=0;
		boolean enviar = false;
		Pedido pedido = new Pedido();
		Producto producto;
		
		if((mensaje[i] & 0xFF)==129) { // recibir = 10000001 (129)
			enviar = false;	
			i++;
		}
		else if((mensaje[i] & 0xFF)==130) { // enviar = 10000010 (130)
			enviar = true;	
			i++;
			pedido.setDestino(destinos[(mensaje[i++] & 0xFF)]);
		}

		do {
			int valores[] = new int[3];
			valores[0] = mensaje[i++]& 0xff; 								//Tipo
			valores[1] = obtenerCantidad(mensaje[i++], mensaje[i++]);		//((mensaje[i++] & 0xff) << 8) | (mensaje[i++] & 0xff)	//Cantidad
			valores[2] = mensaje[i++]& 0xff;								//Procedencia
			
			if(isProductoCorrecto(valores)) {
				producto = new Producto(tipos[valores[0]],Calendar.getInstance().getTime(),valores[1],procedencias[valores[2]]);
				if(enviar)pedido.addProducto(producto);
			}
			else return false;		
			
		} while((mensaje[i]& 0xFF) != 255);
		
		if(enviar) {
			Pedido tmp = encontrarPedido(pedido);
			if (tmp == null) return false;
			else enviarPedido(tmp);
		}
		else recibirProducto(producto);
		
		return true;
	}
	
	private void enviarPedido(Pedido pedido) {
		Cliente cl = new Cliente("Sacar pedido de almacen", pedido.transformToString());
		cl.start();
	}

	private void recibirProducto(Producto producto) {
		Cliente cl = new Cliente("Añadir stock", producto.transformToString());
		cl.start();
	}

	private boolean isProductoCorrecto(int[] valores) {
		if(valores[1] <= 0) return false;
		if(valores[2]>=procedencias.length || 
				valores[0]>= tipos.length||
				!procedencias[valores[2]].getListTipo().contains(tipos[valores[0]])) return false;
		return true;
	}

	private int obtenerCantidad(byte b, byte c) {
		int primerByte = (b & 0xff) << 7;
		int segundoByte = bitExtracted(c, 7, 1);
		
		return primerByte+segundoByte;
	}

	public int bitExtracted(int number, int k, int p) { 
        return (((1 << k) - 1) & (number >> (p - 1))); 
    }

	private List<Pedido> leerListadoPedidos(){
		List<Pedido> list = null;
		boolean opened = false;
		
		while(!opened) {
			list = Lector.leerPedidos("Files/Pedidos.dat");
			
			if(list != null) opened = true;
			else {
				System.out.println("esperando...");
				try {
					Thread.sleep(300);					
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	private Pedido encontrarPedido(Pedido pedido) {
		Pedido tmp = lista.stream().filter(f->f.getDestino().equals(pedido.getDestino())&& 
					  f.compareProductList(pedido.getListaProductos())&&
					  f.getEstado()==Estado.ACEPTADO).findFirst().orElse(null);
		
		return tmp;
	}

}
