package renderizadoTablaTipos;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import elementos.Producto;

public class ModeloTablaTipos  extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	private static final String[] NOMBRE_COLUMNAS = { "Tipo", "Cantidad", "Procedencia", "Fecha llegada" };
	
	List<Producto> listaProductos;
	
	public ModeloTablaTipos(List<Producto> listaProductos) {
		this.listaProductos = listaProductos;
	}
	
	// Cambiar la lista de elementos que mostramos en caso de haber cambios en estos.
	public void setLista(List<Producto> listaProductos) {
		this.listaProductos = listaProductos;
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
		return listaProductos.size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		Producto p = listaProductos.get(row);
		return p.getFieldAt(column);
	}
}
