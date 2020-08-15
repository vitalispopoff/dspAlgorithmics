package algorithms.analyzers;

public abstract class BitRepresent {

	public static String bitRepresent(int number){

		return String.format("%32s", Integer.toBinaryString(number)).replace(' ', '0');
	}

	public static String bitRepresent(short number){

		String s = String.format("%16s", Integer.toBinaryString(number & 0xFFFF)).replace(' ', '0');

		return s.length() > 16 ? s.substring(16, 32) : s;
	}

	public static String bitRepresent(byte number){

		return String.format("%8s", Integer.toBinaryString(number & 0xFF)).replace(' ', '0');
	}


}
