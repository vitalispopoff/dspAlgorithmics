//	@formatter:off

package algorithms.metaProcessors;

public interface FileContentReader {

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
}

//	@formatter:on