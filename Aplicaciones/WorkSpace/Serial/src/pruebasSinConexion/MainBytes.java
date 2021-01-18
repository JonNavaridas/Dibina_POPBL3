package pruebasSinConexion;

import java.util.Calendar;
import java.util.List;

import elementos.Estado;
import elementos.Pedido;
import elementos.Procedencia;
import elementos.Producto;
import elementos.Tipo;

public class MainBytes {

	List<Pedido> lista;
	String[] destinos;
	Procedencia[] procedencias;
	Tipo[] tipos;
	byte[] mensaje;
	
	public MainBytes() {		
		inicializar();
		procesarMensaje(mensaje);
	}
		
	private boolean procesarMensaje(byte[] mensaje) {
		int i=0;
		boolean enviar = false;
		Pedido pedido = new Pedido();
		Producto producto;
		
		if((mensaje[i] & 0xFF)==129) {			//recibir = 10000001 (129)
			enviar = false;	
			i++;
		}
		else if((mensaje[i] & 0xFF)==130) {		//enviar = 10000010 (130)
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
			
		}while((mensaje[i]& 0xFF) != 255);
		
		if(enviar) {
			Pedido tmp = encontrarPedido(pedido);
			if (tmp == null) return false;
			else enviarPedido(tmp);
		}
		else recibirProducto(producto);
		
		return true;
	}
	
	private void enviarPedido(Pedido pedido) {
		//COMUNICACION SOCKET
		System.out.println(pedido);
	}

	private void recibirProducto(Producto producto) {
		//COMUNICACION SOCKET
		System.out.println(producto);
	}

	private boolean isProductoCorrecto(int[] valores) {
		if(valores[1] <= 0) return false;
		if(!procedencias[valores[2]].getListTipo().contains(tipos[valores[0]])) return false;
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

	private void inicializar() {
		lista = leerListadoPedidos();
		destinos = Lector.leerDestinos();
		procedencias = Lector.leerProcedencias().toArray(new Procedencia[0]);
		tipos = Lector.leerTipos().toArray(new Tipo[0]);
	}

	private List<Pedido> leerListadoPedidos(){
		List<Pedido> list = null;
		boolean opened = false;
		int i=0;
		
		while(!opened) {
			if(i==3)list = Lector.leerPedidos("Files/Pedidos.dat"); //PARA COMPROBAR QUE FUNCIONA EL WHILE
			else list = Lector.leerPedidos("Files/Pedidos2.dat");
			
			if(list != null) opened = true;
			else {
				System.out.println("esperando...");
				try {
					Thread.sleep(300);					
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
			i++;
		}
		for(Pedido pedido: lista)System.out.println(pedido.toString());
		return list;
	}

	private Pedido encontrarPedido(Pedido pedido) {
		Pedido tmp = lista.stream().filter(f->f.getDestino().equals(pedido.getDestino())&& 
					  f.compareProductList(pedido.getListaProductos())&&
					  f.getEstado()==Estado.ACEPTADO).findFirst().orElse(null);
		
		return tmp;
	}
	
	public static void main(String[] args) {
		MainBytes programa = new MainBytes();
	}

}
