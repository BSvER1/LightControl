package lightcontrol;

import lightcontrol.control.serial.SerialCommunicator;
import lightcontrol.gui.LightControlWindow;

public class LightControl {

	public static void main(String[] args) {

		//switch commented lines below to test serial communicator. it will send test packets as part of the thread wake up sequence.
		
		new LightControlWindow();
		//SerialCommunicator sc = new SerialCommunicator();


	}
	

}
