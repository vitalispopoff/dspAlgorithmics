package data.structure.header;

import java.util.Arrays;

public interface FileHeader {


	static FileHeader instanceOf(byte[] fileContent) {

		String
			s = new String(Arrays.copyOfRange(fileContent,8, 12));

		WAVE_IDs
			type = WAVE_IDs.valueOf(s);

		return type.getInstance(fileContent);
	}



	int getField(WaveFileContentStructure field);

	void setField(WaveFileContentStructure field, int value);

	byte[] getSource();
}



