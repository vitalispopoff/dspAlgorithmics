package algorithms.metaProcessors;

abstract class _DeserializationReader {


	static byte[]
		source;

	static int
		blockAlign = 2,
		byteIndex = 0;



	static byte readSource() {

		if (byteIndex == blockAlign) byteIndex = 0;

		return source[byteIndex];
	}

	static int byteToProperInt(byte b) {

		int
			result = Byte.toUnsignedInt(b),
			shift = 8 * byteIndex;

		result <<= shift;

		return result;
	}

	static int assembleSampleValue() {

		int
			t = 0;

		boolean
			isNegative = false;

		while (byteIndex < blockAlign) {

			byte
				b = readSource();
			int
				inc = byteToProperInt(b);

			t += inc;

			isNegative = ++byteIndex == blockAlign && b < 0;
		}

		if (isNegative){

			int
				shift = 32 - 8 * blockAlign;

			t <<= shift;
			t >>= shift;
		}

		return t;
	}


	//	----------------------------------------------------------------------------------------------------------------


	public static void main(String[] args) {

		int
			t = assembleSampleValue();

		System.out.println(t);
		System.out.println(Integer.toHexString(t));
	}
}