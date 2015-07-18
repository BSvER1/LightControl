package lightcontrol.control;

import java.awt.Color;
import java.util.ArrayList;

import lightcontrol.enums.StripID;

public class LightControlChannel {

	ArrayList<Color> notes;
	StripID[] hardwareChannels; //strips know their 'name' and hardware channel numbers.
	
	public LightControlChannel(StripID... strips) {
		notes = new ArrayList<Color>();
		hardwareChannels = strips;
	}
	
	public void addNoteAtEnd(Color col) {
		notes.add(col);
	}
	
	public void addNoteAtPos(Color col, int pos) {
		notes.add(pos, col);
	}
	
	public Color getColorAtPos(int pos) {
		return notes.get(pos);
	}
	
}
