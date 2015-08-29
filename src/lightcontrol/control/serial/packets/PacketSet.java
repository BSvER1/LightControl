package lightcontrol.control.serial.packets;

import lightcontrol.control.serial.constructs.Packet;
import lightcontrol.control.serial.constructs.PacketCRC;
import lightcontrol.control.serial.constructs.PacketCommand;
import lightcontrol.control.serial.constructs.PacketData;
import lightcontrol.control.serial.constructs.PacketHeader;
import lightcontrol.control.serial.constructs.PacketPayload;

public class PacketSet extends Packet {

	final int MAX_CONFIGS = 4; //TODO this has to be moved someplace more central.
	final int MAX_CHANNELS = 255; //TODO this has to be set to 511 
	
	public PacketSet(int config, int channel, int value) {
		if (config < 0 || config > MAX_CONFIGS) {
			throw new IllegalArgumentException("config must be between 0 and "+MAX_CONFIGS);
		}
		if (channel < 1 || channel > MAX_CHANNELS) {
			throw new IllegalArgumentException("channel must be between 1 and "+ MAX_CHANNELS);
		}
		
		PacketHeader h = new PacketHeader(PacketHeader.commandPacket);
		PacketData data = new PacketData();
		//byte configBytes[]
		data.setData(0, (byte) config);
		data.setData(1, (byte) channel); // this will need work once we go to DMX standard!!
		data.setData(2, (byte) value);
		data.setData(3, (byte) 0xFF);

		PacketPayload payload = new PacketPayload(PacketCommand.SET, data);
		
		init(h, payload);
	}
	
	public void updateData(int config, int channel, int value) {
		if (config < 0 || config > MAX_CONFIGS) {
			throw new IllegalArgumentException("config must be between 0 and "+MAX_CONFIGS);
		}
		if (channel < 1 || channel > MAX_CHANNELS) {
			throw new IllegalArgumentException("channel must be between 1 and "+ MAX_CHANNELS);
		}
		
		payload.getData().setData(0, (byte) config);
		payload.getData().setData(1, (byte) channel);
		payload.getData().setData(2, (byte) value);
		
		reverifyPacket();
	}
}
