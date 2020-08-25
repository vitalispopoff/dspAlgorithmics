//	@formatter:off

package algorithms.metaProcessors;

import java.nio.*;
import java.util.*;
import data.FormatTags;

import static data.FileContentStructure.*;
import static data.FormatTags.*;

public interface FileContentConverter {

	static int[][] readSignalChannels(byte[] fileContent){

		int[]
			signal = readSignal(fileContent);
		int
			numberOfInputs = readNumberOfChannels(fileContent),
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
				dataBlockLength = readDataBlockLength(fileContent),
				sampleSize = readBlockSize(fileContent),
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



	static int readFileLength(byte[] fileContent){

		int
			start = FILE_SIZE.getStart(),
			length = FILE_SIZE.getLength();

		return /*8 + */readDataSample(fileContent, start, length);	// ? value to be decided : whether including first chunk first slots
	}

	static byte[] writeFileLength(int fileLength){

		return writeDataSample(fileLength - 8, 4);
	}



	static int readNumberOfChannels(byte[] fileContent){

		int
			start = CHANNELS.getStart(),
			length = CHANNELS.getLength();

		return readDataSample(fileContent, start, length);
	}

	static byte[] writeNumberOfChannels(int numberOfChannels){
		
		return writeDataSample(numberOfChannels, 2);
	}	



	static int readSampleRate(byte[] fileContent){

		int
			start = SAMPLING_RATE.getStart(),
			length = SAMPLING_RATE.getLength();

		return readDataSample(fileContent, start, length);
	}

	static byte[] writeSampleRate(int sampleRate){

		return writeDataSample(sampleRate, 4);
	}	



	static int readBlockSize(byte[] fileContent){

		int
				start = BLOCK_SIZE.getStart(),
				length = BLOCK_SIZE.getLength();

		return readDataSample(fileContent, start, length);
	}

	static byte[] writeBlockSize(int sampleFrameSize){

		return writeDataSample(sampleFrameSize, 2);
	}	



	static int readBitDepth(byte[] fileContent){

		int
				start = BIT_DEPTH.getStart(),
				length = BIT_DEPTH.getLength();

		return readDataSample(fileContent, start, length);
	}

	static byte[] writeBitDepth(int bitDepth){

		return writeDataSample(bitDepth, 2);
	}	



	static int readDataBlockLength(byte[] fileContent){

		int
				start = DATA_SIZE.getStart(),
				length = DATA_SIZE.getLength();

		return readDataSample(fileContent, start, length);
	}

	static byte[] writeDataBlockLength(int dataBlockLength){

		return writeDataSample(dataBlockLength, 4);
	}	
}

//	@formatter:on