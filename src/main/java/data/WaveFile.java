package data;

import data.structure.*;
import data.structure.signal.Signal;

import java.io.File;
import java.util.Arrays;

import static data.FileCache.addToCache;
import static data.structure.FileContentStructure.*;
import static data.structure.WaveHeader.instanceOf;

import static algorithms.metaProcessors.FileManager.*;

public class WaveFile {

	public FileAddress
		fileAddress;

	public WaveHeader
		header;

	Signal
		signal;



	private WaveFile( ){ }

	public WaveFile(File file){

		byte[]
			fileContent = loadFile(file);

		header = instanceOf(fileContent);

		int
			start = 44,
			end = 44 + header.getField(DATA_SIZE);
		byte[]
			signalSource = Arrays.copyOfRange(fileContent, start, end);

		signal = new Signal(signalSource, header.getField(BLOCK_ALIGN), header.getField(CHANNELS));

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

	public Signal getSignal(){

		return signal;
	}

	public byte[] getSource(){

		int[]
			lengths = updateFileSize();

		byte[]
			headerSource = header.getSource(),
			signalSource = signal.getSource(header.getField(BITS_PER_SAMPLE)),
			result = new byte[lengths[2]];

		System.arraycopy(headerSource, 0, result, 0, lengths[0]);
		System.arraycopy(signalSource, 0, result, lengths[0], lengths[1]);

		return result;
	}

	int[] updateFileSize(){

		int
			headerLength = header.getSource().length,
			signalLength = signal.getSource(header.getField(BITS_PER_SAMPLE)).length,
			currentLength = headerLength + signalLength;

		header.setField(currentLength - 8, FILE_SIZE);

		return new int[] {headerLength, signalLength, currentLength};
	}
}