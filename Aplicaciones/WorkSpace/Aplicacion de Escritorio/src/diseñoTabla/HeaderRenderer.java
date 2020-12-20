package diseñoTabla;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import gestionPantallas.RoundedBorder;

public class HeaderRenderer implements TableCellRenderer {

	 DefaultTableCellRenderer renderer;

	    public HeaderRenderer(JTable table) {
	        renderer = (DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer();
	        renderer.setHorizontalAlignment(JLabel.CENTER);
	    }

	    @Override
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
	    	renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
	    	
	    	renderer.setFont(new Font("Times new roman", Font.HANGING_BASELINE, 16));
	    	renderer.setBorder(new RoundedBorder(5));
	    	
	    	return renderer;
	    }
}
