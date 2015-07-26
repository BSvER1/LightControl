package lightcontrol.control.serial.constructs;

public class PacketPayload {

	PacketCommand command;
	PacketData data;
	
	public PacketPayload(PacketCommand command, PacketData data) {
		this.command = command;
		this.data = data;
	}
	
	public PacketPayload(Character command, PacketData data) {
		this.command = new PacketCommand(command);
		this.data = data;
	}

	public PacketCommand getCommand() {
		return command;
	}

	public void setCommand(PacketCommand command) {
		this.command = command;
	}

	public PacketData getData() {
		return data;
	}

	public void setData(PacketData data) {
		this.data = data;
	}
	
	public boolean equals(PacketPayload other) {
		if (!command.equals(other.command)) return false;
		if (!data.equals(other.data)) return false;
		return true;
	}
}
