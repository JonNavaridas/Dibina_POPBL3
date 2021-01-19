package pruebasSinConexion;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;
import com.fazecast.jSerialComm.*;

public class Enviar {
	SerialPort serialPort;
	Scanner teclado = new Scanner (System.in);
	
	public Enviar(SerialPort serialport) {
		this.serialPort=serialport;
	}
	
	public void accion() {
		String dato;
		
		while (!(dato = teclado.nextLine()).equals("fin")){
			escribir(dato.getBytes());
			System.out.println("Enviado:"+ dato);
		}
	}

    public void escribir  (byte[] dato)
    {
        serialPort.writeBytes(dato, dato.length);            
    }
}
