//	@formatter:off

package algorithms.metaProcessors.fileContentConverterClass;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

import static algorithms.metaProcessors.FileContentConverter.writeDataSample;
import static algorithms.metaProcessors.fileContentConverterClass._InputData.*;

	@RunWith(Parameterized.class)
public class WriteDataSampleParamTest {

	byte[]
		correctAnswer;

	int
		frame,
		frameLength;

//	--------------------------------------------------------------------------------------------------------------------

	public WriteDataSampleParamTest(int frame, int frameLength, byte[] correctAnswer){

		this.frame = frame;
		this.frameLength = frameLength;
		this.correctAnswer = correctAnswer;
	}

//	--------------------------------------------------------------------------------------------------------------------

		@Parameterized.Parameters
	public static Collection<?> convertInput(){

		return Arrays.asList(setInput());
	}

	public static Object[][] setInput(){

			Object[][]
				input = new Object[_InputData.input1.length][3];

			int
				maxIndex = Math.max(_InputData.input1.length, input2.length);

			for (int i = 0; i < maxIndex; i++) {

				if(i < input2.length)
					input[i][0] = input2[i];

				if (i < _InputData.input1.length){

					input[i][1] = _InputData.input1[i].length;
					input[i][2] = _InputData.input1[i];
				}
			}

		return input;
	}

	private static boolean equal(byte[] given, byte[] correct) {

		int
			maxIndex = Math.max(given.length, correct.length);

		for (int i = 0; i < maxIndex; i++)

			if(given[i] == correct[i] && i < given.length && i < correct.length)

				return true;

		return false;
	}

//	--------------------------------------------------------------------------------------------------------------------

		@Test
	public void dataFrameReaderTest(){

		try {

			byte[]
				givenAnswer = writeDataSample(frame, frameLength);

			Assert.assertTrue(equal(givenAnswer, correctAnswer));
		}

		catch (AssertionError error){

			System.out.println(
				"\n\tgiven: "
				+ Arrays.toString(writeDataSample(frame, frameLength))
				+ ", correct: "
				+ Arrays.toString(correctAnswer));

			throw error;
		}
	}
}

//	@formatter:on