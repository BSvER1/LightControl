package lightcontrol;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import lightcontrol.gui.LightControlWindow;

public class LightControl {

	public static void main(String[] args) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			System.setProperty("apple.laf.useScreenMenuBar", "true");
			System.setProperty("com.apple.mrj.application.apple.menu.about.name", "LightControl");
		}
		catch(ClassNotFoundException e) {
			System.out.println("ClassNotFoundException: " + e.getMessage());
		}
		catch(InstantiationException e) {
			System.out.println("InstantiationException: " + e.getMessage());
		}
		catch(IllegalAccessException e) {
			System.out.println("IllegalAccessException: " + e.getMessage());
		}
		catch(UnsupportedLookAndFeelException e) {
			System.out.println("UnsupportedLookAndFeelException: " + e.getMessage());
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new LightControlWindow();
			}
		});
	}
}
