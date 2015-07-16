import jssc.SerialPortList;

	public class JSCCTest {
	   
	    public static void main2(String[] args) {
	        System.out.println("hw");
	    	
	    	String[] portNames = SerialPortList.getPortNames();
	        for(int i = 0; i < portNames.length; i++){
	            System.out.println(portNames[i]);
	        }
	    }
	}