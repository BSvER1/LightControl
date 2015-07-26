package lightcontrol.control.serial.constructs;

public class PacketCommand {

	public final static Character ACK = 0x02;
	public final static Character SET = 0xA0;
	public final static Character SWITCH = 0xB0;
	
	char command;
	
	public PacketCommand(Character command) {
		this.command = command;
	}
	
	public byte[] toBytes() {
		return new byte[] {(byte) command};
	}
	
	public boolean equals(PacketCommand other) {
		return (command == other.command);
	}
	
}
