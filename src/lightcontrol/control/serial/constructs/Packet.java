package lightcontrol.control.serial.constructs;

import lightcontrol.helpers.ArrayHelper;
import lightcontrol.helpers.COBSCodec;

public abstract class Packet {
	
	public PacketHeader header;
	public PacketPayload payload;
	public PacketCRC crc;
	
	byte[] pad = {(byte) 0x00};
	
	/**
	 * constructs the packet with defined data.
	 * @param header
	 * @param payload
	 * @param crc
	 */
	public Packet(PacketHeader header, PacketPayload payload) {
		init(header, payload);
	}
	
	/**
	 * Creates a blank packet ready for construction. use init() to construct the packet once ready.
	 */
	public Packet() {
		
	}
	
	/**
	 * Initialises the packet to be the given header and payload data.
	 * @param header header packet with type set
	 * @param payload data formated to be 1 byte of command followed by 4 bytes of data
	 */
	public void init(PacketHeader header, PacketPayload payload) {
		this.header = header;
		this.payload = payload;
		this.crc = new PacketCRC(header, payload);
	}
	
	private byte[] toBytes() {
		return ArrayHelper.concatAll(header.toBytes(), payload.getCommand().toBytes(), payload.getData().toBytes(), crc.getCRC());
	}
	
	private byte[] getCOBSArray() {
		//byte[] cobsInput = toBytes();
		//System.out.print("COBS input: ");
		//for (int i = 0; i < cobsInput.length; i++) {
		//	System.out.printf("0x%02X ", cobsInput[i]);
		//}
		//System.out.println();
		
		//byte[] cobsOUT = COBSCodec.encode(toBytes());
		//System.out.print("COBS output: ");
		//for (int i = 0; i < cobsOUT.length; i++) {
		//	System.out.printf("0x%02X ", cobsOUT[i]);
		//}
		//System.out.println();
		
		return COBSCodec.encode(toBytes());
	}
	
	private byte[] padPacket() {
		return ArrayHelper.concatAll(pad, getCOBSArray(), pad);
	}
	
	public byte[] getFinishedPacket() {
		return padPacket();
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
	
	public void reverifyPacket() {
		crc = new PacketCRC(header, payload);
	}
	
}
