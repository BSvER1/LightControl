package lightcontrol.control;

import javax.sound.midi.MidiUnavailableException;

import lightcontrol.control.launchpad.MyLaunchpadListener;
import lightcontrol.gui.TimingsThread;
import net.thecodersbreakfast.lp4j.api.BackBufferOperation;
import net.thecodersbreakfast.lp4j.api.Button;
import net.thecodersbreakfast.lp4j.api.Color;
import net.thecodersbreakfast.lp4j.api.Launchpad;
import net.thecodersbreakfast.lp4j.api.LaunchpadClient;
import net.thecodersbreakfast.lp4j.api.LaunchpadException;
import net.thecodersbreakfast.lp4j.api.LightIntensity;
import net.thecodersbreakfast.lp4j.midi.MidiDeviceConfiguration;
import net.thecodersbreakfast.lp4j.midi.MidiLaunchpad;



public class LaunchpadDriver {
	
	public static Launchpad launchpad;
	public static LaunchpadClient client;
	public static MyLaunchpadListener listener;
	
	TimingsThread tt;
	Thread timings;
	
	public LaunchpadDriver() {
		
		tt = new TimingsThread();
		timings = new Thread(tt);
		
		try {
			launchpad = new MidiLaunchpad(MidiDeviceConfiguration.autodetect());
		} catch (MidiUnavailableException e) {
			System.out.println("Could not connect to launchpad.");
			return;
		}
		
		try {
			client = launchpad.getClient();
		} catch (LaunchpadException ex) {
			System.out.println("Could not connect to launchpad.");
			return;
		}
		listener = new MyLaunchpadListener(client);
	    launchpad.setListener(listener);
	    
	    client.reset();
	    
	    client.testLights(LightIntensity.LOW);
	    
	    try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    client.reset();
	    
        client.setButtonLight(Button.UP, Color.AMBER, BackBufferOperation.NONE);
        client.setButtonLight(Button.DOWN, Color.AMBER, BackBufferOperation.NONE);
        client.setButtonLight(Button.SESSION, Color.GREEN, BackBufferOperation.NONE);

        // launch loop thread
        timings.setName("Timings Thread");
		TimingsThread.setRunning(true);
		timings.start();
	}

	public static Launchpad getLaunchpad() {
		return launchpad;
	}

	public static LaunchpadClient getClient() {
		return client;
	}

	public static MyLaunchpadListener getListener() {
		return listener;
	}

	public TimingsThread getTimingThread() {
		return tt;
	}
}
