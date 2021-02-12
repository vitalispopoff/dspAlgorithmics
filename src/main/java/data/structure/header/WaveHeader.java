package data.structure.header;

import java.util.Arrays;
import static algorithms.metaProcessors.FileContentConverter.*;

public class WaveHeader {

	private byte[]
		source;

	
	private WaveHeader(){ }
	
	private WaveHeader(byte[] fileContent){

		this();

		if (fileContent != null) source = Arrays.copyOfRange(fileContent, 0, 44);
	}
	

	public static WaveHeader instanceOf(byte[] fileContent){

		if (fileContent != null && fileContent.length > 0)

			return new WaveHeader(fileContent);

		else return new WaveHeader();
	}


	public int getField(WaveFileContentStructure field){

		if (field.getLength() > 0)

			return readDataField(source, field);

		return 0;
	}

	public void setField(int value, WaveFileContentStructure field){

		if (field.getLength() > 0)

			writeDataField(source, value, field);
	}

	public byte[] getSource(){

		return source;
	}

	public String toString() {

		StringBuilder
			output = new StringBuilder("\n\t fields \n\t");

		for (WaveFileContentStructure e : WaveFileContentStructure.values())

			output
				.append("\n\t")
				.append(e)
				.append(" : ")
				.append(getField(e));

		return output.toString();
	}
}