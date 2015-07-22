package lightcontrol.control.serial;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

	public class JSCCTest {
	   
	    public static void main2(String[] args) {
	        
	        SerialPort serialPort = new SerialPort("/dev/tty.SLAB_USBtoUART");
	        
	        try {
	            serialPort.openPort();//Open serial port
	            serialPort.setParams(SerialPort.BAUDRATE_9600, 
	                                 SerialPort.DATABITS_8,
	                                 SerialPort.STOPBITS_1,
	                                 SerialPort.PARITY_NONE);
	            //Set params. Also you can set params by this string: serialPort.setParams(9600, 8, 1, 0);
	            serialPort.addEventListener(new SerialPortEventListener() {
					@Override
					public void serialEvent(SerialPortEvent e) {
						// TODO Auto-generated method stub
						//System.out.println("Event received!");
						try {
							//System.out.println(Byte.valueOf(serialPort.readBytes(1)[0]).toString());
							byte received_byte = serialPort.readBytes(1)[0];
							if (received_byte == 115){
								System.out.println("ACK Received!");
							} else if (received_byte == 116){
								System.out.println("NAK Received!");
							}
							
						} catch (SerialPortException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						/*
						try {
							if (serialPort.readBytes(1).equals("s".getBytes())) {
								System.out.println("S Received!");
							}
						} catch (SerialPortException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}*/
						
					}
	            });
	            serialPort.writeBytes("s".getBytes());
	            serialPort.writeBytes("t".getBytes());
	            
	            //System.out.println("t".getBytes()[0]);
	            
	            //serialPort.writeBytes("This is a test string".getBytes());//Write data to port
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