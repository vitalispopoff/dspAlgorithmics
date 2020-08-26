package algorithms.metaProcessors;

import data.FileContentStructure;

import java.nio.*;
//import java.util.Arrays;
//import java.util.*;
import static data.FileContentStructure.*;


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

/*	static int readFormatTagOrdinal(byte[]fileContent){

		int
			start = FORMAT_TAG.getStart(),
			end = start + FORMAT_TAG.getLength(),
			index = 0;

		byte[]
			b = Arrays.copyOfRange(fileContent, start, end);

		for (byte[] pair : FormatTags.bytes){

			boolean
				arraysAreEqual = Arrays.equals(b, pair);

			if (arraysAreEqual)

				return index;

			else index++;
		}

		return 0;
	}

	static FormatTags readFormatTag(byte[]fileContent){

		int
			tagOrdinal = readFormatTagOrdinal(fileContent);

		return FormatTags.values()[tagOrdinal];
	}*/		// * disposable
/*	static int[] readSignal(byte[] fileContent) {

		int
			dataBlockLength = readDataField(fileContent, DATA_SIZE),
			sampleSize = readDataField(fileContent, BITS_PER_SAMPLE) >> 3,
			tagNumber = readFormatTagOrdinal(fileContent),
			start = FormatTags.starts[tagNumber],
			index = 0;

		int[]
			signal = new int[dataBlockLength / sampleSize];

		for (int i = start; i < start + dataBlockLength; i += sampleSize)

			signal[index++] = readDataSample(fileContent, i, sampleSize);

		return signal;
	}

	static int[][] readSignalChannels(byte[] fileContent){

		int[]
			signal = readSignal(fileContent);

		return readSignalChannels(signal, fileContent);
	}

	static int[][] readSignalChannels(int[] signal, byte[] fileContent){

		int
			numberOfInputs = readDataField(fileContent, CHANNELS),
			outputLength = signal.length / numberOfInputs;

		int[][]
			outputs = new int[numberOfInputs][outputLength];

		for (int i = 0; i < signal.length ; i++){

			int
				channel = i % numberOfInputs,
				index = i / numberOfInputs;

			outputs[channel][index] = signal[i];
		}

		return outputs;
	}

	static byte[] writeSignalChannels(int[][] signalChannels, int bitsPerSample){

		int
			sampleSize =  (bitsPerSample >> 3),
			signalLength = signalChannels.length * signalChannels[0].length * sampleSize,
			channels = signalChannels.length;

		byte[]
			signal = new byte[signalLength];

		for (int sampleIndex = 0; sampleIndex < signalChannels[0].length; sampleIndex++)								// * selecting sample

			for (int channel = 0; channel < channels; channel++){														// * selecting channel array

				int
					sampleInChannel = sampleIndex * sampleSize + channel * channels,
					sample = signalChannels[channel][sampleIndex];

				byte[]
					bytes = writeDataSample(sample ,sampleSize);

				System.arraycopy(bytes, 0, signal, sampleInChannel , sampleSize);
			}

		return signal;
	}*/		// * disposable

}
