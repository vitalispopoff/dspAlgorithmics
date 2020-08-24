//	@formatter:off

package algorithms.metaProcessors.fileContentConverterClass;

import java.util.*;

import _inputs.FileContentConverter_InputData;import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static algorithms.metaProcessors.FileContentConverter.writeDataSample;

	@RunWith(Parameterized.class)
public class WriteDataSampleParamTest {

	static byte[][]
		input1 = FileContentConverter_InputData.input1;

	static int[]
		input2 = FileContentConverter_InputData.input2;

	byte[]
		correctAnswer;

	int
		sample,
		sampleLength;

//	--------------------------------------------------------------------------------------------------------------------

	public WriteDataSampleParamTest(int sample, int sampleLength, byte[] correctAnswer){

		this.sample = sample;
		this.sampleLength = sampleLength;
		this.correctAnswer = correctAnswer;
	}

//	--------------------------------------------------------------------------------------------------------------------

		@Parameterized.Parameters
	public static Collection<?> convertInput(){

		return Arrays.asList(setInput());
	}

	public static Object[][] setInput(){

			Object[][]
				input = new Object[input1.length][3];

			int
				maxIndex = Math.max(input1.length, input2.length);

			for (int i = 0; i < maxIndex; i++) {

				if (i < input2.length)

					input[i][0] = input2[i];

				if (i < input1.length){

					input[i][1] = input1[i].length;
					input[i][2] = input1[i];
				}
			}

		return input;
	}

//	--------------------------------------------------------------------------------------------------------------------

	private static boolean equal(byte[] given, byte[] correct) {

		int
			maxIndex = Math.max(given.length, correct.length);

		for (int i = 0; i < maxIndex; i++)

			if(given[i] == correct[i] && i < given.length && i < correct.length)

				return true;

		return false;
	}

		@Test
	public void writeDataSampleTest(){

		try {

			byte[]
				givenAnswer = writeDataSample(sample, sampleLength);

			Assert.assertTrue(equal(givenAnswer, correctAnswer));
		}

		catch (AssertionError error){

			System.out.println(
				"\n\tgiven: "
				+ Arrays.toString(writeDataSample(sample, sampleLength))
				+ ", correct: "
				+ Arrays.toString(correctAnswer));

			throw error;
		}
	}
}

//	@formatter:on