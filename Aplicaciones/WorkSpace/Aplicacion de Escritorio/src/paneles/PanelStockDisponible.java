package paneles;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

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

	
	private List<Producto> listaProductos;
	ControladorPedidos controlador;
	
	public PanelStockDisponible(ControladorPedidos controlador, List<Producto> listaProductos) {
		this.controlador = controlador;
		this.listaProductos = listaProductos;
		
		dataset = createDataset();
		chart = createChart(dataset, true, "Tipo y Procedencia");
		chartPanel = new ChartPanel(chart);
		chartPanel.setPreferredSize(new java.awt.Dimension(ALTO, ANCHO));
		chartPanel.setMaximumDrawWidth( 2000 );
		
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
		Map<Procedencia, List<Producto>> mapaProcedencia;
		mapaProcedencia = listaProductos.stream().collect(Collectors.groupingBy(Producto::getProcedencia));
		mapaProcedencia.entrySet().stream().forEach(p->{
			dataset.setValue(p.getValue().stream().mapToInt(Producto::getCantidad).sum(), "PROCEDENCIA", p.getKey().getNombre());
		});

        return dataset;
	}
	
	private CategoryDataset createDatasetTipo() {
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		Map<Tipo, List<Producto>> mapaTipo;
		mapaTipo = listaProductos.stream().collect(Collectors.groupingBy(Producto::getTipo));
		mapaTipo.entrySet().stream().forEach(p->{
			dataset.setValue(p.getValue().stream().mapToInt(Producto::getCantidad).sum(), "CANTIDAD", p.getKey().getNombre());
		});

        return dataset;
	}

	private CategoryDataset createDataset() {
		Set<String> procedencias = new TreeSet<>(listaProductos.stream().map(Producto::getProcedencia).distinct().collect(Collectors.toList())
				.stream().map(Procedencia::getNombre).distinct().collect(Collectors.toSet()));
		Set<String> tipos =  new TreeSet<>(listaProductos.stream().map(Producto::getTipo).distinct().collect(Collectors.toList())
				.stream().map(Tipo::getNombre).distinct().collect(Collectors.toSet()));
		
		double[][] data = new double[procedencias.size()][tipos.size()];
		
		Map<String, List<Producto>> mapaHash = listaProductos.stream().collect(Collectors.groupingBy(Producto::getNombreTipo));
		Map<String, List<Producto>> mapaTipo = convertToTreeMap(mapaHash);
		int i=0,j=0;
		for (Entry<String, List<Producto>> entry : mapaTipo.entrySet()) {
			List<Producto> productos = entry.getValue();
			for(String proc : procedencias.stream().collect(Collectors.toList())) {
				Producto pro = productos.stream().filter(x->proc.equals(x.getProcedencia().getNombre())).findFirst().orElse(null);
				if(pro != null) data[i][j] = pro.getCantidad();
				else  data[i][j] = 0;
				i++;
			}
			j++;
			i=0;
		}
		  return DatasetUtilities.createCategoryDataset(procedencias.stream().toArray(String[]::new), tipos.stream().toArray(String[]::new), data);
	  }
	
	public static <K, V> Map<K, V> convertToTreeMap(Map<K, V> hashMap){ 
        Map<K, V> treeMap = hashMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,Map.Entry::getValue,(oldValue,newValue)-> newValue,TreeMap::new)); 
  
        return treeMap; 
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
		else if(ambos.isSelected()){
			dataset = createDataset();
			chart = createChart(dataset, true, "Tipo y procedencia"); 
		}
		else return;
		chartPanel.setChart(chart);
		setMinimumTickUnits();
		crearRenderer();
  	}
}