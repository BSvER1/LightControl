package lightcontrol.control.serial;

import java.nio.charset.StandardCharsets;

public class PacketTerminator {

	Character endOfTransmission = 004;
	
	public byte[] toBytes() {
		return endOfTransmission.toString().getBytes(StandardCharsets.US_ASCII);
	}
	
	@Override
	public String toString() {
		return endOfTransmission.toString();
	}
	
}
