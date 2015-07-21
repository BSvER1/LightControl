package lightcontrol.gui;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import lightcontrol.control.LaunchpadDriver;
import lightcontrol.control.LightControlSequence;
import lightcontrol.control.LightControlSequenceList;
import lightcontrol.enums.StripColor;
import lightcontrol.gui.constructs.LightControlFileFilter;
import lightcontrol.gui.preview.InstallationPreviewWindow;
import lightcontrol.gui.preview.constructs.LightDataCenter;
import lightcontrol.gui.sequence.constructs.SequenceChannel;
import net.miginfocom.swing.MigLayout;


public class LightControlWindow {

	final JFileChooser fc = new JFileChooser();

	String version = "pre-alpha 0.3";

	public static LightDataCenter light;

	int numButtonColumns = 256;
	int maxNumStrips = 55;
	int pxPerCol = 10;

	static InstallationPreviewWindow previewCanvas;

	JFrame frame;
	public static JTabbedPane viewTabs;
	JSplitPane split_padPreviewRecent, split_recentPreview;
	JScrollPane recentSequencesListPane, sequenceViewTab;
	JPanel launchpadViewTab, launchpadPadPanel, recentListPanel, barPreviewPanel, previewPanel;
	public static JPanel sequencePanel;

	JButton[][] launchpadPad;
	JButton btnPreviewSequence, btnNewStrip;
	JLabel lblRecentSequences, lblBpm, lblCurrentSequence, lblNextSequence;
	JList<String> recentSequencesList;
	DefaultListModel<String> recentSequences;
	public static JTextField bpmDisplay;
	static JTextField currentSequenceDisplay;

	JTextField nextSequenceDisplay;
	JMenuBar menuBar;
	JMenu file, edit, view, sequence, quantization, help;
	JMenuItem newSeq, openSeq, saveSeq, importSeq, exit;
	JMenuItem undo, redo;
	JMenuItem addBar, removeBar, addChannel;
	JMenuItem bar, half, quart, eighth, sixteenth;
	JMenuItem about;

	JButton[] colorToggle;
	public static Color currentColor = StripColor.OFF.toColor();

	LinkedList<SequenceChannel> sequenceViewChannels; // buttons/methods for creating and editing sequences
	LightControlSequenceList performanceSequences; 

	Canvas barPreviewCanvas;


	public LightControlWindow() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException 
				| IllegalAccessException | UnsupportedLookAndFeelException ex) {
		}

		frame = new JFrame("LightControl " + version);

		frame.setResizable(false);
		frame.setBounds(0, 0, 1500, 900);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.addWindowListener( new WindowAdapter() { 
			@Override
			public void windowClosing(WindowEvent e) {
				closeLightControl();
			}});

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		performanceSequences = new LightControlSequenceList();

		light = new LightDataCenter();

		addMenuBar();
		initFrame();

		new LaunchpadDriver();

		previewCanvas.invalidate();
		menuBar.invalidate();

	}

	public void initFrame() {

		sequenceViewChannels = new LinkedList<SequenceChannel>();

		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		viewTabs = new JTabbedPane(JTabbedPane.TOP);
		frame.getContentPane().add(viewTabs);

		launchpadViewTab = new JPanel();
		viewTabs.addTab("Launchpad View", null, launchpadViewTab, null);
		launchpadViewTab.setName("Launchpad View");
		viewTabs.setSelectedComponent(launchpadViewTab);
		launchpadViewTab.setLayout(new BoxLayout(launchpadViewTab, BoxLayout.X_AXIS));

		split_padPreviewRecent = new JSplitPane();
		split_padPreviewRecent.setResizeWeight(1.0); // 1.0
		split_padPreviewRecent.setEnabled(false);
		split_padPreviewRecent.setAlignmentY(Component.CENTER_ALIGNMENT);
		split_padPreviewRecent.setAlignmentX(Component.CENTER_ALIGNMENT);
		launchpadViewTab.add(split_padPreviewRecent);

		launchpadPadPanel = new JPanel();
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


		launchpadPad = new JButton[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				launchpadPad[i][j] = new JButton("");
				launchpadPad[i][j].setEnabled(false);
				launchpadPad[i][j].setMaximumSize(new Dimension(25,25));
				launchpadPadPanel.add(launchpadPad[i][j], (""+(i+2)+", "+(j+2)+", fill, fill"));
			}
		}

		split_recentPreview = new JSplitPane();
		split_recentPreview.setResizeWeight(1.0); //1.0
		split_recentPreview.setEnabled(false);
		split_recentPreview.setOrientation(JSplitPane.VERTICAL_SPLIT);
		split_padPreviewRecent.setRightComponent(split_recentPreview);

		recentListPanel = new JPanel();
		split_recentPreview.setLeftComponent(recentListPanel);
		recentListPanel.setLayout(new MigLayout("", "[][][][][grow][grow]", "[][][][][][][][][grow]"));

		lblRecentSequences = new JLabel("Recent Sequences:");
		recentListPanel.add(lblRecentSequences, "cell 0 0,alignx left,aligny center");

		btnPreviewSequence = new JButton("Preview Sequence");
		btnPreviewSequence.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setSequence(performanceSequences.getSequence(recentSequencesList.getSelectedValue()));
				
				//previewCanvas.setCurrentPreview(performanceSequences.getSequence(recentSequencesList.getSelectedValue()));
				//currentSequenceDisplay.setText(recentSequencesList.getSelectedValue());
			}
		});
		recentListPanel.add(btnPreviewSequence, "cell 2 0,alignx right,growy");

		recentSequencesListPane = new JScrollPane();
		recentListPanel.add(recentSequencesListPane, "cell 0 1 3 8,grow");

		recentSequencesList = new JList<String>();
		recentSequencesListPane.setViewportView(recentSequencesList);
		recentSequences = new DefaultListModel<String>();
		recentSequencesList.setModel(recentSequences);
		recentSequencesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		lblBpm = new JLabel("BPM: ");
		recentListPanel.add(lblBpm, "cell 4 2,alignx right");

		bpmDisplay = new JTextField();
		bpmDisplay.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TimingsThread.setBPM(Double.valueOf(bpmDisplay.getText()));
				System.out.println("setting bpm to " + bpmDisplay.getText());
			}

		});
		recentListPanel.add(bpmDisplay, "cell 5 2,grow");
		bpmDisplay.setColumns(10);

		lblCurrentSequence = new JLabel("Current Sequence: ");
		recentListPanel.add(lblCurrentSequence, "cell 4 4,alignx left");

		currentSequenceDisplay = new JTextField();
		currentSequenceDisplay.setEditable(false);
		recentListPanel.add(currentSequenceDisplay, "cell 4 5 2 1,growx");
		currentSequenceDisplay.setColumns(10);

		lblNextSequence = new JLabel("Next Sequence:");
		recentListPanel.add(lblNextSequence, "cell 4 6,alignx left");

		nextSequenceDisplay = new JTextField();
		nextSequenceDisplay.setEditable(false);
		recentListPanel.add(nextSequenceDisplay, "cell 4 7 2 1,growx");
		nextSequenceDisplay.setColumns(10);

		barPreviewPanel = new JPanel();
		recentListPanel.add(barPreviewPanel, "cell 4 8 2 1,grow");
		barPreviewPanel.setLayout(new BorderLayout(0, 0));

		barPreviewCanvas = new Canvas();
		barPreviewCanvas.setBackground(Color.WHITE);
		barPreviewPanel.add(barPreviewCanvas, BorderLayout.CENTER);

		previewPanel = new JPanel();
		previewPanel.setBackground(new Color(0, 0, 0));
		previewPanel.setSize(500, 500);
		previewPanel.setMinimumSize(new Dimension(500,500));
		previewPanel.setMaximumSize(previewPanel.getMinimumSize());
		split_recentPreview.setRightComponent(previewPanel);
		previewPanel.setLayout(new BorderLayout(0, 0));

		previewCanvas = new InstallationPreviewWindow();
		previewCanvas.setMinimumSize(new Dimension(500,500));
		previewCanvas.setMaximumSize(previewCanvas.getMinimumSize());
		previewPanel.add(previewCanvas, BorderLayout.SOUTH);

		sequenceViewTab = new JScrollPane();
		viewTabs.addTab("Sequence View", null, sequenceViewTab, null);

		createSequencePanel();

	}

	public void addMenuBar() {
		menuBar = new JMenuBar();

		file = new JMenu("File");
		edit = new JMenu("Edit");
		edit.setEnabled(false);
		view = new JMenu("View");
		view.setEnabled(false);
		sequence = new JMenu("Sequence");
		help = new JMenu("Help");

		newSeq = new JMenuItem("New Sequence");
		newSeq.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!sequenceViewChannels.isEmpty()) {
					int dialogResult = JOptionPane.showConfirmDialog((Component) null, 
							"Are you sure you want a new sequence?\nPressing OK will result in all unsaved changes being lost.",
							"New Sequence", JOptionPane.OK_CANCEL_OPTION);
					if (dialogResult == 0) {
						sequenceViewChannels = new LinkedList<SequenceChannel>();
						resetSequencePanel();
					}
				}
			} 
			
		});
		
		openSeq = new JMenuItem("Open Sequence");
		openSeq.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!sequenceViewChannels.isEmpty()) {
					int dialogResult = JOptionPane.showConfirmDialog((Component) null, 
							"Are you sure you want to open a new sequence?\nPressing OK will result in all unsaved changes being lost.",
							"Open Sequence", JOptionPane.OK_CANCEL_OPTION);
					if (dialogResult != 0) {
						return;
					}
				}
				int returnVal = fc.showOpenDialog(openSeq);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					if (!LightControlFileFilter.getExtension(file).equals(LightControlFileFilter.LightControlSequence)) {
						JOptionPane.showMessageDialog((Component) null, "Need to select a file that ends with \".lcs\"");
						return;
					}
					System.out.println("Selected "+ file.getAbsolutePath());
					performanceSequences.add(new LightControlSequence(file));
					recentSequences.addElement(performanceSequences.get(performanceSequences.size()-1).getFriendlyName());
				}	
			}
		});
		
		saveSeq = new JMenuItem("Save Sequence");
		saveSeq.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (sequenceViewChannels.isEmpty()) {
					JOptionPane.showMessageDialog((Component) null, "There is nothing to save!");
				} else {

					fc.setFileFilter(new LightControlFileFilter());
					fc.setAcceptAllFileFilterUsed(false);
					fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

					int returnVal = fc.showSaveDialog(saveSeq);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File file = fc.getSelectedFile();
						System.out.println("Selected "+ file.getAbsolutePath());
						if (LightControlFileFilter.getExtension(file).equals(LightControlFileFilter.LightControlSequence)) {
							PrintWriter out;
							try {
								out = new PrintWriter(file.getAbsolutePath());
								out.println(exportSequence());
								out.close();
								out = null;
							} catch (FileNotFoundException e1) {
								
							}
						} else {
							JOptionPane.showMessageDialog((Component) null, "Need to select a file that ends with \".lcs\"");
						}
					}	
				}
			}
		});
		
		importSeq = new JMenuItem("Import Sequences from Directory");
		importSeq.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int returnVal = fc.showOpenDialog(importSeq);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fc.getSelectedFile();
					//TODO import directory of files
					
					System.out.println("Selected "+ file.getAbsolutePath());
				}

			}
		});
		
		exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				closeLightControl();
			}
		});

		undo = new JMenuItem("Undo");
		undo.setEnabled(false);
		redo = new JMenuItem("Redo");
		redo.setEnabled(false);

		//view items here

		quantization = new JMenu("Quantization Level");
		addBar = new JMenuItem("Add new bar");
		removeBar = new JMenuItem("Remove bar");
		addChannel = new JMenuItem("Add Channel");
		addChannel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				addChannelSequence();
			}
		});


		bar = new JMenuItem("1/1");
		half = new JMenuItem("1/2");
		quart = new JMenuItem("1/4");
		eighth = new JMenuItem("1/8");
		sixteenth = new JMenuItem("1/16");

		createAboutDialog();

		file.add(newSeq);
		file.add(openSeq);
		file.add(saveSeq);
		file.addSeparator();
		file.add(importSeq);
		file.addSeparator();
		file.add(exit);

		edit.add(undo);
		edit.add(redo);

		//view items here

		sequence.add(addBar);
		sequence.add(removeBar);
		sequence.addSeparator();
		sequence.add(quantization);
		sequence.addSeparator();
		sequence.add(addChannel);

		quantization.add(bar);
		quantization.add(half);
		quantization.add(quart);
		quantization.add(eighth);
		quantization.add(sixteenth);

		help.add(about);

		menuBar.add(file);
		menuBar.add(edit);
		menuBar.add(view);
		menuBar.add(sequence);
		menuBar.add(help);

		frame.setJMenuBar(menuBar);
	}
	
	public void createSequencePanel() {
		sequencePanel = new JPanel();
		sequencePanel.setBackground(UIManager.getColor("Panel.background"));
		sequenceViewTab.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		sequenceViewTab.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		sequenceViewTab.setViewportView(sequencePanel);

		setSequencePanelLayout();

		btnNewStrip = new JButton("New Strip");
		btnNewStrip.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addChannelSequence();
				//System.out.println("Making new strip");
			}
		});
		sequencePanel.add(btnNewStrip, "cell 2 0,grow");

		addColourPalette();
	}
	
	public void resetSequencePanel() {
		sequencePanel.removeAll();
		createSequencePanel();
	}
	
	public static void setSequence(LightControlSequence lcs) {
		previewCanvas.setCurrentPreview(lcs);
		currentSequenceDisplay.setText(lcs.getFriendlyName());
	}

	public void setSequencePanelLayout() {
		//build column format string
		String col = "[30px:n:30px][50px:n:50px,center][150px:n:150px,fill]";
		for (int i = 0; i < numButtonColumns; i++) {
			col = col.concat("["+pxPerCol+"px:n:"+pxPerCol+"px,fill]");
		}
		col = col.concat("[400px:n:1600px,grow]");

		//build row format string
		String row = "";
		for (int i = 0; i < maxNumStrips; i++) {
			row = row.concat("[30px:n]");
		}

		sequencePanel.setLayout(new MigLayout("", col, row));
	}

	public void createAboutDialog() {
		about = new JMenuItem("About LightControl");
		about.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showMessageDialog(frame,
						"LightControl version " + version + "\n"
								+ "This application is designed to create sequences of events for playback on DMX\n"
								+ "lighting installations.\n"
								+ "It uses the LP4J library by Olivier Croisier (olivier.croisier@gmail.com)\n"
								+ "to interface with a Launchpad to create a simple and easy to use control scheme.\n"
								+ "Display Layout created using JGoodies and MigLayout and Eclipse WindowBuilder toolkit.\n"
								+ "jSSC (Java Simple Serial Connector) by (scream3r.org@gmail.com) for interfacing with lighting controllers over serial.\n\n"
								+ "Software written by Brendon Veronese (bsver1@hotmail.com). \n"
								+ "Hardware designed and built by James Newlands.",
								"About LightControl",
								JOptionPane.PLAIN_MESSAGE);
			}
		});
	}

	public void addColourPalette() {
		String location;
		colorToggle = new JButton[StripColor.values().length];

		for (int i = 0; i < StripColor.values().length; i++) {
			colorToggle[i] = new JButton("");
			colorToggle[i].setBackground(StripColor.values()[i].toColor());
			colorToggle[i].setOpaque(true);
			colorToggle[i].setBorderPainted(false);
			colorToggle[i].setMaximumSize(new Dimension(25, 25));
			colorToggle[i].addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent e) {
					currentColor = ((JButton) e.getSource()).getBackground();
					//System.out.println("Colour set to " + currentColor.toString());
				}

			});

			location = "cell " + (3+2*i) + " 0,alignx center,aligny center";
			sequencePanel.add(colorToggle[i], location);
		}
	}

	public void addChannelSequence() {
		if (sequenceViewChannels.size() == maxNumStrips-1) {
			btnNewStrip.setEnabled(false);
		}

		sequenceViewChannels.addLast(new SequenceChannel(sequenceViewChannels.size()));
		sequenceViewChannels.getLast().addBarBtn.revalidate();
	}

	public String exportSequence() {
		String sequence = "";
		for (int i = 0; i < sequenceViewChannels.size(); i++) {
			sequence = sequence.concat(sequenceViewChannels.get(i).exportChannel());
		}
		//sequence = sequence.concat("//end of file");
		return sequence;
	}

	public void closeLightControl() {
		try {
			LaunchpadDriver.getListener().shutdownLaunchpad();
		} catch (NullPointerException ex) {}

		InstallationPreviewWindow.setRunning(false);
		TimingsThread.setRunning(false);
		System.exit(0);
	}

	public static LightDataCenter getLightData() {
		return light;
	}

}












