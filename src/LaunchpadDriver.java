import javax.sound.midi.MidiUnavailableException;

import net.thecodersbreakfast.lp4j.api.*;
import net.thecodersbreakfast.lp4j.midi.*;



public class LaunchpadDriver {
	
	public static Launchpad launchpad;
	public static LaunchpadClient client;
	
	
	
	public LaunchpadDriver() {
		try {
			launchpad = new MidiLaunchpad(MidiDeviceConfiguration.autodetect());
		} catch (MidiUnavailableException e) {
			System.out.println("Could not connect to launchpad.");
			return;
		}
		
		client = launchpad.getClient();
	    launchpad.setListener(new MyLaunchpadListener(client));
	    
	    client.reset();
        client.setButtonLight(Button.MIXER, Color.RED, BackBufferOperation.NONE);

        
        // launch loop thread
        
	}
	
}
