package algorithms.analyzers;

import java.util.HashMap;

public abstract class FormatTag{

	static public Integer
		key0001 = 0x0001,
		key0003 = 0x0003,
		key0006 = 0x0006,
		key0007 = 0x0007,
		keyFFFE = 0xFFFE;

	public static HashMap<Integer, FormatTags>
		formatTags = new HashMap<>();
	
	static {
		
		formatTags.put(key0001, FormatTags.WAVE_FORMAT_PCM);
		formatTags.put(key0003, FormatTags.WAVE_FORMAT_IEEE_FLOAT);
		formatTags.put(key0006, FormatTags.WAVE_FORMAT_ALAW);
		formatTags.put(key0007, FormatTags.WAVE_FORMAT_MULAW);
		formatTags.put(keyFFFE, FormatTags.WAVE_FORMAT_EXTENSIBLE);
	}

	private enum FormatTags {

		WAVE_FORMAT_PCM,
		WAVE_FORMAT_IEEE_FLOAT,
		WAVE_FORMAT_ALAW,
		WAVE_FORMAT_MULAW,
		WAVE_FORMAT_EXTENSIBLE

	}

	public static void main(String[] args) {

		Integer temp = (Integer) 0xFFFE;

		System.out.println(formatTags.get(temp));

	}



}


