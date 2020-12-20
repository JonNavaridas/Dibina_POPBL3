package diseñoTabla;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class RendererTabla extends DefaultTableCellRenderer {
	
	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable tabla, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		
		JLabel componente = (JLabel) super.getTableCellRendererComponent(tabla, value, isSelected, hasFocus, row, column);
		componente.setFont(new Font("Times new roman", Font.TRUETYPE_FONT, 12));
		return componente;
	}
}