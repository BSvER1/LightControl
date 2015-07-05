import java.awt.Color;


public enum StripColor {
	
	OFF(0,0,0),
	WHITE(255,255,255),
	DEEP_RED(153,0,51),
	DARK_RED(204,00,51),
	BRIGHT_RED(255,51,0),
	ORANGE(255,102,0),
	BRIGHT_ORANGE(255,153,0),
	LIGHT_ORANGE(255,204,0),
	YELLOW(255,255,0),
	LIGHT_YELLOW(255,255,153),
	LIGHTEST_GREEN(204,255,204),
	LIGHT_GREEN(153,255,153),
	GREEN(51,255,102),
	BRIGHT_GREEN(0,255,51),
	DARK_GREEN(0,204,0),
	DEEP_GREEN(0,153,0),
	DARK_AQUA(0,153,102),
	DARK_BLUE_AQUA(0,102,153),
	NAVY_BLUE(0,51,153),
	DARK_BLUE(0,0,255),
	BLUE(0,153,255),
	LIGHT_BLUE(0,204,255),
	SKY_BLUE(0,255,255),
	SKY_PINK(204,204,255),
	LIGHT_PINK(204,153,255),
	PINK(204,102,255),
	BRIGHT_DARK_PINK(204,0,255),
	DARK_PINK(204,51,204),
	BRIGHT_PINK(255,51,255),
	BRIGHT_LIGHT_PINK(255,102,255),
	PURPLE(153,51,204),
	DARK_PURPLE(153,0,153);
	
	
	private final Integer r, g, b;
    private final String rgb;

    private StripColor(final Integer r, final Integer g, final Integer b) {
        this.r = r;
        this.g = g;
        this.b = b;
        this.rgb = r + ", " + g + ", " + b;
    }
    
    public int getRed() {
        return r;
    }

    public int getGreen() {
        return g;
    }

    public int getBlue() {
        return r;
    }

    public String toString() {
        return rgb;
    }
    
    public Color toColor() {
    	return new Color(r, g, b);
    }
}
