package lightcontrol.control;

import java.io.File;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class LightControlSequenceList extends ArrayList<LightControlSequence>{
	
	public LightControlSequenceList () {
		super();
	}
	
	public LightControlSequence getSequence(String name) {
		for (int i = 0; i < size(); i++) {
			if (get(i).getFriendlyName().equals(name)) {
				return get(i);
			}
		}
		return null;
	}
	
	public void overwriteSequence(File lcs) {
		for (int i = 0; i < size(); i++) {
			if (get(i).getFileName().equals(lcs.getName())) {
				set(i, new LightControlSequence(lcs));
				return;
			}
		}
	}
	
	

}
