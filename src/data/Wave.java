//	@formatter:off

package data;

import algorithms.processors.metaProcessors.ChannelSplitter;import algorithms.processors.metaProcessors.FileContentReader;

import java.util.Arrays;import static algorithms.analyzers.FormatTag.FormatTags.starts;
import static algorithms.processors.metaProcessors.FileContentReader.dataFrameReader;import static algorithms.processors.metaProcessors.FileManager.*;

public class Wave {

	public String
		fileAddress;

	public byte[]
		fileContent;

	public WaveHeader
		header;

	public int[]
		signal;

	public int[][]
		channels;

//	--------------------------------------------------------------------------------------------------------------------

	private Wave() { }

	public Wave (String fileAddress){

		setHeader(null);	// prevents NullPointerException when file is not found.

		if(verifyFileExistence(fileAddress)){

			setFileAddress(fileAddress);
			setFileContent(fileAddress);
			setHeader(fileContent);
			setSignal();
		}
	}

//	--------------------------------------------------------------------------------------------------------------------

	public void setFileAddress(String fileAddress){

		this.fileAddress = fileAddress;
	}

	public String getFileAddress() {

		return fileAddress;
	}


	public void setFileContent(String fileAddress){

		this.fileContent = loadFile(fileAddress);
	}

	public byte[] getFileContent() {

		return fileContent;
	}


	public void setHeader(byte[] fileContent){

		this.header = WaveHeader.instanceOf(fileContent);
	}

	public WaveHeader getHeader(){

		return header;
	}


	public void setSignal() {
		
		int
			dataBlockLength = header.getDataBlockLength(),
			sampleFrameSize = header.getSampleFrameSize(),
			start = starts[header.getFormatOrdinal()],
			index = 0;
		
		signal = new int[dataBlockLength / sampleFrameSize];

		for (int i = start; i < start + dataBlockLength; i += sampleFrameSize) {

/*			int
				sample = fileContent[i + sampleFrameSize - 1];

			sample <<= (sampleFrameSize - 1) << 3;

			for (int j = 0; j < sampleFrameSize - 1; j++) {

				int
					aByte = fileContent[j + i] & 0xFF;

				aByte <<= j << (3 * (j + 1));

				sample |= aByte;
			}*/		// * disposable

			int
			 sample = dataFrameReader(Arrays.copyOfRange(fileContent, i, i + sampleFrameSize));

			signal[index++] = sample;
		}
	}

	public int[] getSignal() {

		return signal;
	}


	public void setChannels(){

		this.channels = ChannelSplitter.splitChannels(this);
	}

	public int[][] getChannels(){

		return channels;
	}

//	--------------------------------------------------------------------------------------------------------------------

	@Override
	public String toString() {

		return "Wave{\n"
				+ "fileAddress = "
				+ fileAddress
				+ '\n'
				+ header.toString();
	}

//	--------------------------------------------------------------------------------------------------------------------

	public static void main(String[] args) {

		Wave
			temporal;

		{
			String
				address_folder = "C:\\Users\\Voo\\Desktop\\unpeak\\shortie\\",

				address_0 = "2_samples-mono.wav",
				address_1 = "2_samples-mono-8bit.wav",
				address_2 = "2_samples-mono-byte_unsigned.wav",
				address_3 = "2_samples-mono-8bit.wav",
				address_4 = "2_samples-mono-float.wav",
				address_5 = "2_samples-mono-double.wav",

				address_400 = "*.wav",
				address_404 = "nope.wave",

				address =  address_folder + address_0;

			temporal = new Wave(address);

		}    // * load waveFile

		int
			dataBlockLength = temporal.header.dataBlockLength,
			sampleFrameSize = temporal.header.sampleFrameSize,
			sampleRate = temporal.header.sampleRate,
			channels = temporal.header.numberOfChannels,

			nope = 0;

		int[]
			signal = temporal.signal;

	//	----------------------------------------------------------------------------------------------------------------

//		System.out.println(temporal);

		System.out.println(Arrays.toString(temporal.signal));




	}
}

//	@formatter:on