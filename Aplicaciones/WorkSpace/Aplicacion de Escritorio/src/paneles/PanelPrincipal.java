package paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JScrollPane;

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
		
		JLabel label = new JLabel("Dibina.eus©");
		
		label.setFont(new Font("Times new roman", Font.TRUETYPE_FONT, 18));
		label.setPreferredSize(new Dimension(200, 40));
		label.setForeground(new Color(255, 118, 0));
		label.setHorizontalAlignment(JLabel.CENTER);
		
		panel.add(label, BorderLayout.SOUTH);
		
		return panel;
	}
}