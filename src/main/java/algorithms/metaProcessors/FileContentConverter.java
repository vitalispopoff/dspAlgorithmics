//	@formatter:off

package algorithms.metaProcessors;

import java.nio.*;
import java.util.*;
import data.FileContentStructure;
import data.FormatTags;

import static data.FileContentStructure.*;
import static data.FormatTags.*;

public interface FileContentConverter {

	static FormatTags readFormat(byte[]fileContent){

		int
				tagOrdinal = readFormatOrdinal(fileContent);

		return FormatTags.values()[tagOrdinal];
	}

	static int readFormatOrdinal(byte[]fileContent){

		int
				start = FORMAT_TAG.getStart(),
				end = start + FORMAT_TAG.getLength(),
				index = 0;

		byte[]
				b = Arrays.copyOfRange(fileContent, start, end);

		for (byte[] pair : bytes){

			boolean
					arraysAreEqual = Arrays.equals(b, pair);

			if (arraysAreEqual)

				return index;

			else index++;
		}

		return 0;
	}

	static byte[] writeFormat(FormatTags format){

		return FormatTags.bytes[format.ordinal()];
	}



	static int[][] readSignalChannels(byte[] fileContent){

		int[]
			signal = readSignal(fileContent);
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

	static int[] readSignal(byte[] fileContent) {

		int
				dataBlockLength = readDataField(fileContent, DATA_SIZE),
				sampleSize = readDataField(fileContent, BLOCK_SIZE),
				start = starts[readFormatOrdinal(fileContent)],
				index = 0;

		int[]
				signal = new int[dataBlockLength / sampleSize];

		for (int i = start; i < start + dataBlockLength; i += sampleSize)

			signal[index++] = readDataSample(fileContent, i, sampleSize);

		return signal;
	}

	static byte[] writeSignalChannels(int[][] signalChannels, int sampleFrameSize){

		int
			sampleSize = sampleFrameSize / signalChannels.length,
			signalLength = signalChannels.length * sampleFrameSize;

		byte[]
			signal = new byte[signalLength];

		for (int i = 0; i < signalChannels[0].length; i++)

			for (int j = 0; j < 2; j++){

				int
					signalIndex = i * sampleFrameSize;

				byte[]
					bytes = writeDataSample(signalChannels[j][i],sampleSize);

				System.arraycopy(bytes, 0, signal, signalIndex, bytes.length);
			}

		return signal;
	}



	static int readDataSample(byte[] fileContent, int startIndex, int sampleSize){

		byte[]
				bytes = new byte[4];

		System.arraycopy(fileContent, startIndex, bytes, 0, sampleSize);

		boolean
				sampleIsNegative = bytes[sampleSize - 1] < 0;

		if (sampleIsNegative){

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

			sampleLength = 4;	// only preventing possible errors with declared sampleLength

		boolean
				isFrameNegative = sample < 0;

		byte[]
				bytes = new byte[sampleLength];

		for (int i = 0; i < sampleLength; i++){

			byte
					cache = (byte) (sample & 0xFF);

			if (isFrameNegative)

				cache |= 0x80;

			bytes[i] = cache;
		}

		return bytes;
	}



	static int readDataField(byte[] fileContent, FileContentStructure field){

		int
			start = field.getStart(),
			length = field.getLength();

		return readDataSample(fileContent, start, length);
	}

	static byte[] writeDataField(int input, FileContentStructure field){

		int
				length = field.getLength();

		return writeDataSample(input, length);
	}

/*	static byte[] writeFileLength(int fileLength){

		return writeDataSample(fileLength - 8, 4);
	}



	static byte[] writeNumberOfChannels(int numberOfChannels){
		
		return writeDataSample(numberOfChannels, 2);
	}	



	static byte[] writeSampleRate(int sampleRate){

		return writeDataSample(sampleRate, 4);
	}	



	static byte[] writeBlockSize(int sampleFrameSize){

		return writeDataSample(sampleFrameSize, 2);
	}	



	static byte[] writeBitDepth(int bitDepth){

		return writeDataSample(bitDepth, 2);
	}	



	static byte[] writeDataBlockLength(int dataBlockLength){

		return writeDataSample(dataBlockLength, 4);
	}	*/	// disposable

}

//	@formatter:on