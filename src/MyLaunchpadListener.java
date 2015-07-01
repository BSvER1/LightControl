import java.io.IOException;

import net.thecodersbreakfast.lp4j.api.BackBufferOperation;
import net.thecodersbreakfast.lp4j.api.Button;
import net.thecodersbreakfast.lp4j.api.Color;
import net.thecodersbreakfast.lp4j.api.LaunchpadClient;
import net.thecodersbreakfast.lp4j.api.LaunchpadListener;
import net.thecodersbreakfast.lp4j.api.Pad;


public class MyLaunchpadListener implements LaunchpadListener {

	LaunchpadClient client;
	
	public MyLaunchpadListener(LaunchpadClient client) {
		this.client = client; 
	}
	
	@Override
	public void onPadPressed(Pad pad, long timestamp) {
		client.setPadLight(pad, Color.GREEN, BackBufferOperation.NONE);
		
	}

	@Override
	public void onPadReleased(Pad pad, long timestamp) {
		client.setPadLight(pad, Color.BLACK, BackBufferOperation.NONE);
		
	}

	@Override
	public void onButtonPressed(Button button, long timestamp) {
		if (button == Button.MIXER) {
			shutdownLaunchpad();
		} else {
			client.setButtonLight(button, Color.RED, BackBufferOperation.NONE);
		}
		
	}

	@Override
	public void onButtonReleased(Button button, long timestamp) {
		client.setButtonLight(button, Color.BLACK, BackBufferOperation.NONE);
		
	}

	@Override
	public void onTextScrolled(long timestamp) {
		// TODO Auto-generated method stub
		
	}
	
	public void shutdownLaunchpad() {
		
		System.out.println("Shutting down...");
        System.out.println("Resetting launchpad...");
        client.reset();
        System.out.println("Closing launchpad...");
        try {
			LaunchpadDriver.launchpad.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("...done");
	}

}
