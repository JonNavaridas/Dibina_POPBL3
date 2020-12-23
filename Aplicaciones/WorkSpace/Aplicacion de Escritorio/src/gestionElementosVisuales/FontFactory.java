package gestionElementosVisuales;

import java.awt.Font;

public class FontFactory {

	public static final String BASE_FONT = "Times new roman + Trutype font";
	
	public static Font createFont(String fontType, int size) {
		switch(fontType) {
		case BASE_FONT: return new Font("Times new roman", Font.TRUETYPE_FONT, size);
		default: return null;
		}
	}
}