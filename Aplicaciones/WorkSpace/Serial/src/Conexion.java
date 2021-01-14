import java.util.Scanner;

import com.fazecast.jSerialComm.SerialPort;

public class Conexion {
    // El nombre del puerto puede variar dependiendo de la entrada USB del PC
  //inicializamos y declaramos variables	
   SerialPort serialport;
   Recibir reader;
   Enviar writer;
   Scanner teclado;
	
  	public Conexion() {
		int puerto;
  		teclado = new Scanner(System.in);
		SerialPort puertosDisponibles[] = SerialPort.getCommPorts();
       
        if (puertosDisponibles.length == 0) {
            System.out.println("No hay ningún puerto COM disponible.");
        }
        else {
            System.out.println("Puertos disponibles:");
            for (int i = 0; i < puertosDisponibles.length; i++) {
                System.out.println("[" + i + "]  " + puertosDisponibles[i].getSystemPortName()
                                   + " : " + puertosDisponibles[i].getDescriptivePortName());
            }
            System.out.println("Que puerto desea utilizar?");
            puerto = teclado.nextInt();
                	try {
                		serialport=puertosDisponibles[puerto];               	
                		reader = new Recibir(serialport);
                		writer = new Enviar(serialport);
                		configurarPuerto();//configuramos el puerto que nos interesa              
                	}

                	catch (Exception e) {
                		System.out.println(e.getMessage());
        			} 
                }
            }
  	
     private void configurarPuerto() {
 		serialport.setComPortTimeouts(SerialPort.TIMEOUT_NONBLOCKING, 5000, 0);
		serialport.setBaudRate(115200);
		serialport.setNumDataBits(8);
		serialport.setParity(SerialPort.NO_PARITY);
		serialport.setNumStopBits(SerialPort.ONE_STOP_BIT);
		serialport.setFlowControl(SerialPort.FLOW_CONTROL_DISABLED);
		serialport.addDataListener(reader);
		serialport.openPort();				
	}
     
     public static void main(String[] args) {
    	 Conexion conexion = new Conexion();
		 conexion.writer.accion();
	  }
}
