//	@formatter:off

package algorithms.processors.metaProcessors;

public interface FileContentReader {

	static int dataFrameReader(byte[] frame){

		int
			sample = frame[frame.length - 1];

		sample <<= (frame.length - 1) << 3;

		for (int i = 0; i < frame.length - 1; i++){

			int
				b = frame[i] & 0xFF;

			b <<= i << 3;

			sample |= b;
		}

		return sample;
	}
}

//	@formatter:on