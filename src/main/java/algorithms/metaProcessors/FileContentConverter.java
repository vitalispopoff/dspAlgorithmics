//	@formatter:off

package algorithms.metaProcessors;

public interface FileContentConverter {

	static int dataFrameReader(byte[] frame){

		int
			max = frame.length - 1,
			sample = frame[max],
			byteShift = max << 3;

		sample <<= byteShift;

		for (int i = 0; i < max; i++){

			byteShift = i << 3;

			int
				b = frame[i] & 0xFF;

			b <<= byteShift;

			sample |= b;
		}

		return sample;
	}

	static byte[] dataFrameWriter(int frame, int frameLength){

		if (frameLength > 3) frameLength = 4;

		boolean
			isFrameNegative = frame < 0;

		byte[]
			bytes = new byte[frameLength];

		for (int i = 0; i < frameLength; i++){

			int
				filterShift = 1 >> (8 * i);

			byte
				cache = (byte) (frame & 0xFF);

			if (isFrameNegative) cache |= 0x80;

			bytes[i] = cache;
		}

		return bytes;
	}
}

//	@formatter:on