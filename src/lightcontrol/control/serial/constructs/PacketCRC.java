package lightcontrol.control.serial.constructs;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.BitSet;

public class PacketCRC {

	PacketHeader header;
	PacketPayload data;
	
	public PacketCRC(PacketHeader header, PacketPayload data) {
		this.header = header;
		this.data = data;
	}
	
	public byte[] getCRC() {
		return Integer.valueOf(
				BitSet.valueOf(header.toBytes()).cardinality() 
				+ BitSet.valueOf(data.getCommand().toBytes()).cardinality() 
				+ BitSet.valueOf(data.getData().toBytes()).cardinality())
					.toString().getBytes(StandardCharsets.US_ASCII);
	}
	
	public boolean equals(PacketCRC other) {
		if (!header.equals(other.header)) return false;
		if (!data.equals(other.data)) return false;
		if (!Arrays.equals(getCRC(), other.getCRC())) return false; //should always be true if the others dont fire. possibly duplicated.
		return true;
	}
}
