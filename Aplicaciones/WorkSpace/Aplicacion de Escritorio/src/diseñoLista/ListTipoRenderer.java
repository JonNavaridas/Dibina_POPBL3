package diseñoLista;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import gestionPantallas.RoundedBorder;

public class ListTipoRenderer implements ListCellRenderer<String> {

	@Override
	public Component getListCellRendererComponent(JList<? extends String> lista, String tipo, int index, boolean isSelected, boolean cellHashFocus) {
		JLabel label = new JLabel(tipo);
		label.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(1, 0, 0, 0), new RoundedBorder(5)));
		
		label.setFont(new Font("Times new roman", Font.TRUETYPE_FONT, 16));
		if (isSelected) {
			label.setBackground(Color.orange);
			label.setOpaque(true);
		}
		
		return label;
	}
}