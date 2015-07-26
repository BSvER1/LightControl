package lightcontrol.control.serial.constructs;

import java.nio.charset.StandardCharsets;

public class PacketCommand {

	public final static Character ACK = 0x02;
	public final static Character SET = 0xA0;
	public final static Character SWITCH = 0xB0;
	
	Character command;
	
	public PacketCommand(Character command) {
		this.command = command;
	}
	
	public byte[] toBytes() {
		return command.toString().getBytes(StandardCharsets.US_ASCII);
	}
	
	public boolean equals(PacketCommand other) {
		if (!command.equals(other.command)) return false;
		return true;
	}
	
}
