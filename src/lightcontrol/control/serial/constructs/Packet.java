package lightcontrol.control.serial.constructs;

public abstract class Packet {
	
	PacketHeader header;
	PacketPayload payload;
	PacketCRC crc;
	
	/**
	 * constructs the packet with defined data.
	 * @param header
	 * @param payload
	 * @param crc
	 */
	public Packet(PacketHeader header, PacketPayload payload, PacketCRC crc) {
		init(header, payload, crc);
	}
	
	/**
	 * Creates a blank packet ready for construction. use init() to construct the packet once ready.
	 */
	public Packet() {
		
	}
	
	public void init(PacketHeader header, PacketPayload payload, PacketCRC crc) {
		this.header = header;
		this.payload = payload;
		this.crc = crc;
	}
	
	public byte[] toBytes() {
		//TODO
		System.out.println("Attempting to get bytes out of a packet, but this method is not complete yet!");
		return null;
	}
	
	public byte[] getCOBSArray() {
		//TODO
		return null;
	}
	
	/**
	 * recursively check to see if this packet contains the same data as the provided packet.
	 * @param other the packet to compare this packet against.
	 * @return true of all values are equal. false otherwise. this method is fast-fail. it will fail as soon as it determines a mismatch.
	 */
	public boolean equals(Packet other) {
		if (!header.equals(other.header)) return false;
		if (!payload.equals(other.payload)) return false;
		if (!crc.equals(other.crc)) return false;
		return true;
	}
	
}
