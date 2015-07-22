package lightcontrol.gui.sequence.constructs;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.LinkedList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import lightcontrol.enums.StripColor;
import lightcontrol.enums.StripGroup;
import lightcontrol.enums.StripID;
import lightcontrol.gui.LightControlWindow;


public class SequenceChannel {

	private final int BUTTONS_PER_BAR = 32;
	private final int MAX_BARS = 16;
	private final int ROW_OFFSET = 2;

	private int channelNum; // indicated where the channel falls on the GUI layout not the hardware channel number
	private int numBars = 0;
	private StripID hardwareChannel[];

	private JButton deleteChannelBtn;
	private JButton addBarBtn;
	private JLabel lblStrip;
	private JComboBox<String> stripSelectComboBox;
	private DefaultComboBoxModel<String> stripSelectDropDown;

	private LinkedList<SequenceChannelBar> sequence;

	public SequenceChannel(int channelNum) {
		this.channelNum = channelNum;
		hardwareChannel = new StripID[] {};
		
		setupDeleteChannelBtn();
		setupLblStrip();
		setupComboBox();
		setupFirstBar();
		setupAddBarButton();
	}
	
	private void setupComboBox() {
		stripSelectComboBox = new JComboBox<String>();
		setStripSelectComboBoxModel();
		stripSelectComboBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					//TODO
					if (StripGroup.getStripGroup(stripSelectComboBox.getSelectedItem().toString()) != null) {
						hardwareChannel = StripGroup.getStripGroup(stripSelectComboBox.getSelectedItem().toString()).getStrips();
					} else if (StripID.getStrip(stripSelectComboBox.getSelectedItem().toString()) != null) {
						hardwareChannel = StripID.getStrip(stripSelectComboBox.getSelectedItem().toString());
					} else {
						hardwareChannel = new StripID[] {};
						System.err.println("managed to set strip selector to a value not in the groups or strips. set to be an array of length zero.");
					}
				}
			}
			
		});
		
		stripSelectComboBox.setMaximumSize(new Dimension(150, 25));
		//LightControlWindow.sequencePanel.add(stripSelectComboBox, "flowx,cell 2 2,grow");
		String locFormat = "flowx,cell 2 "+(channelNum+ROW_OFFSET)+",grow";
		LightControlWindow.getSequencePanel().add(stripSelectComboBox, locFormat);
	}
	
	private void setupDeleteChannelBtn() {
		deleteChannelBtn = new JButton("");
		deleteChannelBtn.setForeground(Color.RED);
		deleteChannelBtn.setBackground(Color.RED);
		deleteChannelBtn.setOpaque(true);
		deleteChannelBtn.setBorderPainted(false);
		deleteChannelBtn.setMaximumSize(new Dimension(15,15));
		deleteChannelBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog((Component) null, "Are you sure you want to reset this channel?!",
				        "Confirm Channel Reset", JOptionPane.OK_CANCEL_OPTION);
				if (result != 0) return;
				
				stripSelectComboBox.setSelectedIndex(0);
				for (int i = 0; i < sequence.size(); i++) {
					SequenceChannelBar bar = sequence.get(i);
					for (int j = 0; j < bar.getNumButtons(); j++) {
						//remove buttons from display
						bar.getBarButtons().get(j).removeMouseListener(bar.getBarButtons().get(j).getMouseListeners()[0]);
						LightControlWindow.getSequencePanel().remove(bar.getBarButtons().get(j));
					}
					
				}
				sequence = new LinkedList<SequenceChannelBar>();
				addBarBtn.removeActionListener(addBarBtn.getActionListeners()[0]);
				LightControlWindow.getSequencePanel().remove(addBarBtn);
				setupFirstBar();
				setupAddBarButton();
				
			}
		});
		String locFormat = "cell 0 "+(channelNum+ROW_OFFSET)+",alignx center,aligny center";
		//LightControlWindow.sequencePanel.add(deleteChannelBtn, "cell 0 2,alignx center,aligny center");
		LightControlWindow.getSequencePanel().add(deleteChannelBtn, locFormat);
	}
	
	private void setupLblStrip() {
		lblStrip = new JLabel("Strip:");
		String locFormat = "cell 1 "+(channelNum+ROW_OFFSET)+",alignx center,aligny center";
		//LightControlWindow.sequencePanel.add(lblStrip, "cell 1 2,alignx center,aligny center");
		LightControlWindow.getSequencePanel().add(lblStrip, locFormat);
	}
	
	private void setupFirstBar() {
		sequence = new LinkedList<SequenceChannelBar>();

		SequenceChannelBar newBar = new SequenceChannelBar(channelNum, numBars);
		newBar.createButton(3, SequenceChannelBar.MAX_SEGMENTS, StripColor.OFF.toColor());
		sequence.addLast(newBar);
		//String locFormat = "cell "+(3+i)+" "+(channelNum+ROW_OFFSET)+",alignx center,aligny center";
		//LightControlWindow.sequencePanel.add(sequence.getLast().getBarButtons().peek(), locFormat);
		
		numBars = 1;
	}
	
	private void setupAddBarButton() {
		addBarBtn = new JButton("Add bar");
		addBarBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addBar();
			}
			
		});
		String locFormat = "cell "+(3+BUTTONS_PER_BAR*numBars) + " " + (channelNum+ROW_OFFSET);
		//sequencePanel.add(addBarBtn, "cell 19 2");
		LightControlWindow.getSequencePanel().add(addBarBtn, locFormat);
	}
	
	private void addBar() {
		LightControlWindow.getSequencePanel().remove(addBarBtn);
		SequenceChannelBar newBar = new SequenceChannelBar(channelNum, numBars);
		newBar.createButton(3+BUTTONS_PER_BAR*numBars, SequenceChannelBar.MAX_SEGMENTS, StripColor.OFF.toColor());
		sequence.addLast(newBar);
		
		numBars++;
		
		if (numBars == MAX_BARS) return;
		
		setupAddBarButton();
		addBarBtn.revalidate();
	}

	private void setStripSelectComboBoxModel() {

		stripSelectDropDown = new DefaultComboBoxModel<String>();

		stripSelectDropDown.addElement("Unassigned");
		stripSelectDropDown.addElement("----- Groups -----");

		//add groups
		for (int i = 0; i < StripGroup.values().length; i++) {
			stripSelectDropDown.addElement(StripGroup.values()[i].name());
		}

		stripSelectDropDown.addElement("----- Strips -----");

		//add strips
		for (int i = 0; i < StripID.values().length; i++) {
			stripSelectDropDown.addElement(StripID.values()[i].name());
		}

		stripSelectComboBox.setModel(stripSelectDropDown);
		stripSelectComboBox.setSelectedIndex(0);
	}
	
	public String exportChannel() {
		if (hardwareChannel.length > 0) {
			String channel = ""+stripSelectComboBox.getSelectedItem().toString()+" ";
			for (int i = 0; i < hardwareChannel.length-1; i++) {
				channel = channel.concat(""+hardwareChannel[i]+",");
			}
			channel = channel.concat(hardwareChannel[hardwareChannel.length-1]+" "); //should only error when there is no channel set. this is what we want.
			for (int i = 0; i < sequence.size(); i++) {
				channel = channel.concat(sequence.get(i).exportBar());
			}
			channel = channel.concat(System.lineSeparator());
			return channel;
		} else {
			return "";
		}
	}
	
	public void importChannel() {
		//TODO similar to addBar
	}

	public JButton getAddBarBtn() {
		return addBarBtn;
	}

}
