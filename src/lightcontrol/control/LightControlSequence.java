package lightcontrol.control;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import lightcontrol.control.serial.packets.PacketSet;
import lightcontrol.control.serial.packets.PacketSwitch;
import lightcontrol.enums.StripColor;
import lightcontrol.enums.StripID;
import lightcontrol.gui.LightControlWindow;
import lightcontrol.gui.TimingsThread;

public class LightControlSequence {
	
	ArrayList<LightControlChannel> channels;
	String fileName;
	
	int currentBar = -1;

	static int numBars;
	static int barTimeout = 3000; // 3 seconds
	static long lastBarTime, secLastTap;
	static double avgTime = 0;
	static double avgMillis;
	
	PacketSet toSend;
	PacketSwitch newCfg;
	
	public LightControlSequence(File lcs) {
		channels = new ArrayList<LightControlChannel>();
		fileName = lcs.getName();
		initChannels(lcs);
		
		toSend = new PacketSet(0,1,0);
		newCfg = new PacketSwitch(0);
		
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
					//System.out.println("passing " +barPos +" ["+barPos%channels.get(i).notes.size()+" of "+ channels.get(i).notes.size()+"]");
					if (LightControlWindow.getLightData().getStrip(channels.get(i).hardwareChannels[j].getValue())!= null) {
						LightControlWindow.getLightData().getStrip(channels.get(i).hardwareChannels[j].getValue()).setStripColor(channels.get(i).getColorAtPos((barPos%channels.get(i).notes.size())));
					}
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
		if (barPos != currentBar) {
			currentBar = barPos;
			
			calcTime();
			
			//System.out.println("returning "+avgTime);
			if (avgTime < 2.65) { // set to avgTime for 150 bpm
				return;
			}
			
			//System.out.println("Playing");
			//
			newCfg.setConfigData((barPos)%4);
			LightControlWindow.sc.sendMessage(newCfg);
			
			for (int i = 0; i < channels.size(); i++) {
				for (int j = 0; j < channels.get(i).hardwareChannels.length; j++) {
					if (!channels.get(i).getColorAtPos(((barPos+1)%channels.get(i).notes.size())).equals(
							StripColor.DONT_UPDATE.toColor())) {
						//System.out.println("passing " +barPos +" ["+barPos%channels.get(i).notes.size()+" of "+ channels.get(i).notes.size()+"]");
						//LightControlWindow.getLightData().getStrip(channels.get(i).hardwareChannels[j].getValue()).setStripColor(channels.get(i).getColorAtPos((barPos%channels.get(i).notes.size())));
						
						//
						toSend.updateData((barPos+1)%4, channels.get(i).hardwareChannels[j].getRedChannel(),
								channels.get(i).getColorAtPos((barPos+1)%channels.get(i).notes.size()).getRed());
						LightControlWindow.sc.sendMessage(toSend);
						
						toSend.updateData((barPos+1)%4, channels.get(i).hardwareChannels[j].getGreenChannel(),
								channels.get(i).getColorAtPos((barPos+1)%channels.get(i).notes.size()).getGreen());
						LightControlWindow.sc.sendMessage(toSend);
						
						toSend.updateData((barPos+1)%4, channels.get(i).hardwareChannels[j].getBlueChannel(),
								channels.get(i).getColorAtPos((barPos+1)%channels.get(i).notes.size()).getBlue());
						LightControlWindow.sc.sendMessage(toSend);
						
						calcTime();
					}
				}
			}
		}
		
	}
	
	private void calcTime() {
		if (System.currentTimeMillis()-lastBarTime > barTimeout) {
			lastBarTime = System.currentTimeMillis();
			numBars = 1;
		} else if (numBars > 600) {
			numBars = 600;
		}
		avgTime = ((numBars-1) * avgTime + (System.currentTimeMillis() - lastBarTime))/numBars;
		lastBarTime = System.currentTimeMillis();
		numBars++;
	}
	
	public void playAndPreview(int barPos) {
		//play(barPos);
		preview(barPos);
	}
	
}
