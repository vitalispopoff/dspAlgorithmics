//	@formatter:off

package data;

import algorithms.processors.metaProcessors.ChannelSplitter;

import static algorithms.analyzers.FormatTag.FormatTags.starts;
import static algorithms.processors.metaProcessors.FileManager.*;

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

		setHeader();	// prevents NullPointerException when file is not found.

		if(verifyFileExistence(fileAddress)){

			setFileAddress(fileAddress);
			setFileContent(fileAddress);
			setHeader();
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

	public void setHeader(){

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

			int
				sample = fileContent[i + sampleFrameSize - 1];

			boolean
				flag = sample < 0;

			if (flag)
				sample <<= (sampleFrameSize - 1) << 3;

			else
				sample <<= (sampleFrameSize - 1) << 3;

			for (int j = 0; j < sampleFrameSize - 1; j++) {

				int
					aByte = fileContent[j + i] & 0xFF;

				if (flag)
					aByte <<= (j) << 3;

				else
					aByte <<= j << 3;

				sample |= aByte;
			}

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

		String
			address_folder = "C:\\Users\\Voo\\Desktop\\unpeak\\shortie\\";

		{
			String
				address_0 = "2_samples-mono.wav",
				address_1 = "2_samples.wav",
				address_2 = "2_samples-mono-8bit.wav",
				address_3 = "shortie-mono-16bit.wav",
				address_4 = "2_samples-mono-temp.wav",
				address_5 = "1kHz_16_mono.wav",
				address_6 = "2_samples-mono.aiff",

				address_400 = "*.wav",
				address_404 = "nope.wave",

				address =  address_folder + address_6;

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

	}
}

//	@formatter:on