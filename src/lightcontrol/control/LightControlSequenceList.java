package lightcontrol.control;

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

}
