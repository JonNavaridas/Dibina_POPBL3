import com.fazecast.jSerialComm.*;

public class Recibir  implements SerialPortDataListener {
	
	SerialPort serialport;
	String textoRecibido =null;
	
	  public Recibir (SerialPort serialport) {
				this.serialport = serialport;				
		}
	  
      
       @Override
   	public void serialEvent(SerialPortEvent event) {
   		
   		if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
          return;
          byte[] bufferDeLectura = new byte[1]; //preparamos el buffer de lectura
		  int numeroDeBytesLeidos = serialport.readBytes(bufferDeLectura, bufferDeLectura.length);
		  textoRecibido = new String(bufferDeLectura);
		  System.out.println("Se han recibido " + numeroDeBytesLeidos + " caracteres:");
		  System.out.println("[" + textoRecibido + "]");     
   	}
   	@Override
   	public int getListeningEvents() {
   		return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
   	}		 
  }
