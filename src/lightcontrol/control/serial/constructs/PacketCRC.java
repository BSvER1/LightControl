package lightcontrol.control.serial.constructs;

import lightcontrol.helpers.ArrayHelper;
import lightcontrol.helpers.CRC8;

public class PacketCRC {

	PacketHeader header;
	PacketPayload data;

	public PacketCRC(PacketHeader header, PacketPayload data) {
		this.header = header;
		this.data = data;
	}

	private byte[] getData() {
		return ArrayHelper.concatAll(header.toBytes(), data.getCommand().toBytes(), data.getData().toBytes());
	}

	public byte[] getCRC() {
		CRC8 crc8 = new CRC8();
		crc8.update(getData());
		//System.out.printf("CRC: 0x%02X \n", crc8.getValue());
		return new byte[] {(byte) crc8.getValue()};
	}

	public boolean equals(PacketCRC other) {
		return (getCRC() == other.getCRC());
	}
}
