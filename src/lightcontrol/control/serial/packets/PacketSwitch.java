package lightcontrol.control.serial.packets;

import lightcontrol.control.serial.constructs.Packet;
import lightcontrol.control.serial.constructs.PacketCRC;
import lightcontrol.control.serial.constructs.PacketCommand;
import lightcontrol.control.serial.constructs.PacketData;
import lightcontrol.control.serial.constructs.PacketHeader;
import lightcontrol.control.serial.constructs.PacketPayload;

public class PacketSwitch extends Packet {

	final int MAX_CONFIGS = 4; //TODO this has to be moved someplace more central.
	
	public PacketSwitch(int config) {
		if (config < 0 || config > MAX_CONFIGS) {
			throw new IllegalArgumentException("config must be between 0 and "+MAX_CONFIGS);
		}
		
		PacketHeader h = new PacketHeader(PacketHeader.commandPacket);
		PacketData data = new PacketData();
		//byte configBytes[]
		data.setDataForAck();
		data.setData(0, (byte) config);
		PacketPayload payload = new PacketPayload(PacketCommand.SWITCH, data);
		
		init(h, payload, new PacketCRC(h, payload));
	}
}
