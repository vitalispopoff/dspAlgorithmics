package data.structure;

public enum WaveFileStructure implements FileStructure{

	FILE_ID {

		@Override
		public int[] getLocation(){

			return new int[]{Integer.MIN_VALUE, 0x52494646};
		}
	},		//	1
	FILE_SIZE,			//	2
	WAVE_ID,			//	3

	FMT_ID {

		@Override
		public int[] getLocation(){

			return new int[]{Integer.MIN_VALUE, 0x666D7420};
		}

	},		//	4
	FMT_SIZE,			//	5
	FORMAT_TAG,			//	6
	CHANNELS,			//	7
	SAMPLE_PER_SEC,		//	8
	AV_BYTE_PER_SEC,	//	9
	BLOCK_ALIGN,		//	10
	BITS_PER_SAMPLE,	//	11

	DATA_ID {

		@Override
		public int[] getLocation(){

			return new int[]{Integer.MIN_VALUE, 0x64617461};
		}

	},		//	12
	DATA_SIZE,			//	13
	SIGNAL;				//	14

//	--------------------------------------------------------------------------------------------------------------------

	@Override
	public int[] getLocation(){

		return slotLocations[this.ordinal()];
	}

	@Override
	public int getStart(){

		return slotLocations[this.ordinal()][0];
	}

	@Override
	public int getLength(){

		return slotLocations[this.ordinal()][1];
	}

//	--------------------------------------------------------------------------------------------------------------------

	int[][]
	slotLocations = {

		{0, 4},		//  1	fileId
		{4, 4},		//	2	fileSize
		{8, 4},		//	3	waveId

		{12, 4},	//	4	fmt Id
		{16, 4},	//	5	fmt size
		{20, 2},	//	6	formatTag
		{22, 2},	//	7	channels
		{24, 4},	//	8	samplePerSec
		{28, 4},	//	9	avBytePerSec
		{32, 2},	//	10	blockAlign
		{34, 2},	//	11	bitsPerSample

		{36, 4},	//	12	dataId
		{40, 4},	//	13	dataSize
		{44, Integer.MIN_VALUE}		//	signal
	};
}