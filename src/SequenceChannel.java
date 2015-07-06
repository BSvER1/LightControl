import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;


public class SequenceChannel {

	private final int BUTTONS_PER_BAR = 16;
	private final int ROW_OFFSET = 2;

	int channelNum;
	int numBars;

	JButton deleteChannelBtn, addBarBtn;
	JLabel lblStrip;
	JComboBox<String> stripSelectComboBox;
	DefaultComboBoxModel<String> stripSelectDropDown;

	LinkedList<JButton> sequence;

	public SequenceChannel(int channelNum) {
		this.channelNum = channelNum;
		
		setupDeleteChannelBtn();
		setupLblStrip();
		setupComboBox();
		setupFirstBar();
		setupAddBarButton();
	}
	
	private void setupComboBox() {
		stripSelectComboBox = new JComboBox<String>();
		setStripSelectComboBoxModel();
		stripSelectComboBox.setMaximumSize(new Dimension(150, 25));
		//LightControlWindow.sequencePanel.add(stripSelectComboBox, "flowx,cell 2 2,grow");
		String locFormat = "flowx,cell 2 "+(channelNum+ROW_OFFSET)+",grow";
		LightControlWindow.sequencePanel.add(stripSelectComboBox, locFormat);
	}
	
	private void setupDeleteChannelBtn() {
		deleteChannelBtn = new JButton("");
		deleteChannelBtn.setForeground(Color.RED);
		deleteChannelBtn.setBackground(Color.RED);
		deleteChannelBtn.setOpaque(true);
		deleteChannelBtn.setBorderPainted(false);
		deleteChannelBtn.setMaximumSize(new Dimension(15,15));
		String locFormat = "cell 0 "+(channelNum+ROW_OFFSET)+",alignx center,aligny center";
		//LightControlWindow.sequencePanel.add(deleteChannelBtn, "cell 0 2,alignx center,aligny center");
		LightControlWindow.sequencePanel.add(deleteChannelBtn, locFormat);
	}
	
	private void setupLblStrip() {
		lblStrip = new JLabel("Strip:");
		String locFormat = "cell 1 "+(channelNum+ROW_OFFSET)+",alignx center,aligny center";
		//LightControlWindow.sequencePanel.add(lblStrip, "cell 1 2,alignx center,aligny center");
		LightControlWindow.sequencePanel.add(lblStrip, locFormat);
	}
	
	private void setupFirstBar() {
		sequence = new LinkedList<JButton>();

		for (int i = 0; i < BUTTONS_PER_BAR; i++) {
			sequence.addLast(createSequenceButton());
			String locFormat = "cell "+(3+i)+" "+(channelNum+ROW_OFFSET)+",alignx center,aligny center";
			LightControlWindow.sequencePanel.add(sequence.getLast(), locFormat);
		}
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
		String locFormat = "cell 19 "+(channelNum+ROW_OFFSET);
		//sequencePanel.add(addBarBtn, "cell 19 2");
		LightControlWindow.sequencePanel.add(addBarBtn, locFormat);
	}
	
	private JButton createSequenceButton() {
		JButton patternButton = new JButton("");
		patternButton.setOpaque(true);
		patternButton.setBorderPainted(false);
		patternButton.setMaximumSize(new Dimension(25,25));
		patternButton.setBackground(StripColor.OFF.toColor());
		patternButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				((JButton) e.getSource()).setBackground(LightControlWindow.currentColor);
				
			}
		});
		
		return patternButton;
	}
	
	private void addBar() {
		for (int i = 0; i < BUTTONS_PER_BAR; i++) {

			sequence.addLast(createSequenceButton());
			String locFormat = "cell "+(3+BUTTONS_PER_BAR*numBars+i)+" "+(channelNum+ROW_OFFSET)+",alignx center,aligny center";
			LightControlWindow.sequencePanel.add(sequence.getLast(), locFormat);
			sequence.getLast().revalidate();
		}
		
		numBars++;
		
		LightControlWindow.sequencePanel.remove(addBarBtn);
		String locFormat = "cell "+(3+BUTTONS_PER_BAR*numBars)+" "+(channelNum+ROW_OFFSET)+",alignx center,aligny center";
		LightControlWindow.sequencePanel.add(addBarBtn, locFormat);
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

}
