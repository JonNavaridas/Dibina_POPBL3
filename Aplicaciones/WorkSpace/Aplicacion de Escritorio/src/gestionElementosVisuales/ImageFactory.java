package gestionElementosVisuales;

import java.awt.Image;

import javax.swing.ImageIcon;

public class ImageFactory {

	public static final String IMAGEN_CARGA = "Images/carga.png";
	public static final String IMAGEN_INICIO = "Images/inicio.png";

	public static final String IMAGEN_HOME = "Images/home.png";
	public static final String IMAGEN_STOCK = "Images/stock.png";
	public static final String IMAGEN_PEDIDO = "Images/pedido.png";
	public static final String IMAGEN_BUSQUEDA = "Images/busqueda.png";
	public static final String IMAGEN_PEDIDOS = "Images/pedidos.png";
	public static final String IMAGEN_HISTORIAL = "Images/historial.png";
	
	public static final String ICONO = "Icons/icon.png";
	public static final String ICONO_LOGO = "Icons/logo.png";

	public static final String ICONO_STOCK = "Icons/stock.png";
	public static final String ICONO_PEDIDO = "Icons/pedido.png";
	public static final String ICONO_BUSQUEDA = "Icons/busqueda.png";
	public static final String ICONO_PEDIDOS = "Icons/pedido.png";
	public static final String ICONO_HISTORIAL = "Icons/historial.png";
	public static final String ICONO_USUARIO = "Icons/usuario.png";
	public static final String ICONO_SALIR = "Icons/salir.png";
	
	public static final String ICONO_IDIOMA = "Icons/idioma.png";
	public static final String ICONO_CASTELLANO = "Icons/castellano.png";
	public static final String ICONO_EUSKERA = "Icons/euskera.png";
	public static final String ICONO_INGLES = "Icons/ingles.png";
	
	public static ImageIcon createImageIcon(String route) {
		return new ImageIcon(route);
	}
	
	public static Image createImage(String route) {
		return new ImageIcon(route).getImage();
	}
}