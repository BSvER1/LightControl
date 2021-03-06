package lightcontrol.gui;



import java.math.BigDecimal;
import java.math.RoundingMode;

import lightcontrol.control.LaunchpadDriver;
import lightcontrol.control.launchpad.MyLaunchpadListener;
import net.thecodersbreakfast.lp4j.api.BackBufferOperation;
import net.thecodersbreakfast.lp4j.api.Button;
import net.thecodersbreakfast.lp4j.api.Color;


public class TimingsThread implements Runnable {
	
	//Quarter bar
	final int BEAT_1 = 0;
	final int BEAT_2 = 1;
	final int BEAT_3 = 2;
	final int BEAT_4 = 3;
	
	//eighth of a bar
	final int HALF_1 = 0;
	final int HALF_2 = 1;
	final int HALF_3 = 2;
	final int HALF_4 = 3;
	final int HALF_5 = 4;
	final int HALF_6 = 5;
	final int HALF_7 = 6;
	final int HALF_8 = 7;
	
	//16th of a bar
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
	
	//32nd of a bar / eighth of a beat
	final int EIGHTH_1 = 0;
	final int EIGHTH_2 = 1;
	final int EIGHTH_3 = 2;
	final int EIGHTH_4 = 3;
	final int EIGHTH_5 = 4;
	final int EIGHTH_6 = 5;
	final int EIGHTH_7 = 6;
	final int EIGHTH_8 = 7;
	final int EIGHTH_9 = 8;
	final int EIGHTH_10 = 9;
	final int EIGHTH_11 = 10;
	final int EIGHTH_12 = 11;
	final int EIGHTH_13 = 12;
	final int EIGHTH_14 = 13;
	final int EIGHTH_15 = 14;
	final int EIGHTH_16 = 15;
	final int EIGHTH_17 = 16;
	final int EIGHTH_18 = 17;
	final int EIGHTH_19 = 18;
	final int EIGHTH_20 = 19;
	final int EIGHTH_21 = 20;
	final int EIGHTH_22 = 21;
	final int EIGHTH_23 = 22;
	final int EIGHTH_24 = 23;
	final int EIGHTH_25 = 24;
	final int EIGHTH_26 = 25;
	final int EIGHTH_27 = 26;
	final int EIGHTH_28 = 27;
	final int EIGHTH_29 = 28;
	final int EIGHTH_30 = 29;
	final int EIGHTH_31 = 30;
	final int EIGHTH_32 = 31;
	
	static boolean running;
	static boolean launchpadAvailiable = true;

	boolean quantized;
	
	long start;
	static long offset;
	static double bpm;
	static double delayTime;
	
	public static int currentBar;
	int currentBeat;
	int currentHalf;
	int currentQuarter;
	public static int currentEighth;
	
	static int numTaps;
	static double tapTimeout = 3000; // 3 seconds
	static double lastTap, secLastTap;
	static double avgBPM;
	static double avgMillis;
	
	boolean barTickable = true;
	
	
	public TimingsThread() {
		System.out.println("Timings thread starting...");
	}
	
	public void tickCurrentBar(int i) {
		if (i == 1) {
			barTickable = true;
			return;
		}
		
		if (barTickable && i == 0) {
			if (currentBar != 15)
				currentBar++;
			else
				currentBar = 0;
			
			// trigger the sequence changing
			//LightControlWindow.advanceQueuedSequenceToCurrent();
			
			barTickable = false;
		}
	}
	
	public void setBeatIndicators() {
		if (-(offset - System.currentTimeMillis()) % (32*delayTime) < delayTime) {
			tickCurrentBar(0);
			currentBeat = BEAT_1;
			currentHalf = HALF_1;
			currentQuarter = QUARTER_1;
			currentEighth = EIGHTH_1;
			
		} else if (-(offset - System.currentTimeMillis()) % (32*delayTime) < (2*delayTime)) {
			currentEighth = EIGHTH_2;
		} else if (-(offset - System.currentTimeMillis()) % (32*delayTime) < (3*delayTime)) {
			currentQuarter = QUARTER_2;
			currentEighth = EIGHTH_3;
		} else if (-(offset - System.currentTimeMillis()) % (32*delayTime) < (4*delayTime)) {
			currentEighth = EIGHTH_4;
		} else if (-(offset - System.currentTimeMillis()) % (32*delayTime) < (5*delayTime)) {
			currentHalf = HALF_2;
			currentQuarter = QUARTER_3;
			currentEighth = EIGHTH_5;
		} else if (-(offset - System.currentTimeMillis()) % (32*delayTime) < (6*delayTime)) {
			currentEighth = EIGHTH_6;
		} else if (-(offset - System.currentTimeMillis()) % (32*delayTime) < (7*delayTime)) {
			currentQuarter = QUARTER_4;
			currentEighth = EIGHTH_7;
		} else if (-(offset - System.currentTimeMillis()) % (32*delayTime) < (8*delayTime)) {
			currentEighth = EIGHTH_8;
		} else if (-(offset - System.currentTimeMillis()) % (32*delayTime) < (9*delayTime)) {
			tickCurrentBar(1);
			currentBeat = BEAT_2;
			currentHalf = HALF_3;
			currentQuarter = QUARTER_5;
			currentEighth = EIGHTH_9;
		} else if (-(offset - System.currentTimeMillis()) % (32*delayTime) < (10*delayTime)) {
			currentEighth = EIGHTH_10;
		} else if (-(offset - System.currentTimeMillis()) % (32*delayTime) < (11*delayTime)) {
			currentQuarter = QUARTER_6;
			currentEighth = EIGHTH_11;
		} else if (-(offset - System.currentTimeMillis()) % (32*delayTime) < (12*delayTime)) {
			currentEighth = EIGHTH_12;
		} else if (-(offset - System.currentTimeMillis()) % (32*delayTime) < (13*delayTime)) {
			currentHalf = HALF_4;
			currentQuarter = QUARTER_7;
			currentEighth = EIGHTH_13;
		} else if (-(offset - System.currentTimeMillis()) % (32*delayTime) < (14*delayTime)) {
			currentEighth = EIGHTH_14;
		} else if (-(offset - System.currentTimeMillis()) % (32*delayTime) < (15*delayTime)) {
			currentQuarter = QUARTER_8;
			currentEighth = EIGHTH_15;
		} else if (-(offset - System.currentTimeMillis()) % (32*delayTime) < (16*delayTime)) {
			currentEighth = EIGHTH_16;
		} else if (-(offset - System.currentTimeMillis()) % (32*delayTime) < (17*delayTime)) {
			tickCurrentBar(1);
			currentBeat = BEAT_3;
			currentHalf = HALF_5;
			currentQuarter = QUARTER_9;
			currentEighth = EIGHTH_17;
		} else if (-(offset - System.currentTimeMillis()) % (32*delayTime) < (18*delayTime)) {
			currentEighth = EIGHTH_18;
		} else if (-(offset - System.currentTimeMillis()) % (32*delayTime) < (19*delayTime)) {
			currentQuarter = QUARTER_10;
			currentEighth = EIGHTH_19;
		} else if (-(offset - System.currentTimeMillis()) % (32*delayTime) < (20*delayTime)) {
			currentEighth = EIGHTH_20;
		} else if (-(offset - System.currentTimeMillis()) % (32*delayTime) < (21*delayTime)) {
			currentHalf = HALF_6;
			currentQuarter = QUARTER_11;
			currentEighth = EIGHTH_21;
		} else if (-(offset - System.currentTimeMillis()) % (32*delayTime) < (22*delayTime)) {
			currentEighth = EIGHTH_22;
		} else if (-(offset - System.currentTimeMillis()) % (32*delayTime) < (23*delayTime)) {
			currentQuarter = QUARTER_12;
			currentEighth = EIGHTH_23;
		} else if (-(offset - System.currentTimeMillis()) % (32*delayTime) < (24*delayTime)) {
			currentEighth = EIGHTH_24;
		} else if (-(offset - System.currentTimeMillis()) % (32*delayTime) < (25*delayTime)) {
			tickCurrentBar(1);
			currentBeat = BEAT_4;
			currentHalf = HALF_7;
			currentQuarter = QUARTER_13;
			currentEighth = EIGHTH_25;
		} else if (-(offset - System.currentTimeMillis()) % (32*delayTime) < (26*delayTime)) {
			currentEighth = EIGHTH_26;
		} else if (-(offset - System.currentTimeMillis()) % (32*delayTime) < (27*delayTime)) {
			currentQuarter = QUARTER_14;
			currentEighth = EIGHTH_27;
		} else if (-(offset - System.currentTimeMillis()) % (32*delayTime) < (28*delayTime)) {
			currentEighth = EIGHTH_28;
		} else if (-(offset - System.currentTimeMillis()) % (32*delayTime) < (29*delayTime)) {
			currentHalf = HALF_8;
			currentQuarter = QUARTER_15;
			currentEighth = EIGHTH_29;
		} else if (-(offset - System.currentTimeMillis()) % (32*delayTime) < (30*delayTime)) {
			currentEighth = EIGHTH_30;
		} else if (-(offset - System.currentTimeMillis()) % (32*delayTime) < (31*delayTime)) {
			currentQuarter = QUARTER_16;
			currentEighth = EIGHTH_31;
		} else if (-(offset - System.currentTimeMillis()) % (32*delayTime) < (32*delayTime)) {
			currentEighth = EIGHTH_32;
		}

		//System.out.println(currentBar*32 + currentEighth);
		updateButtons();
		
	}
	
	@Override
	public void run() {
		
		long lastTime = System.nanoTime();
		double amountOfTicks = 60.0;//per second
		double timePerTick = 1000000000/amountOfTicks;
		double delta = 0;
		
		offset = System.currentTimeMillis();
		setBPM(80.0);

		while(running) {
			long now = System.nanoTime();
			//System.out.println((double) ((now-lastTime)/timePerTick));
			delta += ((now-lastTime)/timePerTick);
			//System.out.println(delta);
			lastTime = now;
			
			if (delta > 5) {
				System.err.println("Dropping timings thread ticks");
				delta = 1.5;
			}
			while(delta>=1){
				setBeatIndicators();
				
				
				
				LightControlWindow.advanceQueuedSequenceToCurrent();
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

	public static void setBPM(Double newBpm) {
		bpm = newBpm;
		delayTime = 60000/bpm/8;  // 60 seconds to a minute. 8 divisions per beat
		//System.out.println("Setting bpm to " + bpm + ", thats a delay of "+ delayTime + " per eighth of a beat");
		LightControlWindow.getBpmDisplay().setText(newBpm.toString());
	}
	
	public static void setOffset() {
		//System.out.println("Resetting first beat position");
		offset = System.currentTimeMillis();
		currentBar = 0;
	}
	
	public void updateButtons() {
		if (launchpadAvailiable) {
			if (currentBeat == BEAT_1) LaunchpadDriver.getClient().setButtonLight(Button.VOL, Color.GREEN, BackBufferOperation.NONE);
			else LaunchpadDriver.getClient().setButtonLight(Button.VOL, Color.RED, BackBufferOperation.NONE);
			if (currentBeat == BEAT_2) LaunchpadDriver.getClient().setButtonLight(Button.PAN, Color.GREEN, BackBufferOperation.NONE);
			else LaunchpadDriver.getClient().setButtonLight(Button.PAN, Color.RED, BackBufferOperation.NONE);
			if (currentBeat == BEAT_3) LaunchpadDriver.getClient().setButtonLight(Button.SND_A, Color.GREEN, BackBufferOperation.NONE);
			else LaunchpadDriver.getClient().setButtonLight(Button.SND_A, Color.RED, BackBufferOperation.NONE);
			if (currentBeat == BEAT_4) LaunchpadDriver.getClient().setButtonLight(Button.SND_B, Color.GREEN, BackBufferOperation.NONE);
			else LaunchpadDriver.getClient().setButtonLight(Button.SND_B, Color.RED, BackBufferOperation.NONE);
		}
		if (MyLaunchpadListener.getPadToTick() != null) {
			if (currentHalf%2 == 0) {
				LaunchpadDriver.getClient().setPadLight(MyLaunchpadListener.getPadToTick(), Color.GREEN, BackBufferOperation.NONE);
			} else {
				LaunchpadDriver.getClient().setPadLight(MyLaunchpadListener.getPadToTick(), Color.YELLOW, BackBufferOperation.NONE);
			}
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
			
			BigDecimal bd = new BigDecimal(60000/avgMillis);
		    bd = bd.setScale(2, RoundingMode.HALF_UP);
			
			//avgBPM = 60000/avgMillis;
			
			//System.out.println(avgBPM);
			//setBPM(avgBPM);
		    setBPM(bd.doubleValue());
		}
		setOffset();
			
	}
	
	public boolean isRunning() {
		return running;
	}

	public static void setRunning(boolean setRunning) {
		running = setRunning;
	}
	
	public static boolean isLaunchpadAvailiable() {
		return launchpadAvailiable;
	}

	public static void setLaunchpadAvailiable(boolean launchpadAvailiable) {
		TimingsThread.launchpadAvailiable = launchpadAvailiable;
	}

	public static double getBpm() {
		return bpm;
	}

	public static void setBpm(double bpm) {
		TimingsThread.bpm = bpm;
	}
}
