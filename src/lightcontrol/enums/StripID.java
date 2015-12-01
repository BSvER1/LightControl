package lightcontrol.enums;

import java.util.Random;

public enum StripID {
	
	PAR1("PAR1", 1),
	PAR2("PAR2", 4),
	PAR3("PAR3", 7),
	PAR4("PAR4", 10),
	PAR5("PAR5", 13),
	PAR6("PAR6", 16),
	PAR7("PAR7", 19),
	PAR8("PAR8", 22),
	PAR9("PAR9", 25),
	PAR10("PAR10", 28),
	PAR11("PAR11", 31),
	PAR12("PAR12", 34),
	PAR13("PAR13", 37),
	PAR14("PAR14", 40),
	PAR15("PAR15", 43),
	PAR16("PAR16", 46),
	PAR17("PAR17", 49),
	PAR18("PAR18", 52),
	PAR19("PAR19", 55),
	PAR20("PAR20", 58),
	S1_2("1-2", 1),
	S2_1(S1_2),
	S1_3("1-3", 4),
	S3_1(S1_3),
	S1_4("1-4", 7),
	S4_1(S1_4),
	S1_5("1-5", 10),
	S5_1(S1_5),
	S1_6("1-6", 13),
	S6_1(S1_6),
	S1_7("1-7", 16),
	S7_1(S1_7),
	S1_8("1-8", 19),
	S8_1(S1_8),
	S1_9("1-9", 22),
	S9_1(S1_9),
	S1_10("1-10", 25),
	S10_1(S1_10),
	S1_11("1-11", 28),
	S11_1(S1_11),
	S2_3("2-3", 31),
	S3_2(S2_3),
	S2_4("2-4", 34),
	S4_2(S2_4),
	S2_5("2-5", 37),
	S5_2(S2_5),
	S2_6("2-6", 40),
	S6_2(S2_6),
	S2_7("2-7", 43),
	S7_2(S2_7),
	S2_8("2-8", 46),
	S8_2(S2_8),
	S2_9("2-9", 49),
	S9_2(S2_9),
	S2_10("2-10", 52),
	S10_2(S2_10),
	S2_11("2-11", 55),
	S11_2(S2_11),
	S3_4("3-4", 58),
	S4_3(S3_4),
	S3_5("3-5", 61),
	S5_3(S3_5),
	S3_6("3-6", 64),
	S6_3(S3_6),
	S3_7("3-7", 67),
	S7_3(S3_7),
	S3_8("3-8", 70),
	S8_3(S3_8),
	S3_9("3-9", 73),
	S9_3(S3_9),
	S3_10("3-10", 76),
	S10_3(S3_10),
	S3_11("3-11", 79),
	S11_3(S3_11),
	S4_5("4-5", 82),
	S5_4(S4_5),
	S4_6("4-6", 85),
	S6_4(S4_6),
	S4_7("4-7", 88),
	S7_4(S4_7),
	S4_8("4-8", 91),
	S8_4(S4_8),
	S4_9("4-9", 94),
	S9_4(S4_9),
	S4_10("4-10", 97),
	S10_4(S4_10),
	S4_11("4-11", 100),
	S11_4(S4_11),
	S5_6("5-6", 103),
	S6_5(S5_6),
	S5_7("5-7", 106),
	S7_5(S5_7),
	S5_8("5-8", 109),
	S8_5(S5_8),
	S5_9("5-9", 112),
	S9_5(S5_9),
	S5_10("5-10", 115),
	S10_5(S5_10),
	S5_11("5-11", 118),
	S11_5(S5_11),
	S6_7("6-7", 121),
	S7_6(S6_7),
	S6_8("6-8", 124),
	S8_6(S6_8),
	S6_9("6-9", 127),
	S9_6(S6_9),
	S6_10("6-10", 130),
	S10_6(S6_10),
	S6_11("6-11", 133),
	S11_6(S6_11),
	S7_8("7-8", 136),
	S8_7(S7_8),
	S7_9("7-9", 139),
	S9_7(S7_9),
	S7_10("7-10", 142),
	S10_7(S7_10),
	S7_11("7-11", 145),
	S11_7(S7_11),
	S8_9("8-9", 148),
	S9_8(S8_9),
	S8_10("8-10", 151),
	S10_8(S8_10),
	S8_11("8-11", 154),
	S11_8(S8_11),
	S9_10("9-10", 157),
	S10_9(S9_10),
	S9_11("9-11", 160),
	S11_9(S9_11),
	S10_11("10-11", 163),
	S11_10(S10_11);
	
	private String value;
	private Integer rgbStartChannel;
	
	private StripID(String value, Integer rgbStartChannel) {
		this.value = value;
		this.rgbStartChannel = rgbStartChannel;
	}
	
	private StripID(StripID strip) {
		this.value = strip.value;
		this.rgbStartChannel = strip.rgbStartChannel;
	}
	
	public static StripID getRandom() {
		Random r = new Random();
		int start = r.nextInt(11)+1;
		int stop;
		do {
			stop = r.nextInt(11)+1;
		} while(start == stop);
		
		return getStrip(start, stop);
	}
	
	public String getValue() {
		return value;
	}
	
	public static StripID getStrip(int start, int stop) {
		String temp;

		if (start < stop)
			temp = "" + start + "-" + stop;
		else
			temp = "" + stop + "-" + start;
		
		for (StripID tempStrip : StripID.values()) {
			if (tempStrip.getValue().equals(temp))
				return tempStrip;
		}
		return null;
	}
	
	public static StripID[] getStrip(String value) {
		for (StripID str : StripID.values()) {
			if (str.toString().equals(value)) {
				return new StripID[] {str};
			}
		}
		return null;
		
	}
	
	public Integer getRedChannel() {
		return rgbStartChannel;
	}
	
	public Integer getGreenChannel() {
		return rgbStartChannel+2;
	}
	
	public Integer getBlueChannel() {
		return rgbStartChannel+1;
	}
	
	
	
//	for (int i = 1; i <= 11; i++) {
//		for (int j = 1; j <= 11; j++) {
//			if(i!=j && i < j) {
//				System.out.println("\tS"+i+"_"+j+"(\""+i+"-"+j+"\"),");
//				System.out.println("\tS"+j+"_"+i+"(\""+i+"-"+j+"\"),");
//			}
//		}
//	}
}
