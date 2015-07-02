import javax.sound.midi.MidiUnavailableException;

import net.thecodersbreakfast.lp4j.api.*;
import net.thecodersbreakfast.lp4j.midi.*;



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
