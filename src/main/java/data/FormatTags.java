//	@formatter:off

package data;

public enum FormatTags{

	WAVE_FORMAT_PCM,
	WAVE_FORMAT_IEEE_FLOAT,
	WAVE_FORMAT_ALAW,
	WAVE_FORMAT_MULAW,
	WAVE_FORMAT_EXTENSIBLE;

	public static byte[][]
		bytes = {
			{1, 0},
			{3, 0},
			{6, 0},
			{7, 0},
			{(byte) 0xFF, (byte)0xFE}
	};

	public static String[]
		byteStrings = {

			"[1, 0]",
			"[3, 0]",
			"[6, 0]",
			"[7, 0]",
			"[-1, -2]"
	};

	public static int[]
		starts = {44, 58, 58, 58, 72};	// TODO to be dependent on the actual header stream readings

	public byte[] getBytes(){

		return bytes[this.ordinal()];
	}
}

//	@formatter:on