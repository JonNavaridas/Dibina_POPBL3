package usuarios;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import elementos.Estado;
import elementos.Pedido;
import gestionElementosVisuales.FontFactory;
import gestionElementosVisuales.ImageFactory;
import gestionPantallas.RoundedBorder;
import gestionPaquetes.PedidoException;
import paneles.PanelResumenPedido;
import renderizadoTablaPedidos.HeaderRenderer;
import renderizadoTablaPedidos.ModeloColumnas;
import renderizadoTablaPedidos.ModeloTablaPedidos;
import renderizadoTablaPedidos.RendererTabla;

public class PanelUsuario extends JScrollPane {

	private static final long serialVersionUID = 1L;
	
	List<Integer> listaCantidades;
	JComboBox<String> fecha, destino, cantidad;
	String[] listaCantidad, listaFecha, listaDestino;
	
	List<Pedido> listaPedidos;
	List<Pedido> listaDisplay;
	
	ModeloTablaPedidos modeloTabla;
	JTable tabla;
	User user;

	public PanelUsuario(ModeloTablaPedidos modelo, User user) {
		super(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
		this.setBackground(Color.white);
		
		this.getVerticalScrollBar().setUnitIncrement(20);
		
		this.user = user;
		this.modeloTabla = modelo;
		this.listaPedidos = user.getListaPedidos();
		this.listaDisplay = listaPedidos;
		this.modeloTabla.setLista(listaDisplay);
		
		// Pasar los datos a arrays para poder a�adirlos a los combo boxes.
		listaDestino = this.transformToArray(listaPedidos.stream().map(Pedido::getDestino).distinct().collect(Collectors.toList()));
		listaFecha = this.transformToArray(listaPedidos.stream().map(Pedido::getDia).distinct().collect(Collectors.toList()));
		listaCantidades = listaPedidos.stream().map(Pedido::getNumElements).distinct().collect(Collectors.toList());
		
		if (listaCantidades.size() > 0) setCantidades();
		else {
			listaCantidad = new String[1];
			listaCantidad[0] = "Todo";
		}
		
		fecha = new JComboBox<>(listaFecha);
		destino = new JComboBox<>(listaDestino);
		cantidad = new JComboBox<>(listaCantidad);
		
		fecha.setSelectedIndex(0);
		destino.setSelectedIndex(0);
		cantidad.setSelectedIndex(0);
		
		this.setViewportView(crearPanelVentana());
		this.setBackground(Color.white);
	}

	private Component crearPanelVentana() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.white);
		
		panel.add(crearPanelElementosUsuario(), BorderLayout.NORTH);
		panel.add(crearPanelGestionTabla(), BorderLayout.CENTER);
		
		return panel;
	}

	private Component crearPanelElementosUsuario() {
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
		panel.setBackground(Color.white);
		
		Font font = FontFactory.createFont(FontFactory.BASE_FONT, 14);
		Dimension dimension = new Dimension(300, 40);
		
		JLabel label = new JLabel("Usuario: " + user.getName() + " " + user.getID());
		label.setBorder(BorderFactory.createCompoundBorder(new RoundedBorder(5), BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		label.setPreferredSize(dimension);
		label.setFont(font);
		
		panel.add(label);
		
		return panel;
	}

	private Component crearPanelFiltros() {
		JPanel panel = new JPanel(new GridLayout(1, 3));
		
		fecha.setFont(FontFactory.createFont(FontFactory.BASE_FONT, 16));
		destino.setFont(FontFactory.createFont(FontFactory.BASE_FONT, 16));
		cantidad.setFont(FontFactory.createFont(FontFactory.BASE_FONT, 16));
		
		panel.add(fecha);
		panel.add(destino);
		panel.add(cantidad);
		
		return panel;
	}
	
	private Component crearPanelGestionTabla() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
		panel.setBackground(Color.white);

		panel.add(crearPanelFiltros(), BorderLayout.NORTH);
		panel.add(crearPanelTabla(), BorderLayout.CENTER);
		panel.add(crearToolBar(), BorderLayout.EAST);
		
		return panel;
	}

	private Component crearToolBar() {
		JToolBar toolBar = new JToolBar(null, JToolBar.VERTICAL);
		toolBar.setBorder(BorderFactory.createLineBorder(Color.gray));
		
		JButton boton;
		
		// Ver un pedido especifico.
		boton = new JButton(ImageFactory.createImageIcon(ImageFactory.ICONO_VER));
		boton.addActionListener((l)->{
			try {
				PanelResumenPedido resumen = new PanelResumenPedido(listaDisplay.get(tabla.getSelectedRow()));
				resumen.setVisible(true);
			}
			catch (IndexOutOfBoundsException e) {
				JOptionPane.showMessageDialog(this, "Selecciona una fila", "Fila no encontrada", JOptionPane.WARNING_MESSAGE);
			}
		});
		boton.setToolTipText("Ver pedido."); // Aplicar una descripci�n
		toolBar.add(boton);
		
		// Filtrar aplicando los filtros seleccionados en los ComboBoxes.
		boton = new JButton(ImageFactory.createImageIcon(ImageFactory.ICONO_FILTRAR));
		boton.addActionListener((l)->{
			listaDisplay = listaPedidos;
			if (!((String)fecha.getSelectedItem()).equals("Todo")) { // Aplicar filtro fecha
				listaDisplay = listaDisplay.stream().filter((p)->p.getDia().equals(((String)fecha.getSelectedItem()))).collect(Collectors.toList());
			}
			if (!((String)destino.getSelectedItem()).equals("Todo")) { // Aplicar filtro destino
				listaDisplay = listaDisplay.stream().filter((p)->p.getDestino().equals(
						((String)destino.getSelectedItem()))).collect(Collectors.toList());
			}
			if (!((String)cantidad.getSelectedItem()).equals("Todo")) { // Aplicar filtro cantidad
				listaDisplay = listaDisplay.stream().filter((p)->{
					return p.getNumElements() >= Integer.parseInt(((String)cantidad.getSelectedItem()).substring(1));
				}).collect(Collectors.toList());
			}
			modeloTabla.setLista(listaDisplay); // Mostrar la nueva lista con los filtros aplicados
			this.repaint();
		});
		boton.setToolTipText("Filtrar pedidos."); // Aplicar una descripci�n
		toolBar.add(boton);
		
		// Cancelar los pedidos seleccionados.
		boton = new JButton(ImageFactory.createImageIcon(ImageFactory.ICONO_CANCELAR));
		boton.addActionListener((l)->{
			try {
				int[] seleccionados = tabla.getSelectedRows();
				for (int i = 0; i < seleccionados.length; i++) {
					Pedido p = user.getListaPedidos().get(seleccionados[i]);
					
					if (!p.getEstado().equals(Estado.RECOGIDO) || !p.getEstado().equals(Estado.ACEPTADO)) p.setEstado(Estado.DENEGADO);
					else throw new PedidoException("El pedido N�" + (i+1) + " no se puede cancelar. Pongase en contacto con el administrador"
							+ "para mas informaci�n.");
				}
			}
			catch (PedidoException e) {
				JOptionPane.showMessageDialog(this, e.getMessage(), "Error en pedido", JOptionPane.WARNING_MESSAGE);
			}
			catch (IndexOutOfBoundsException e) {
				JOptionPane.showMessageDialog(this, "Selecciona una fila", "Fila no encontrada", JOptionPane.WARNING_MESSAGE);
			}
			finally {
				this.repaint();
			}
		});
		boton.setToolTipText("Cancelar pedido."); // Aplicar una descripci�n
		toolBar.add(boton);
		
		boton = new JButton(ImageFactory.createImageIcon(ImageFactory.ICONO_CAMBIAR_CONTRASE�A));
		boton.addActionListener((l)->{
			
		});
		boton.setToolTipText("Cambiar contrase�a."); // Aplicar una descripci�n
		toolBar.add(boton);
		
		toolBar.add(Box.createVerticalGlue());
		
		boton = new JButton(ImageFactory.createImageIcon(ImageFactory.ICONO_AJUSTES));
		boton.addActionListener((l)->{
			
		});
		boton.setToolTipText("Cambiar idioma."); // Aplicar una descripci�n
		toolBar.add(boton);
		
		boton = new JButton(ImageFactory.createImageIcon(ImageFactory.ICONO_LIMPIAR));
		boton.addActionListener((l)->{
			Iterator<Pedido> it = listaPedidos.iterator();
			
			while(it.hasNext())
				if (it.next().getEstado().comprobar()) it.remove();
			
			modeloTabla.setLista(listaPedidos);
			listaDisplay = listaPedidos;
			
			this.repaint();
		});
		boton.setToolTipText("Quitar pedidos entregados y denegados de la lista."); // Aplicar una descripci�n
		toolBar.add(boton);
		
		return toolBar;
	}

	private Component crearPanelTabla() {
		JScrollPane panel = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		tabla = new JTable(modeloTabla, new ModeloColumnas(new RendererTabla()));
		tabla.getTableHeader().setDefaultRenderer(new HeaderRenderer(tabla));
		
		tabla.getTableHeader().addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) { // Detectar si el usuario a clickado en el titulo de la lista
				switch(tabla.columnAtPoint(e.getPoint())) { // Comprobar en que columna a clickado
				case 0:  // Ordenar alfabeticamente teniendo en cuenta el tipo
					listaDisplay = listaDisplay.stream().sorted(Comparator.comparing(Pedido::getUserString)).collect(Collectors.toList());
					break;
				case 1: // Ordenar por valor (de menor a mayor)
					listaDisplay = listaDisplay.stream().sorted(Comparator.comparing(Pedido::getNumElements)).collect(Collectors.toList());
					break;
				case 2: // Ordenar alfabeticamente teniendo en cuenta la procedencia
					listaDisplay = listaDisplay.stream().sorted(Comparator.comparing(Pedido::getDestino)).collect(Collectors.toList());
					break;
				case 3: // Ordenar por fecha
					listaDisplay = listaDisplay.stream().sorted(Comparator.comparing(Pedido::getDia)).collect(Collectors.toList());
					break;
				case 4:
					listaDisplay = listaDisplay.stream().sorted(Comparator.comparing(Pedido::getEstado)).collect(Collectors.toList());
					break;
				}
				modeloTabla.setLista(listaDisplay);
				PanelUsuario.this.repaint();
			}
		});
		panel.setViewportView(tabla);
		
		return panel;
	}
	
	// Transformar cualquier lista en un array de String teniendo en cuenta su toString()
	private <T> String[] transformToArray(List<T> elementos) {
		String[] array = new String[elementos.size() + 1];
		
		array[0] = "";
		for (int i = 0; i < elementos.size(); i++) {
			array[i + 1] = elementos.get(i).toString();
		}
		Arrays.sort(array);
		array[0] = "Todo";
		
		return array;
	}

	//  Determinar el filtro de cantidad dependiendo de la cantidad m�s alta
	private void setCantidades() {
		Integer max = listaCantidades.stream().reduce((a, b)->(a > b) ? a : b).get();
		Integer divisor = (max >= 900) ? 100 : (max >= 500) ? 50 : (max >= 200) ? 20 : (max >= 20) ? 10 : 1;
		Integer length = (max/divisor) + 1;
		
		listaCantidad = new String[length];
		listaCantidad[0] = "Todo";
		
		for (int i = 0; i < length - 1; i++)
			listaCantidad[i + 1] = "+" + String.valueOf((i + 1) * divisor);	
	}
}