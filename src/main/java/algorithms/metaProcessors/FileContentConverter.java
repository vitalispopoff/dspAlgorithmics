//	@formatter:off

package algorithms.metaProcessors;

import java.nio.*;
import java.util.*;
import data.*;

import static data.FileContentStructure.*;

public interface FileContentConverter {

	static byte[] convertToBytes(Wave source){

		WaveHeader
			header = source.header;

		int
			bitDepth = header.bitsPerSample,
			fileLength = source.header.getFileSize() + 8,
			signalLength = 0;

		byte[]
			export = new byte[fileLength],

			fileSize = writeDataField(header.getFileSize(), FILE_SIZE),
			fmtSize = writeDataField(header.getFmtSize(), FMT_SIZE),
			channels = writeDataField(header.getChannels(), CHANNELS),
			samplePerSec = writeDataField(header.getChannels(), SAMPLE_PER_SEC),
			avBytePerSec = writeDataField(header.getAvgBytesPerSec(), AV_BYTE_PER_SEC),
			blockAlign = writeDataField(header.getBlockAlign(), BLOCK_ALIGN),
			bitsPerSample = writeDataField(header.getBitsPerSample(), BITS_PER_SAMPLE),

			dataSize = writeDataField(header.getDataSize(), DATA_SIZE),
			signal = writeSignalChannels(source.getChannelSignals(), header.getBitsPerSample());

		byte[][]
			fields = {

			WaveHeader.getFileId(),
			fileSize,
			WaveHeader.getWaveId(),

			WaveHeader.getFmt_Id(),
			fmtSize,
			header.getFormatTag().getBytes(),
			channels,
			samplePerSec,
			avBytePerSec,
			blockAlign,
			bitsPerSample,

			WaveHeader.getDataId(),
			dataSize,
		};

		for (int i = 0; i < fields.length ; i++) {

			int
				start = values()[i].getStart(),

				length = values()[i] == SIGNAL
					 ? header.getDataSize()
					 : values()[i].getLength();

			System.arraycopy(fields[i], 0, export, start, length);
		}
		return export;
	}



	static int readDataSample(byte[] fileContent, int startIndex, int sampleSize){

		byte[]
			bytes = new byte[4];

		System.arraycopy(fileContent, startIndex, bytes, 0, sampleSize);

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

			sampleLength = 4;	// preventing possible errors with declared sampleLength being to big only

		byte[]
			bytes = new byte[sampleLength];

		for (int i = 0; i < sampleLength; i++)

			bytes[i] = (byte) (sample >>> (i << 3));

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



	static int readFormatTagOrdinal(byte[]fileContent){

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
	}



	static int[] readSignal(byte[] fileContent) {

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
	}
}

//	@formatter:on