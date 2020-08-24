//	@formatter:off

package algorithms.metaProcessors;

import java.nio.*;
import java.util.*;
import data.FormatTags;

import static java.nio.ByteBuffer.wrap;
import static data.FormatTags.*;

public interface FileContentConverter {

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
				sampleSize = readSampleFrameSize(fileContent),
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



	static FormatTags readFormat(byte[]fileContent){

		int
			tagOrdinal = readFormatOrdinal(fileContent);

		return FormatTags.values()[tagOrdinal];
	}

	static int readFormatOrdinal(byte[]fileContent){

/*		byte[]
			b = Arrays.copyOfRange(fileContent, 20, 23);

		String
			byteString = Arrays.toString(b);

		for(int i = 0 ; i < FormatTags.byteStrings.length ; i++)

			if (FormatTags.byteStrings[i].compareTo(byteString) == 0)

				return i;

		return 0;*/	// disposable

		return readDataSample(fileContent, 20, 2);
	}

	static byte[] writeFormat(FormatTags format){

		return FormatTags.bytes[format.ordinal()];
	}



	static int readFileLength(byte[] fileContent){

/*		ByteBuffer
			buffer = wrap(fileContent, 4, 4);

		buffer.order(ByteOrder.LITTLE_ENDIAN);

		return 8 + buffer.getInt();*/	// disposable

		return 8 + readDataSample(fileContent, 4, 4);
	}

	static byte[] writeFileLength(int fileLength){

		return writeDataSample(fileLength - 8, 4);
	}


	static int readNumberOfChannels(byte[] fileContent){

/*		ByteBuffer
			buffer = wrap(fileContent, 22, 2);

		buffer.order(ByteOrder.LITTLE_ENDIAN);

		short
			s = buffer.getShort();

		return Short.toUnsignedInt(s);*/	// disposable

		return readDataSample(fileContent, 22, 2);
	}

	static byte[] writeNumberOfChannels(int numberOfChannels){
		
		return writeDataSample(numberOfChannels, 2);
	}	



	static int readSampleRate(byte[] fileContent){

/*		ByteBuffer
			buffer = wrap(fileContent, 24, 4);

		buffer.order(ByteOrder.LITTLE_ENDIAN);

		return buffer.getInt();*/	// disposable

		return readDataSample(fileContent, 24, 4);
	}

	static byte[] writeSampleRate(int sampleRate){

		return writeDataSample(sampleRate, 4);
	}	



	static int readSampleFrameSize(byte[] fileContent){

/*		ByteBuffer
			buffer = wrap(fileContent, 32, 2);

		buffer.order(ByteOrder.LITTLE_ENDIAN);

		short
			s = buffer.getShort();

		return Short.toUnsignedInt(s);*/	// disposable

		return readDataSample(fileContent, 32, 2);
	}

	static byte[] writeSampleFrameSize(int sampleFrameSize){

		return writeDataSample(sampleFrameSize, 2);
	}	



	static int readBitDepth(byte[] fileContent){

/*		ByteBuffer
			buffer = wrap(fileContent, 34, 2);

		buffer.order(ByteOrder.LITTLE_ENDIAN);

		short
			s = buffer.getShort();

		return Short.toUnsignedInt(s);*/	// disposable

		return readDataSample(fileContent, 24, 2);
	}

	static byte[] writeBitDepth(int bitDepth){

		return writeDataSample(bitDepth, 2);
	}	



	static int readDataBlockLength(byte[] fileContent){

/*		ByteBuffer
			buffer = wrap(fileContent, 40, 4);

		buffer.order(ByteOrder.LITTLE_ENDIAN);

		return buffer.getInt();*/	// disposable

		return readDataSample(fileContent, 40, 4);
	}

	static byte[] writeDataBlockLength(int dataBlockLength){

		return writeDataSample(dataBlockLength, 4);
	}	
}

//	@formatter:on