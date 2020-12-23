package diseñoTabla;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import gestionElementosVisuales.FontFactory;

public class RendererTabla extends DefaultTableCellRenderer {
	
	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable tabla, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel componente = (JLabel) super.getTableCellRendererComponent(tabla, value, isSelected, hasFocus, row, column);
		
		componente.setBorder(BorderFactory.createEmptyBorder(3, 0, 3, 0));
		componente.setFont(FontFactory.createFont(FontFactory.BASE_FONT, 16));
		
		switch(column) {
		case 0: 
			componente.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
			componente.setHorizontalAlignment(JLabel.LEFT);
			break;
		case 1:
			componente.setHorizontalAlignment(JLabel.CENTER);
			break;
		case 2:
			componente.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
			componente.setHorizontalAlignment(JLabel.LEFT);
			break;
		case 3:
			componente.setHorizontalAlignment(JLabel.CENTER);
			break;
		default: break;
		}
		
		return componente;
	}
}