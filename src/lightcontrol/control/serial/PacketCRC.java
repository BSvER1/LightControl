package lightcontrol.control.serial;

import java.util.BitSet;

public class PacketCRC {

	PacketHeader header;
	PacketData data;
	
	public PacketCRC(PacketHeader header, PacketData data) {
		this.header = header;
		this.data = data;
	}
	
	public int getCRC() {
		return BitSet.valueOf(header.toBytes()).cardinality() + BitSet.valueOf(data.toBytes()).cardinality();
	}
}
