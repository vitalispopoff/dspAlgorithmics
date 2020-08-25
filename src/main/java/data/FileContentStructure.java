//	@formatter:off

package data;

public enum FileContentStructure {

	FILE_SIZE,
	WAVE_ID,
	FMT_SIZE,
	FORMAT_TAG,
	CHANNELS,
	SAMPLING_RATE,
	DATA_RATE,
	BLOCK_SIZE,
	BIT_DEPTH,
	DATA_SIZE,
	SIGNAL;



	public int getStart(){

		return slotLocations[this.ordinal()][0];
	}

	public int getLength(){

		return slotLocations[this.ordinal()][1];
	}

	int[][]
	slotLocations = {

		{4, 4},		//	fileSize
		{8, 4},		//	waveId
		{16, 4},	//	fmt_size
		{20, 2},	//	formatTag
		{22, 2},	//	channels
		{24, 4},	//	sampleRate
		{28, 4},	//	dataRate
		{32, 2},	//	blockSize
		{34, 2},	//	bitDepth
		{40, 4},	//	dataSize
		{44, 0}		//	signal
	};
}

//	@formatter:on