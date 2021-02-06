package data.structure.signal;

import org.junit.*;

public class AudioDataUnitTest {


/*	@Test
	public void consolidateChannelsTest_0() {

		Signal
			input_0 = new Signal(3);

		Integer[]
			strip_1 = {0, 3, 6},
			strip_2 = {1, 4, 7},
			strip_3 = {2, 5, 8};

		for (Integer i : strip_1) input_0.channels.get(0).addSample(Sampling.instanceOf(i));
		for (Integer i : strip_2) input_0.channels.get(1).addSample(Sampling.instanceOf(i));
		for (Integer i : strip_3) input_0.channels.get(2).addSample(Sampling.instanceOf(i));

		Integer[]
			correct = {0, 1, 2, 3, 4, 5, 6, 7, 8},
			returned = input_0.consolidateChannels();

		Assert.assertArrayEquals(correct, returned);
	}*/		// ? priv consolidateChannels() test. not needed anymore ?

//	--------------------------------------------------------------------------------------------------------------------

	@Test
	public void getSourceTest_0() {

		AudioData
			input_0 = new AudioData(3);

		Integer[]
			strip_1 = {0, 3, 6},
			strip_2 = {1, 4, 7},
			strip_3 = {2, 5, 8};

		for (Integer i : strip_1) input_0._channelList.get(0).addSampling(Sampling.newInstance(i));
		for (Integer i : strip_2) input_0._channelList.get(1).addSampling(Sampling.newInstance(i));
		for (Integer i : strip_3) input_0._channelList.get(2).addSampling(Sampling.newInstance(i));

		int
			input_1 = 24;

		byte[]
			correct = {
				(byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0x01, (byte) 0x00, (byte) 0x00,
				(byte) 0x02, (byte) 0x00, (byte) 0x00,

				(byte) 0x03, (byte) 0x00, (byte) 0x00,
				(byte) 0x04, (byte) 0x00, (byte) 0x00,
				(byte) 0x05, (byte) 0x00, (byte) 0x00,

				(byte) 0x06, (byte) 0x00, (byte) 0x00,
				(byte) 0x07, (byte) 0x00, (byte) 0x00,
				(byte) 0x08, (byte) 0x00, (byte) 0x00,
			},
			returned = input_0.getSource(input_1);

		Assert.assertArrayEquals(correct, returned);
	}
}