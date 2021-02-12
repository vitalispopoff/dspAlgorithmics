package data.structure.header;

import java.util.Arrays;

public interface FileHeader {


	static FileHeader instanceOf(byte[] fileContent) {

		String
			s = new String(Arrays.copyOfRange(fileContent,8, 12));

		FileTypes
			type = FileTypes.valueOf(s);

		return type.getInstance(fileContent);
	}



	int getField(WaveFileContentStructure field);

	void setField(WaveFileContentStructure field, int value);

	byte[] getSource();
}



enum FileTypes {

	WAVE{
		@Override
		FileHeader getInstance(byte[] fileContent){

			return WaveHeader.instanceOf(fileContent);
		}
	},
	AIFF;

	FileHeader getInstance(byte[] fileContent){

		return FileHeader.instanceOf(fileContent);
	}
}