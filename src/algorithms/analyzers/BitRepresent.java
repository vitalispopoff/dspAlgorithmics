//	@formatter:off

package algorithms.analyzers;

public interface BitRepresent {

	static String bitRepresent(byte number){

		return String.format("%8s", Integer.toBinaryString(number & 0xFF)).replace(' ', '0');
	}

	static String bitRepresent(short number){

		return String.format("%16s", Integer.toBinaryString(number & 0xFFFF)).replace(' ', '0');
	}

	static String bitRepresent(int number){

		return String.format("%32s", Integer.toBinaryString(number)).replace(' ', '0');
	}

	static String bitRepresent(long number){

		return String.format("%64s", Long.toBinaryString(number)).replace(' ', '0');
	}
}

//	@formatter:on