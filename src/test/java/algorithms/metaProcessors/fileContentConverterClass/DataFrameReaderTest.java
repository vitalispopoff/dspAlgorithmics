//	@formatter:off

package algorithms.metaProcessors.fileContentConverterClass;

import java.util.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static algorithms.metaProcessors.FileContentConverter.*;

	@RunWith(Parameterized.class)
public class DataFrameReaderTest {

	byte[]
		frame;

	int
		correctAnswer;

//	--------------------------------------------------------------------------------------------------------------------

	public DataFrameReaderTest(byte[] frame, int correctAnswer){

		this.frame = frame;
		this.correctAnswer = correctAnswer;
	}

//	--------------------------------------------------------------------------------------------------------------------

		@Parameterized.Parameters
	public static Collection<?> setInput(){

			Object[][]
					input = new Object[InputData.input1.length][2];

			for(int i = 0 ; i < Math.max(InputData.input1.length, InputData.input2.length); i++) {

				if (i < InputData.input1.length)
					input[i][0] = InputData.input1[i];

				if(i < InputData.input2.length)
					input[i][1] = InputData.input2[i];
			}

		return Arrays.asList(input);
	}

//	--------------------------------------------------------------------------------------------------------------------

	@Test
	public void dataFrameReaderTest(){

		Assert.assertEquals(readDataFrame(frame), correctAnswer);
	}
}

//	@formatter:on