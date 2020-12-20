package diseñoTabla;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

public class ModeloColumnas extends DefaultTableColumnModel {

	private static final long serialVersionUID = 1L;
	RendererTabla renderer;

	public ModeloColumnas(RendererTabla renderer) {
		super();
		this.renderer = renderer;
		this.addColumn(crearColumna("Tipo", 0, 250));
		this.addColumn(crearColumna("Cantidad", 1, 100));
		this.addColumn(crearColumna("Procedencia", 2, 250));
		this.addColumn(crearColumna("Fecha", 3, 250));
	}

	private TableColumn crearColumna(String string, int i, int j) {
		TableColumn columna = new TableColumn(i, j);
		columna.setHeaderValue(string);
		columna.setCellRenderer(renderer);
		return columna;
	}
}
