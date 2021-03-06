package algorithms.metaProcessors;

import java.nio.*;

import data.structure.WaveFileStructure;

import static data.structure.WaveFileStructure.*;

public interface FileContentConverter {


	static int readDataSample(byte[] source, int startIndex, int blockAlign){

		byte[]
			bytes = new byte[4];

		System.arraycopy(source, startIndex, bytes, 0, blockAlign);

		boolean
			sampleIsNegative = bytes[blockAlign - 1] < 0;

		if (sampleIsNegative) {

			for (int i = 3 ; i >= blockAlign ; i--)

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



	static int readDataField(byte[] source, WaveFileStructure field){

		int[]
			location = field.getLocation();

		if (location[0] == Integer.MIN_VALUE)

			return location[1];

		return readDataSample(source, location[0], location[1]);
	}

	static void writeDataField(byte[] source, int input, WaveFileStructure field){

		int[]
			location = field.getLocation();

		if (location[0] > 0 && field != WAVE_ID){

			byte[]
				bytes = writeDataSample(input, location[1]);

			System.arraycopy(bytes, 0, source, location[0], location[1]);
		}
	}



	static Integer[] bytesToIntegers(byte[] source, int sampleLength){

		int
			stripLength = source.length / sampleLength;

		Integer[]
			result = new Integer[stripLength];

		for (int i = 0; i < stripLength; i++)

			result[i] =  FileContentConverter.readDataSample(source, i * sampleLength, sampleLength);

		return result;
	}

	static byte[] integersToBytes(Integer[] signal, int bitsPerSample){

		int
			sampleLength = bitsPerSample >>> 3;

		byte[]
			result = new byte[signal.length * sampleLength],
			sample;

		for(int i = 0 ; i < signal.length; i++) {

			int
				resultIndex = i * sampleLength;

			sample = writeDataSample(signal[i], sampleLength);

			System.arraycopy(sample, 0, result, resultIndex, sampleLength);
		}

		return result;
	}
}