package algorithms.analyzers;

import java.util.HashMap;

public abstract class FormatTag{

	public static HashMap<Integer, String>
		formatTags = new HashMap<>();

	static {
		
		formatTags.put(0x0001, "WAVE_FORMAT_PCM)");
		formatTags.put(0x0003, "WAVE_FORMAT_IEEE_FLOAT");
		formatTags.put(0x0006, "WAVE_FORMAT_ALAW");
		formatTags.put(0x0007, "WAVE_FORMAT_MULAW");
		formatTags.put(0xFFFE, "WAVE_FORMAT_EXTENSIBLE");
	}

//	--------------------------------------------------------------------------------------------------------------------

	public static String readFormatTag(int data){

		return formatTags.get(data);
	}

	public static void main(String[] args) {

		System.out.println(readFormatTag(0x0003));

	}



}


