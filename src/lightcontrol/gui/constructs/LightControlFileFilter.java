package lightcontrol.gui.constructs;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class LightControlFileFilter extends FileFilter {


	public final static String LightControlSequence = "lcs";

	/*
	 * Get the extension of a file.
	 */  
	public static String getExtension(File f) {
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');

		if (i > 0 &&  i < s.length() - 1) {
			ext = s.substring(i+1).toLowerCase();
		}
		//System.out.println("Checking files.. found ext "+ ext);
		return ext;
	}


	@Override
	public String getDescription() {
		return "LightControl Sequence (.lcs)";
	}


	@Override
	public boolean accept(File f) {
		if (f.isDirectory()) {
			return true;
		}

		String extension = getExtension(f);
		if (extension != null) {
			if (extension.equals(LightControlSequence)) {
				//System.out.println("admitting lcs");
				return true;
			} else {
				//System.out.println("forbidding ext "+extension);
				return false;
			}
		}

		return false;
	}

}
