import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.AbstractListModel;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;

import net.miginfocom.swing.MigLayout;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.Font;
import javax.swing.JToggleButton;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JTextField;



public class ControlWindow {

	private JFrame frame;
	private JTextField bpmDisplay;
	private JTextField currentSequenceDisplay;
	private JTextField nextSequenceDisplay;

	/**
	 * Launch the application.
	 */
	public static void main2(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ControlWindow window = new ControlWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ControlWindow() {
		initialize();
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("serial")
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBackground(Color.WHITE);
		frame.setBounds(0, 0, 1500, 900);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		
		JTabbedPane viewTabs = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(viewTabs);
		
		JPanel launchpadViewTab = new JPanel();
		viewTabs.addTab("Launchpad View", null, launchpadViewTab, null);
		launchpadViewTab.setLayout(new BoxLayout(launchpadViewTab, BoxLayout.X_AXIS));
		
		JSplitPane split_padPreviewRecent = new JSplitPane();
		split_padPreviewRecent.setResizeWeight(1.0);
		split_padPreviewRecent.setEnabled(false);
		split_padPreviewRecent.setAlignmentY(Component.CENTER_ALIGNMENT);
		split_padPreviewRecent.setAlignmentX(Component.CENTER_ALIGNMENT);
		launchpadViewTab.add(split_padPreviewRecent);
		
		JPanel launchpadPadPanel = new JPanel();
		split_padPreviewRecent.setLeftComponent(launchpadPadPanel);
		launchpadPadPanel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("5px"),
				ColumnSpec.decode("96px"),
				ColumnSpec.decode("96px"),
				ColumnSpec.decode("96px"),
				ColumnSpec.decode("96px"),
				ColumnSpec.decode("96px"),
				ColumnSpec.decode("96px"),
				ColumnSpec.decode("96px"),
				ColumnSpec.decode("96px"),},
			new RowSpec[] {
				RowSpec.decode("5px"),
				RowSpec.decode("96px"),
				RowSpec.decode("96px"),
				RowSpec.decode("96px"),
				RowSpec.decode("96px"),
				RowSpec.decode("96px"),
				RowSpec.decode("96px"),
				RowSpec.decode("96px"),
				RowSpec.decode("96px"),}));
		
		
		JButton[][] launchpadPad = new JButton[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				launchpadPad[i][j] = new JButton("");
				launchpadPad[i][j].setEnabled(false);
				launchpadPad[i][j].setMaximumSize(new Dimension(25,25));
				launchpadPadPanel.add(launchpadPad[i][j], (""+(i+2)+", "+(j+2)+", fill, fill"));
			}
		}
		
		JSplitPane split_recentPreview = new JSplitPane();
		split_recentPreview.setResizeWeight(1.0);
		split_recentPreview.setOrientation(JSplitPane.VERTICAL_SPLIT);
		split_padPreviewRecent.setRightComponent(split_recentPreview);
		
		JPanel recentListPanel = new JPanel();
		split_recentPreview.setLeftComponent(recentListPanel);
		recentListPanel.setLayout(new MigLayout("", "[][][][][grow][grow]", "[][][][][][][][][grow]"));
		
		JLabel lblRecentSequences = new JLabel("Recent Sequences:");
		recentListPanel.add(lblRecentSequences, "cell 0 0,alignx left,aligny center");
		
		JButton btnPreviewSequence = new JButton("Preview Sequence");
		recentListPanel.add(btnPreviewSequence, "cell 2 0,alignx right,growy");
		
		JScrollPane recentSequencesListPane = new JScrollPane();
		recentListPanel.add(recentSequencesListPane, "cell 0 1 3 8,grow");
		
		JList<String> recentSequencesList = new JList<String>();
		recentSequencesListPane.setViewportView(recentSequencesList);
		recentSequencesList.setModel(new AbstractListModel<String>() {
			String[] values = new String[] {"Test1", "Spiral", "Test2", "Test3", "Test4", "Test5", "Test6", "Test7", "Test8", "Test9"};
			public int getSize() {
				return values.length;
			}
			public String getElementAt(int index) {
				return values[index];
			}
		});
		recentSequencesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JLabel lblBpm = new JLabel("BPM: ");
		recentListPanel.add(lblBpm, "cell 4 2,alignx right");
		
		bpmDisplay = new JTextField();
		recentListPanel.add(bpmDisplay, "cell 5 2,grow");
		bpmDisplay.setColumns(10);
		
		JLabel lblCurrentSequence = new JLabel("Current Sequence: ");
		recentListPanel.add(lblCurrentSequence, "cell 4 4,alignx left");
		
		currentSequenceDisplay = new JTextField();
		currentSequenceDisplay.setEditable(false);
		recentListPanel.add(currentSequenceDisplay, "cell 4 5 2 1,growx");
		currentSequenceDisplay.setColumns(10);
		
		JLabel lblNextSequence = new JLabel("Next Sequence:");
		recentListPanel.add(lblNextSequence, "cell 4 6,alignx left");
		
		nextSequenceDisplay = new JTextField();
		nextSequenceDisplay.setEditable(false);
		recentListPanel.add(nextSequenceDisplay, "cell 4 7 2 1,growx");
		nextSequenceDisplay.setColumns(10);
		
		JPanel barPreviewPanel = new JPanel();
		recentListPanel.add(barPreviewPanel, "cell 4 8 2 1,grow");
		barPreviewPanel.setLayout(new BorderLayout(0, 0));
		
		Canvas barPreviewCanvas = new Canvas();
		barPreviewCanvas.setBackground(Color.WHITE);
		barPreviewPanel.add(barPreviewCanvas, BorderLayout.CENTER);
		
		JPanel previewPanel = new JPanel();
		previewPanel.setBackground(new Color(0, 0, 0));
		previewPanel.setSize(500, 500);
		previewPanel.setMinimumSize(new Dimension(500,500));
		previewPanel.setMaximumSize(previewPanel.getMinimumSize());
		split_recentPreview.setRightComponent(previewPanel);
		previewPanel.setLayout(new BorderLayout(0, 0));
		
		InstallationPreviewWindow previewCanvas = new InstallationPreviewWindow();
		previewCanvas.setMinimumSize(new Dimension(500,500));
		previewCanvas.setMaximumSize(previewCanvas.getMinimumSize());
		previewPanel.add(previewCanvas, BorderLayout.SOUTH);
		
		JScrollPane sequenceViewTab = new JScrollPane();
		viewTabs.addTab("Sequence View", null, sequenceViewTab, null);
		
		JPanel sequencePanel = new JPanel();
		sequencePanel.setBackground(UIManager.getColor("Panel.background"));
		sequenceViewTab.setColumnHeaderView(sequencePanel);
		
		//build column format string
		int numButtonColumns = 32;
		String col = "[30px:n:30px][50px:n:50px,center][150px:n:150px,fill]";
		for (int i = 0; i < numButtonColumns; i++) {
			col = col.concat("[30px:n:30px,fill]");
		}
		col = col.concat("[400px:n:1600px,grow]");
		
		//build row format string
		int numStrips = 55;
		String row = "";
		for (int i = 0; i < numStrips; i++) {
			row = row.concat("[30px:n]");
		}
		
		sequencePanel.setLayout(new MigLayout("", col, row));
		
		JButton btnNewStrip = new JButton("New Strip");
		sequencePanel.add(btnNewStrip, "cell 2 0,grow");
		
		JToggleButton colorToggleBlack = new JToggleButton("");
		colorToggleBlack.setBackground(new Color(0, 0, 0));
		colorToggleBlack.setOpaque(true);
		colorToggleBlack.setBorderPainted(false);
		colorToggleBlack.setMaximumSize(new Dimension(25, 25));
		sequencePanel.add(colorToggleBlack, "cell 3 0,alignx center,aligny center");
		
		JToggleButton colorToggleWhite = new JToggleButton("");
		colorToggleWhite.setOpaque(true);
		colorToggleWhite.setMaximumSize(new Dimension(25, 25));
		colorToggleWhite.setBorderPainted(false);
		colorToggleWhite.setBackground(new Color(255, 255, 255));
		sequencePanel.add(colorToggleWhite, "cell 4 0,alignx center,aligny center");
		
		JToggleButton colorToggleRed1 = new JToggleButton("");
		colorToggleRed1.setOpaque(true);
		colorToggleRed1.setMaximumSize(new Dimension(25, 25));
		colorToggleRed1.setBorderPainted(false);
		colorToggleRed1.setBackground(new Color(153, 0, 51));
		sequencePanel.add(colorToggleRed1, "cell 5 0,alignx center,aligny center");
		
		JToggleButton colorToggleRed2 = new JToggleButton("");
		colorToggleRed2.setOpaque(true);
		colorToggleRed2.setMaximumSize(new Dimension(25, 25));
		colorToggleRed2.setBorderPainted(false);
		colorToggleRed2.setBackground(new Color(204, 0, 51));
		sequencePanel.add(colorToggleRed2, "cell 6 0,alignx center,aligny center");
		
		JToggleButton colorToggleRed3 = new JToggleButton("");
		colorToggleRed3.setOpaque(true);
		colorToggleRed3.setMaximumSize(new Dimension(25, 25));
		colorToggleRed3.setBorderPainted(false);
		colorToggleRed3.setBackground(new Color(255, 51, 0));
		sequencePanel.add(colorToggleRed3, "cell 7 0,alignx center,aligny center");
		
		JToggleButton toggleButton = new JToggleButton("");
		toggleButton.setOpaque(true);
		toggleButton.setMaximumSize(new Dimension(25, 25));
		toggleButton.setBorderPainted(false);
		toggleButton.setBackground(new Color(255, 102, 0));
		sequencePanel.add(toggleButton, "cell 8 0,alignx center,aligny center");
		
		JToggleButton toggleButton_1 = new JToggleButton("");
		toggleButton_1.setOpaque(true);
		toggleButton_1.setMaximumSize(new Dimension(25, 25));
		toggleButton_1.setBorderPainted(false);
		toggleButton_1.setBackground(new Color(255, 153, 0));
		sequencePanel.add(toggleButton_1, "cell 9 0,alignx center,aligny center");
		
		JToggleButton toggleButton_5 = new JToggleButton("");
		toggleButton_5.setOpaque(true);
		toggleButton_5.setMaximumSize(new Dimension(25, 25));
		toggleButton_5.setBorderPainted(false);
		toggleButton_5.setBackground(new Color(255, 204, 0));
		sequencePanel.add(toggleButton_5, "cell 10 0,alignx center,aligny center");
		
		JToggleButton toggleButton_6 = new JToggleButton("");
		toggleButton_6.setOpaque(true);
		toggleButton_6.setMaximumSize(new Dimension(25, 25));
		toggleButton_6.setBorderPainted(false);
		toggleButton_6.setBackground(new Color(255, 255, 0));
		sequencePanel.add(toggleButton_6, "cell 11 0,alignx center,aligny center");
		
		JToggleButton toggleButton_7 = new JToggleButton("");
		toggleButton_7.setOpaque(true);
		toggleButton_7.setMaximumSize(new Dimension(25, 25));
		toggleButton_7.setBorderPainted(false);
		toggleButton_7.setBackground(new Color(255, 255, 153));
		sequencePanel.add(toggleButton_7, "cell 12 0,alignx center,aligny center");
		
		JToggleButton toggleButton_8 = new JToggleButton("");
		toggleButton_8.setOpaque(true);
		toggleButton_8.setMaximumSize(new Dimension(25, 25));
		toggleButton_8.setBorderPainted(false);
		toggleButton_8.setBackground(new Color(204, 255, 204));
		sequencePanel.add(toggleButton_8, "cell 13 0,alignx center,aligny center");
		
		JToggleButton toggleButton_9 = new JToggleButton("");
		toggleButton_9.setOpaque(true);
		toggleButton_9.setMaximumSize(new Dimension(25, 25));
		toggleButton_9.setBorderPainted(false);
		toggleButton_9.setBackground(new Color(153, 255, 153));
		sequencePanel.add(toggleButton_9, "cell 14 0,alignx center,aligny center");
		
		JToggleButton toggleButton_10 = new JToggleButton("");
		toggleButton_10.setOpaque(true);
		toggleButton_10.setMaximumSize(new Dimension(25, 25));
		toggleButton_10.setBorderPainted(false);
		toggleButton_10.setBackground(new Color(51, 255, 102));
		sequencePanel.add(toggleButton_10, "cell 15 0,alignx center,aligny center");
		
		JToggleButton toggleButton_11 = new JToggleButton("");
		toggleButton_11.setOpaque(true);
		toggleButton_11.setMaximumSize(new Dimension(25, 25));
		toggleButton_11.setBorderPainted(false);
		toggleButton_11.setBackground(new Color(0, 255, 51));
		sequencePanel.add(toggleButton_11, "cell 16 0,alignx center,aligny center");
		
		JToggleButton toggleButton_12 = new JToggleButton("");
		toggleButton_12.setOpaque(true);
		toggleButton_12.setMaximumSize(new Dimension(25, 25));
		toggleButton_12.setBorderPainted(false);
		toggleButton_12.setBackground(new Color(0, 204, 0));
		sequencePanel.add(toggleButton_12, "cell 17 0,alignx center,aligny center");
		
		JToggleButton toggleButton_13 = new JToggleButton("");
		toggleButton_13.setOpaque(true);
		toggleButton_13.setMaximumSize(new Dimension(25, 25));
		toggleButton_13.setBorderPainted(false);
		toggleButton_13.setBackground(new Color(0, 153, 0));
		sequencePanel.add(toggleButton_13, "cell 18 0,alignx center,aligny center");
		
		JToggleButton toggleButton_14 = new JToggleButton("");
		toggleButton_14.setOpaque(true);
		toggleButton_14.setMaximumSize(new Dimension(25, 25));
		toggleButton_14.setBorderPainted(false);
		toggleButton_14.setBackground(new Color(0, 153, 102));
		sequencePanel.add(toggleButton_14, "cell 19 0,alignx center,aligny center");
		
		JToggleButton toggleButton_15 = new JToggleButton("");
		toggleButton_15.setOpaque(true);
		toggleButton_15.setMaximumSize(new Dimension(25, 25));
		toggleButton_15.setBorderPainted(false);
		toggleButton_15.setBackground(new Color(0, 102, 153));
		sequencePanel.add(toggleButton_15, "cell 20 0,alignx center,aligny center");
		
		JToggleButton toggleButton_16 = new JToggleButton("");
		toggleButton_16.setOpaque(true);
		toggleButton_16.setMaximumSize(new Dimension(25, 25));
		toggleButton_16.setBorderPainted(false);
		toggleButton_16.setBackground(new Color(0, 51, 153));
		sequencePanel.add(toggleButton_16, "cell 21 0,alignx center,aligny center");
		
		JToggleButton toggleButton_17 = new JToggleButton("");
		toggleButton_17.setOpaque(true);
		toggleButton_17.setMaximumSize(new Dimension(25, 25));
		toggleButton_17.setBorderPainted(false);
		toggleButton_17.setBackground(new Color(0, 0, 255));
		sequencePanel.add(toggleButton_17, "cell 22 0,alignx center,aligny center");
		
		JToggleButton toggleButton_18 = new JToggleButton("");
		toggleButton_18.setOpaque(true);
		toggleButton_18.setMaximumSize(new Dimension(25, 25));
		toggleButton_18.setBorderPainted(false);
		toggleButton_18.setBackground(new Color(0, 153, 255));
		sequencePanel.add(toggleButton_18, "cell 23 0,alignx center,aligny center");
		
		JToggleButton toggleButton_19 = new JToggleButton("");
		toggleButton_19.setOpaque(true);
		toggleButton_19.setMaximumSize(new Dimension(25, 25));
		toggleButton_19.setBorderPainted(false);
		toggleButton_19.setBackground(new Color(0, 204, 255));
		sequencePanel.add(toggleButton_19, "cell 24 0,alignx center,aligny center");
		
		JToggleButton toggleButton_20 = new JToggleButton("");
		toggleButton_20.setOpaque(true);
		toggleButton_20.setMaximumSize(new Dimension(25, 25));
		toggleButton_20.setBorderPainted(false);
		toggleButton_20.setBackground(new Color(0, 255, 255));
		sequencePanel.add(toggleButton_20, "cell 25 0,alignx center,aligny center");
		
		JToggleButton toggleButton_21 = new JToggleButton("");
		toggleButton_21.setOpaque(true);
		toggleButton_21.setMaximumSize(new Dimension(25, 25));
		toggleButton_21.setBorderPainted(false);
		toggleButton_21.setBackground(new Color(204, 204, 255));
		sequencePanel.add(toggleButton_21, "cell 26 0,alignx center,aligny center");
		
		JToggleButton toggleButton_22 = new JToggleButton("");
		toggleButton_22.setOpaque(true);
		toggleButton_22.setMaximumSize(new Dimension(25, 25));
		toggleButton_22.setBorderPainted(false);
		toggleButton_22.setBackground(new Color(204, 153, 255));
		sequencePanel.add(toggleButton_22, "cell 27 0,alignx center,aligny center");
		
		JToggleButton toggleButton_23 = new JToggleButton("");
		toggleButton_23.setOpaque(true);
		toggleButton_23.setMaximumSize(new Dimension(25, 25));
		toggleButton_23.setBorderPainted(false);
		toggleButton_23.setBackground(new Color(204, 102, 255));
		sequencePanel.add(toggleButton_23, "cell 28 0,alignx center,aligny center");
		
		JToggleButton toggleButton_24 = new JToggleButton("");
		toggleButton_24.setOpaque(true);
		toggleButton_24.setMaximumSize(new Dimension(25, 25));
		toggleButton_24.setBorderPainted(false);
		toggleButton_24.setBackground(new Color(204, 0, 255));
		sequencePanel.add(toggleButton_24, "cell 29 0,alignx center,aligny center");
		
		JToggleButton toggleButton_25 = new JToggleButton("");
		toggleButton_25.setOpaque(true);
		toggleButton_25.setMaximumSize(new Dimension(25, 25));
		toggleButton_25.setBorderPainted(false);
		toggleButton_25.setBackground(new Color(204, 51, 204));
		sequencePanel.add(toggleButton_25, "cell 30 0,alignx center,aligny center");
		
		JToggleButton toggleButton_26 = new JToggleButton("");
		toggleButton_26.setOpaque(true);
		toggleButton_26.setMaximumSize(new Dimension(25, 25));
		toggleButton_26.setBorderPainted(false);
		toggleButton_26.setBackground(new Color(255, 51, 255));
		sequencePanel.add(toggleButton_26, "cell 31 0,alignx center,aligny center");
		
		JToggleButton toggleButton_27 = new JToggleButton("");
		toggleButton_27.setOpaque(true);
		toggleButton_27.setMaximumSize(new Dimension(25, 25));
		toggleButton_27.setBorderPainted(false);
		toggleButton_27.setBackground(new Color(255, 102, 255));
		sequencePanel.add(toggleButton_27, "cell 32 0,alignx center,aligny center");
		
		JToggleButton toggleButton_28 = new JToggleButton("");
		toggleButton_28.setOpaque(true);
		toggleButton_28.setMaximumSize(new Dimension(25, 25));
		toggleButton_28.setBorderPainted(false);
		toggleButton_28.setBackground(new Color(153, 51, 204));
		sequencePanel.add(toggleButton_28, "cell 33 0,alignx center,aligny center");
		
		JToggleButton toggleButton_29 = new JToggleButton("");
		toggleButton_29.setOpaque(true);
		toggleButton_29.setMaximumSize(new Dimension(25, 25));
		toggleButton_29.setBorderPainted(false);
		toggleButton_29.setBackground(new Color(153, 0, 153));
		sequencePanel.add(toggleButton_29, "cell 34 0,alignx center,aligny center");
		
		JButton deleteStripButton = new JButton("");
		deleteStripButton.setFont(new Font("Lucida Grande", Font.PLAIN, 5));
		deleteStripButton.setForeground(Color.RED);
		deleteStripButton.setBackground(Color.RED);
		deleteStripButton.setOpaque(true);
		deleteStripButton.setBorderPainted(false);
		deleteStripButton.setMaximumSize(new Dimension(15,15));
		sequencePanel.add(deleteStripButton, "cell 0 2,alignx center,aligny center");
		
		JLabel lblStrip = new JLabel("Strip:");
		sequencePanel.add(lblStrip, "cell 1 2,alignx center,aligny center");
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(
				new String[] {"Unassigned", "Groups", "Outer Ring", "", "Strips", 
						"1-2", "1-3", "1-4", "1-5", "1-6", "1-7", "1-8", "1-9", 
						"1-10", "1-11", "2-3", "2-4", "2-5", "2-6", "2-7", "2-8", 
						"2-9", "2-10", "2-11", "3-4", "3-5", "3-6", "3-7", "3-8", 
						"3-9", "3-10", "3-11", "4-5", "4-6", "4-7", "4-8", "4-9", 
						"4-10", "4-11", "5-6", "5-7", "5-8", "5-9", "5-10", "5-11", 
						"6-7", "6-8", "6-9", "6-10", "6-11", "7-8", "7-9", "7-10", 
						"7-11", "8-9", "8-10", "8-11", "9-10", "9-11", "10-11"}
				));
		comboBox.setSelectedIndex(0);
		//comboBox.setMinimumSize(new Dimension(150, 25));
		//comboBox.setPreferredSize(comboBox.getMinimumSize());
		sequencePanel.add(comboBox, "flowx,cell 2 2,grow");
		
		JToggleButton patternStartPos = new JToggleButton("");
		patternStartPos.setOpaque(true);
		patternStartPos.setMaximumSize(new Dimension(25, 25));
		patternStartPos.setBorderPainted(false);
		patternStartPos.setBackground(Color.BLACK);
		sequencePanel.add(patternStartPos, "cell 3 2,alignx center,aligny center");
		
		JButton btnAddBar = new JButton("Add bar");
		sequencePanel.add(btnAddBar, "cell 19 2");
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenu mnEdit = new JMenu("Edit");
		menuBar.add(mnEdit);
		
		JMenu mnView = new JMenu("View");
		menuBar.add(mnView);
		
		JMenu mnAbout = new JMenu("About");
		menuBar.add(mnAbout);
	}

}
