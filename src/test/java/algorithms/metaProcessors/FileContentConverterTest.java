//	@formatter:off

package algorithms.metaProcessors;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

import static algorithms.metaProcessors.FileContentConverter.*;

	@RunWith(Parameterized.class)
public class FileContentConverterTest {

/*	static byte[]
		input16_min = {0, MIN_VALUE},
		input16_neg = {-1, -1},
		input16_mid = {0, 0},
		input16_pos = {1, 0},
		input16_max = {MAX_VALUE, MAX_VALUE},

		input24_min = {0, 0, MIN_VALUE},
		input24_neg = {-1, -1, -1},
		input24_mid = {0, 0, 0},
		input24_pos = {1, 0, 0},
		input24_max = {MAX_VALUE, MAX_VALUE, MAX_VALUE},

		input32_min = {0, 0, 0, MIN_VALUE},
		input32_neg = {-1, -1, -1, -1},
		input32_mid = {0, 0, 0, 0},
		input32_pos = {1, 0, 0, 0},
		input32_max = {MAX_VALUE, MAX_VALUE, MAX_VALUE, MAX_VALUE};*/		//	* disposable

	byte[]
		frame;

	int
		result;

		@Parameterized.Parameters
	public static Collection<?> setInput(){

		byte
			min = (byte) 0x80,
			max = (byte) 0x7F,
			full = (byte) 0xFF;


		Object[][]
			input = {

				{new byte[] {0, min}, (int) Short.MIN_VALUE},
				{new byte[] {-1, -1}, -1},
				{new byte[] {0, 0}, 0},
				{new byte[] {1, 0}, 1},
				{new byte[] {full, max}, (int) Short.MAX_VALUE},

				{new byte[] {0, 0, min}, (1 << 23) * -1},
				{new byte[] {-1, -1, -1}, -1},
				{new byte[] {0, 0, 0}, 0},
				{new byte[] {1, 0, 0}, 1},
				{new byte[] {full, full, max}, (1 << 23) - 1},

				{new byte[] {0, 0, 0, min}, Integer.MIN_VALUE},
				{new byte[] {-1, -1, -1, -1}, -1},
				{new byte[] {0, 0, 0, 0}, 0},
				{new byte[] {1, 0, 0, 0}, 1},
				{new byte[] {full, full, full, max}, Integer.MAX_VALUE},
		};

		return Arrays.asList(input);
	}

	public FileContentConverterTest(byte[] frame, int result){

		this.frame = frame;
		this.result = result;
	}

	@Test
	public void dataFrameReaderTest(){

		Assert.assertEquals(dataFrameReader(frame), result);
	}
}

//	@formatter:on