package lightcontrol.control.serial;

import jssc.SerialPort;
import jssc.SerialPortException;

	public class JSCCTest {
	   
	    public static void main2(String[] args) {
	        
	        SerialPort serialPort = new SerialPort("/dev/tty.SLAB_USBtoUART");
	        
	        try {
	            serialPort.openPort();//Open serial port
	            serialPort.setParams(SerialPort.BAUDRATE_9600, 
	                                 SerialPort.DATABITS_8,
	                                 SerialPort.STOPBITS_1,
	                                 SerialPort.PARITY_NONE);//Set params. Also you can set params by this string: serialPort.setParams(9600, 8, 1, 0);
	            serialPort.writeBytes("This is a test string".getBytes());//Write data to port
	            try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            String response = serialPort.readString();
	            System.out.println(response);
	            
	            serialPort.purgePort(SerialPort.PURGE_RXCLEAR | SerialPort.PURGE_TXCLEAR);
	            serialPort.closePort();//Close serial port
	        }
	        catch (SerialPortException ex) {
	            System.out.println(ex);
	        }
	    }
	}