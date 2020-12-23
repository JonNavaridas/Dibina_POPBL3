package paneles;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import diseñoTabla.HeaderRenderer;
import diseñoTabla.ModeloColumnas;
import diseñoTabla.ModeloTabla;
import diseñoTabla.RendererTabla;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;


public class PanelHistorial extends JPanel implements ActionListener, ChangeListener{
	
	private static final long serialVersionUID = 1L;
	final static int ALTO = 300;
	final static int ANCHO  = 500;
	
	UtilDateModel dateModel;
	JDatePanelImpl datePanel;
	JDatePickerImpl datePicker;
	
	Calendar fechaSeleccionada;
	Integer previousMonth;
	JLabel labelFecha;
	
	ModeloTabla modeloTabla;
	JTable tabla;
	
	public PanelHistorial(ModeloTabla controlador) {
		this.modeloTabla = controlador;
		inicializarCalendario();			
		JPanel panel = new JPanel();
		panel.add(datePicker);
		
		this.setLayout(new BorderLayout(0,40));
		this.add(panel, BorderLayout.NORTH);
		this.add(crearTabla(), BorderLayout.CENTER);
		this.add(crearLabelFecha(), BorderLayout.SOUTH);
		
		this.setPreferredSize(new Dimension(ANCHO, ALTO));
		this.setVisible(true);
	}

	private Component crearTabla() {
		JScrollPane panel = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
		tabla = new JTable(modeloTabla, new ModeloColumnas(new RendererTabla()));
		tabla.getTableHeader().setDefaultRenderer(new HeaderRenderer(tabla));
		
		panel.setViewportView(tabla);
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

	private Component crearLabelFecha() {
		JPanel panel = new JPanel();
		labelFecha = new JLabel("Ninguna fecha");
		panel.add(labelFecha);		
		return panel;
	}

	@Override
	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent arg0) {
		try {
			Date selectedDate = (Date) datePanel.getModel().getValue();
			Calendar selectedValue = Calendar.getInstance();
			selectedValue.setTime(selectedDate);
			
			fechaSeleccionada = selectedValue;
			labelFecha.setText(fechaSeleccionada.get(Calendar.YEAR)+" "+(fechaSeleccionada.get(Calendar.MONTH)+1)+" "+fechaSeleccionada.get(Calendar.DAY_OF_MONTH));
			if(previousMonth == null) previousMonth = new Integer(selectedValue.get(Calendar.MONTH));
			else previousMonth = selectedValue.get(Calendar.MONTH);
		
		}catch(Exception e) {
			System.out.println("Seleccione fecha");
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
