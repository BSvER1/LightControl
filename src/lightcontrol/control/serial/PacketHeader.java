package lightcontrol.control.serial;

import java.nio.charset.StandardCharsets;

public class PacketHeader {

	Character startOfTransmission = 001;
	
	public byte[] toBytes() {
		return startOfTransmission.toString().getBytes(StandardCharsets.US_ASCII);
	}
	
	public String toString() {
		return startOfTransmission.toString();
	}
}
