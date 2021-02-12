package data.structure.header;

enum WAVE_IDs {

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
