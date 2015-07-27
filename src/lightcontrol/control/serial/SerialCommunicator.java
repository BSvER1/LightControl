package lightcontrol.control.serial;

import java.util.concurrent.ConcurrentLinkedQueue;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import lightcontrol.control.serial.constructs.Packet;
import lightcontrol.control.serial.packets.PacketMaxChannels;
import lightcontrol.control.serial.packets.PacketNOP;
import lightcontrol.helpers.OsCheck;

public class SerialCommunicator {
	
	private Thread consumer;
	private PacketConsumer pC;
	
	private SerialPort s;
	
	long timeout = 1; // 1ms
	boolean ackRecd = false;
	
	private ConcurrentLinkedQueue<Packet> toSend;
	private boolean canSend = false; // used to determine of the serial communicator is availiable.
	
	public SerialCommunicator() {
		toSend = new ConcurrentLinkedQueue<Packet>();
		
		OsCheck.OSType ostype=OsCheck.getOperatingSystemType();
		switch (ostype) {
		    case Windows: 
		    	System.out.println("Serial communications on this OS are not yet supported.");
		    	return;
		    	//break;
		    case MacOS: 
		    	s = new SerialPort("/dev/tty.SLAB_USBtoUART");
		    	break;
		    case Linux: 
		    	System.out.println("Serial communications on this OS are not yet supported.");
		    	return;
		    	//break;
		    case Other: 
		    	System.out.println("Serial communications on this OS are not yet supported.");
		    	return;
		    	//break;
		}
		
		canSend = true;
		
		openPort();
		addListener();
		setupConsumer();
		startConsumer();
		
		sendInit();
	}
	
	/**
	 * logs the supplied packet in the queue if the serial communicator is availiable for this OS. 
	 * the packet is ignored otherwise.
	 * @param packet the packet to send.
	 */
	public void sendMessage(Packet packet) {
		if (canSend)
			toSend.add(packet);
	}
	
	public ConcurrentLinkedQueue<Packet> getPacketQueue() {
		return toSend;
	}
	
	private void openPort() {
		try {
			s.openPort();
			s.setParams(SerialPort.BAUDRATE_9600, 
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
			
		} catch (SerialPortException e) {}
        
	}
	
	private void addListener() {
		try {
			s.addEventListener(new SerialPortEventListener() {
				@Override
				public void serialEvent(SerialPortEvent e) {
					//System.out.println("Event received!");
					try {
						//TODO
						//System.out.println(Byte.valueOf(serialPort.readBytes(1)[0]).toString());
						if (e.getEventType() == SerialPortEvent.RXCHAR && e.getEventValue() >=1 ) {
							byte[] received_bytes = s.readBytes(e.getEventValue());
							
							for (int i = 0; i < received_bytes.length; i++) {
								if (received_bytes[i] == (byte) 0x02) {
									System.out.println("ACK Received!");
									ackRecd = true;
								}
								if (received_bytes[i] == (byte) 0x03) { // command wrong, crc fail, header incorrect, data fucked, etc
									System.out.println("NAK received");
								}
							}
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
		pC.setRunning(false);
		try {
			s.purgePort(SerialPort.PURGE_RXCLEAR | SerialPort.PURGE_TXCLEAR);
			s.closePort();//Close serial port
		} catch (SerialPortException e) {}
       
	}
	
	private void setupConsumer() {
		pC = new PacketConsumer(this);
		consumer = new Thread(pC);
		consumer.setName("Serial Packet Sender");
	}
	
	private void startConsumer() {
		pC.setRunning(true);
	}
	
	private void sendInit() {
		sendMessage(new PacketNOP());
		sendMessage(new PacketMaxChannels(19));
	}
	
	public void writePacket(Packet packet) {
		long now = System.currentTimeMillis();
		while (!ackRecd) {
			try {
				s.writeBytes(packet.getFinishedPacket());
				ackRecd = false;
				now = System.currentTimeMillis();
			} catch (SerialPortException e) {}
			
			while(!ackRecd || ((System.currentTimeMillis() - now) > timeout )) {
				//wait for acknowledgement from listener
			}
		}
	}
}
