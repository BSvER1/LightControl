package lightcontrol.control.serial.constructs;

import java.nio.charset.StandardCharsets;

public class PacketHeader {

	public final static Character commandPacket = 0x01;
	public final static Character ackPacket = 0x02;
	
	Character type;
	
	public PacketHeader(Character type){
		this.type = type;
	}
	
	public byte[] toBytes() {
		return type.toString().getBytes(StandardCharsets.US_ASCII);
	}
	
	public String toString() {
		return type.toString();
	}
	
	public boolean equals(PacketHeader other) {
		if (!type.equals(other.type)) return false;
		return true;
	}
}
