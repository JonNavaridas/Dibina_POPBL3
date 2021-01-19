import java.util.ArrayList;
import java.util.List;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

public class Recibir  implements SerialPortDataListener {
	
	SerialPort serialport;
	String textoRecibido =null;
	List<Byte> bufferDeMensaje;
	Comprobador comprobador;
	boolean bien;
	public Recibir (SerialPort serialport) {
		this.serialport = serialport;	
		bufferDeMensaje= new ArrayList<>();
	}
	  
      
	@Override
	public void serialEvent(SerialPortEvent event) {
	   		
   		if (event.getEventType() != SerialPort.LISTENING_EVENT_DATA_AVAILABLE)
          return;
   		
   		byte[] bufferDeEnvio = new byte[1];
   		byte[] bufferDeLectura = new byte[1];//preparamos el buffer de lectura
		int numeroDeBytesLeidos = serialport.readBytes(bufferDeLectura, bufferDeLectura.length);
		System.out.println(Integer.toBinaryString((bufferDeLectura[0] & 0xFF) + 0x100).substring(1));   
		  
		switch(Integer.toBinaryString((bufferDeLectura[0] & 0xFF) + 0x100).substring(1)) {
		case "10000001":
		case "10000010":
			bufferDeMensaje= new ArrayList<>();
			bufferDeMensaje.add(bufferDeLectura[0]);
			break;
		case "11111111":
			bufferDeMensaje.add(bufferDeLectura[0]);
			comprobador=new Comprobador();
			
			if(comprobador.procesarMensaje(bufferDeMensaje.toArray(new Byte[0]))) {
				bufferDeEnvio[0]=1;
				System.out.println("bien");
			}else {
				bufferDeEnvio[0]=0;
				System.out.println("nope");
			}
			serialport.writeBytes(bufferDeEnvio,1);
			break;
		default:
			bufferDeMensaje.add(bufferDeLectura[0]);
			break;
			
		}
	}
   	@Override
   	public int getListeningEvents() {
   		return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
   	}		 
  }