package renderizadoTablaTipos;

import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;

import gestionFicheros.LectorElementos;

public class ModeloColumnas extends DefaultTableColumnModel {

	private static final long serialVersionUID = 1L;
	RendererTabla renderer;

	// Dividimos la tabla en cuatro columnas: Tipo, Cantidad, Procedencia y Fecha.
	public ModeloColumnas(RendererTabla renderer, int language) {
		super();
		this.renderer = renderer;
		
		String[] words = LectorElementos.leerTablaProducto(language);
		this.addColumn(crearColumna(words[2], 0, 250));	//tipo
		this.addColumn(crearColumna(words[0], 1, 100));	//cantidad
		this.addColumn(crearColumna(words[3], 2, 250));	//procedencia
		this.addColumn(crearColumna(words[1], 3, 250));	//fecha
	}

	private TableColumn crearColumna(String title, int i, int j) {
		TableColumn columna = new TableColumn(i, j);
		
		columna.setHeaderValue(title);
		columna.setCellRenderer(renderer);
		
		return columna;
	}
}
