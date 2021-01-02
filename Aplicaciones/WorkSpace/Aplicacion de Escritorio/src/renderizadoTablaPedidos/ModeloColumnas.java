package renderizadoTablaPedidos;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

public class ModeloColumnas extends DefaultTableColumnModel {

	private static final long serialVersionUID = 1L;
	RendererTabla renderer;

	// Dividimos la tabla en cuatro columnas: Tipo, Cantidad, Procedencia y Fecha.
	public ModeloColumnas(RendererTabla renderer) {
		super();
		this.renderer = renderer;
		
		this.addColumn(crearColumna("Usuario", 0, 250));
		this.addColumn(crearColumna("Cantidad", 1, 100));
		this.addColumn(crearColumna("Destino", 2, 250));
		this.addColumn(crearColumna("Fecha", 3, 250));
		this.addColumn(crearColumna("Estado", 4, 100));
	}

	private TableColumn crearColumna(String title, int i, int j) {
		TableColumn columna = new TableColumn(i, j);
		
		columna.setHeaderValue(title);
		columna.setCellRenderer(renderer);
		
		return columna;
	}
}
