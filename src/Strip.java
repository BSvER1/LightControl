import java.awt.Color;


public class Strip extends Edge {

	private final static int RED = 0;
	private final static int GREEN = 1;
	private final static int BLUE = 2;
	
	boolean isRGB;
	int[] value;
	int channel;
	
	public Strip(Edge edge) {
		super(edge.getStart(), edge.getStop());
		value = new int[3];
		setOff();
		
	}
	
	public void setStripColor(int red, int green, int blue) {
		if (red < 0 || red > 255
				|| green < 0 || green > 255
				|| blue < 0 || blue > 255) {
			System.err.println("Strip was passed an invalid colour! - no change was made.");
			return;
		}
		
		value[RED] = red;
		value[GREEN] = green;
		value[BLUE] = blue;
	}
	
	public void setStripColor(StripColor col) {
		value[RED] = col.getRed();
		value[GREEN] = col.getGreen();
		value[BLUE] = col.getBlue();
	}
	
	public void setStripColor(Color col) {
		value[RED] = col.getRed();
		value[GREEN] = col.getGreen();
		value[BLUE] = col.getBlue();
	}
	
	public void setOff() {
		value[RED] = 0;
		value[GREEN] = 0;
		value[BLUE] = 0;
	}
	
	public Color getStripColor() {
		return new Color(value[RED], value[GREEN], value[BLUE]);
	}
	
	public int getRedValue() {
		return value[RED];
	}
	
	public int getGreenValue() {
		return value[GREEN];
	}
	
	public int getBlueValue() {
		return value[BLUE];
	}

	
	
	

}
