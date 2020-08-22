//	@formatter:off

package algorithms.metaProcessors.fileContentConverterClass;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

import static algorithms.metaProcessors.FileContentConverter.writeDataFrame;

	@RunWith(Parameterized.class)
public class DataFrameWriterParamTest {

	byte[]
		correctAnswer;

	int
		frame,
		frameLength;

//	--------------------------------------------------------------------------------------------------------------------

	public DataFrameWriterParamTest(int frame, int frameLength, byte[] correctAnswer){

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
					input = new Object[InputData.input1.length][3];

			for(int i = 0; i < Math.max(InputData.input1.length, InputData.input2.length); i++) {

				if(i < InputData.input2.length)
					input[i][0] = InputData.input2[i];

				if (i < InputData.input1.length){

					input[i][1] = InputData.input1[i].length;
					input[i][2] = InputData.input1[i];
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
				givenAnswer = writeDataFrame(frame, frameLength);

			Assert.assertTrue(equal(givenAnswer, correctAnswer));
		}

		catch (AssertionError error){

			System.out.println(
				"\n\tgiven: "
				+ Arrays.toString(writeDataFrame(frame, frameLength))
				+ ", correct: "
				+ Arrays.toString(correctAnswer));

			throw error;
		}
	}
}

//	@formatter:on