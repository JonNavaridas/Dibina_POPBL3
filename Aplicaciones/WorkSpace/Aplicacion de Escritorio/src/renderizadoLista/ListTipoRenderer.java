package renderizadoLista;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import gestionElementosVisuales.FontFactory;
import gestionPantallas.RoundedBorder;

// Renderer de las listas de elementos, utilizado para mostrar los tipos en el panel "Hacer Pedido".
public class ListTipoRenderer implements ListCellRenderer<String> {

	@Override
	public Component getListCellRendererComponent(JList<? extends String> lista, String tipo, int index, boolean isSelected, boolean cellHashFocus) {
		JLabel label = new JLabel(tipo);
		
		label.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(1, 0, 0, 0), new RoundedBorder(5)));
		label.setFont(FontFactory.createFont(FontFactory.BASE_FONT, 16));
		
		if (isSelected) { // Si el elemento esta seleccionado se le asigna un fondo naranja.
			label.setBackground(new Color(255, 200, 120));
			label.setOpaque(true);
		}
		
		return label;
	}
}