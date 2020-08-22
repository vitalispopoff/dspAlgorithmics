//	@formatter:off

package algorithms.metaProcessors;

import java.nio.*;import java.util.Arrays;

import data.FormatTags;

import static java.nio.ByteBuffer.wrap;
import static java.util.Arrays.copyOfRange;

import static data.FormatTags.*;

public interface FileContentConverter {

	static int readDataFrame(byte[] frame){

		int
			max = frame.length - 1,
			sample = frame[max],
			byteShift = max << 3;

		sample <<= byteShift;

		for (int i = 0; i < max; i++){

			byteShift = i << 3;

			int
				b = frame[i] & 0xFF;

			b <<= byteShift;

			sample |= b;
		}

		return sample;
	}

	static byte[] writeDataFrame(int frame, int frameLength){

		if (frameLength > 3)

			frameLength = 4;

		boolean
			isFrameNegative = frame < 0;

		byte[]
			bytes = new byte[frameLength];

		for (int i = 0; i < frameLength; i++){

			int
				filterShift = 1 >> (8 * i);

			byte
				cache = (byte) (frame & 0xFF);

			if (isFrameNegative)

				cache |= 0x80;

			bytes[i] = cache;
		}

		return bytes;
	}



	static int[] readSignal(byte[] fileContent) {

		int
			dataBlockLength = readDataBlockLength(fileContent),
			sampleFrameSize = readSampleFrameSize(fileContent),
			start = starts[readFormatOrdinal(fileContent)],
			index = 0;

		int[]
			signal = new int[dataBlockLength / sampleFrameSize];

		for (int i = start; i < start + dataBlockLength; i += sampleFrameSize){

			byte[]
				bytes = copyOfRange(fileContent, i, i + sampleFrameSize);

			signal[index++] = readDataFrame(bytes);
		}

		return signal;
	}

	static byte[] writeSignal(int[] signal){

		byte[] output = {};

		return output;
	}	// * TODO



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