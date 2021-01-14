package comunicacionServer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import elementos.Estado;
import elementos.Pedido;
import elementos.Permisos;
import elementos.Procedencia;
import elementos.Producto;
import elementos.Tipo;
import elementos.User;

public class ElementoEnCola {

	private static final SimpleDateFormat SDF = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
	
	int operacion;
	String elemento;
	
	public ElementoEnCola(int operacion, String line) {
		this.operacion = operacion;
		this.elemento = line;
	}
	
	public int getOperacion() {
		return operacion;
	}

	public String getElemento() {
		return elemento;
	}

	public Pedido transformToPedido() throws ParseException {
		String[] valores = elemento.split("[#]");
		
		Long id = Long.parseLong(valores[0]);
		User user = transformToUser(valores[1]);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(SDF.parse(valores[2]));
		Date fecha = cal.getTime();
		
		Estado estado = Estado.getEstado(valores[3]);
		String destino = valores[4];
		
		List<Producto> listaProductos = manageProductos(valores[5]);
		
		return new Pedido(id, user, fecha, estado, destino, listaProductos);
	}
	
	public User transformToUser() {
		String[] valores = elemento.split("[$]");
		
		String id = valores[0];
		String name = valores[1];
		String fullName = valores[2];
		Integer password = Integer.parseInt(valores[3]);
		Permisos permisos = Permisos.getPermiso(valores[4]);
		
		return new User(id, name, fullName, password, permisos);
	}
	
	private User transformToUser(String parametros) {
		String[] valores = parametros.split("[-]")[0].split("[$]");
		
		String id = valores[0];
		String name = valores[1];
		String fullName = valores[2];
		Integer password = Integer.parseInt(valores[3]);
		Permisos permisos = Permisos.getPermiso(valores[4]);
		
		return new User(id, name, fullName, password, permisos);
	}
	
	private List<Producto> manageProductos(String productos) throws ParseException {
		String[] elementos = productos.split("[%]");
		List<Producto> lista = new ArrayList<>();
		
		for (int i = 0; i < elementos.length; i++) {
			lista.add(transformProducto(elementos[i]));
		}
		
		return lista;
	}

	private Producto transformProducto(String elemento) throws ParseException {	
		String[] valores = elemento.split("[$]");
		
		Tipo tipo = transformToTipo(valores[0]);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(SDF.parse(valores[1]));
		Date fecha = cal.getTime();
		
		int cantidad = Integer.parseInt(valores[2]);
		Procedencia procedencia = transformToProcedencia(valores[3]);
		
		return new Producto(tipo, fecha, cantidad, procedencia);
	}

	private Tipo transformToTipo(String elemento) {
		String[] valores = elemento.split("[%]");
		return new Tipo(Integer.parseInt(valores[0]), valores[1]);
	}

	private Procedencia transformToProcedencia(String elemento) {
		String[] valores = elemento.split("[%]");
		return new Procedencia(valores[0], valores[1]);
	}
}