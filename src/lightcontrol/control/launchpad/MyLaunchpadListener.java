package lightcontrol.control.launchpad;

import java.io.IOException;
import java.util.Random;

import lightcontrol.control.LaunchpadDriver;
import lightcontrol.control.serial.packets.PacketSet;
import lightcontrol.enums.StripColor;
import lightcontrol.gui.LightControlWindow;
import lightcontrol.gui.TimingsThread;
import net.thecodersbreakfast.lp4j.api.BackBufferOperation;
import net.thecodersbreakfast.lp4j.api.Button;
import net.thecodersbreakfast.lp4j.api.Color;
import net.thecodersbreakfast.lp4j.api.LaunchpadClient;
import net.thecodersbreakfast.lp4j.api.LaunchpadListener;
import net.thecodersbreakfast.lp4j.api.Pad;


public class MyLaunchpadListener implements LaunchpadListener {

	LaunchpadClient client;
	Random r;
	int start;
	int stop;
	String stripString;
	
	int[] laserChannels;
	
	private static Pad toTick;

	public MyLaunchpadListener(LaunchpadClient client) {
		this.client = client; 
		r = new Random();
		laserChannels = new int[19];
	}
	
	@Override
	public void onPadPressed(Pad pad, long timestamp) {
		System.out.println("Received pad press: "+pad.toString());
		//client.setPadLight(pad, Color.GREEN, BackBufferOperation.NONE);
		
		int x = pad.getX();
		int y = pad.getY();
		if (LightControlWindow.getLaunchpadSequence(LightControlWindow.getCurrentBank(), x, y) != null) {
			if (toTick != null) {
				client.setPadLight(toTick, Color.YELLOW, BackBufferOperation.NONE);
			}
			toTick = pad;
		
			LightControlWindow.setQueuedSequence(LightControlWindow.getPerformanceSequences().getSequence(
					LightControlWindow.getLaunchpadSequence(LightControlWindow.getCurrentBank(), x, y)));
		}
				
		
	}

	@Override
	public void onPadReleased(Pad pad, long timestamp) {
		//client.setPadLight(pad, Color.YELLOW, BackBufferOperation.NONE);
		
		
	}

	@Override
	public void onButtonPressed(Button button, long timestamp) {
		//System.out.println("Received button press: "+button.name());
		
		if (button == Button.UP){
			TimingsThread.increaseBPM();
		} else if (button == Button.DOWN) {
			TimingsThread.decreaseBPM();
		} else if (button == Button.VOL) {
			TimingsThread.setOffset();
		} else if (button == Button.SESSION) {
			TimingsThread.tapToBPM();
		}
		
		else {
			client.setButtonLight(button, Color.RED, BackBufferOperation.NONE);
		}
		
	}

	@Override
	public void onButtonReleased(Button button, long timestamp) {
		if (getButtonShouldTurnOff(button)) {
			client.setButtonLight(button, Color.BLACK, BackBufferOperation.NONE);
		}
		
	}
	
	private boolean getButtonShouldTurnOff(Button button) {
		if (button == Button.UP) return false;
		if (button == Button.DOWN) return false;
		if (button == Button.VOL) return false;
		if (button == Button.SESSION) return false;
		if (button == Button.PAN) return false;
		if (button == Button.SND_A) return false;
		if (button == Button.SND_B) return false;
		return true;
	}

	@Override
	public void onTextScrolled(long timestamp) {
		
	}
	
	public void shutdownLaunchpad() {
		System.out.println("Shutting down...");
		
		System.out.println("Terminating threads...");
        TimingsThread.setRunning(false);
        
        System.out.println("Resetting launchpad...");
        try {
        	client.reset();
        } catch (NullPointerException ex) {
        	
        }
        
        System.out.println("Closing launchpad...");
        try {
			LaunchpadDriver.getLaunchpad().close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
        
        System.out.println("...done");
	}

	public static Pad getPadToTick() {
		return toTick;
	}

	public static void setPadToTick(Pad toTick) {
		MyLaunchpadListener.toTick = toTick;
	}
}
