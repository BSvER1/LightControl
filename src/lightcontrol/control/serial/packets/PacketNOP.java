package lightcontrol.control.serial.packets;

import lightcontrol.control.serial.constructs.Packet;
import lightcontrol.control.serial.constructs.PacketCRC;
import lightcontrol.control.serial.constructs.PacketCommand;
import lightcontrol.control.serial.constructs.PacketData;
import lightcontrol.control.serial.constructs.PacketHeader;
import lightcontrol.control.serial.constructs.PacketPayload;

public class PacketNOP extends Packet {
	
	public PacketNOP() {
		PacketHeader h = new PacketHeader(PacketHeader.commandPacket);
		PacketData data = new PacketData();
		data.setDataForAck();
		PacketPayload payload = new PacketPayload(PacketCommand.NOP, data);
		
		init(h, payload);
	}
}
