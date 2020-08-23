//	@formatter:off

package algorithms.metaProcessors;

import java.nio.*;
import java.util.ArrayList;import java.util.Arrays;
import algorithms.analyzers.BitRepresent;import data.FormatTags;

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

	static Byte[] writeSignal(int[] signal, int bitDepth){

		ArrayList<Byte>
			cache = new ArrayList<>();

		for (int sample : signal){

			boolean
				sampleIsNegative = sample < 0;

			int
				numberOfBytes = bitDepth >> 3;
			
			if (bitDepth % 8 > 0) 
				
				bitDepth++;

			for (int i = 0; i < numberOfBytes; i++){

				boolean
					containsMSB = i == numberOfBytes - 1;
				
				int
					byteShift = i << 3,	
					b = sample & (0xFF << byteShift);
				
				b >>>= (i << 3);
				
				if(containsMSB && bitDepth < 32 && sampleIsNegative)

					b |= 0x80;
				
				cache.add((byte) b);
			}

			return (Byte[]) cache.toArray();	// ? this is doubtful, as the byte[] is desired ...
		}

		return null;
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

	static byte[] writeSignalChannels(int[][] signalChannels, int bitDepth){






		byte[] output = {};

		return output;
	}	// TODO




	static FormatTags readFormat(byte[]fileContent){

		int
			tagOrdinal = readFormatOrdinal(fileContent);

		return FormatTags.values()[tagOrdinal];
	}

	static int readFormatOrdinal(byte[]fileContent){

		byte[]
			b = Arrays.copyOfRange(fileContent, 20, 23);

		String
			byteString = Arrays.toString(b);

		for(int i = 0 ; i < FormatTags.byteStrings.length ; i++)

			if (FormatTags.byteStrings[i].compareTo(byteString) == 0)

				return i;

		return 0;
	}

	static byte[] writeFormat(FormatTags format){

		byte[] output = {};

		return output;
	}	// * TODO



	static int readFileLength(byte[] fileContent){

		ByteBuffer
			buffer = wrap(fileContent, 4, 4);

		buffer.order(ByteOrder.LITTLE_ENDIAN);

		return buffer.getInt();
	}

	static byte[] writeFileLength(int fileLength){

		byte[] output = {};

		return output;
	}	// * TODO



	static int readNumberOfChannels(byte[] fileContent){

		ByteBuffer
			buffer = wrap(fileContent, 22, 2);

		buffer.order(ByteOrder.LITTLE_ENDIAN);

		short
			s = buffer.getShort();

		return Short.toUnsignedInt(s);
	}

	static byte[] writeNumberOfChannels(int numberOfChannels){

		byte[] output = {};

		return output;
	}	// * TODO



	static int readSampleRate(byte[] fileContent){

		ByteBuffer
			buffer = wrap(fileContent, 24, 4);

		buffer.order(ByteOrder.LITTLE_ENDIAN);

		return buffer.getInt();
	}

	static byte[] writeSampleRate(int sampleRate){

		byte[] output = {};

		return output;
	}	// * TODO



	static int readSampleFrameSize(byte[] fileContent){

		ByteBuffer
			buffer = wrap(fileContent, 32, 2);

		buffer.order(ByteOrder.LITTLE_ENDIAN);

		short
			s = buffer.getShort();

		return Short.toUnsignedInt(s);
	}

	static byte[] writeSampleFrameSize(int sampleFrameSize){

		byte[] output = {};

		return output;
	}	// * TODO



	static int readBitDepth(byte[] fileContent){

		ByteBuffer
			buffer = wrap(fileContent, 34, 2);

		buffer.order(ByteOrder.LITTLE_ENDIAN);

		short
			s = buffer.getShort();

		return Short.toUnsignedInt(s);
	}

	static byte[] writeBitDepth(int bitDepth){

		byte[] output = {};

		return output;
	}	// * TODO



	static int readDataBlockLength(byte[] fileContent){

		ByteBuffer
			buffer = wrap(fileContent, 40, 4);

		buffer.order(ByteOrder.LITTLE_ENDIAN);

		return buffer.getInt();
	}

	static byte[] writeDataBlockLength(int dataBlockLength){

		byte[] output = {};

		return output;
	}	// * TODO
}

//	@formatter:on