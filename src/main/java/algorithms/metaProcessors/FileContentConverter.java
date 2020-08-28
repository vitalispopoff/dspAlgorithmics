package algorithms.metaProcessors;

import data.structure.FileContentStructure;

import java.nio.*;
import static data.structure.FileContentStructure.*;

public interface FileContentConverter {

	static int readDataSample(byte[] source, int startIndex, int sampleSize){

		byte[]
			bytes = new byte[4];

		System.arraycopy(source, startIndex, bytes, 0, sampleSize);

		boolean
			sampleIsNegative = bytes[sampleSize - 1] < 0;

		if (sampleIsNegative) {

			for (int i = 3 ; i >= sampleSize ; i--)

				bytes[i] |= 0xFF;

			bytes[3] |= 0x80;
		}

		ByteBuffer
			buffer = ByteBuffer.wrap(bytes);

		buffer.order(ByteOrder.LITTLE_ENDIAN);

		return buffer.getInt();
	}

	static byte[] writeDataSample(int sample, int sampleLength){

		if (sampleLength > 3)

			sampleLength = 4;

		byte[]
			bytes = new byte[sampleLength];

		for (int i = 0; i < sampleLength; i++)

			bytes[i] = (byte) (sample >>> (i << 3));

		return bytes;
	}



	static int readDataField(byte[] source, FileContentStructure field){

		int[]
			location = field.getLocation();

		if (location[0] == Integer.MIN_VALUE)

			return location[1];

		return readDataSample(source, location[0], location[1]);
	}

	static void writeDataField(byte[] source, int input, FileContentStructure field){

		int[]
			location = field.getLocation();

		if (location[0] > 0 && field != WAVE_ID){

			byte[]
				bytes = writeDataSample(input, location[1]);

			System.arraycopy(bytes, 0, source, location[0], location[1]);
		}
	}
}
