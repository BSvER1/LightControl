import java.util.LinkedList;


public class LightDataCenter {
	
	private final int ADD = 0;
	private final int REMOVE = 1;
	private final int RETRIEVE = 2;
	
	private LinkedList<Strip> lights;
	
	public LightDataCenter() {
		lights = new LinkedList<Strip>();
	}
	
	private Strip performListOperation(int operation, int num, Strip newStrip) {
		if (operation == ADD) {
			lights.add(newStrip);
			return newStrip;
		} else if (operation == REMOVE) {
			return lights.remove(num);
		} else if (operation == RETRIEVE) {
			return lights.get(num);
		}
		
		else {
			return null;
		}
	}
	
	public void addStrip(Strip newStrip) {
		performListOperation(ADD, 0, newStrip);
	}
	
	public void removeStrip(int num) {
		performListOperation(REMOVE, num, null);
	}
	
	public void removeStrip(String id) {
		for(int i = 0; i < getNumStrips(); i++) {
			if (performListOperation(RETRIEVE, i , null).getId().equals(id)) {
				performListOperation(REMOVE, i, null);
				return;
			}
		}
	}
	
	public int getNumStrips() {
		return lights.size();
	}
	
	public Strip getStrip(String id) {
		for(int i = 0; i < getNumStrips(); i++) {
			if (performListOperation(RETRIEVE, i , null).getId().equals(id)) {
				return performListOperation(RETRIEVE, i, null);
			}
		}
		
		return null;
	}
	
	public Strip getStrip(int num) {
		return performListOperation(RETRIEVE, num, null);
	}
	
}
