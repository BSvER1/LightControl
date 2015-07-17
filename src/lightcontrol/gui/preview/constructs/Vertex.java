package lightcontrol.gui.preview.constructs;


public class Vertex {

	Location loc;
	String name;
	int id;
	
	public Vertex(int id, String name, Location loc) {
		this.name = name;
		this.loc = loc;
		this.id = id;
	}
	
	public Vertex(int id, Location loc) {
		name = "" + id;
		this.loc = loc;
		this.id = id;
	}

	public Location getLoc() {
		return loc;
	}

	public void setLoc(Location loc) {
		this.loc = loc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getID() {
		return id;
	}

	public void setID(int id) {
		this.id = id;
	}
	
	
}
