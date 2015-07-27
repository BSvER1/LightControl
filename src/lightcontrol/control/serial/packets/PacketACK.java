package lightcontrol.control.serial.packets;

import lightcontrol.control.serial.constructs.Packet;
import lightcontrol.control.serial.constructs.PacketCRC;
import lightcontrol.control.serial.constructs.PacketCommand;
import lightcontrol.control.serial.constructs.PacketData;
import lightcontrol.control.serial.constructs.PacketHeader;
import lightcontrol.control.serial.constructs.PacketPayload;

public class PacketACK extends Packet {

	public PacketACK() {
		PacketHeader h = new PacketHeader(PacketHeader.ackPacket);
		PacketData data = new PacketData();
		data.setDataForAck();
		PacketPayload payload = new PacketPayload(PacketCommand.ACK, data);
		
		init(h, payload);
	}

	
}
