package renderizadoTablaTipos;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

public class ModeloColumnas extends DefaultTableColumnModel {

	private static final long serialVersionUID = 1L;
	RendererTabla renderer;

	// Dividimos la tabla en cuatro columnas: Tipo, Cantidad, Procedencia y Fecha.
	public ModeloColumnas(RendererTabla renderer) {
		super();
		this.renderer = renderer;
		this.addColumn(crearColumna("Tipo", 0, 250));
		this.addColumn(crearColumna("Cantidad", 1, 100));
		this.addColumn(crearColumna("Procedencia", 2, 250));
		this.addColumn(crearColumna("Fecha", 3, 250));
	}

	private TableColumn crearColumna(String title, int i, int j) {
		TableColumn columna = new TableColumn(i, j);
		
		columna.setHeaderValue(title);
		columna.setCellRenderer(renderer);
		
		return columna;
	}
}
