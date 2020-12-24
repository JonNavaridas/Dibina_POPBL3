package paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;

import gestionElementosVisuales.ImageFactory;
import gestionPantallas.PanelGradiente;

public class PanelImagen extends PanelGradiente {

	private static final long serialVersionUID = 1L;
	ImageIcon mainImage, iconImage;

	public PanelImagen() {
		super(new BorderLayout());
		
		this.setBackground(Color.white);
		this.mainImage = ImageFactory.createImageIcon(ImageFactory.IMAGEN_INICIO); // Imagen de fondo
		this.iconImage = ImageFactory.createImageIcon(ImageFactory.ICONO_LOGO); // Logo
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		// Colocar las imagenes de fondo
		g.drawImage(mainImage.getImage(), this.getWidth()/2 - mainImage.getIconWidth()/2, this.getHeight()/2 - mainImage.getIconHeight()/2, null);
		g.drawImage(iconImage.getImage(), this.getWidth() - iconImage.getIconWidth() - this.getWidth()/50, this.getHeight()/50, null);
	}
}