//	@formatter:off

package algorithms.analyzers;

import java.util.HashMap;

public abstract class FormatTag{

	private static final HashMap<Integer, FormatTags>
		formatTags = new HashMap<>( );

	static {
		
		formatTags.put(0x0001, FormatTags.WAVE_FORMAT_PCM);
		formatTags.put(0x0003, FormatTags.WAVE_FORMAT_IEEE_FLOAT);
		formatTags.put(0x0006, FormatTags.WAVE_FORMAT_ALAW);
		formatTags.put(0x0007, FormatTags.WAVE_FORMAT_MULAW);
		formatTags.put(0xFEFF, FormatTags.WAVE_FORMAT_EXTENSIBLE);
	}	// * formatTags population

//	--------------------------------------------------------------------------------------------------------------------

	public static FormatTags getFormatTag(int data){

		return formatTags.get(data);
	}

//	--------------------------------------------------------------------------------------------------------------------

	public enum FormatTags {

		WAVE_FORMAT_PCM,
		WAVE_FORMAT_IEEE_FLOAT,
		WAVE_FORMAT_ALAW,
		WAVE_FORMAT_MULAW,
		WAVE_FORMAT_EXTENSIBLE;

		public static int[]
			starts = {44, 58, 58, 58, 72};	// TODO to be dependent on the actual header stream readings
	}
}

//	@formatter:on