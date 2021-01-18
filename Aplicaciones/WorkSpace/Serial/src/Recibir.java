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
   		byte[] bufferDeEnvio = new byte[1];
   		bufferDeEnvio[0]=0;
   		byte[] bufferDeLectura = new byte[1];//preparamos el buffer de lectura
		int numeroDeBytesLeidos = serialport.readBytes(bufferDeLectura, bufferDeLectura.length);
		System.out.println(Integer.toBinaryString((bufferDeLectura[0] & 0xFF) + 0x100).substring(1));   
		  
		switch(Integer.toBinaryString((bufferDeLectura[0] & 0xFF) + 0x100).substring(1)) {
		case "10000001":
		case "10000010":
			bufferDeMensaje=null;
			bufferDeMensaje= new byte[256];
			puntero=0;
			bufferDeMensaje[puntero++]=bufferDeLectura[0];
			break;
		case "11111111":
			bufferDeMensaje[puntero++]=bufferDeLectura[0];
			for(int i=0;i<puntero;i++) {
				System.out.println(Integer.toBinaryString((bufferDeMensaje[i] & 0xFF) + 0x100).substring(1));
			}
			serialport.writeBytes(bufferDeEnvio,1);
			break;
		default:
			bufferDeMensaje[puntero++]=bufferDeLectura[0];
			break;
			
		}
	}
   	@Override
   	public int getListeningEvents() {
   		return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
   	}		 
  }