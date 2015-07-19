package lightcontrol.control;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import lightcontrol.enums.StripColor;
import lightcontrol.enums.StripID;
import lightcontrol.gui.LightControlWindow;

public class LightControlSequence {
	
	ArrayList<LightControlChannel> channels;
	String fileName;
	
	public LightControlSequence(File lcs) {
		channels = new ArrayList<LightControlChannel>();
		fileName = lcs.getName();
		initChannels(lcs);
	}
	
	/** 
	 * takes in a .lcs file and is able to read it into the appropriate data structures. (not for sequence editing). 
	 * */
	private void initChannels(File lcs) {
		//selected item name | stripID values | r,g,b | r,g,b | r,g,b | ...
		try(BufferedReader br = new BufferedReader(new FileReader(lcs))) {
		    for(String line; (line = br.readLine()) != null; ) {
		        // process the line.
		    	if (line.length() > 0) {
		    		String[] result = line.split("\\s+");
		    		//result[0] is friendly strip name
		    		//result[1] is comma separated list of StripIDs
		    		String[] stripStrings = result[1].split(",");
		    		StripID[] stripIDs = new StripID[stripStrings.length];
		    		for (int i = 0; i < stripStrings.length; i++) {
		    			stripIDs[i] = StripID.getStrip(stripStrings[i])[0];
		    		}
		    		//further entried in result are sets of r,g,b values
		    		LightControlChannel ch = new LightControlChannel(stripIDs);
		    		String[] colTrips;
		    		for (int i = 2; i < result.length; i++) {
		    			colTrips = result[i].split(",");
		    			Color col = new Color(Integer.valueOf(colTrips[0]), Integer.valueOf(colTrips[1]), Integer.valueOf(colTrips[2]));
		    			ch.addNoteAtEnd(col);
		    		}
		    		//channels.add()
		    		channels.add(ch);
		    	}
		    }
		    
		    br.close();
		} catch (IOException e) {
			System.err.println("Something went wrong when reading file "+ lcs.getAbsolutePath());
		}
	}
	
	/**
	 * sets the preview window to be the values contained in this sequence at given position
	 * @param barPos takes "EIGHTH" values from TimingsThread (eighth of a beat, 32nd of a bar) with bar data included
	 */
	public void preview(int barPos) {
		for (int i = 0; i < channels.size(); i++) {
			for (int j = 0; j < channels.get(i).hardwareChannels.length; j++) {
				if (!channels.get(i).getColorAtPos((barPos%channels.get(i).notes.size())).equals(StripColor.DONT_UPDATE.toColor())) {
					//System.out.println(channels.get(i).getColorAtPos(barPos%channels.get(i).notes.size()).toString());
					LightControlWindow.getLightData().getStrip(channels.get(i).hardwareChannels[j].getValue()).setStripColor(channels.get(i).getColorAtPos((barPos%channels.get(i).notes.size())));
				}
			}
		}
	}
	
	public String getFriendlyName() {
		return fileName.substring(0,fileName.length()-4);
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public int getNumNotes() {
		int maxLength = 0;
		for (int i = 0; i < channels.size(); i++) {
			if (channels.get(i).notes.size() > maxLength) {
				maxLength = channels.get(i).notes.size();
			}
		}
		return maxLength;
	}
	
	/**
	 * sets the display to be the values contained in this sequence at given position
	 * @param barPos takes "EIGHTH" values from TimingsThread (eighth of a beat, 32nd of a bar)
	 */
	public void play(int barPos) {
		//TODO
	}
	
	public void playAndPreview(int barPos) {
		play(barPos);
		preview(barPos);
	}
	
}
