package data.structure.signal;

import org.junit.*;

import static data.structure.signal.AudioData.*;

public class AudioDataUnitTest {

	@Ignore
	@Test
	public void newInstanceTest_0() {

		int
			input = 1,
			result = newInstance(input).getValue();

		Assert.assertEquals(input, result);
	}	// ! redundant to the implementation class

//	@Ignore
	@Test
	public void setFromSourceTest_0() {

		byte[]
			input = {(byte) 0x00};

		int
			blockAlign = 1;

		Assert.assertTrue(setFromSource(input, blockAlign) instanceof AudioData);
	}

//	@Ignore
	@Test
	public void setFromSourceTest_1() {

		byte[]
			input = {(byte) 0x00};

		int
			blockAlign = 1;

		AudioData
			result = setFromSource(input, blockAlign).getNext();

		Assert.assertEquals(result.getNext(), result);
	}

//	@Ignore
	@Test
	public void setFromSourceTest_2() {

		byte[]
			input = {(byte) 0x01};

		int
			blockAlign = 1;

		AudioData
			result = setFromSource(input, blockAlign);

		Assert.assertEquals((int) input[0], result.getValue());

	}

//	@Ignore
	@Test
	public void setFromSourceTest_3() {

		byte[]
			input = {(byte) 0x10, (byte) 0x00, (byte) 0x00, (byte) 0x80};

		int
			blockAlign = 2;

		AudioData
			r = setFromSource(input, blockAlign);

		int
			result_1 = r.getValue(),
			result_2 = r.getNext().getValue(),
			expected_1 = 16,
			expected_2 = -32768;

		Assert.assertEquals(expected_1, result_1);
		Assert.assertEquals(expected_2, result_2);
	}
}
