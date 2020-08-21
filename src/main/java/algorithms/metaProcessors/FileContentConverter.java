//	@formatter:off

package algorithms.metaProcessors;

import algorithms.analyzers.FormatTag;import algorithms.analyzers.FormatTag.FormatTags;import java.nio.ByteBuffer;import java.nio.ByteOrder;import static algorithms.analyzers.FormatTag.FormatTags.starts;
import static algorithms.analyzers.FormatTag.getFormatTag;import static java.nio.ByteBuffer.wrap;import static java.util.Arrays.copyOfRange;

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

		if (frameLength > 3) frameLength = 4;

		boolean
			isFrameNegative = frame < 0;

		byte[]
			bytes = new byte[frameLength];

		for (int i = 0; i < frameLength; i++){

			int
				filterShift = 1 >> (8 * i);

			byte
				cache = (byte) (frame & 0xFF);

			if (isFrameNegative) cache |= 0x80;

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

	static FormatTags readFormat(byte[]fileContent){

		ByteBuffer
				buffer = wrap(fileContent, 20, 2);

		buffer.order(ByteOrder.LITTLE_ENDIAN);

		short
				s = buffer.getShort();

		int
				i = Short.toUnsignedInt(s);

		return getFormatTag(i);
	}

	static int readFormatOrdinal(byte[]fileContent){

		FormatTags
			result = readFormat(fileContent);

		return result.ordinal();
	}

	static int readFileLength(byte[] fileContent){

		ByteBuffer
				buffer = wrap(fileContent, 4, 4);

		buffer.order(ByteOrder.LITTLE_ENDIAN);

		return buffer.getInt();
	}

	static int readNumberOfChannels(byte[] fileContent){

		ByteBuffer
				buffer = wrap(fileContent, 22, 2);

		buffer.order(ByteOrder.LITTLE_ENDIAN);

		short
				s = buffer.getShort();

		return Short.toUnsignedInt(s);
	}

	static int readSampleRate(byte[] fileContent){

		ByteBuffer
				buffer = wrap(fileContent, 24, 4);

		buffer.order(ByteOrder.LITTLE_ENDIAN);

		return buffer.getInt();
	}

	static int readSampleFrameSize(byte[] fileContent){

		ByteBuffer
				buffer = wrap(fileContent, 32, 2);

		buffer.order(ByteOrder.LITTLE_ENDIAN);

		short
				s = buffer.getShort();

		return Short.toUnsignedInt(s);
	}

	static int readBitDepth(byte[] fileContent){

		ByteBuffer
				buffer = wrap(fileContent, 34, 2);

		buffer.order(ByteOrder.LITTLE_ENDIAN);

		short
				s = buffer.getShort();

		return Short.toUnsignedInt(s);
	}

	static int readDataBlockLength(byte[] fileContent){

		ByteBuffer
				buffer = wrap(fileContent, 40, 4);

		buffer.order(ByteOrder.LITTLE_ENDIAN);

		return buffer.getInt();
	}
}

//	@formatter:on