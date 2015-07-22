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
	
	private static Launchpad launchpad;
	private static LaunchpadClient client;
	private static MyLaunchpadListener listener;
	
	TimingsThread tt;
	Thread timings;
	
	public LaunchpadDriver() {
		
		tt = new TimingsThread();
		timings = new Thread(tt);
		
		try {
			launchpad = new MidiLaunchpad(MidiDeviceConfiguration.autodetect());
			client = launchpad.getClient();
			listener = new MyLaunchpadListener(client);
		    launchpad.setListener(listener);
		    
		    client.reset();
		    
		    client.testLights(LightIntensity.LOW);
		    
		    Thread.sleep(1000);
		    
		    client.reset();
		    
	        client.setButtonLight(Button.UP, Color.AMBER, BackBufferOperation.NONE);
	        client.setButtonLight(Button.DOWN, Color.AMBER, BackBufferOperation.NONE);
	        client.setButtonLight(Button.SESSION, Color.GREEN, BackBufferOperation.NONE);
	        
		} catch (MidiUnavailableException e) {
			System.out.println("Launchpad midi device unreachable.");
			TimingsThread.setLaunchpadAvailiable(false);
		} catch (LaunchpadException e) {
			System.out.println("Could not connect to launchpad.");
			TimingsThread.setLaunchpadAvailiable(false);
		} catch (InterruptedException e) {
			//Could not sleep thread. Dont do anything about this.
		} finally {
			// launch loop thread
	        timings.setName("Timings Thread");
			TimingsThread.setRunning(true);
			timings.start();
		}
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
