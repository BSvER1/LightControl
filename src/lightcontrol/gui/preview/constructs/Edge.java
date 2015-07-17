package lightcontrol.gui.preview.constructs;


public class Edge {

	public Vertex start, stop;
	public String id;
	
	public Edge(Vertex start, Vertex stop) {
		if (start.getID() > stop.getID()) {
			this.start = stop;
			this.stop = start;
		} else {
			this.start = start;
			this.stop = stop;
		}
		
		id = start.getName() + "-" + stop.getName();
	}
	
	public double getLength() {
		return Math.sqrt(
				Math.pow((start.getLoc().getX() - stop.getLoc().getX()),2)
				+ Math.pow((start.getLoc().getY() - stop.getLoc().getY()),2));
	}

	public Vertex getStart() {
		return start;
	}

	public void setStart(Vertex start) {
		this.start = start;
	}

	public Vertex getStop() {
		return stop;
	}

	public void setStop(Vertex stop) {
		this.stop = stop;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	
}
