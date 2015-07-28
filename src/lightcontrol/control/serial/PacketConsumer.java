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
				Thread.sleep(0,1000);
				//System.out.println("waiting for packets");
			} catch (InterruptedException e) {}
		} else {
			//System.out.println("writing packet");
			comms.writePacket(comms.getPacketQueue().poll());
			if (comms.getPacketQueue().size() > 1000) {
				comms.getPacketQueue().clear();
			}
			
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
		//sendTestPacket();
		
		while (running) {
			performConsumption();
		}
	}

}
