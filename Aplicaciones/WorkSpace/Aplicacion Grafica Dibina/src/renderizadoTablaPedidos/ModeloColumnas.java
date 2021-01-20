package renderizadoTablaPedidos;

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
		String[] words = LectorElementos.leerTablaPedido(language);
		
		this.addColumn(crearColumna(words[0], 0, 250));	// Usuario
		this.addColumn(crearColumna(words[3], 1, 100)); // Cantidad
		this.addColumn(crearColumna(words[1], 2, 250));	// Destino
		this.addColumn(crearColumna(words[4], 3, 250));	// Fecha
		this.addColumn(crearColumna(words[2], 4, 100));	// Estado
	}

	private TableColumn crearColumna(String title, int i, int j) {
		TableColumn columna = new TableColumn(i, j);
		
		columna.setHeaderValue(title);
		columna.setCellRenderer(renderer);
		
		return columna;
	}
}
