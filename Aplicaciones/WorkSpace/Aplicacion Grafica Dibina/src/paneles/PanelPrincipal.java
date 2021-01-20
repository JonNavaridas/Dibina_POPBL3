package paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JLabel;
import javax.swing.JScrollPane;

import gestionElementosVisuales.FontFactory;

public class PanelPrincipal extends JScrollPane {

	private static final long serialVersionUID = 1L;

	public PanelPrincipal() {
		super(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.setBackground(Color.white);
		this.setBorder(null);
		
		this.getVerticalScrollBar().setUnitIncrement(20);
		
		this.setViewportView(crearPanelVentana());
		this.setBackground(Color.white);
	}

	private Component crearPanelVentana() {
		PanelImagen panel = new PanelImagen();
		panel.setBackground(Color.white);
		
		JLabel label = new JLabel("Dibina.eus®");		
		label.setFont(FontFactory.createFont(FontFactory.BASE_FONT, 18));
		label.setPreferredSize(new Dimension(200, 40));
		label.setForeground(new Color(255, 118, 0));
		label.setHorizontalAlignment(JLabel.CENTER);
		
		label.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				try {
					Desktop.getDesktop().browse(new URI("https://labur.eus/dibina"));
			    } catch (IOException | URISyntaxException e) {
			        e.printStackTrace();
			    }
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				label.setForeground(Color.blue);
				label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				label.setForeground(new Color(255, 118, 0));
			}

			@Override
			public void mousePressed(MouseEvent arg0) {}

			@Override
			public void mouseReleased(MouseEvent arg0) {}
		});
		
		panel.add(label, BorderLayout.SOUTH);
		
		return panel;
	}
}