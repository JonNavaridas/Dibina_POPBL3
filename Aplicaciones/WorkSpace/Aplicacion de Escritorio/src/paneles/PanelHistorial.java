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

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import elementos.Producto;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;
import renderizadoTablaTipos.HeaderRenderer;
import renderizadoTablaTipos.ModeloColumnas;
import renderizadoTablaTipos.ModeloTablaTipos;
import renderizadoTablaTipos.RendererTabla;


public class PanelHistorial extends JPanel implements ActionListener, ChangeListener{
	
	private static final long serialVersionUID = 1L;
	final static int ALTO = 300;
	final static int ANCHO  = 500;
	
	UtilDateModel dateModel;
	JDatePanelImpl datePanel;
	JDatePickerImpl datePicker;
	
	Calendar fechaSeleccionada;
	Integer previousMonth;
	
	ModeloTablaTipos modeloTabla;
	JTable tabla;
	List<Producto> listaDisplay;
	List<Producto> listaProductos;
	
	public PanelHistorial(List<Producto> listaProductos) {
		this.modeloTabla = new ModeloTablaTipos(listaProductos);
		this.listaDisplay = listaProductos;
		this.listaProductos = listaProductos;
		inicializarCalendario();			
		JPanel panel = new JPanel();
		panel.add(datePicker);
		
		this.setLayout(new BorderLayout(0,40));
		this.add(panel, BorderLayout.NORTH);
		this.add(crearTabla(), BorderLayout.CENTER);
		
		this.setPreferredSize(new Dimension(ANCHO, ALTO));
		this.setVisible(true);
	}

	private Component crearTabla() {
		JScrollPane panel = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		tabla = new JTable(modeloTabla, new ModeloColumnas(new RendererTabla()));
		tabla.getTableHeader().setDefaultRenderer(new HeaderRenderer(tabla));
		
		tabla.getTableHeader().addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) { // Detectar si el usuario a clickado en el titulo de la lista
				switch(tabla.columnAtPoint(e.getPoint())) { // Comprobar en que columna a clickado
				case 0:  // Ordenar alfabeticamente teniendo en cuenta el tipo
					listaDisplay = listaProductos.stream().sorted(Comparator.comparing(Producto::getNombreTipo)).collect(Collectors.toList());
					break;
				case 1: // Ordenar por valor (de menor a mayor)
					listaDisplay = listaProductos.stream().sorted(Comparator.comparing(Producto::getCantidad)).collect(Collectors.toList());
					break;
				case 2: // Ordenar alfabeticamente teniendo en cuenta la procedencia
					listaDisplay = listaProductos.stream().sorted(Comparator.comparing(Producto::getNombreProcedencia)).collect(Collectors.toList());
					break;
				case 3: // Ordenar por fecha
					listaDisplay = listaProductos.stream().sorted(Comparator.comparing(Producto::getFecha).reversed()).collect(Collectors.toList());
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

	private void inicializarCalendario() {
		previousMonth = null;
		fechaSeleccionada = Calendar.getInstance();
		
		dateModel = new UtilDateModel();
		dateModel.addChangeListener(this);
		datePanel = new JDatePanelImpl(dateModel);
		datePanel.addActionListener(this);
		datePicker = new JDatePickerImpl(datePanel);
		datePicker.setTextEditable(true);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			Date selectedDate = (Date) datePanel.getModel().getValue();
			Calendar selectedValue = Calendar.getInstance();
			selectedValue.setTime(selectedDate);
			if(previousMonth == null) previousMonth = new Integer(selectedValue.get(Calendar.MONTH));
			else previousMonth = selectedValue.get(Calendar.MONTH);
			
			listaDisplay = new ArrayList<>();
			for(Producto producto: listaProductos){
				Calendar cal = Calendar.getInstance();
				cal.setTime(producto.getFecha());
				if(cal.get(Calendar.YEAR) == selectedValue.get(Calendar.YEAR) && 
						cal.get(Calendar.MONTH) == selectedValue.get(Calendar.MONTH) && 
						cal.get(Calendar.DAY_OF_MONTH) == selectedValue.get(Calendar.DAY_OF_MONTH)) 
					listaDisplay.add(producto);
			};
			modeloTabla.setLista(listaDisplay); // Mostrar la nueva lista con filtro de fecha
			this.repaint();
		
		}catch(Exception e) {
			modeloTabla.setLista(listaProductos);
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
