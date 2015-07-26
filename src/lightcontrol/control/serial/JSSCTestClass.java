package lightcontrol.control.serial;

import java.util.concurrent.ConcurrentLinkedQueue;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import lightcontrol.control.serial.constructs.Packet;
import lightcontrol.control.serial.constructs.PacketHeader;

public class JSSCTestClass {
	
	SerialPort s;
	
	long timeout = 1; // 1ms
	boolean ackRecd = false;
	boolean canSend = false;
	
	ConcurrentLinkedQueue<Packet> toSend;
	
	public JSSCTestClass() {
		s = new SerialPort("/dev/tty.SLAB_USBtoUART");
		
		toSend = new ConcurrentLinkedQueue<Packet>();
		
		openPort();
		addListener();
	}
	
	public void sendMessage(Packet packet) {
		if (canSend) {
			writePacket(packet);
		} else {
			toSend.add(packet);
		}
		
	}
	
	private void openPort() {
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
	
	private void addListener() {
		try {
			s.addEventListener(new SerialPortEventListener() {
				@Override
				public void serialEvent(SerialPortEvent e) {
					//System.out.println("Event received!");
					try {
						//System.out.println(Byte.valueOf(serialPort.readBytes(1)[0]).toString());
						byte received_byte = s.readBytes(1)[0];
						if (received_byte == PacketHeader.ackPacket) {
							System.out.println("ACK Received!");
							ackRecd = true;
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
	
	private void writePacket(Packet packet) {
		long now = System.currentTimeMillis();
		while (!ackRecd) {
			try {
				s.writeBytes(packet.toBytes());
				ackRecd = false;
				now = System.currentTimeMillis();
			} catch (SerialPortException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			while(!ackRecd || ((System.currentTimeMillis() - now) > timeout )) {
				//wait for acknowledgement from listener
			}
		}
	}
}
