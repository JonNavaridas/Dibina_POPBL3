package impresora;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

import elementos.Pedido;

public class Imprimir implements Printable {

	Pedido pedido; // El pedido que queremos imprimir.
	
	public Imprimir(Pedido p) {
		this.pedido = p;
	}
	
	@Override
	public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
		
		// Solo vamos a imprimir una pagina, por lo que solo imprimiremos cuando la página sea 0.
		// Las páginas empiezan siempre desde 0.
		if (page > 0) return NO_SUCH_PAGE;
		
		Graphics2D g2d = (Graphics2D) g;
		// El (0,0) suele quedar fuera de la zona donde podemos imprimir, por lo tanto tenemos que
		// determinar cuales son las zonas donde podemos dibujar.
		g2d.translate(pf.getImageableX(), pf.getImageableY());
		
		// Renderizado de la página.
		//g.drawString(pedido.setDisplayElements(), 100, 100);
		pedido.drawPedido(g2d, pf.getWidth(), pf.getHeight());
		
		return PAGE_EXISTS;
	}

}