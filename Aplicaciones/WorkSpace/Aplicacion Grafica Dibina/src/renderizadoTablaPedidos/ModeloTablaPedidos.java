package renderizadoTablaPedidos;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import elementos.Pedido;

public class ModeloTablaPedidos  extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static final String[] NOMBRE_COLUMNAS = { "Usuario", "Cantidad", "Destino", "Fecha", "Estado" };
	
	List<Pedido> listaPedidos;
	
	public ModeloTablaPedidos(List<Pedido> listaPedidos) {
		this.listaPedidos = listaPedidos;
	}
	
	// Cambiar la lista de elementos que mostramos en caso de haber cambios en estos.
	public void setLista(List<Pedido> listaPedidos) {
		this.listaPedidos = listaPedidos;
	}
	
	@Override
	public String getColumnName(int column) {
		return NOMBRE_COLUMNAS[column];
	}

	@Override
	public int getColumnCount() {
		return NOMBRE_COLUMNAS.length;
	}

	@Override
	public int getRowCount() {
		return listaPedidos.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		Pedido p = listaPedidos.get(row);
		return p.getFieldAt(column);
	}
}
