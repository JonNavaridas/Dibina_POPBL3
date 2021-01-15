package pruebasSinConexion;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import elementos.Estado;
import elementos.Pedido;
import elementos.Permisos;
import elementos.Procedencia;
import elementos.Producto;
import elementos.Tipo;
import elementos.User;

public class MainBytes {

	List<Pedido> lista;
	String[] destinos;
	Procedencia[] procedencias;
	Tipo[] tipos;
	byte[] mensaje;
	
	public MainBytes() {		
		inicializar();
		start();
		
		mensaje = crearMensajeProducto();
		convertirMensaje(mensaje);
		mensaje = crearMensajePedido();
		convertirMensaje(mensaje);
	}
	
	//Integer.toBinaryString((byte & 0xFF) + 0x100).substring(1)
	
	private void convertirMensaje(byte[] mensaje) {
		int i=0;
		boolean enviar = false;
		Pedido pedido = new Pedido();
		Producto producto;
		
		if((mensaje[i] & 0xFF)==129) {						//recibir = 10000001 (129)
			enviar = false;	
			i++;
		}
		else if((mensaje[i] & 0xFF)==130) {					//enviar = 10000010 (130)
			enviar = true;	
			i++;
			pedido.setDestino(destinos[(mensaje[i++] & 0xFF)]);
		}

		do {
			int valores[] = new int[3];
			valores[0] = mensaje[i++]& 0xff; 									//Tipo
			valores[1] = ((mensaje[i++] & 0xff) << 8) | (mensaje[i++] & 0xff);	//Cantidad
			valores[2] = mensaje[i++]& 0xff;									//Procedencia
			
			producto = new Producto(tipos[valores[0]], 
					Calendar.getInstance().getTime(), 
					valores[1], 
					procedencias[valores[2]]);
			if(enviar)pedido.addProducto(producto);
			
		}while((mensaje[i]& 0xFF) != 255);
		
		if(enviar) enviarPedido(pedido);
		else recibirProducto(producto);
	}

	private void enviarPedido(Pedido pedido) {
		//enviar el pedido por socket		
		System.out.println(pedido.toString());
		System.out.println((encontrarPedido(pedido)==null)? "ERROR":"OK\n"+pedido);

	}

	private void recibirProducto(Producto producto) {
		//enviar el producto por socket		
		System.out.println(producto.toString() + " " +producto.getCantidad()+ " " +producto.getFecha());
	}

	private byte[] crearMensajeProducto() {
		byte[] mensaje = new byte[128];
		mensaje[0] = (byte) Integer.parseInt("10000001",2);	//Byte de inicio
		mensaje[1] = (byte) Integer.parseInt("00000000",2);	//Producto
		mensaje[2] = (byte) Integer.parseInt("00000000",2);	//Cantidad[0]
		mensaje[3] = (byte) Integer.parseInt("00000011",2);	//Cantidad[1]
		mensaje[4] = (byte) Integer.parseInt("00000001",2);	//Empresa
		mensaje[5] = (byte) Integer.parseInt("11111111",2);	//Byte final
		return mensaje;
	}

	private byte[] crearMensajePedido() {
		byte[] mensaje = new byte[128];
		mensaje[0] = (byte) Integer.parseInt("10000010",2);	//Byte de inicio
		mensaje[1] = (byte) Integer.parseInt("00000001",2);	//Destino
		
		mensaje[2] = (byte) Integer.parseInt("00000000",2);	//Producto
		mensaje[0] = (byte) Integer.parseInt("10000010",2);	//Byte de inicio
		mensaje[1] = (byte) Integer.parseInt("00000001",2);	//Destino
		
		mensaje[2] = (byte) Integer.parseInt("00000000",2);	//Producto
		mensaje[3] = (byte) Integer.parseInt("00000000",2);	//Cantidad[0]
		mensaje[4] = (byte) Integer.parseInt("00000011",2);	//Cantidad[1]
		mensaje[5] = (byte) Integer.parseInt("00000001",2);	//Empresa
		
		mensaje[6] = (byte) Integer.parseInt("00000001",2);	//Producto
		mensaje[7] = (byte) Integer.parseInt("00000001",2);	//Cantidad[0]
		mensaje[8] = (byte) Integer.parseInt("00000000",2);	//Cantidad[1]
		mensaje[9] = (byte) Integer.parseInt("00000010",2);	//Empresa
		
		mensaje[10] = (byte) Integer.parseInt("11111111",2);//Byte final
		return mensaje;						
	}

	private void inicializar() {
		lista = new ArrayList<>();
		destinos = Lector.leerDestinos();
		procedencias = Lector.leerProcedencias().toArray(new Procedencia[0]);
		tipos = Lector.leerTipos().toArray(new Tipo[0]);
		
		for(int i  = 0; i < 4; i++) {
			Pedido pedido = null;
			pedido = crearPedido(i);
			lista.add(pedido);
		}
		Lector.escribirPedidos(lista);
	}

	private Pedido crearPedido(int i) {
		Pedido pedido = new Pedido();
		List<User> users = crearUsers();
		
		pedido.setDestino(destinos[(int)(Math.random()*destinos.length)]);
		pedido.setEstado(Estado.ACEPTADO);
		pedido.setFecha(Calendar.getInstance().getTime());
		pedido.setId((long) 11111);
		pedido.setUser(users.get(i));
		for(int j = 0; j < 3; j++) {
			pedido.addProducto(new Producto(
					tipos[(int)(Math.random()*tipos.length)], 
					Calendar.getInstance().getTime(), 
					(int)(Math.random()*100), 
					procedencias[(int)(Math.random()*procedencias.length)]));
		}
		return pedido;
	}

	private List<User> crearUsers() {
		List<User> users = new ArrayList<>();
		users.add(new User("11111", "1", "11", 1, Permisos.TOTAL));
		users.add(new User("22222", "2", "22", 2, Permisos.TOTAL));
		users.add(new User("33333", "3", "33", 3, Permisos.TOTAL));
		users.add(new User("44444", "4", "44", 4, Permisos.TOTAL));		
		return users;
	}

	private void start(){
		lista = null;
		boolean opened = false;
		int i=0;
		
		while(!opened) {
			if(i==3)lista = Lector.leerPedidos("Files/Pedidos.dat"); //PARA COMPROBAR QUE FUNCIONA EL WHILE
			else lista = Lector.leerPedidos("Files/Pedidos2.dat");
			
			if(lista != null) opened = true;
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
