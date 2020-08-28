package data;

import org.junit.*;

import static _inputs.Data_InputData.sample_mono_wav;

public class WaveUnitTest {

//	@Ignore
	@Test
	public void getSourceTest_0(){

		Wave
			input_0 = new Wave("src\\main\\resources\\sample-mono.wav");

		byte[]
			correct = sample_mono_wav,
			returned = input_0.getSource();

		Assert.assertArrayEquals(correct, returned);
	}
}