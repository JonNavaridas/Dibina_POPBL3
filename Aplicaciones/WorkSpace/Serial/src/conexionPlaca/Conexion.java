package conexionPlaca;
import java.util.Scanner;

import com.fazecast.jSerialComm.SerialPort;

public class Conexion {
	
    //El nombre del puerto puede variar dependiendo de la entrada USB del PC inicializamos y declaramos variables	
	private static int RATIO_SERIAL = 9800, RATIO_BLUETOOTH = 115200;
	
    SerialPort serialport;
    ComunicacionPlaca reader;
    
    Scanner teclado;
    Integer puerto;
  	
  	public void conectar() {
		SerialPort puertosDisponibles[] = SerialPort.getCommPorts();
       
        if (puertosDisponibles.length == 0) {
            System.out.println("No hay ningún puerto COM disponible.");
        }
        else {
            getPuerto(puertosDisponibles);
            if (puerto < puertosDisponibles.length && puerto >= 0) {
	        	try {
	        		serialport = puertosDisponibles[puerto];               	
	        		reader = new ComunicacionPlaca(serialport);
	        		System.out.println("Tipo de conexión:\n-[0] Serial (9800 byte/s)\n-[1] Bluetooth (115200 byte/s)");
	        		configurarPuerto(teclado.nextInt()); // Configuramos el puerto que nos interesa 
	        	}
	        	catch (Exception e) {
	        		System.out.println(e.getMessage());
				} 
            }
        }
  	}
  	
  	public void getPuerto(SerialPort[] puertosDisponibles) {
  		teclado = new Scanner(System.in);
  		
  		System.out.println("Puertos disponibles:");
        for (int i = 0; i < puertosDisponibles.length; i++) {
            System.out.println("[" + i + "]  " + puertosDisponibles[i].getSystemPortName()
                               + " : " + puertosDisponibles[i].getDescriptivePortName());
        }
        
        System.out.println("Que puerto desea utilizar?");
        puerto = teclado.nextInt();
  	}
  	
    private void configurarPuerto(int ratio) {
 		serialport.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 5000, 0);
		serialport.setBaudRate((ratio==1)?RATIO_BLUETOOTH:RATIO_SERIAL);
		serialport.setNumDataBits(8);
		serialport.setParity(SerialPort.NO_PARITY);
		serialport.setNumStopBits(SerialPort.ONE_STOP_BIT);
		serialport.setFlowControl(SerialPort.FLOW_CONTROL_DISABLED);
		serialport.addDataListener(reader);
		serialport.openPort();
		if(ratio == 1) sincronizarBluetooth();
	}
     
    private void sincronizarBluetooth() {
    	byte[] sincro = "$$$".getBytes();
    	System.out.println("Entrando a modo comandos...");
    	
    	serialport.writeBytes(sincro, sincro.length);	 
    	try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	
    	sincro = "K,1".getBytes();
    	System.out.println("Reiniciando conectividad...");
    	
    	serialport.writeBytes(sincro, sincro.length);
    	sincro[0] = 13;	// Tecla enter
    	serialport.writeBytes(sincro, 1);
    	try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	
    	System.out.print("Eliga la MAC del dispositivo [default D88039F898FE]: ");
    	teclado.nextLine();
    	String mac = teclado.nextLine();
    	
    	if(mac.length() == 0) sincro = "C,0,D88039F898FE".getBytes();
    	else sincro = ("C,0,"+mac).getBytes();
    	
    	System.out.println("Sincronizando bluetooth");
    	serialport.writeBytes(sincro, sincro.length);
    	
    	sincro[0] = 13;		//Tecla enter
    	serialport.writeBytes(sincro, 1);
    	System.out.println("Sincronización completada");
    }

	public static void main(String[] args) {
    	 Conexion conexion = new Conexion();
    	 conexion.conectar();
	}
}
