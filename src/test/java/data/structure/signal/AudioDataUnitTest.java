package data.structure.signal;

import org.junit.*;

import static data.structure.signal.AudioData.*;

public class AudioDataUnitTest {

	@Test
	public void newInstanceTest_0() {

		int
			input = 1,
			result = newInstance(input).getValue();

		Assert.assertEquals(input, result);
	}


	@Test
	public void setFromSourceTest_0() {

		byte[]
			input = {(byte) 0x00};

		int
			sampleRate = 1;

		Assert.assertTrue(setFromSource(input, sampleRate) instanceof AudioData);
	}

	@Test
	public void setFromSourceTest_1() {

		byte[]
			input = {(byte) 0x00};

		int
			sR = 1;

		AudioData
			result = setFromSource(input, sR).getNext();

		Assert.assertEquals(result.getNext(), result);
	}

	@Test
	public void setFromSourceTest_2() {

		byte[]
			input = {(byte) 0x01};

		int
			sR = 1;

		AudioData
			result = setFromSource(input, sR);

		Assert.assertEquals((int) input[0], result.getValue());

	}

	@Test
	public void setFromSourceTest_3() {

		byte[]
			input = {(byte) 0x10, (byte) 0x00, (byte) 0x00, (byte) 0x80};

		int
			sR = 2;

		AudioData
			r = setFromSource(input, sR);

		int
			result_1 = r.getValue(),
			result_2 = r.getNext().getValue(),
			proper_1 = 16,
			proper_2 = -32768;

		Assert.assertEquals(proper_1, result_1);
		Assert.assertEquals(proper_2, result_2);
	}

	@Test
	public void setFromSourceTest_4() {

		byte[]
			input = {(byte) 0x10, (byte) 0x00, (byte) 0x00, (byte) 0x80};
		int
			sR = 2,
			proper = 2,
			result = 1;

		AudioData
			anchor = setFromSource(input, sR),
			temp = anchor;

		for (; !(temp.getNext().equals(temp)); result++) temp = temp.getNext();

		Assert.assertEquals(proper, result);
	}
}
