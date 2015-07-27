package lightcontrol.control.serial.packets;

import lightcontrol.control.serial.constructs.Packet;
import lightcontrol.control.serial.constructs.PacketCommand;
import lightcontrol.control.serial.constructs.PacketData;
import lightcontrol.control.serial.constructs.PacketHeader;
import lightcontrol.control.serial.constructs.PacketPayload;

public class PacketMaxChannels extends Packet {

	
	public PacketMaxChannels(int numConfigs) {
		PacketHeader h = new PacketHeader(PacketHeader.commandPacket);
		PacketData data = new PacketData();
		data.setDataForAck();
		data.setData(1, (byte) numConfigs);
		PacketPayload payload = new PacketPayload(PacketCommand.ACK, data);
		
		init(h, payload);
	}
}
