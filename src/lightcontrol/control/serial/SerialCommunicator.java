package lightcontrol.control.serial;

import java.util.concurrent.ConcurrentLinkedQueue;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import lightcontrol.control.serial.constructs.Packet;
import lightcontrol.control.serial.packets.PacketMaxChannels;
import lightcontrol.control.serial.packets.PacketNOP;
import lightcontrol.control.serial.packets.PacketSet;
import lightcontrol.control.serial.packets.PacketSwitch;

public class SerialCommunicator {
	
	int numChannels = 12;
	
	private Thread consumer;
	private PacketConsumer pC;
	
	private SerialPort s;
	
	long timeout = 100; // 1ms
	boolean ackRecd = false;
	
	private ConcurrentLinkedQueue<Packet> toSend;
	private boolean canSend = false; // used to determine of the serial communicator is availiable.
	
	public SerialCommunicator() {
		System.out.println("Starting serial communicator...");
		toSend = new ConcurrentLinkedQueue<Packet>();
		
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
		//writePacket(packet);
	}
	
	public ConcurrentLinkedQueue<Packet> getPacketQueue() {
		return toSend;
	}
	
	private void openPort() {
		try {
			s = new SerialPort("/dev/tty.SLAB_USBtoUART");
			s.openPort();
			s.setParams(SerialPort.BAUDRATE_115200, 
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
			
			System.out.println("Opened port successfully");
			
		} catch (SerialPortException e) {
			canSend = false;
		}
		
        
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
							//System.out.print("received: ");
							for (int i = 0; i < received_bytes.length; i++) {
								if (received_bytes[i] == (byte) 0x02) {
									//System.out.print("ACK Received: ");
									ackRecd = true;
								}
								if (received_bytes[i] == (byte) 0x03) { // command wrong, crc fail, header incorrect, data fucked, etc
									//System.out.print("NAK received ");
								}
								//System.out.printf("0x%02X ", received_bytes[i]);
							}
							//System.out.println();
						}
						
					} catch (SerialPortException e1) {
						//e1.printStackTrace();
					}
				}
			});
		} catch (SerialPortException e) {
			System.out.println("Could not connect to serial controller.");
			canSend = false;
			//e.printStackTrace();
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
		consumer.start();
	}
	
	private void sendInit() {
		//System.out.println("sending NOP");
		sendMessage(new PacketNOP());
		//System.out.println("sending set max channels");
		sendMessage(new PacketMaxChannels(numChannels));
		sendMessage(new PacketSwitch(0));
		
		
		
		//sendMessage(new PacketSet(0,5,0));
		
		
	}
	
	public void writePacket(Packet packet) {
		long now = System.currentTimeMillis();
		ackRecd = false;
		//while (!ackRecd) {
			
			//System.out.println(Thread.currentThread().getName() + " is sending a packet: " + packet.getClass().getSimpleName());
			try {
				byte[] toWrite = packet.getFinishedPacket(); 
				s.writeBytes(toWrite);
				//System.out.print("sent:" );
				//for (int i = 0; i < toWrite.length; i++) {
				//	System.out.printf("0x%02X ", toWrite[i]);
				//}
				//System.out.println();
				
				ackRecd = false;
				now = System.currentTimeMillis();
			} catch (SerialPortException e) {
				System.err.println("serial port exception");
			}
			
			//while(true) {
			//	if (ackRecd) {
			//		break;
			//	}
			//	if (System.currentTimeMillis() - now > timeout) {
			//		break;
			//	}
				//wait for acknowledgement from listener
				//System.out.println("" + ackRecd + (System.currentTimeMillis() - now));
			//}
		//}
		//try {
		//	Thread.sleep(0,10000);
		//} catch (InterruptedException e) {
		//	// TODO Auto-generated catch block
		//	e.printStackTrace();
		//}
		
		//System.out.println("finished sending packet");
	}
}
