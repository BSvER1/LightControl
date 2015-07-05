
public enum StripSequence {
	
	PinWheel3(new String[] {"3-11", "3-9", "3-7", "3-5"}),
	PinWheel4(new String[] {"1-4", "4-10", "4-9", "4-7"});
	
	
	
	
	private String[] value;
	
	private StripSequence(String[] value) {
		this.value = value;
	}
	
	public int getNumSteps() {
		if (value.length%4 != 0) {
			System.out.println("Sequence " + this.name() + " has an irregular number of steps!");
		}
		return value.length;
	}
	
	public String getStep(int step) {
		return value[step];
	}
}
