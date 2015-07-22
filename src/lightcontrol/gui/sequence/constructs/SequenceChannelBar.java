package lightcontrol.gui.sequence.constructs;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import lightcontrol.gui.LightControlWindow;

public class SequenceChannelBar {

	public final static int MAX_SEGMENTS = 32;
	private final int ROW_OFFSET = 2;
	private final int COL_OFFSET = 3;
	
	private int channelNum;
	private int barNum;
	
	private LinkedList<SequenceChannelBarButton> barBtns;
	
	public SequenceChannelBar(int channelNum, int barNum) {
		barBtns = new LinkedList<SequenceChannelBarButton>();
		this.channelNum = channelNum;
		this.barNum = barNum;
	}

	public LinkedList<SequenceChannelBarButton> getBarButtons() {
		return barBtns;
	}
	
	public int getNumButtons() {
		return barBtns.size();
	}
	
	public int getBarStartPos() {
		if (barBtns.isEmpty()) return 0;
		return barBtns.peek().getPosition();
	}
	
	public void createButton(int barStartPos, int length, Color col) {
		SequenceChannelBarButton btn = new SequenceChannelBarButton(barStartPos, length);
		btn.setOpaque(true);
		btn.setBorderPainted(false);
		btn.setBackground(col);
		if (length < MAX_SEGMENTS/4) btn.setMaximumSize(new Dimension(length*13, 30));
		btn.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent me) {
				if (me.getButton() == MouseEvent.BUTTON3) { //Should be right mouse button
					if (length != 1) {
						JPopupMenu menu = new JPopupMenu();
						JMenuItem split = new JMenuItem("Split");
						split.addActionListener(new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent ae) {
								btn.setVisible(false);
								//TODO
								createButton(btn.getPosition(), btn.getLength()/2, btn.getBackground());
								createButton(btn.getPosition()+btn.getLength()/2, btn.getLength()/2, btn.getBackground());
								//remove button
								LightControlWindow.getSequencePanel().remove(btn);
								removeFromList(btn);

							}

						});
						menu.add(split);
						menu.show(btn, me.getX(), me.getY());
					}
				}
				
				else {
					btn.setBackground(LightControlWindow.getCurrentColor());
				}
			}
			
		});
		
		//add barBtns to list in order
		addToList(btn);
		//add buttons to the GUI
		String layout = "cell "+(btn.getPosition())+" "+(channelNum+ROW_OFFSET)+" "+btn.getLength()+" 1,grow";
		//System.out.println("Adding button with layout "+layout);
		LightControlWindow.getSequencePanel().add(btn, layout);
	}
	
	private void addToList(SequenceChannelBarButton btn) {
		if (barBtns.isEmpty()) {
			barBtns.add(btn);
			//System.out.println("bar is empty, adding button");
		} else {
			for (int i = 0; i < barBtns.size(); i++) {
				if (btn.getPosition() < barBtns.get(i).getPosition()) {
					barBtns.add(i, btn);
					//System.out.println("Adding button "+ (i+1) + " of " + barBtns.size());
					return;
				}
			}
			//System.out.println("didnt add button before any others, adding it at the back");
			barBtns.addLast(btn);
		}
	}
	
	private void removeFromList(SequenceChannelBarButton btn) {
		//System.out.println("Removing btn at pos " + btn.getPosition()+". "+(barBtns.size()-1)+" btns remain.");
		barBtns.remove(btn);
	}
	
	private Color getColorAtPos(int pos) {
		for (int i = 0; i < barBtns.size(); i++) {
			if ((barBtns.get(i).getPosition() + barBtns.get(i).getLength() - COL_OFFSET) > pos) {
				return barBtns.get(i).getBackground();
			}
		}
		System.err.println("Couldnt find the colour of the button, returning null!");
		return null;
	}
	
	//export to a .lcs
	public String exportBar() {
		String bar = "";
		for (int j = 0; j < MAX_SEGMENTS; j++) {
			bar = bar.concat(""+getColorAtPos(j).getRed()+","+getColorAtPos(j).getGreen()+","+getColorAtPos(j).getBlue()+" ");
		}
		return bar;
	}
	
	
}
