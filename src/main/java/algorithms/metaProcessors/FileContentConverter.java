//	@formatter:off

package algorithms.metaProcessors;

import static algorithms.analyzers.BitRepresent.bitRepresent;

public interface FileContentConverter {

	static int dataFrameReader(byte[] frame){

		int
			sample = frame[frame.length - 1],
			byteShift = (frame.length - 1) << 3;

		sample <<= byteShift;

		for (int i = 0; i < frame.length - 1; i++){

			byteShift = i << 3;

			int
				b = frame[i] & 0xFF;

			b <<= byteShift;

			sample |= b;
		}

		return sample;
	}

	static byte[] dataFrameWriter(int frame, int frameLength){

		boolean
			isFrameNegative = frame < 0;

		if (frameLength > 3) frameLength = 4;

		byte[]
			bytes = new byte[frameLength];

		for (int i = 0; i < frameLength; i++){

			int
				filterShift = 1 >> 8 * i;

			byte
				cache = (byte) (frame & 0xFF);

			if (isFrameNegative) cache |= 0x80;

			bytes[i] = cache;
		}

		return bytes;
	}
}

//	@formatter:on