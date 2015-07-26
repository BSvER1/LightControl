package lightcontrol.control.serial.constructs;

public class PacketHeader {

	public final static Character commandPacket = 0x01;
	public final static Character ackPacket = 0x02;
	
	char type;
	
	public PacketHeader(Character type){
		this.type = type;
	}
	
	public byte[] toBytes() {
		return new byte[] {(byte) type};
	}
	
	@Override
	public String toString() {
		return Character.valueOf(type).toString();
	}
	
	public boolean equals(PacketHeader other) {
		return (type == other.type);
	}
}
