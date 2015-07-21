package lightcontrol.control.serial;

public class Packet {
	
	PacketHeader header;
	PacketData data;
	PacketCRC crc;
	PacketTerminator terminator;
	
	public Packet(PacketHeader header, PacketData data, PacketCRC crc, PacketTerminator terminator) {
		this.header = header;
		this.data = data;
		this.crc = crc;
		this.terminator = terminator;
	}
	
	public void toBytes() {
		//TODO
	}
	
}
