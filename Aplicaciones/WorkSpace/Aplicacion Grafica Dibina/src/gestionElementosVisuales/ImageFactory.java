package gestionElementosVisuales;

import java.awt.Image;

import javax.swing.ImageIcon;

public class ImageFactory {

	public static final String IMAGEN_CARGA = "Images/carga.png";
	public static final String IMAGEN_INICIO = "Images/inicio.png";
	public static final String IMAGEN_USER = "Images/user.png";

	public static final String IMAGEN_HOME = "Images/home.png";
	public static final String IMAGEN_STOCK = "Images/stock.png";
	public static final String IMAGEN_PEDIDO = "Images/pedido.png";
	public static final String IMAGEN_BUSQUEDA = "Images/busqueda.png";
	public static final String IMAGEN_PEDIDOS = "Images/pedidos.png";
	public static final String IMAGEN_HISTORIAL = "Images/historial.png";
	
	public static final String ICONO = "Icons/icon.png";
	public static final String ICONO_LOGO = "Icons/logo.png";
	public static final String ICONO_LOGO_NARANJA = "Icons/logo_naranja.png";

	public static final String ICONO_SHOW = "Icons/showPassword.png";
	public static final String ICONO_HIDE = "Icons/hidePassword.png";

	public static final String ICONO_STOCK = "Icons/stock.png";
	public static final String ICONO_PEDIDO = "Icons/pedido.png";
	public static final String ICONO_BUSQUEDA = "Icons/busqueda.png";
	public static final String ICONO_PEDIDOS = "Icons/pedido.png";
	public static final String ICONO_REFRESCAR = "Icons/refresh.png";
	public static final String ICONO_HISTORIAL = "Icons/historial.png";
	public static final String ICONO_USUARIO = "Icons/usuario.png";
	public static final String ICONO_SALIR = "Icons/salir.png";
	
	public static final String ICONO_IDIOMA = "Icons/idioma.png";
	public static final String ICONO_CASTELLANO = "Icons/castellano.png";
	public static final String ICONO_EUSKERA = "Icons/euskera.png";
	public static final String ICONO_INGLES = "Icons/ingles.png";

	public static final String ICONO_CONFIRMAR = "Icons/confirmacion.png";
	public static final String ICONO_DENEGAR = "Icons/denegar.png";
	public static final String ICONO_ENTREGADO = "Icons/entregado.png";
	public static final String ICONO_LIMPIAR = "Icons/papelera.png";
	
	public static final String ICONO_FILTRAR = "Icons/filtrar.png";
	public static final String ICONO_CANCELAR = "Icons/cancelar.png";
	public static final String ICONO_AJUSTES = "Icons/ajustes.png";
	public static final String ICONO_CAMBIAR_CONTRASEÑA = "Icons/cambiarContraseña.png";
	public static final String ICONO_REHACER = "Icons/rehacer.png";
	public static final String ICONO_VER = "Icons/ver.png";
	
	public static final String ICONO_VER_CONTRASEÑA = "Icons/ver_contraseña.png";
	public static final String ICONO_OCULTAR_CONTRASEÑA = "Icons/ocultar_contraseña.png";
	
	// Generar una ImageIcon.
	public static ImageIcon createImageIcon(String route) {
		return new ImageIcon(route);
	}
	
	// Generar una imagen.
	public static Image createImage(String route) {
		return new ImageIcon(route).getImage();
	}
}