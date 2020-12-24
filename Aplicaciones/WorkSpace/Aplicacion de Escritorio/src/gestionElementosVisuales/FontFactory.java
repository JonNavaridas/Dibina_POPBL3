package gestionElementosVisuales;

import java.awt.Font;

public class FontFactory {

	public static final String BASE_FONT = "Times new roman + Trutype font";
	public static final String TITLE_FONT = "Times new roman + Italic";
	
	private static String times = "Times new roman";
	
	// Generar una fuente determinada por fontType, el cual será de los tipos proporcionados por esta misma clase.
	public static Font createFont(String fontType, int size) {
		switch(fontType) {
		case BASE_FONT: return new Font(times, Font.TRUETYPE_FONT, size);
		case TITLE_FONT :return new Font(times, Font.ITALIC, size);
		default: return null;
		}
	}
}