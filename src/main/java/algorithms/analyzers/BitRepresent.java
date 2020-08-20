//	@formatter:off

package algorithms.analyzers;

import static java.lang.String.format;
import static java.lang.Long.toBinaryString;
import static java.lang.Integer.toBinaryString;

public interface BitRepresent {

	static String bitRepresent(byte number){

		String
			s = toBinaryString(number & 0xFF);

		s = format("%8s", s);

		return s.replace(' ', '0');
	}

	static String bitRepresent(short number){

		String
			s = toBinaryString(number & 0xFFFF);

		s = format("%16s", s);

		return s.replace(' ', '0');
	}

	static String bitRepresent(int number){

		String
			s = toBinaryString(number);

		s = format("%32s", s);

		return s.replace(' ', '0');
	}

	static String bitRepresent(long number){

		String
			s =  toBinaryString(number);

		s = format("%64s", s);

		return s.replace(' ', '0');
	}
}

//	@formatter:on