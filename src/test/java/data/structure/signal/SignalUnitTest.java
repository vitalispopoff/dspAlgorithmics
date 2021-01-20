package data.structure.signal;

import data.structure.signal.Signal;
import data.structure.signal.Strip;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class SignalUnitTest {

	static Signal
		dummy = new Signal();

//	--------------------------------------------------------------------------------------------------------------------

	@Test
	public void bytesToIntegerTest_0(){

		byte[]
			input_1 = {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};

		int
			input_2 = 4;

		Integer[]
			correct = {0},
			returned = dummy.bytesToIntegers(input_1, input_2);

		Assert.assertArrayEquals(correct, returned);
	}

	@Test
	public  void bytesToIntegerTest_1(){

		byte[]
			input_1 = {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x80};

		int
			input_2 = 2;

		Integer[]
			correct = { 0, (int) Short.MIN_VALUE},
			returned = dummy.bytesToIntegers(input_1, input_2);

		Assert.assertArrayEquals(correct, returned);
	}

//	--------------------------------------------------------------------------------------------------------------------

	@Test
	public void integersToBytesTest_0(){

		Integer[]
			input_1 = { 1, -1, (int) Short.MIN_VALUE, Integer.MAX_VALUE >>> 8};

		int
			input_2 = 32;

		byte[]
			correct = {
				(byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x00,
				(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
				(byte) 0x00, (byte) 0x80, (byte) 0xFF, (byte) 0xFF,
				(byte) 0xFF, (byte) 0xFF, (byte) 0x7F, (byte) 0x00,
			},
			returned = dummy.integersToBytes(input_1, input_2);

		Assert.assertArrayEquals(correct, returned);
	}

	@Test
	public void integersToBytesTest_1(){

		Integer[]
			input_1 = { 1, -1, (int) Short.MIN_VALUE, Integer.MAX_VALUE >>> 8};

		int
			input_2 = 24;

		byte[]
			correct = {
			(byte) 0x01, (byte) 0x00, (byte) 0x00,
			(byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
			(byte) 0x00, (byte) 0x80, (byte) 0xFF,
			(byte) 0xFF, (byte) 0xFF, (byte) 0x7F,
		},
			returned = dummy.integersToBytes(input_1, input_2);

		Assert.assertArrayEquals(correct, returned);
	}

//	--------------------------------------------------------------------------------------------------------------------

	@Test
	public void consolidateChannelsTest_0() {

		Signal
			input_0 = new Signal(3);

		Integer[]
			strip_1 = {0, 3, 6},
			strip_2 = {1, 4, 7},
			strip_3 = {2, 5, 8};

		for (Integer i : strip_1) input_0.strips.get(0).addSample(new Sample(i));
		for (Integer i : strip_2) input_0.strips.get(1).addSample(new Sample(i));
		for (Integer i : strip_3) input_0.strips.get(2).addSample(new Sample(i));

		Integer[]
			correct = {0, 1, 2, 3, 4, 5, 6, 7, 8},
			returned = input_0.consolidateChannels();

		Assert.assertArrayEquals(correct, returned);
	}

//	--------------------------------------------------------------------------------------------------------------------

//	@Test
	public void importToStripsTest_0(){

		Integer[]
			input_1 = {0, 1, 2, 3, 4, 5, 6, 7, 8};

		int
			input_2 = 3;

		Signal
			correct = new Signal(3);

		Integer[]
			strip_1 = {0, 3, 6},
			strip_2 = {1, 4, 7},
			strip_3 = {2, 5, 8};

		for (Integer i : strip_1) correct.strips.get(0).addSample(new Sample(i));
		for (Integer i : strip_2) correct.strips.get(1).addSample(new Sample(i));
		for (Integer i : strip_3) correct.strips.get(2).addSample(new Sample(i));

		Signal
			returned = new Signal(3);

		returned.importToStrips(input_1, input_2);

//		for (Stripabple s : returned.strips) System.out.println("\t" + Arrays.toString(s.toArray()));

		for (int i = 0; i < 3; i++)

			for (int j = 0; j < 3; j++)

				Assert.assertEquals(correct.strips.get(i).get(j), returned.strips.get(i).get(j));
	}

//	--------------------------------------------------------------------------------------------------------------------

	@Test
	public void getSourceTest_0() {

		Signal
			input_0 = new Signal(3);

		Integer[]
			strip_1 = {0, 3, 6},
			strip_2 = {1, 4, 7},
			strip_3 = {2, 5, 8};

		for (Integer i : strip_1) input_0.strips.get(0).addSample(new Sample(i));
		for (Integer i : strip_2) input_0.strips.get(1).addSample(new Sample(i));
		for (Integer i : strip_3) input_0.strips.get(2).addSample(new Sample(i));

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