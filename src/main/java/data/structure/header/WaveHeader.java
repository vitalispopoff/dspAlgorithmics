package data.structure.header;

import data.structure.WaveFileStructure;

import java.util.Arrays;
import static algorithms.metaProcessors.FileContentConverter.*;

public class WaveHeader implements FileHeader{


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



	@Override
	public int getField(WaveFileStructure field){

		return field.getLength() > 0
			? readDataField(source, field)
			: 0;
	}

	@Override
	public void setField(WaveFileStructure field, int value){

		if (field.getLength() > 0)

			writeDataField(source, value, field);
	}

	@Override
	public byte[] getSource(){

		return source;
	}

	@Override
	public String toString() {

		StringBuilder
			output = new StringBuilder("\n\t fields \n\t");

		for (WaveFileStructure e : WaveFileStructure.values())

			output
				.append("\n\t")
				.append(e)
				.append(" : ")
				.append(getField(e));

		return output.toString();
	}
}