import net.thecodersbreakfast.lp4j.api.BackBufferOperation;
import net.thecodersbreakfast.lp4j.api.Button;
import net.thecodersbreakfast.lp4j.api.Color;


public class TimingsThread implements Runnable {
	
	final int BEAT_1 = 0;
	final int BEAT_2 = 1;
	final int BEAT_3 = 2;
	final int BEAT_4 = 3;
	
	
	static boolean running;
	boolean quantized;
	
	long start;
	static long offset;
	static double bpm;
	static double delayTime;
	
	int currentBeat;
	
	static int numTaps;
	static double tapTimeout = 3000; // 3 seconds
	static double lastTap, secLastTap;
	static double avgBPM;
	static double avgMillis;
	
	
	public TimingsThread() {
		System.out.println("Timings thread starting...");
		
	}

	public void setBeatIndicators() {
		//currentBeat = BEAT_4;

		if (-(offset - System.currentTimeMillis()) % (4*delayTime) < delayTime) {
			currentBeat = BEAT_1;
		} else if (-(offset - System.currentTimeMillis()) % (4*delayTime) < (2*delayTime)) {
			currentBeat = BEAT_2;
		}else if (-(offset - System.currentTimeMillis()) % (4*delayTime) < (3*delayTime)) {
			currentBeat = BEAT_3;
		}else if (-(offset - System.currentTimeMillis()) % (4*delayTime) < (4*delayTime)) {
			currentBeat = BEAT_4;
		}

		updateButtons(currentBeat);
		
	}
	
	public void run() {
		
		long lastTime = System.nanoTime();
		double amountOfTicks = 20.0;//per second
		double timePerTick = 1000000000/amountOfTicks;
		double delta = 0;
		
		offset = System.currentTimeMillis();
		setBPM(120);

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
				setBeatIndicators();
				delta--;
			}
		}
		
	}
	
	public static void increaseBPM() {
		bpm++;
		setBPM(bpm);
	}
	
	public static void decreaseBPM() {
		bpm--;
		setBPM(bpm);
	}

	public static void setBPM(double newBpm) {
		bpm = newBpm;
		delayTime = 60000/bpm;
		System.out.println("Setting bpm to " + bpm + ", thats a delay of "+ delayTime + ".");
	}
	
	public static void setOffset() {
		//System.out.println("Resetting first beat position");
		offset = System.currentTimeMillis();
	}
	
	public void updateButtons(int current) {
		
		LaunchpadDriver.client.setButtonLight(Button.VOL, Color.RED, BackBufferOperation.NONE);
		LaunchpadDriver.client.setButtonLight(Button.PAN, Color.RED, BackBufferOperation.NONE);
		LaunchpadDriver.client.setButtonLight(Button.SND_A, Color.RED, BackBufferOperation.NONE);
		LaunchpadDriver.client.setButtonLight(Button.SND_B, Color.RED, BackBufferOperation.NONE);
		
		if (currentBeat == BEAT_1) {
			LaunchpadDriver.client.setButtonLight(Button.VOL, Color.GREEN, BackBufferOperation.NONE);
		} else if (currentBeat == BEAT_2) {
			LaunchpadDriver.client.setButtonLight(Button.PAN, Color.GREEN, BackBufferOperation.NONE);
		} else if (currentBeat == BEAT_3) {
			LaunchpadDriver.client.setButtonLight(Button.SND_A, Color.GREEN, BackBufferOperation.NONE);
		} else if (currentBeat == BEAT_4) {
			LaunchpadDriver.client.setButtonLight(Button.SND_B, Color.GREEN, BackBufferOperation.NONE);
		}
		
	}
	
	public static void tapToBPM() {
		
		if (System.currentTimeMillis()-lastTap > tapTimeout) {
			lastTap = System.currentTimeMillis();
			numTaps = 1;
			//System.out.println("got first tap, waiting for next tap...");
		} else if (numTaps >= 1) {
			//New average = old average * (n-1)/n + new value /n
			avgMillis = avgMillis * (numTaps-1)/numTaps + (System.currentTimeMillis() - lastTap)/numTaps;
			lastTap = System.currentTimeMillis();
			numTaps++;
			avgBPM = 60000/avgMillis;
			//System.out.println(avgBPM);
			setBPM(avgBPM);
		}
			
	}
	
	public boolean isRunning() {
		return running;
	}

	public static void setRunning(boolean setRunning) {
		running = setRunning;
	}
}
