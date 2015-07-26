package lightcontrol.control.serial;

public class PacketConsumer implements Runnable {
	
	SerialCommunicator comms;
	
	boolean running;
	
	public PacketConsumer(SerialCommunicator comms) {
		this.comms = comms;
	}

	public void sendTestPacket() {
		//TODO
	}
	
	public void performConsumption() {
		if (comms.getPacketQueue().isEmpty()) {
			try {
				Thread.sleep(0,500);
			} catch (InterruptedException e) {}
		} else {
			comms.writePacket(comms.getPacketQueue().poll());
		}
	}
	
	public void setRunning(boolean running) {
		this.running = running;
	}
	
	
	@Override
	public void run() {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sendTestPacket();
		
		while (running) {
			performConsumption();
		}
	}

}
