package data.structure;

import java.util.Arrays;
import static algorithms.metaProcessors.FileContentConverter.*;

public class WaveHeader {

	private byte[]
		source;

//	--------------------------------------------------------------------------------------------------------------------
	
	private WaveHeader(){ }
	
	private WaveHeader(byte[] fileContent){

		this();

		if (fileContent != null){

			source = Arrays.copyOfRange(fileContent, 0, 44);
		}
	}
	
//	--------------------------------------------------------------------------------------------------------------------

	public static WaveHeader instanceOf(byte[] fileContent){

		if (fileContent != null && fileContent.length > 0)

			return new WaveHeader(fileContent);

		else return new WaveHeader();
	}



	public int getField(FileContentStructure field){

		return readDataField(source, field);
	}

	public void setField(int value, FileContentStructure field){

		writeDataField(source, value, field);
	}

	public byte[] getSource(){

		return source;
	}
}