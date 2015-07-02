import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class LightControlWindow {
	
	String version = "pre-alpha 0.1";
	
	JFrame frame;
	
	JTextField bpmField;
	JMenuBar menuBar;
	JMenu file, edit, view, sequence, help;
	JMenuItem newSeq, openSeq, saveSeq, exit;
	JMenuItem undo, redo;
	//JMenuItem viewItems;
	JMenuItem addBar, removeBar;
	JMenu quantization;
	JMenuItem bar, half, quart, eighth, sixteenth, thirtysecond;
	JMenuItem about;
	
	
	
	public LightControlWindow() {
		
		frame = new JFrame("LightControl v01");

        frame.setPreferredSize( new Dimension(1500, 900) );
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        
        frame.addWindowListener( new WindowAdapter() { 
            @Override
            public void windowClosing(WindowEvent e) {
                LaunchpadDriver.getListener().shutdownLaunchpad();
                System.exit(0);
            }});            
        
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
	
		addComponents();
		
		frame.revalidate();
		
		new LaunchpadDriver();
		
	}
	
	public void addComponents() {
		menuBar = new JMenuBar();
		
		file = new JMenu("File");
		edit = new JMenu("Edit");
		edit.setEnabled(false);
		view = new JMenu("View");
		view.setEnabled(false);
		sequence = new JMenu("Sequence");
		sequence.setEnabled(false);
		help = new JMenu("Help");
		
		newSeq = new JMenuItem("New Sequence");
		openSeq = new JMenuItem("Open Sequence");
		saveSeq = new JMenuItem("Save Sequence");
		saveSeq.setEnabled(false);
		exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	try {
            		LaunchpadDriver.getListener().shutdownLaunchpad();
            	} catch (NullPointerException ex) {}
            	System.exit(0);
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
		
		bar = new JMenuItem("1/1");
		half = new JMenuItem("1/2");
		quart = new JMenuItem("1/4");
		eighth = new JMenuItem("1/8");
		sixteenth = new JMenuItem("1/16");
		thirtysecond = new JMenuItem("1/32");
		
		about = new JMenuItem("About LightControl");
		about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
            	JOptionPane.showMessageDialog(frame,
            		    "LightControl version " + version + "\n"
            		    + "This application is designed to create sequences of events for playback on DMX\n"
            		    + "lighting installations.\n"
            		    + "It uses the LP4J library by Olivier Croisier (olivier.croisier@gmail.com)\n"
            		    + "to interface with a Launchpad to create a simple and easy to use control scheme.\n\n"
            		    + "Software written by Brendon Veronese (bsver1@hotmail.com). \n"
            		    + "Hardware designed and built by James Newlands.",
            		    "About LightControl",
            		    JOptionPane.PLAIN_MESSAGE);
            }
        });
		
		file.add(newSeq);
		file.add(openSeq);
		file.add(saveSeq);
		file.addSeparator();
		file.add(exit);
		
		edit.add(undo);
		edit.add(redo);
		
		//view items here
		
		sequence.add(addBar);
		sequence.add(removeBar);
		sequence.addSeparator();
		sequence.add(quantization);
		
		quantization.add(bar);
		quantization.add(half);
		quantization.add(quart);
		quantization.add(eighth);
		quantization.add(sixteenth);
		quantization.add(thirtysecond);
		
		help.add(about);
		
		menuBar.add(file);
		menuBar.add(edit);
		menuBar.add(view);
		menuBar.add(sequence);
		menuBar.add(help);
		
		frame.setJMenuBar(menuBar);
	}

}
