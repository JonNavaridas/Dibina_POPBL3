package elementos;

public enum Permisos {

	BASICO('B'), AVANZADO('A'), TOTAL('T');
	
	char inicial;
	
	Permisos(char inicial) {
		this.inicial = inicial;
	}
	
	public static Permisos getPermiso(String nombre) {
		switch(nombre) {
		case "B":
		case "basico": return BASICO;
		case "A":
		case "avanzado": return AVANZADO;
		case "T":
		case "total": return TOTAL;
		default: return null;
		}
	}
	
	public char getInicial() {
		return this.inicial;
	}
}