import net.thecodersbreakfast.lp4j.api.BackBufferOperation;
import net.thecodersbreakfast.lp4j.api.Button;
import net.thecodersbreakfast.lp4j.api.Color;
import net.thecodersbreakfast.lp4j.api.Pad;


public class TimingsThread implements Runnable {
	
	final int BEAT_1 = 0;
	final int BEAT_2 = 1;
	final int BEAT_3 = 2;
	final int BEAT_4 = 3;
	
	final int HALF_1 = 0;
	final int HALF_2 = 1;
	final int HALF_3 = 2;
	final int HALF_4 = 3;
	final int HALF_5 = 4;
	final int HALF_6 = 5;
	final int HALF_7 = 6;
	final int HALF_8 = 7;
	
	final int QUARTER_1 = 0;
	final int QUARTER_2 = 1;
	final int QUARTER_3 = 2;
	final int QUARTER_4 = 3;
	final int QUARTER_5 = 4;
	final int QUARTER_6 = 5;
	final int QUARTER_7 = 6;
	final int QUARTER_8 = 7;
	final int QUARTER_9 = 8;
	final int QUARTER_10 = 9;
	final int QUARTER_11 = 10;
	final int QUARTER_12 = 11;
	final int QUARTER_13 = 12;
	final int QUARTER_14 = 13;
	final int QUARTER_15 = 14;
	final int QUARTER_16 = 15;
	
	static boolean running;
	boolean quantized;
	
	long start;
	static long offset;
	static double bpm;
	static double delayTime;
	
	int currentBeat;
	int currentHalf;
	int currentQuarter;
	
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

		if (-(offset - System.currentTimeMillis()) % (16*delayTime) < delayTime) {
			currentBeat = BEAT_1;
			currentHalf = HALF_1;
			currentQuarter = QUARTER_1;
		} else if (-(offset - System.currentTimeMillis()) % (16*delayTime) < (2*delayTime)) {
			currentQuarter = QUARTER_2;
		} else if (-(offset - System.currentTimeMillis()) % (16*delayTime) < (3*delayTime)) {
			currentHalf = HALF_2;
			currentQuarter = QUARTER_3;
		} else if (-(offset - System.currentTimeMillis()) % (16*delayTime) < (4*delayTime)) {
			currentQuarter = QUARTER_4;
		} else if (-(offset - System.currentTimeMillis()) % (16*delayTime) < (5*delayTime)) {
			currentBeat = BEAT_2;
			currentHalf = HALF_3;
			currentQuarter = QUARTER_5;
		} else if (-(offset - System.currentTimeMillis()) % (16*delayTime) < (6*delayTime)) {
			currentQuarter = QUARTER_6;
		} else if (-(offset - System.currentTimeMillis()) % (16*delayTime) < (7*delayTime)) {
			currentHalf = HALF_4;
			currentQuarter = QUARTER_7;
		} else if (-(offset - System.currentTimeMillis()) % (16*delayTime) < (8*delayTime)) {
			currentQuarter = QUARTER_8;
		} else if (-(offset - System.currentTimeMillis()) % (16*delayTime) < (9*delayTime)) {
			currentBeat = BEAT_3;
			currentHalf = HALF_5;
			currentQuarter = QUARTER_9;
		} else if (-(offset - System.currentTimeMillis()) % (16*delayTime) < (10*delayTime)) {
			currentQuarter = QUARTER_10;
		} else if (-(offset - System.currentTimeMillis()) % (16*delayTime) < (11*delayTime)) {
			currentHalf = HALF_6;
			currentQuarter = QUARTER_11;
		} else if (-(offset - System.currentTimeMillis()) % (16*delayTime) < (12*delayTime)) {
			currentQuarter = QUARTER_12;
		} else if (-(offset - System.currentTimeMillis()) % (16*delayTime) < (13*delayTime)) {
			currentBeat = BEAT_4;
			currentHalf = HALF_7;
			currentQuarter = QUARTER_13;
		} else if (-(offset - System.currentTimeMillis()) % (16*delayTime) < (14*delayTime)) {
			currentQuarter = QUARTER_14;
		} else if (-(offset - System.currentTimeMillis()) % (16*delayTime) < (15*delayTime)) {
			currentHalf = HALF_8;
			currentQuarter = QUARTER_15;
		} else if (-(offset - System.currentTimeMillis()) % (16*delayTime) < (16*delayTime)) {
			currentQuarter = QUARTER_16;
		}

		updateButtons();
		
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
		delayTime = 60000/bpm/4;
		System.out.println("Setting bpm to " + bpm + ", thats a delay of "+ delayTime + " per quarter beat");
	}
	
	public static void setOffset() {
		//System.out.println("Resetting first beat position");
		offset = System.currentTimeMillis();
	}
	
	public void updateButtons() {
		if (currentBeat == BEAT_1) LaunchpadDriver.client.setButtonLight(Button.VOL, Color.GREEN, BackBufferOperation.NONE);
		else LaunchpadDriver.client.setButtonLight(Button.VOL, Color.RED, BackBufferOperation.NONE);
		if (currentBeat == BEAT_2) LaunchpadDriver.client.setButtonLight(Button.PAN, Color.GREEN, BackBufferOperation.NONE);
		else LaunchpadDriver.client.setButtonLight(Button.PAN, Color.RED, BackBufferOperation.NONE);
		if (currentBeat == BEAT_3) LaunchpadDriver.client.setButtonLight(Button.SND_A, Color.GREEN, BackBufferOperation.NONE);
		else LaunchpadDriver.client.setButtonLight(Button.SND_A, Color.RED, BackBufferOperation.NONE);
		if (currentBeat == BEAT_4) LaunchpadDriver.client.setButtonLight(Button.SND_B, Color.GREEN, BackBufferOperation.NONE);
		else LaunchpadDriver.client.setButtonLight(Button.SND_B, Color.RED, BackBufferOperation.NONE);
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
