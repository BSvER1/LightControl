package lightcontrol.control.serial.constructs;

import java.util.Arrays;
import java.util.BitSet;

import lightcontrol.helpers.CRC8;

public class PacketCRC {

	PacketHeader header;
	PacketPayload data;

	public PacketCRC(PacketHeader header, PacketPayload data) {
		this.header = header;
		this.data = data;
	}

	private byte[] getData() {
		int totalLength = header.toBytes().length;
		totalLength += data.getCommand().toBytes().length;
		totalLength += data.getData().toBytes().length;
		byte[] result = Arrays.copyOf(header.toBytes(), totalLength);
		int offset = header.toBytes().length;

		System.arraycopy(data.getCommand().toBytes(), 0, result, offset, data.getCommand().toBytes().length);
		offset += data.getCommand().toBytes().length;
		System.arraycopy(data.getData().toBytes(), 0, result, offset, data.getData().toBytes().length);
		offset += data.getCommand().toBytes().length;

		return result;
	}

	public byte[] getCRC() {
		CRC8 crc8 = new CRC8();
		crc8.update(getData());
		return new byte[] {(byte) crc8.getValue()};
	}

	public boolean equals(PacketCRC other) {
		if (!header.equals(other.header)) return false;
		if (!data.equals(other.data)) return false;
		if (!(getCRC() == other.getCRC())) return false;
		//if (!Arrays.equals(getCRC(), other.getCRC())) return false; //should always be true if the others dont fire. possibly duplicated.
		return true;
	}
}
