package paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.ui.TextAnchor;

import elementos.Procedencia;
import elementos.Producto;
import elementos.Tipo;
import gestionPaquetes.ControladorPedidos;


public class PanelStockDisponible extends JPanel implements ItemListener{

	private static final long serialVersionUID = 1L;
	final static int ALTO = 1000;
	final static int ANCHO  = 270;
	
	private CategoryDataset dataset;
	private JFreeChart chart;
	private ChartPanel chartPanel;
	
	private JRadioButton tipo, procedencia, ambos;
	
	String[] paises = {"RUSIA", "USA", "CHINA","ITALIA","JAPON","FRANCIA","AUSTRIA","FINLANDIA","ALEMANIA",
			  "CANADA","SUIZA","INGLATERRA","SUECIA","1", "2","3","4","5","6","7","8","9","10",
			  "11", "12","13","14","15","16","17","18","19","20"};
	String[] tipos = {"Mascarillas", "Vacunas", "Guantes", "Hidrogeles"};
	//ESTOS DOS DEBEN BORRARSE Y CAMBIAR EL THIS. AL CREAR LOS GRAFICOS
	
	private List<Procedencia> listaProcedencias;
	private List<Producto> listaProductos;
	private List<Tipo> listaTipos;
	ControladorPedidos controlador;
	
	public PanelStockDisponible(ControladorPedidos controlador, List<Producto> listaProductos) {
		this.controlador = controlador;
		this.listaProductos = listaProductos;
		this.listaProcedencias = controlador.getListaProcedencias();
		this.listaTipos = controlador.getListaTipos();
		
		dataset = createDataset();
		chart = createChart(dataset, true, "Tipo y Procedencia");
		chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(ALTO, ANCHO));
		chartPanel.setMaximumDrawWidth( 3500 );
		
		setMinimumTickUnits();
		crearRenderer();	  

		this.setLayout(new BorderLayout(0,20));
		this.add(chartPanel, BorderLayout.CENTER);
		this.add(crearBotones(), BorderLayout.SOUTH);
	  }
	
	  public void setMinimumTickUnits() {
		ValueAxis axis = chart.getCategoryPlot().getRangeAxis();
		axis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());			
	  }	
	
	  private void crearRenderer() {
		  CategoryItemRenderer renderer = ((CategoryPlot)chart.getPlot()).getRenderer();
	      renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
	      renderer.setBaseItemLabelsVisible(true);
	      ItemLabelPosition position = new ItemLabelPosition(ItemLabelAnchor.CENTER, 
	                TextAnchor.TOP_CENTER);
	      renderer.setBasePositiveItemLabelPosition(position);
	}


	private Component crearBotones() {
		JPanel filtroGrafico = new JPanel();
		
		ButtonGroup tipos = new ButtonGroup();
		tipo = new JRadioButton ("Tipo");
		tipo.setSelected(false);
		tipo.addItemListener(this);
		
		procedencia = new JRadioButton ("Procedencia");
		procedencia.setSelected(false);
		procedencia.addItemListener (this);
		
		ambos = new JRadioButton ("Ambos");
		ambos.setSelected(true);
		ambos.addItemListener (this);
		
		tipos.add(ambos);
		tipos.add(tipo);
		tipos.add(procedencia);
		filtroGrafico.add(ambos);
		filtroGrafico.add(tipo);
		filtroGrafico.add(procedencia);
		
		return filtroGrafico;
	}

	private CategoryDataset createDatasetProcedencia() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		//String[] paises = (String[]) listaProcedencias.toArray();
    	dataset.setValue(45, "PROCEDENCIA", this.paises[0]);
        dataset.setValue(80, "PROCEDENCIA", this.paises[1]);
        dataset.setValue(150, "PROCEDENCIA", this.paises[2]);
        dataset.setValue(400, "PROCEDENCIA", this.paises[3]);

        return dataset;
	}
	
	private CategoryDataset createDatasetTipo() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		//String[] tipos = (String[]) listaTipos.toArray();
    	dataset.setValue(45, "CANTIDAD", this.tipos[0]);
        dataset.setValue(80, "CANTIDAD", this.tipos[1]);
        dataset.setValue(150, "CANTIDAD", this.tipos[2]);
        dataset.setValue(400, "CANTIDAD", this.tipos[3]);

        return dataset;
	}

	private CategoryDataset createDataset() {
		//String[] paises = (String[]) listaProcedencias.toArray();
		//String[] tipos = (String[]) listaTipos.toArray();
		  double[][] data = new double[][]{
		  {0, 0, 0, 400},
		  {0, 0, 0, 210},
		  {210, 300, 320, 210},
		  {210, 210, 300, 210},
		  {200, 304, 201, 210},
		  {210, 300, 320, 210},
		  {210, 210, 300, 210},
		  {200, 304, 201, 210},
		  {210, 300, 320, 210},
		  {210, 210, 300, 210},
		  {200, 304, 201, 210},
		  {210, 300, 320, 210},
		  {210, 210, 300, 210},
		  {210, 210, 300, 210},
		  {200, 304, 201, 210},
		  {210, 300, 320, 210},
		  {210, 210, 300, 210},
		  {200, 304, 201, 210},
		  {210, 300, 320, 210},
		  {210, 210, 300, 210},
		  {200, 304, 201, 210},
		  {210, 300, 320, 210},
		  {210, 300, 320, 210},
		  {210, 300, 320, 210},
		  {210, 300, 320, 210},
		  {210, 300, 320, 210},
		  {210, 300, 320, 210},
		  {210, 300, 320, 210},
		  {210, 300, 320, 210},
		  {210, 300, 320, 210},
		  {210, 300, 320, 210},
		  {210, 300, 320, 210},
		  {210, 300, 320, 210},
		  };
		  return DatasetUtilities.createCategoryDataset(
				  this.paises, this.tipos, data);
	  }
	
	private JFreeChart createChart(final CategoryDataset dataset, boolean stacked, String filtro) {
		  final JFreeChart chart;
		  if(stacked) {
			  chart = ChartFactory.createStackedBarChart3D("Stock disponible", filtro, "Cantidad",
					  								dataset, PlotOrientation.HORIZONTAL, true, true, false);
			  generarColores(chart);
		  }
		  else {
			  chart = ChartFactory.createBarChart3D("Stock disponible", filtro, "Cantidad",
					  								dataset, PlotOrientation.HORIZONTAL, true, true, false);
		  }
		  chart.setBackgroundPaint(new Color(249, 231, 236));
		  return chart;
	  }
	
	private void generarColores(JFreeChart chart) {
			
		  CategoryPlot plot = chart.getCategoryPlot();
		  int filas = dataset.getRowCount();
		  int r = 0,g = 0,b = 255;
		  for(int i = 0; i < filas; i++) {
			  plot.getRenderer().setSeriesPaint(i, new Color(r, g, b));
			  if(b == 255 && r < 255 && g == 0) {
				  r += 40;
				  if(r>255)r = 255;
			  }
			  else if(r == 255 && b > 0) {
				  b -= 40;
				  if(b<0)b= 0;
			  }
			  else if(r == 255 && g < 255) {
				  g += 40;
				  if(g>255)g = 255;
			  }
			  else if(r > 0 && g == 255) {
				  r -= 40;
				  if(r<0)r= 0;
			  }
			  else if(g == 255 && b < 255) {
				  b += 40;
				  if(b>255)b = 255;
			  }
			  else if(b == 255 && g > 0) {
				  g -= 40;
				  if(g<0)g= 0;
			  }
		  }
	}

	private void setChartColor() {
		CategoryPlot plot = chart.getCategoryPlot();
		BarRenderer renderer = (BarRenderer) plot.getRenderer();
		
		Color color = new Color(223, 20, 20 , 255);
		renderer.setSeriesPaint(0, color);		
	}
	
	@Override
	public void itemStateChanged(ItemEvent arg0) {
		CategoryDataset dataset;
		if(tipo.isSelected()) {
			dataset = createDatasetTipo();
			chart = createChart(dataset, false, "Tipo"); 
			chart.removeLegend();
			setChartColor();
		}
		else if(procedencia.isSelected()){
			dataset = createDatasetProcedencia();
			chart = createChart(dataset, false, "Procedencia"); 
			chart.removeLegend();
			setChartColor();
		}
		else {
			dataset = createDataset();
			chart = createChart(dataset, true, "Tipo y procedencia"); 
		}			
		chartPanel.setChart(chart);
		setMinimumTickUnits();
		crearRenderer();
		
		this.repaint();
  	}
}