//	@formatter:off

package data;

public enum FileContentStructure {

	FILE_ID,
	FILE_SIZE,
	WAVE_ID,

	FMT_ID,
	FMT_SIZE,
	FORMAT_TAG,
	CHANNELS,
	SAMPLE_PER_SEC,
	AV_BYTE_PER_SEC,
	BLOCK_ALIGN,
	BITS_PER_SAMPLE,

	DATA_ID,
	DATA_SIZE,
	SIGNAL;

//	--------------------------------------------------------------------------------------------------------------------

	public int getStart(){

		return slotLocations[this.ordinal()][0];
	}

	public int getLength(){

		return slotLocations[this.ordinal()][1];
	}

//	--------------------------------------------------------------------------------------------------------------------

	int[][]
	slotLocations = {

		{0, 4},		//	fileId
		{4, 4},		//	fileSize
		{8, 4},		//	waveId

		{12, 4},	//	fmt Id
		{16, 4},	//	fmt size
		{20, 2},	//	formatTag
		{22, 2},	//	channels
		{24, 4},	//	samplePerSec
		{28, 4},	//	avBytePerSec
		{32, 2},	//	blockAlign
		{34, 2},	//	bitsPerSample

		{36, 4},	//	dataId
		{40, 4},	//	dataSize
		{44, 0}		//	signal
	};
}

//	@formatter:on