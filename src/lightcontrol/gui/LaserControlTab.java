package lightcontrol.gui;

import java.awt.BorderLayout;
import java.util.Hashtable;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

@SuppressWarnings("serial")
public class LaserControlTab extends JPanel {
	private JTextField laserChannelSelect;
	
	private static JSlider[] slider;

	/**
	 * Create the panel.
	 */
	public LaserControlTab() {
		slider = new JSlider[19];
		
		setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		JPanel sliderPanel = new JPanel();
		scrollPane.setViewportView(sliderPanel);
		sliderPanel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("16px"),
				ColumnSpec.decode("166px"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("pref:grow"),},
			new RowSpec[] {
				FormSpecs.PARAGRAPH_GAP_ROWSPEC,
				RowSpec.decode("55px"),
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				RowSpec.decode("55px"),
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				RowSpec.decode("55px"),
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				RowSpec.decode("55px"),
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				RowSpec.decode("55px"),
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				RowSpec.decode("55px"),
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				RowSpec.decode("55px"),
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				RowSpec.decode("55px"),
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				RowSpec.decode("55px"),
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				RowSpec.decode("55px"),
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				RowSpec.decode("55px"),
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				RowSpec.decode("55px"),
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				RowSpec.decode("55px"),
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				RowSpec.decode("55px"),
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				RowSpec.decode("55px"),
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				RowSpec.decode("55px"),
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				RowSpec.decode("55px"),
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				RowSpec.decode("55px"),
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				RowSpec.decode("55px"),
				FormSpecs.LABEL_COMPONENT_GAP_ROWSPEC,
				RowSpec.decode("55px"),}));
		
		JLabel laserChannelLbl = new JLabel("Laser Channel");
		sliderPanel.add(laserChannelLbl, "2, 2, fill, fill");
		
		laserChannelSelect = new JTextField();
		laserChannelSelect.setText("1");
		sliderPanel.add(laserChannelSelect, "4, 2, fill, fill");
		laserChannelSelect.setColumns(10);
		
		JLabel channel1Lbl = new JLabel("Operation Mode");
		sliderPanel.add(channel1Lbl, "2, 4, fill, fill");
		
		slider[0] = new JSlider();
		slider[0].setSnapToTicks(true);
		slider[0].setValue(0);
		slider[0].setMaximum(255);
		slider[0].setPaintLabels(true);
		slider[0].setPaintTicks(true);
		
		Hashtable<Integer, JLabel> table0 = new Hashtable<Integer, JLabel>();
	    table0.put (0, new JLabel("OFF"));
	    table0.put (70, new JLabel("Auto Grating"));
	    table0.put (120, new JLabel("Auto"));
	    table0.put (170, new JLabel("Sound Grating"));
	    table0.put (220, new JLabel("Grating"));
	    table0.put (255, new JLabel("DMX Mode"));
	    slider[0].setLabelTable(table0);
	    
		sliderPanel.add(slider[0], "4, 4, fill, fill");
		
		JLabel channel2Lbl = new JLabel("Bank Number");
		sliderPanel.add(channel2Lbl, "2, 6, fill, fill");
		
		slider[1] = new JSlider();
		
		Hashtable<Integer, JLabel> table1 = new Hashtable<Integer, JLabel>();
	    table1.put (0, new JLabel("Bank 1"));
	    table1.put (75, new JLabel("Bank 2"));
	    table1.put (125, new JLabel("Bank 3"));
	    table1.put (175, new JLabel("Bank 4"));
	    table1.put (225, new JLabel("Bank 5"));
	    slider[1].setLabelTable(table1);
		
		slider[1].setSnapToTicks(true);
		slider[1].setValue(0);
		slider[1].setMaximum(255);
		slider[1].setPaintLabels(true);
		slider[1].setPaintTicks(true);
		sliderPanel.add(slider[1], "4, 6, fill, fill");
		
		JLabel channel3lbl = new JLabel("Bank Element");
		sliderPanel.add(channel3lbl, "2, 8, fill, fill");
		
		slider[2] = new JSlider();
		
		Hashtable<Integer, JLabel> table2 = new Hashtable<Integer, JLabel>();
	    table2.put (8, new JLabel("1"));
	    table2.put (22, new JLabel("2"));
	    table2.put (40, new JLabel("3"));
	    table2.put (55, new JLabel("4"));
	    table2.put (70, new JLabel("5"));
	    table2.put (88, new JLabel("6"));
	    table2.put (104, new JLabel("7"));
	    table2.put (120, new JLabel("8"));
	    table2.put (132, new JLabel("9"));
	    table2.put (152, new JLabel("10"));
	    table2.put (166, new JLabel("11"));
	    table2.put (184, new JLabel("12"));
	    table2.put (198, new JLabel("13"));
	    table2.put (214, new JLabel("14"));
	    table2.put (230, new JLabel("15"));
	    table2.put (250, new JLabel("16"));
	    slider[2].setLabelTable(table2);
		
		slider[2].setSnapToTicks(true);
		slider[2].setValue(0);
		slider[2].setMaximum(255);
		slider[2].setPaintLabels(true);
		slider[2].setPaintTicks(true);
		sliderPanel.add(slider[2], "4, 8, fill, fill");
		
		JLabel channel4lbl = new JLabel("Colour");
		sliderPanel.add(channel4lbl, "2, 10, fill, fill");
		
		slider[3] = new JSlider();
		
		Hashtable<Integer, JLabel> table3 = new Hashtable<Integer, JLabel>();
	    table3.put (0, new JLabel("Original"));
	    table3.put (12, new JLabel("Red"));
	    table3.put (20, new JLabel("Green"));
	    table3.put (28, new JLabel("Yellow"));
	    table3.put (35, new JLabel("Blue"));
	    table3.put (43, new JLabel("Pink"));
	    table3.put (52, new JLabel("Cyan"));
	    table3.put (60, new JLabel("White"));
	    table3.put (90, new JLabel("Rolling"));
	    table3.put (130, new JLabel("Jumping"));
	    table3.put (180, new JLabel("Moving"));
	    table3.put (250, new JLabel("Strobing"));
	    slider[3].setLabelTable(table3);
		
		slider[3].setSnapToTicks(true);
		slider[3].setValue(0);
		slider[3].setMaximum(255);
		slider[3].setPaintLabels(true);
		slider[3].setPaintTicks(true);
		sliderPanel.add(slider[3], "4, 10, fill, fill");
		
		JLabel channel5Lbl = new JLabel("Draw Speed");
		sliderPanel.add(channel5Lbl, "2, 12, fill, fill");
		
		slider[4] = new JSlider();
		
		Hashtable<Integer, JLabel> table4 = new Hashtable<Integer, JLabel>();
	    table4.put (0, new JLabel("No Clipping"));
	    table4.put (75, new JLabel("0% - 99% Clipping"));
	    table4.put (200, new JLabel("Variable Speed Clipping"));
	    slider[4].setLabelTable(table4);
		
		slider[4].setSnapToTicks(true);
		slider[4].setValue(0);
		slider[4].setMaximum(255);
		slider[4].setPaintLabels(true);
		slider[4].setPaintTicks(true);
		sliderPanel.add(slider[4], "4, 12, fill, fill");
		
		JLabel channel6Lbl = new JLabel("Zoom");
		sliderPanel.add(channel6Lbl, "2, 14, fill, fill");
		
		slider[5] = new JSlider();
		
		Hashtable<Integer, JLabel> table5 = new Hashtable<Integer, JLabel>();
	    table5.put (0, new JLabel("100% - 5% fixed zoom"));
	    table5.put (140, new JLabel("Zoom In"));
	    table5.put (190, new JLabel("Zoom Out"));
	    table5.put (225, new JLabel("Alternate In / Out"));
	    slider[5].setLabelTable(table5);
		
		slider[5].setSnapToTicks(true);
		slider[5].setValue(0);
		slider[5].setMaximum(255);
		slider[5].setPaintLabels(true);
		slider[5].setPaintTicks(true);
		sliderPanel.add(slider[5], "4, 14, fill, fill");
		
		JLabel channel7Lbl = new JLabel("Zoom Speed");
		sliderPanel.add(channel7Lbl, "2, 16, fill, fill");
		
		slider[6] = new JSlider();
		
		Hashtable<Integer, JLabel> table6 = new Hashtable<Integer, JLabel>();
	    table6.put (0, new JLabel("Fast"));
	    table6.put (255, new JLabel("Slow"));
	    slider[6].setLabelTable(table6);
		
		slider[6].setSnapToTicks(true);
		slider[6].setValue(0);
		slider[6].setMaximum(255);
		slider[6].setPaintLabels(true);
		slider[6].setPaintTicks(true);
		sliderPanel.add(slider[6], "4, 16, fill, fill");
		
		JLabel channel8Lbl = new JLabel("X-Scale");
		sliderPanel.add(channel8Lbl, "2, 18, fill, fill");
		
		slider[7] = new JSlider();
		
		Hashtable<Integer, JLabel> table7 = new Hashtable<Integer, JLabel>();
	    table7.put (0, new JLabel("0 - 350 degree fixed roll"));
	    table7.put (225, new JLabel("Variable Speed Mode"));
	    slider[7].setLabelTable(table7);
		
		slider[7].setSnapToTicks(true);
		slider[7].setValue(0);
		slider[7].setMaximum(255);
		slider[7].setPaintLabels(true);
		slider[7].setPaintTicks(true);
		sliderPanel.add(slider[7], "4, 18, fill, fill");
		
		JLabel channel9Lbl = new JLabel("X-Scale Speed");
		sliderPanel.add(channel9Lbl, "2, 20, fill, fill");
		
		slider[8] = new JSlider();
	
	    slider[8].setLabelTable(table6);
		
		slider[8].setSnapToTicks(true);
		slider[8].setValue(0);
		slider[8].setMaximum(255);
		slider[8].setPaintLabels(true);
		slider[8].setPaintTicks(true);
		sliderPanel.add(slider[8], "4, 20, fill, fill");
		
		JLabel channel10Lbl = new JLabel("Y-Scale");
		sliderPanel.add(channel10Lbl, "2, 22, fill, fill");
		
		slider[9] = new JSlider();
		
	    slider[9].setLabelTable(table7);
		
		slider[9].setSnapToTicks(true);
		slider[9].setValue(0);
		slider[9].setMaximum(255);
		slider[9].setPaintLabels(true);
		slider[9].setPaintTicks(true);
		sliderPanel.add(slider[9], "4, 22, fill, fill");
		
		JLabel channel11Lbl = new JLabel("Y-Scale Speed");
		sliderPanel.add(channel11Lbl, "2, 24, fill, fill");
		
		slider[10] = new JSlider();
		
	    slider[10].setLabelTable(table6);
		
		slider[10].setSnapToTicks(true);
		slider[10].setValue(0);
		slider[10].setMaximum(255);
		slider[10].setPaintLabels(true);
		slider[10].setPaintTicks(true);
		sliderPanel.add(slider[10], "4, 24, fill, fill");
		
		JLabel channel12Lbl = new JLabel("Rotation / Draw Start");
		sliderPanel.add(channel12Lbl, "2, 26, fill, fill");
		
		slider[11] = new JSlider();
		slider[11].setSnapToTicks(true);
		slider[11].setValue(0);
		slider[11].setMaximum(255);
		slider[11].setPaintLabels(true);
		slider[11].setPaintTicks(true);
		sliderPanel.add(slider[11], "4, 26, fill, fill");
		
		JLabel channel13Lbl = new JLabel("Rotation Speed");
		sliderPanel.add(channel13Lbl, "2, 28, fill, fill");
		
		slider[12] = new JSlider();
		
	    slider[12].setLabelTable(table6);
		
		slider[12].setSnapToTicks(true);
		slider[12].setValue(0);
		slider[12].setMaximum(255);
		slider[12].setPaintLabels(true);
		slider[12].setPaintTicks(true);
		sliderPanel.add(slider[12], "4, 28, fill, fill");
		
		JLabel channel14Lbl = new JLabel("X-Location");
		sliderPanel.add(channel14Lbl, "2, 30, fill, fill");
		
		slider[13] = new JSlider();
		slider[13].setSnapToTicks(true);
		slider[13].setValue(0);
		slider[13].setMaximum(255);
		slider[13].setPaintLabels(true);
		slider[13].setPaintTicks(true);
		sliderPanel.add(slider[13], "4, 30, fill, fill");
		
		JLabel channel15Lbl = new JLabel("X-Translation Speed");
		sliderPanel.add(channel15Lbl, "2, 32, fill, fill");
		
		slider[14] = new JSlider();
		
	    slider[14].setLabelTable(table6);
		
		slider[14].setSnapToTicks(true);
		slider[14].setValue(0);
		slider[14].setMaximum(255);
		slider[14].setPaintLabels(true);
		slider[14].setPaintTicks(true);
		sliderPanel.add(slider[14], "4, 32, fill, fill");
		
		JLabel channel16Lbl = new JLabel("Y-Location");
		sliderPanel.add(channel16Lbl, "2, 34, fill, fill");
		
		slider[15] = new JSlider();
		slider[15].setSnapToTicks(true);
		slider[15].setValue(0);
		slider[15].setMaximum(255);
		slider[15].setPaintLabels(true);
		slider[15].setPaintTicks(true);
		sliderPanel.add(slider[15], "4, 34, fill, fill");
		
		JLabel channel17Lbl = new JLabel("Y-Translation Speed");
		sliderPanel.add(channel17Lbl, "2, 36, fill, fill");
		
		slider[16] = new JSlider();
		
	    slider[16].setLabelTable(table6);
	    
		slider[16].setSnapToTicks(true);
		slider[16].setValue(0);
		slider[16].setMaximum(255);
		slider[16].setPaintLabels(true);
		slider[16].setPaintTicks(true);
		sliderPanel.add(slider[16], "4, 36, fill, fill");
		
		JLabel channel18Lbl = new JLabel("Diffraction Mode");
		sliderPanel.add(channel18Lbl, "2, 38, fill, fill");
		
		slider[17] = new JSlider();
		
		Hashtable<Integer, JLabel> table17 = new Hashtable<Integer, JLabel>();
	    table17.put (0, new JLabel("Grating OFF"));
	    table17.put (255, new JLabel("Grating ON"));
	    slider[17].setLabelTable(table17);
		
		slider[17].setSnapToTicks(true);
		slider[17].setValue(0);
		slider[17].setMaximum(255);
		slider[17].setPaintLabels(true);
		slider[17].setPaintTicks(true);
		sliderPanel.add(slider[17], "4, 38, fill, fill");
		
		JLabel channel19Lbl = new JLabel("Diffraction Rotation Speed");
		sliderPanel.add(channel19Lbl, "2, 40, fill, fill");
		
		slider[18] = new JSlider();
		
		Hashtable<Integer, JLabel> table18 = new Hashtable<Integer, JLabel>();
	    table18.put (0, new JLabel("No Rotation"));
	    table18.put (70, new JLabel("Clockwise Grating Rotation"));
	    table18.put (130, new JLabel("No Rotation"));
	    table18.put (180, new JLabel("Anti-Clockwise Grating Rotation"));
	    slider[18].setLabelTable(table18);
		
		slider[18].setSnapToTicks(true);
		slider[18].setValue(0);
		slider[18].setMaximum(255);
		slider[18].setPaintLabels(true);
		slider[18].setPaintTicks(true);
		sliderPanel.add(slider[18], "4, 40, fill, fill");

	}

	public static JSlider getSlider(int channel) {
		return slider[channel];
	}

}
