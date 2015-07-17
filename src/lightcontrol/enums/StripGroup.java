package lightcontrol.enums;


public enum StripGroup {
	ALL_STRIPS(new StripID[] {StripID.S1_2, StripID.S1_3, StripID.S1_4, StripID.S1_5, 
			StripID.S1_6, StripID.S1_7, StripID.S1_8, StripID.S1_9, StripID.S1_10, 
			StripID.S1_11, StripID.S2_3, StripID.S2_4, StripID.S2_5, StripID.S2_6, 
			StripID.S2_7, StripID.S2_8, StripID.S2_9, StripID.S2_10, StripID.S2_11, 
			StripID.S3_4, StripID.S3_5, StripID.S3_6, StripID.S3_7, StripID.S3_8, 
			StripID.S3_9, StripID.S3_10, StripID.S3_11, StripID.S4_5, StripID.S4_6, 
			StripID.S4_7, StripID.S4_8, StripID.S4_9, StripID.S4_10, StripID.S4_11, 
			StripID.S5_6, StripID.S5_7, StripID.S5_8, StripID.S5_9, StripID.S5_10, 
			StripID.S5_11, StripID.S6_7, StripID.S6_8, StripID.S6_9, StripID.S6_10, 
			StripID.S6_11, StripID.S7_8, StripID.S7_9, StripID.S7_10, StripID.S7_11, 
			StripID.S8_9, StripID.S8_10, StripID.S8_11, StripID.S9_10, StripID.S9_11, 
			StripID.S10_11}),
			
	OUTER_RING(new StripID[] {StripID.S1_2, StripID.S2_3, StripID.S3_4, StripID.S4_5, 
			StripID.S5_6, StripID.S6_7, StripID.S7_8, StripID.S8_9, StripID.S9_10, 
			StripID.S10_11, StripID.S11_1}),
	//V pointing to number, specifying width		
	V1_1W(new StripID[] {StripID.S1_6, StripID.S1_7}),
	V2_1W(new StripID[] {StripID.S2_7, StripID.S2_8}),
	V3_1W(new StripID[] {StripID.S3_8, StripID.S3_9}),
	V4_1W(new StripID[] {StripID.S4_9, StripID.S4_10}),
	V5_1W(new StripID[] {StripID.S5_10, StripID.S5_11}),
	V6_1W(new StripID[] {StripID.S6_1, StripID.S6_11}),
	V7_1W(new StripID[] {StripID.S7_1, StripID.S7_2}),
	V8_1W(new StripID[] {StripID.S8_2, StripID.S8_3}),
	V9_1W(new StripID[] {StripID.S9_3, StripID.S9_4}),
	V10_1W(new StripID[] {StripID.S10_4, StripID.S10_5}),
	V11_1W(new StripID[] {StripID.S11_5, StripID.S11_6}),
	
	V1_3W(new StripID[] {StripID.S1_5, StripID.S1_8}),
	V2_3W(new StripID[] {StripID.S2_6, StripID.S2_9}),
	V3_3W(new StripID[] {StripID.S3_7, StripID.S3_10}),
	V4_3W(new StripID[] {StripID.S4_8, StripID.S4_11}),
	V5_3W(new StripID[] {StripID.S5_9, StripID.S5_1}),
	V6_3W(new StripID[] {StripID.S6_10, StripID.S6_2}),
	V7_3W(new StripID[] {StripID.S7_11, StripID.S7_3}),
	V8_3W(new StripID[] {StripID.S8_1, StripID.S8_4}),
	V9_3W(new StripID[] {StripID.S9_2, StripID.S9_5}),
	V10_3W(new StripID[] {StripID.S10_3, StripID.S10_6}),
	V11_3W(new StripID[] {StripID.S11_4, StripID.S11_7}),
	
	V1_5W(new StripID[] {StripID.S1_4, StripID.S1_9}),
	V2_5W(new StripID[] {StripID.S2_5, StripID.S2_10}),
	V3_5W(new StripID[] {StripID.S3_6, StripID.S3_11}),
	V4_5W(new StripID[] {StripID.S4_7, StripID.S4_1}),
	V5_5W(new StripID[] {StripID.S5_8, StripID.S5_2}),
	V6_5W(new StripID[] {StripID.S6_9, StripID.S6_3}),
	V7_5W(new StripID[] {StripID.S7_10, StripID.S7_4}),
	V8_5W(new StripID[] {StripID.S8_11, StripID.S8_5}),
	V9_5W(new StripID[] {StripID.S9_1, StripID.S9_6}),
	V10_5W(new StripID[] {StripID.S10_2, StripID.S10_7}),
	V11_5W(new StripID[] {StripID.S11_3, StripID.S11_8}),
	
	V1_7W(new StripID[] {StripID.S1_3, StripID.S1_10}),
	V2_7W(new StripID[] {StripID.S2_4, StripID.S2_11}),
	V3_7W(new StripID[] {StripID.S3_5, StripID.S3_1}),
	V4_7W(new StripID[] {StripID.S4_6, StripID.S4_2}),
	V5_7W(new StripID[] {StripID.S5_7, StripID.S5_3}),
	V6_7W(new StripID[] {StripID.S6_8, StripID.S6_4}),
	V7_7W(new StripID[] {StripID.S7_9, StripID.S7_5}),
	V8_7W(new StripID[] {StripID.S8_10, StripID.S8_6}),
	V9_7W(new StripID[] {StripID.S9_11, StripID.S9_7}),
	V10_7W(new StripID[] {StripID.S10_1, StripID.S10_8}),
	V11_7W(new StripID[] {StripID.S11_2, StripID.S11_9}),
	
	V1_9W(new StripID[] {StripID.S1_2, StripID.S1_11}),
	V2_9W(new StripID[] {StripID.S2_3, StripID.S2_1}),
	V3_9W(new StripID[] {StripID.S3_4, StripID.S3_2}),
	V4_9W(new StripID[] {StripID.S4_5, StripID.S4_3}),
	V5_9W(new StripID[] {StripID.S5_6, StripID.S5_4}),
	V6_9W(new StripID[] {StripID.S6_7, StripID.S6_5}),
	V7_9W(new StripID[] {StripID.S7_8, StripID.S7_6}),
	V8_9W(new StripID[] {StripID.S8_9, StripID.S8_7}),
	V9_9W(new StripID[] {StripID.S9_10, StripID.S9_8}),
	V10_9W(new StripID[] {StripID.S10_11, StripID.S10_9}),
	V11_9W(new StripID[] {StripID.S11_1, StripID.S11_10}),
	
	LETTER_C(new StripID[] {StripID.S11_1, StripID.S1_2, StripID.S2_3, StripID.S3_4, 
			StripID.S4_5, StripID.S5_6, StripID.S6_7, StripID.S7_8, StripID.S8_9}),
	LETTER_E(new StripID[] {StripID.S3_11, StripID.S3_5, StripID.S5_9, StripID.S4_10}),
	LETTER_M(new StripID[] {StripID.S3_5, StripID.S2_3, StripID.S2_7, StripID.S7_1, 
			StripID.S1_11, StripID.S11_9}),
	LETTER_S(new StripID[] {StripID.S11_1, StripID.S1_2, StripID.S2_3, StripID.S3_9, 
			StripID.S9_8, StripID.S8_7, StripID.S7_6, StripID.S6_5});
	
	
	
	
	
	private StripID[] value;
	
	private StripGroup(StripID[] value) {
		this.value = value;
	}
	
	public int getNumStrips() {
		return value.length;
	}
	
	public StripGroup getStripGroup(String title) {
		for (StripGroup str : StripGroup.values()) {
			if (str.toString().equals(title)) {
				return str;
			}
		}
		return null;
	}
	
	public StripID[] getStrips() {
		return value;
	}
}
