package paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import elementos.Pedido;
import gestionElementosVisuales.FontFactory;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import renderizadoTablaPedidos.HeaderRenderer;
import renderizadoTablaPedidos.ModeloColumnas;
import renderizadoTablaPedidos.ModeloTablaPedidos;
import renderizadoTablaPedidos.RendererTabla;

public class PanelHistorial extends JScrollPane implements ActionListener, ChangeListener {
	
	private static final long serialVersionUID = 1L;
	final static int ALTO = 300;
	final static int ANCHO  = 500;
	
	UtilDateModel dateModel;
	JDatePanelImpl datePanel;
	JDatePickerImpl datePicker;
	
	Calendar fechaSeleccionada;
	Integer previousMonth;
	
	String[] words, wordsResumen;
	ModeloTablaPedidos modeloTabla;
	JTable tabla;
	
	List<Pedido> listaDisplay;
	List<Pedido> listaPedidos;
	
	public PanelHistorial(List<Pedido> listaPedidos, String[] words, String[] wordsResumen) {
		this.modeloTabla = new ModeloTablaPedidos(listaPedidos);
		this.listaDisplay = listaPedidos;
		this.listaPedidos = listaPedidos;
		
		this.words = words;
		this.wordsResumen = wordsResumen;
		
		this.setViewportView(crearPanel());
		this.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
		this.setBackground(Color.white);
	}
	
	private Component crearPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBackground(Color.white);
		
		panel.add(crearCalendario(), BorderLayout.NORTH);
		panel.add(crearTabla(), BorderLayout.CENTER);
		panel.add(crearBotones(), BorderLayout.SOUTH);
		
		return panel;
	}

	private Component crearCalendario() {
		previousMonth = null;
		fechaSeleccionada = Calendar.getInstance();
		
		dateModel = new UtilDateModel();
		dateModel.addChangeListener(this);
		datePanel = new JDatePanelImpl(dateModel);
		datePanel.addActionListener(this);
		datePicker = new JDatePickerImpl(datePanel);
		datePicker.setTextEditable(true);
		
		JPanel panel = new JPanel();
		panel.add(datePicker);
		panel.setBackground(Color.white);
		panel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
		return panel;
	}

	private Component crearBotones() {
		JPanel panel = new JPanel();
		JButton boton = new JButton(words[0]);
		boton.addActionListener((l)->{
			try {
				PanelResumenPedido resumen = new PanelResumenPedido(listaDisplay.get(tabla.getSelectedRow()), wordsResumen);
				resumen.setVisible(true);
			}
			catch (IndexOutOfBoundsException e) {
				JOptionPane.showMessageDialog(this, words[1], words[2], JOptionPane.WARNING_MESSAGE);
			}
		});
		boton.setFont(FontFactory.createFont(FontFactory.BASE_FONT, 16));
		boton.setPreferredSize(new Dimension(150, 30));
		panel.add(boton);
		panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 20, 0));
		panel.setBackground(Color.white);
		return panel;
	}

	private Component crearTabla() {
		JScrollPane panel = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		int language;
		switch(words[0]) {
		case "Eskaria ikusi": language = 1; break;
		case "See order": language  = 2; break;
		case "Ver Pedido": 
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
				PanelHistorial.this.repaint();
			}
		});
		panel.setViewportView(tabla);
		panel.getViewport().setBackground(Color.white);
		return panel;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			Date selectedDate = (Date) datePanel.getModel().getValue();
			Calendar selectedValue = Calendar.getInstance();
			selectedValue.setTime(selectedDate);
			if(previousMonth == null) previousMonth = new Integer(selectedValue.get(Calendar.MONTH));
			else previousMonth = selectedValue.get(Calendar.MONTH);
			
			listaDisplay = new ArrayList<>();
			for(Pedido pedido: listaPedidos){
				Calendar cal = Calendar.getInstance();
				cal.setTime(pedido.getFecha());
				if(cal.get(Calendar.YEAR) == selectedValue.get(Calendar.YEAR) && 
						cal.get(Calendar.MONTH) == selectedValue.get(Calendar.MONTH) && 
						cal.get(Calendar.DAY_OF_MONTH) == selectedValue.get(Calendar.DAY_OF_MONTH)) 
					listaDisplay.add(pedido);
			};
			modeloTabla.setLista(listaDisplay); // Mostrar la nueva lista con filtro de fecha
			this.repaint();
		
		}catch(Exception e) {
			modeloTabla.setLista(listaPedidos);
			this.repaint();
		}
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		if(previousMonth != null && dateModel.getMonth() != previousMonth) {
			previousMonth = null;
			dateModel.setSelected(false);
		}
	}
}