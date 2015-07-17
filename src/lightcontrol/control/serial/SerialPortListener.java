package lightcontrol.control.serial;

public class SerialPortListener implements Runnable {
	final boolean TRACING = true;
	
	boolean running;
	
	
	public SerialPortListener() {
		
	}

	@Override
	public void run() {
		long lastTime = System.nanoTime();
		double amountOfTicks = 20.0;//per second
		double timePerTick = 1000000000/amountOfTicks;
		double delta = 0;

		while(running) {
			long now = System.nanoTime();
			//System.out.println((double) ((now-lastTime)/timePerTick));
			delta += ((now-lastTime)/timePerTick);
			//System.out.println(delta);
			lastTime = now;
			
			if (delta > 5) {
				System.err.println("Dropping main ticks");
				delta = 1.5;
			}
			while(delta>=1){
				//do something
				delta--;
			}
		}
	}
	
	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public void trace(String msg) {
		if (TRACING) {
			System.out.println("[Serial IN]: " + msg);
		}
	}
}
