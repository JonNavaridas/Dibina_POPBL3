package pruebasSinConexion;
import com.fazecast.jSerialComm.*;

public class Recibir  implements SerialPortDataListener {
	
	SerialPort serialport;
	String textoRecibido =null;
	byte[] bufferDeMensaje;
	int puntero;
	
	public Recibir (SerialPort serialport) {
		this.serialport = serialport;	
		bufferDeMensaje= new byte[256];
		puntero=0;
	}
	  
      
	@Override
	public void serialEvent(SerialPortEvent event) {
	   		
   		if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
          return;
   		byte[] bufferDeLectura = new byte[1];//preparamos el buffer de lectura
		int numeroDeBytesLeidos = serialport.readBytes(bufferDeLectura, bufferDeLectura.length);
		textoRecibido = new String(bufferDeLectura);
		System.out.println("Se han recibido " + numeroDeBytesLeidos + " caracteres:");
		System.out.println("[" + textoRecibido + "]");   
		  
		switch(bufferDeLectura[1]& 0xFF) {
		case 129:
		case 130:
			bufferDeMensaje=null;
			puntero=0;
			bufferDeMensaje[puntero++]=bufferDeLectura[1];
			break;
		case 128:
			bufferDeMensaje[puntero++]=bufferDeLectura[1];
			for(int i=0;i<puntero;i++) {
				System.out.println(Integer.toBinaryString((bufferDeMensaje[i] & 0xFF) + 0x100).substring(1));
			}
			break;
		default:
			bufferDeMensaje[puntero++]=bufferDeLectura[1];
			break;
			
		}
	}
   	@Override
   	public int getListeningEvents() {
   		return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
   	}		 
  }
