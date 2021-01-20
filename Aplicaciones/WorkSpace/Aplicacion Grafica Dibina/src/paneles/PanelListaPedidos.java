package paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;

import comunicacionSockets.Cliente;
import elementos.Estado;
import elementos.Pedido;
import gestionElementosVisuales.FontFactory;
import gestionElementosVisuales.ImageFactory;
import renderizadoTablaPedidos.HeaderRenderer;
import renderizadoTablaPedidos.ModeloColumnas;
import renderizadoTablaPedidos.ModeloTablaPedidos;
import renderizadoTablaPedidos.RendererTabla;

public class PanelListaPedidos extends JScrollPane {

	private static final long serialVersionUID = 1L;
	
	List<Integer> listaCantidades;
	JComboBox<String> fecha, destino, cantidad;
	String[] listaCantidad, listaFecha, listaDestino, words, wordsResumen;
	
	List<Pedido> listaPedidos;
	List<Pedido> listaDisplay;
	ModeloTablaPedidos modeloTabla;
	JTable tabla;

	public PanelListaPedidos(ModeloTablaPedidos modelo, List<Pedido> listaPedidos, String[] words, String[] wordsResumen) {
		super(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));
		this.setBackground(Color.white);
		
		this.getVerticalScrollBar().setUnitIncrement(20);
		
		this.modeloTabla = modelo;
		this.listaPedidos = listaPedidos.stream().filter((p)->{
				return p.getEstado().equals(Estado.PROCESANDO) || p.getEstado().equals(Estado.ACEPTADO);
			}).collect(Collectors.toList());
		this.listaDisplay = this.listaPedidos;
		this.modeloTabla.setLista(listaDisplay);
		this.wordsResumen = wordsResumen;
		this.words = words;
		
		// Pasar los datos a arrays para poder añadirlos a los combo boxes.
		listaDestino = this.transformToArray(listaPedidos.stream().map(Pedido::getDestino).distinct().collect(Collectors.toList()));
		listaFecha = this.transformToArray(listaPedidos.stream().map(Pedido::getDia).distinct().collect(Collectors.toList()));
		listaCantidades = listaPedidos.stream().map(Pedido::getNumElements).distinct().collect(Collectors.toList());
		
		if (listaCantidades.size() > 0) setCantidades();
		else {
			listaCantidad = new String[1];
			listaCantidad[0] = words[0];
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
		
		panel.add(crearPanelDisplay(), BorderLayout.CENTER);
		panel.add(crearPanelBotones(), BorderLayout.SOUTH);
		
		return panel;
	}
	
	private Component crearToolBar() {
		JToolBar toolBar = new JToolBar();
		toolBar.setBorder(BorderFactory.createLineBorder(Color.gray));
		
		JButton boton;
		
		boton = new JButton(ImageFactory.createImageIcon(ImageFactory.ICONO_CONFIRMAR));
		boton.addActionListener((l)->{
			List<Pedido> pedidosRealizados = new ArrayList<>();
			try {
				if (tabla.getSelectedRows().length > 0 && JOptionPane.showConfirmDialog(this,
						"Una vez se acepte un pedido no podra denegarlo. ¿Esta seguro de que quiere continuar?") == JOptionPane.YES_OPTION) {
					
					int[] seleccionados = tabla.getSelectedRows();
					for (int i = 0; i < seleccionados.length; i++) {
						if (listaDisplay.get(seleccionados[i]).getEstado().equals(Estado.PROCESANDO)) {
							listaDisplay.get(seleccionados[i]).setEstado(Estado.aceptar());
							pedidosRealizados.add(listaDisplay.get(seleccionados[i]));
						}
					}
					this.repaint();
				}
			}
			catch (IndexOutOfBoundsException e) {
				JOptionPane.showMessageDialog(this, words[2], words[3], JOptionPane.WARNING_MESSAGE);
			}
			finally {
				String idPedidos = "";
				for (Pedido p : pedidosRealizados) idPedidos += String.valueOf(p.getId()) + "$";
				
				if (pedidosRealizados.size() > 0) {
					Cliente cliente = new Cliente(idPedidos.substring(0, idPedidos.length() - 1), "Aceptar pedido", this);
					cliente.start();
				}
			}
		});
		boton.setToolTipText(words[6]); // Aplicar una descripción
		toolBar.add(boton);
		
		boton = new JButton(ImageFactory.createImageIcon(ImageFactory.ICONO_DENEGAR));
		boton.addActionListener((l)->{
			List<Pedido> pedidosRealizados = new ArrayList<>();
			try {
				int[] seleccionados = tabla.getSelectedRows();
				for (int i = 0; i < seleccionados.length; i++) {
					if (listaDisplay.get(seleccionados[i]).getEstado().equals(Estado.PROCESANDO)) {
						listaDisplay.get(seleccionados[i]).setEstado(Estado.denegar());
						pedidosRealizados.add(listaDisplay.get(seleccionados[i]));
					}
				}
				this.repaint();
			}
			catch (IndexOutOfBoundsException e) {
				JOptionPane.showMessageDialog(this, words[2], words[3], JOptionPane.WARNING_MESSAGE);
			}
			finally {
				String idPedidos = "";
				for (Pedido p : pedidosRealizados) idPedidos += String.valueOf(p.getId()) + "$";
				
				if (pedidosRealizados.size() > 0) {
					Cliente cliente = new Cliente(idPedidos.substring(0, idPedidos.length() - 1), "Denegar pedido", this);
					cliente.start();
				}
			}
		});
		boton.setToolTipText(words[7]); // Aplicar una descripción
		toolBar.add(boton);
		
		boton = new JButton(ImageFactory.createImageIcon(ImageFactory.ICONO_ENTREGADO));
		boton.addActionListener((l)->{
			List<Pedido> pedidosRealizados = new ArrayList<>();
			try {
				int[] seleccionados = tabla.getSelectedRows();
				for (int i = 0; i < seleccionados.length; i++) {
					if (listaDisplay.get(seleccionados[i]).getEstado().equals(Estado.ACEPTADO)) {
						listaDisplay.get(seleccionados[i]).setEstado(Estado.marcarRecogido());
						pedidosRealizados.add(listaDisplay.get(seleccionados[i]));
					}
				}
				this.repaint();
			}
			catch (IndexOutOfBoundsException e) {
				JOptionPane.showMessageDialog(this, words[2], words[3], JOptionPane.WARNING_MESSAGE);
			}
			finally {
				String idPedidos = "";
				for (Pedido p : pedidosRealizados) idPedidos += String.valueOf(p.getId()) + "$";
				
				if (pedidosRealizados.size() > 0) {
					Cliente cliente = new Cliente(idPedidos.substring(0, idPedidos.length() - 1), "Entregar pedido", this);
					cliente.start();
				}
			}
		});
		boton.setToolTipText(words[4]); // Aplicar una descripción
		toolBar.add(boton);
		
		toolBar.add(Box.createHorizontalGlue());
		
		boton = new JButton(ImageFactory.createImageIcon(ImageFactory.ICONO_LIMPIAR));
		boton.addActionListener((l)->{
			Iterator<Pedido> it = listaPedidos.iterator();
			
			while(it.hasNext())
				if (it.next().getEstado().comprobar()) it.remove();
			
			modeloTabla.setLista(listaPedidos);
			listaDisplay = listaPedidos;
			
			this.repaint();
		});
		boton.setToolTipText(words[5]); // Aplicar una descripción
		toolBar.add(boton);
		
		return toolBar;
	}

	private Component crearPanelBotones() {
		JPanel panel = new JPanel(new FlowLayout());
		panel.setBackground(Color.white);
		
		JButton boton = new JButton(words[1]);
		boton.addActionListener((e)->{
			listaDisplay = listaPedidos;
			if (!((String)fecha.getSelectedItem()).equals(words[0])) { // Aplicar filtro fecha
				listaDisplay = listaDisplay.stream().filter((p)->p.getDia().equals(((String)fecha.getSelectedItem()))).collect(Collectors.toList());
			}
			if (!((String)destino.getSelectedItem()).equals(words[0])) { // Aplicar filtro destino
				listaDisplay = listaDisplay.stream().filter((p)->p.getDestino().equals(
						((String)destino.getSelectedItem()))).collect(Collectors.toList());
			}
			if (!((String)cantidad.getSelectedItem()).equals(words[0])) { // Aplicar filtro cantidad
				listaDisplay = listaDisplay.stream().filter((p)->{
					return p.getNumElements() >= Integer.parseInt(((String)cantidad.getSelectedItem()).substring(1));
				}).collect(Collectors.toList());
			}
			modeloTabla.setLista(listaDisplay); // Mostrar la nueva lista con los filtros aplicados
			this.repaint();
		});
		boton.setFont(FontFactory.createFont(FontFactory.BASE_FONT, 16));
		boton.setPreferredSize(new Dimension(150, 30));
		
		JPanel pBoton = new JPanel();
		pBoton.setBackground(Color.white);
		pBoton.add(boton);
		
		panel.add(pBoton);
		
		boton = new JButton(words[8]);
		boton.addActionListener((l)->{
			try {
				PanelResumenPedido resumen = new PanelResumenPedido(listaDisplay.get(tabla.getSelectedRow()), wordsResumen);
				resumen.setVisible(true);
			}
			catch (IndexOutOfBoundsException e) {
				JOptionPane.showMessageDialog(this, words[2], words[3], JOptionPane.WARNING_MESSAGE);
			}
		});
		boton.setFont(FontFactory.createFont(FontFactory.BASE_FONT, 16));
		boton.setPreferredSize(new Dimension(150, 30));
		
		pBoton = new JPanel();
		pBoton.setBackground(Color.white);
		pBoton.add(boton);
		
		panel.add(pBoton);
		
		return panel;
	}
	
	private Component crearPanelDisplay() {
		JPanel panel = new JPanel(new BorderLayout());

		panel.add(crearToolBar(), BorderLayout.NORTH);
		panel.add(crearPanelGestionTabla(), BorderLayout.CENTER);
		
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

		panel.add(crearPanelFiltros(), BorderLayout.NORTH);
		panel.add(crearPanelTabla(), BorderLayout.CENTER);
		
		return panel;
	}

	private Component crearPanelTabla() {
		JScrollPane panel = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		int language;
		switch(words[0]) {
		case "Guztia": language = 1; break;
		case "All": language  = 2; break;
		case "Todo": 
		default: language = 0; break;
		}
		
		tabla = new JTable(modeloTabla, new ModeloColumnas(new RendererTabla(), language));
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
					listaDisplay = listaDisplay.stream().sorted(Comparator.comparing(Pedido::getFecha)).collect(Collectors.toList());
					break;
				case 4:
					listaDisplay = listaDisplay.stream().sorted(Comparator.comparing(Pedido::getEstado)).collect(Collectors.toList());
					break;
				}
				modeloTabla.setLista(listaDisplay);
				PanelListaPedidos.this.repaint();
			}
		});
		panel.setViewportView(tabla);
		panel.getViewport().setBackground(Color.white);
		
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
		array[0] = words[0];
		
		return array;
	}

	//  Determinar el filtro de cantidad dependiendo de la cantidad más alta
	private void setCantidades() {
		Integer max = listaCantidades.stream().reduce((a, b)->(a > b) ? a : b).get();
		Integer divisor = (max >= 900) ? 100 : (max >= 500) ? 50 : (max >= 200) ? 20 : (max >= 20) ? 10 : 1;
		Integer length = (max/divisor) + 1;
		
		listaCantidad = new String[length];
		listaCantidad[0] = words[0];
		
		for (int i = 0; i < length - 1; i++)
			listaCantidad[i + 1] = "+" + String.valueOf((i + 1) * divisor);	
	}
}