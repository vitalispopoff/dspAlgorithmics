package algorithms.metaProcessors;

import org.junit.*;

import static _inputs.Data_InputData.*;
import static algorithms.metaProcessors.FileContentConverter.*;


public class FileContentConverterUnitTest {


	@Test
	public void readDataSampleTest_0() {

		byte[]
			input = {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};

		int
			correct = 0,
			given = readDataSample(input, 0, 4);

		Assert.assertEquals(correct, given);
	}

	@Test
	public void readDataSampleTest_1() {

		byte[]
			input = {
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
			(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x80
		};

		int
			correct = Integer.MIN_VALUE,
			given = readDataSample(input, 4, 4);

		Assert.assertEquals(correct, given);
	}

	@Test
	public void readDataSampleTest_2() {

		byte[]
			input = sample_mono_wav;

		int
			correct = 0x58,
			given = readDataSample(input, 4, 4);

		Assert.assertEquals(correct, given);
	}

//  --------------------------------------------------------------------------------------------------------------------

	@Test
	public void writeDataSampleTest_0() {

		int
			input = 88;

		byte[]
			correct = {
			(byte) 0x58, (byte) 0x00, (byte) 0x00, (byte) 0x00
		},
			given = writeDataSample(input, 4);

		Assert.assertArrayEquals(given, correct);
	}

	@Test
	public void writeDataSampleTest_1() {

		int
			input = Short.MAX_VALUE;

		byte[]
			correct = {
			(byte) 0xFF, (byte) 0x7F
		},
			given = writeDataSample(input, 2);

		Assert.assertArrayEquals(correct, given);
	}

	@Test
	public void writeDataSampleTest_2() {

		int
			input = 0xFFFF7F00;

		byte[]
			correct = {
			(byte) 0x00, (byte) 0x7F, (byte) 0xFF
		},
			given = writeDataSample(input, 3);

		Assert.assertArrayEquals(correct, given);
	}

//	--------------------------------------------------------------------------------------------------------------------

	@Test
	public void bytesToIntegerTest_0(){

		byte[]
			input_1 = {(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};

		int
			input_2 = 4;

		Integer[]
			correct = {0},
			returned = bytesToIntegers(input_1, input_2);

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
			returned = bytesToIntegers(input_1, input_2);

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
			returned = integersToBytes(input_1, input_2);

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
			returned = integersToBytes(input_1, input_2);

		Assert.assertArrayEquals(correct, returned);
	}



}