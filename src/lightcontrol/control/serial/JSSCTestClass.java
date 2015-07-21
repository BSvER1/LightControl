package lightcontrol.control.serial;

import java.util.concurrent.ConcurrentLinkedQueue;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;

public class JSSCTestClass {
	
	SerialPort s;
	
	long timeout = 1; // 1ms
	boolean ackRecd = false;
	
	ConcurrentLinkedQueue<Packet> toSend;
	
	public JSSCTestClass() {
		s = new SerialPort("/dev/tty.SLAB_USBtoUART");
		openPort();
		addListener();
		sendCommand();
		sendCommand();
		sendCommand();
		
		closePort();
	}
	
	public void openPort() {
		try {
			s.openPort();
			s.setParams(SerialPort.BAUDRATE_9600, 
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
			
			
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
	}
	
	public void addListener() {
		try {
			s.addEventListener(new SerialPortEventListener() {
				@Override
				public void serialEvent(SerialPortEvent e) {
					//System.out.println("Event received!");
					try {
						//System.out.println(Byte.valueOf(serialPort.readBytes(1)[0]).toString());
						byte received_byte = s.readBytes(1)[0];
						if (received_byte == 115){
							System.out.println("ACK Received!");
							ackRecd = true;
						} else if (received_byte == 116){
							System.out.println("NAK Received!");
						}
						
					} catch (SerialPortException e1) {
						e1.printStackTrace();
					}
				}
			});
		} catch (SerialPortException e) {
			e.printStackTrace();
		}
	}
	
	public void closePort() {
		try {
			s.purgePort(SerialPort.PURGE_RXCLEAR | SerialPort.PURGE_TXCLEAR);
			s.closePort();//Close serial port
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       
	}
	
	public boolean ackRecd() {
		//TODO
		return false;
		
	}
	

	
	public boolean sendCommand(byte[] data) {
		long now;
		try {
			//packet header
			//payload
			//CRC/parity
			//stop
			
			
			s.writeBytes(packet);
			ackRecd = false;
			now = System.currentTimeMillis();
		} catch (SerialPortException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(!ackRecd || ((System.currentTimeMillis() - now) > timeout )) {
			//wait for acknowledgement from listener
		}
		if (ackRecd) return true;
		else return false;
	}
}
