//	@formatter:off

package algorithms.metaProcessors.fileContentConverterClass;

import java.util.*;

import _inputs.FileContentConverter_InputData;import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static algorithms.metaProcessors.FileContentConverter.*;

	@RunWith(Parameterized.class)
public class ReadDataSampleParamTest {

	byte[]
		frame;

	int
		correctAnswer;

//	--------------------------------------------------------------------------------------------------------------------

	public ReadDataSampleParamTest(byte[] frame, int correctAnswer){

		this.frame = frame;
		this.correctAnswer = correctAnswer;
	}

//	--------------------------------------------------------------------------------------------------------------------

		@Parameterized.Parameters
	public static Collection<?> setInput(){

			Object[][]
					input = new Object[FileContentConverter_InputData.input1.length][2];

			for(int i = 0 ; i < Math.min(FileContentConverter_InputData.input1.length, FileContentConverter_InputData.input2.length); i++) {

//				if (i < _InputData.input1.length)
					input[i][0] = FileContentConverter_InputData.input1[i];

//				if(i < _InputData.input2.length)
					input[i][1] = FileContentConverter_InputData.input2[i];
			}

		return Arrays.asList(input);
	}

//	--------------------------------------------------------------------------------------------------------------------

//	@Test
	public void readDataSampleTest_1(){

//		Assert.assertEquals(readDataSample(frame), correctAnswer);
	}

	@Test
	public void readDataSampleTest_2(){

		int
			givenAnswer = 0;

		try {

			givenAnswer = readDataSample(frame, 0, frame.length);

			Assert.assertEquals(givenAnswer, correctAnswer);
		}

		catch (AssertionError error){

			System.out.println(
				"\n\tgiven : frame = "
				+ Arrays.toString(frame)
				+ ", given = "
				+ givenAnswer
				+ ", correct = "
				+ correctAnswer
			);

			throw error;
		}
	}	// TODO
}

//	@formatter:on