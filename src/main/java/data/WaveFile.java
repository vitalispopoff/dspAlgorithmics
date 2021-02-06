package data;

import java.io.File;
import java.util.Arrays;

import data.structure.*;
import data.structure.header.WaveHeader;
import data.structure.signal.Audio;

import static algorithms.metaProcessors.FileManager.*;
import static data.FileCache.addToCache;
import static data.structure.FileContentStructure.*;
import static data.structure.header.WaveHeader.instanceOf;

public class WaveFile {


	public FileAddress
		fileAddress;

	public WaveHeader
		header;

	Audio
		audioData;


	public WaveFile(File file){

		byte[]
			fileContent = loadFile(file);

		header = instanceOf(fileContent);

		int
			start = 44,
			end = start + header.getField(DATA_SIZE);

		byte[]
			signalSource = Arrays.copyOfRange(fileContent, start, end);

		audioData = Audio.newInstance(signalSource, header.getField(BLOCK_ALIGN), header.getField(CHANNELS));

		setFileAddress(file.getPath());

		addToCache(this);
	}



	public void setFileAddress(String fileAddress){

		this.fileAddress = FileAddress.readFileAddress(fileAddress);
	}


	public FileAddress getFileAddress( ){

		return fileAddress;
	}

	public WaveHeader getHeader( ){

		return header;
	}


	public Audio getAudioData(){

		return audioData;
	}

	public byte[] getSource(){

		int[]
			lengths = updateFileSize();

		byte[]
			headerSource = header.getSource(),
			signalSource =  audioData.getSource(header.getField(BITS_PER_SAMPLE)),
			result = new byte[lengths[2]];

		System.arraycopy(headerSource, 0, result, 0, lengths[0]);
		System.arraycopy(signalSource, 0, result, lengths[0], lengths[1]);

		return result;
	}


	int[] updateFileSize(){

		int
			headerLength = header.getSource().length,
			signalLength =  audioData.getSource(header.getField(BITS_PER_SAMPLE)).length,
			currentLength = headerLength + signalLength;

		header.setField(currentLength - 8, FILE_SIZE);

		return new int[] {headerLength, signalLength, currentLength};
	}

}