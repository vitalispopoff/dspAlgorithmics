//	@formatter:off

package _inputs;

public class FileContentConverter_InputData {

	private static final byte
		min = (byte) 0x80,
		max = (byte) 0x7F,
		full = (byte) 0xFF;

	public static byte[][]
		input1 = {

			{min},
			{-1},
			{0},
			{1},
			{max},

			{0, min},
			{-1, -1},
			{0, 0},
			{1, 0},
			{full, max},

			{0, 0, min},
			{-1, -1, -1},
			{0, 0, 0},
			{1, 0, 0},
			{full, full, max},

			{0, 0, 0, min},
			{-1, -1, -1, -1},
			{0, 0, 0, 0},
			{1, 0, 0, 0},
			{full, full, full, max},
		};

	public static int[]
		input2 = {

			min,
			-1,
			0,
			1,
			max,

			(int) Short.MIN_VALUE,
			-1,
			0,
			1,
			(int) Short.MAX_VALUE,

			(1 << 23) * -1,
			-1,
			0,
			1,
			(1 << 23) - 1,

			Integer.MIN_VALUE,
			-1,
			0,
			1,
			Integer.MAX_VALUE
	};
}

//	@formatter:on