package lightcontrol.control.launchpad;

import java.io.IOException;
import java.util.Random;

import lightcontrol.control.LaunchpadDriver;
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
	
	public MyLaunchpadListener(LaunchpadClient client) {
		this.client = client; 
		r = new Random();
	}
	
	@Override
	public void onPadPressed(Pad pad, long timestamp) {
		System.out.println("Received pad press: "+pad.toString());
		client.setPadLight(pad, Color.GREEN, BackBufferOperation.NONE);
		
		
		if (pad == Pad.at(0, 0)) { //random strip random color.
			start = r.nextInt(10)+1;
			do {
				stop = r.nextInt(11)+1;
			} while (start >= stop);
			stripString = "" + start + "-"+stop;
			LightControlWindow.getLightData().getStrip(stripString).setStripColor(StripColor.values()[ r.nextInt(StripColor.values().length-1)+1 ]);
		}
			
		
	}

	@Override
	public void onPadReleased(Pad pad, long timestamp) {
		client.setPadLight(pad, Color.BLACK, BackBufferOperation.NONE);
		
		if (pad == Pad.at(0, 0)) LightControlWindow.getLightData().getStrip(stripString).setStripColor(0, 0, 0);
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
		
		if (button != Button.UP && button != Button.DOWN
				&& button != Button.VOL && button != Button.SESSION && button != Button.PAN
				&& button != Button.SND_A && button != Button.SND_B) {
		
			client.setButtonLight(button, Color.BLACK, BackBufferOperation.NONE);
		}
		
	}

	@Override
	public void onTextScrolled(long timestamp) {
		// TODO Auto-generated method stub
		
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
			LaunchpadDriver.launchpad.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
        
        System.out.println("...done");
	}

}
