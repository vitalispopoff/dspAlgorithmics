package data;

import java.io.File;
import java.util.Arrays;

import data.structure.*;
import data.structure.header.*;
import data.structure.signal.*;


import static algorithms.metaProcessors.FileManager.*;
import static data.FileCache.addToCache;
import static data.structure.header.WaveFileContentStructure.*;


public class WaveFile implements AudioFile {


	public FileAddress
		fileAddress;

	public FileHeader
		header;

	AudioData
		audioAnchor;

	Channeling
		channelAnchor;



	public WaveFile(File file){

		byte[]
			fileContent = loadFile(file);

		header = FileHeader.instanceOf(fileContent);

		int
			start = WaveFileContentStructure.SIGNAL.getLocation()[0],
			end = start + header.getField(DATA_SIZE);

		byte[]
			signalSource = Arrays.copyOfRange(fileContent, start, end);

		audioAnchor = AudioData.setFromSource(signalSource, header.getField(BLOCK_ALIGN));


		channelAnchor = Channeling.newInstance(signalSource, header.getField(BLOCK_ALIGN), header.getField(CHANNELS));

		setFileAddress(file.getPath());

		addToCache(this);
	}



	public void setFileAddress(String fileAddress){

		this.fileAddress = FileAddress.readFileAddress(fileAddress);
	}

	public FileAddress getFileAddress( ){

		return fileAddress;
	}



	public FileHeader getHeader( ){

		return header;
	}

	public AudioData getAudioAnchor(){

		return audioAnchor;
	}

	public Channeling getChannelAnchor(){

		return channelAnchor;
	}

	public byte[] releaseSource(){

		int[]
			lengths = updateFileSize();

		byte[]
			headerSource = header.getSource(),
			signalSource =  channelAnchor.getSource(header.getField(BITS_PER_SAMPLE)),
			result = new byte[lengths[2]];

		System.arraycopy(headerSource, 0, result, 0, lengths[0]);
		System.arraycopy(signalSource, 0, result, lengths[0], lengths[1]);

		return result;
	}



	int[] updateFileSize(){

		int
			headerLength = header.getSource().length,
			signalLength =  channelAnchor.getSource(header.getField(BITS_PER_SAMPLE)).length,
			currentLength = headerLength + signalLength;

		header.setField(FILE_SIZE, currentLength - 8);

		return new int[] {headerLength, signalLength, currentLength};
	}
}