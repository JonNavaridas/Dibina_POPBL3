package elementos;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;

import gestionElementosVisuales.FontFactory;
import gestionElementosVisuales.ImageFactory;
import gestionPedidos.PedidoException;
import usuarios.User;

public class Pedido {
	
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
	
	// Reducimos el stock que ha pedido el usuario, descontandolo del stock total. Si no hay suficiente stock lanza una excepción.
	public void removeStock(List<Producto> stock) throws PedidoException {
		// No estoy seguro de si va a tener que ser utilizada, de momento aqui se queda.
	}
	
	public String setDisplayElementsHTML() {
		String texto = "";
		
		texto += "<html>"
				+ "<div  style=\"font-size:12px;\">"
				+ "<h2><center>Pedido " + this.id + "</center></h2>\r\n"
				+ "	\r\n"
				+ "	<p style=\"font-family:arial;margin:auto;\">\r\n"
				+ "		<u>Usuario</u>: " + this.user.getName() + " (" + this.user.getID() + ")<br>\r\n"
				+ "		<u>Fecha</u>: " + this.getDia() + "\r\n"
				+ "		<hr>\r\n"
				+ "		<table style=\"float:rigth;border-collapse:collapse;\">\r\n"
				+ "		  <tr>\r\n"
				+ "			<th style=\"padding:5px;\">Elemento</th>\r\n"
				+ "			<th style=\"padding:5px;\">Cantidad</th> \r\n"
				+ "		  </tr>\r\n"
				+ "		  \r\n"
				+ 		  getProductosHTML()
				+ "		  <tr>\r\n"
				+ "			<td style=\"text-align:right;\">Total:</td>\r\n"
				+ "			<td style=\"text-align:right;padding:5px;\">" + this.getNumElements() + "</td>\r\n"
				+ "		  </tr>\r\n"
				+ "		</table>\r\n"
				+ "		<hr>\r\n"
				+ "		<div style=\"font-family:arial;\"><u>Punto de entrega</u>: " + this.destino + "."
				+ "</div>" + "</div></html>";
		
		return texto;
	}
	
	private String getProductosHTML() {
		String texto = "";
		
		for (Producto p : listaProductos) {
			texto += "<tr>\r\n"
					+ "<td style=\"padding:5px;\">" + p.toString() + "</td>\r\n"
					+ "<td style=\"text-align:right;padding:5px;font-weight:bold;\">"
					+ p.getCantidad() +"</td>\r\n"
					+ "</tr>\r\n"
					+ "\r\n";
		}
		
		return texto;
	}
	
	public void drawPedido(Graphics2D g, double w, double h) {
		Font font = FontFactory.createFont(FontFactory.TITLE_FONT, 16);
		FontMetrics metrics = g.getFontMetrics(font);
		
		ImageIcon img = ImageFactory.createImageIcon(ImageFactory.ICONO_LOGO_NARANJA);
		g.drawImage(img.getImage(), (int) (w - 10 - img.getIconWidth()), 10, null);
		
		g.setFont(font);
		g.drawString("Pedido " + this.id, (int)(w/2 - metrics.stringWidth("Pedido " + this.id)/2), 100);
		
		font = FontFactory.createFont(FontFactory.BASE_FONT, 12);
		g.setFont(font);
		
		g.drawString("Usuario: " + this.user.getName() + " (" + this.user.getID() + ")", 80, 130);
		g.drawString("Fecha: " + this.getDia(), 80, 150);
		g.drawLine(80, 160, (int) (w - 80), 160);
		
		metrics = g.getFontMetrics(font);
		
		g.drawString("Elemento", 80, 180);
		g.drawString("Cantidad", (int) (w - 80 - metrics.stringWidth("Cantidad")), 180);
		
		g.drawLine(80, 181, (int) (80 + metrics.stringWidth("Elemento")), 181);
		g.drawLine((int) (w - 80 - metrics.stringWidth("Cantidad")), 181, (int) (w - 80), 181);
		
		Producto p;
		for (int i = 0; i < listaProductos.size(); i++) {
			p = listaProductos.get(i);
			g.drawString(p.toString(), 80, 200 + (i * 20));
			g.drawString(String.valueOf(p.getCantidad()), (int) (w - 80 - metrics.stringWidth(String.valueOf(p.getCantidad()))), 200 + (i * 20));
		}

		Integer jump = (20 * (listaProductos.size() - 1));
		g.drawLine(80, 210 + jump, (int) (w - 80), 210 + jump);
		
		g.drawString("Total:", 80, 230 + jump);
		g.drawString(String.valueOf(this.getNumElements()), (int) (w - 80 - metrics.stringWidth(String.valueOf(this.getNumElements()))), 230 + jump);
		
		g.drawLine(80, 240 + jump, (int) (w - 80), 240 + jump);
		g.drawString("Destino: " + this.destino + ".", 80, 260 + jump);
	}
}