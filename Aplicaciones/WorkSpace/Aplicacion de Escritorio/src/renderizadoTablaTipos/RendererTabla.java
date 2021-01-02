package renderizadoTablaTipos;

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
		case 0: // Estilo de la columna Tipo.
			componente.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
			componente.setHorizontalAlignment(JLabel.LEFT);
			break;
		case 1: // Estilo de la columna Cantidad.
			componente.setHorizontalAlignment(JLabel.CENTER);
			break;
		case 2: // Estilo de la columna Procedencia.
			componente.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));
			componente.setHorizontalAlignment(JLabel.LEFT);
			break;
		case 3: // Estilo de la columna Fecha.
			componente.setHorizontalAlignment(JLabel.CENTER);
			break;
		default: break;
		}
		
		return componente;
	}
}