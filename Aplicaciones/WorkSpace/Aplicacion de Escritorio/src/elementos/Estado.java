package elementos;

public enum Estado {

	PROCESANDO, ACEPTADO, DENEGADO, RECOGIDO;
	
	public static Estado aceptar() {
		return ACEPTADO;
	}
	
	public static Estado denegar() {
		return DENEGADO;
	}
	
	public static Estado marcarRecogido() {
		return RECOGIDO;
	}

	public boolean comprobar() {
		if (this.equals(DENEGADO) || this.equals(RECOGIDO)) return true;
		return false;
	}
}