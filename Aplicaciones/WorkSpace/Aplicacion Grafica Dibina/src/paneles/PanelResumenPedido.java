package paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import elementos.Pedido;
import gestionElementosVisuales.FontFactory;
import gestionElementosVisuales.ImageFactory;
import impresora.Imprimir;

public class PanelResumenPedido extends JFrame {

	private static final long serialVersionUID = 1L;
	public final static int DEFAULT_WIDTH = 400;
	public final static int DEFAULT_HEIGHT = 700;
	
	String[] words;
	Pedido pedido;
	JLabel label;

	public PanelResumenPedido(Pedido pedido, String[] words) {
		super(words[0]);
		
		this.words = words;
		this.pedido = pedido;
		label = new JLabel();
		
		// Determinar el tamaño de la pantalla.
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		int width = (int) toolkit.getScreenSize().getWidth();
		int height = (int) toolkit.getScreenSize().getHeight();

		// Colocar pantalla en el centro
		this.setIconImage(ImageFactory.createImage(ImageFactory.ICONO));
		this.setLocation(width/2 - DEFAULT_WIDTH/2, height/2 - DEFAULT_HEIGHT/2);
		this.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

		this.setContentPane(crearPanelVentana());
		
		this.setBackground(Color.white);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	private Container crearPanelVentana() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createLineBorder(Color.gray));
		panel.setBackground(Color.white);
		
		panel.add(panelInformacion(), BorderLayout.CENTER);
		panel.add(panelBoton(), BorderLayout.SOUTH);
		
		return panel;
	}

	private Component panelInformacion() {
		JScrollPane panel = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		panel.setBackground(Color.white);

		label.setOpaque(true);
		label.setBackground(Color.white);
		label.setVerticalAlignment(JLabel.TOP);
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setFont(FontFactory.createFont(FontFactory.BASE_FONT, 14));
		
		label.setText(pedido.setDisplayElementsHTML(Arrays.copyOfRange(words, 1, 7)));
		panel.setViewportView(label);
		
		return panel;
	}

	private Component panelBoton() {
		JPanel panel = new JPanel();
		panel.setBackground(Color.white);
		
		JButton boton = new JButton(words[1]);
		boton.addActionListener((l)->{
			PrinterJob job = PrinterJob.getPrinterJob();
			job.setPrintable(new Imprimir(pedido));
			
			if (job.printDialog()) {
				try {
					job.print();
				} catch (PrinterException e) {
					e.printStackTrace();
				}
			}
		});
		boton.setFont(FontFactory.createFont(FontFactory.BASE_FONT, 16));
		boton.setPreferredSize(new Dimension(100, 30));
		panel.add(boton);
		
		return panel;
	}
}