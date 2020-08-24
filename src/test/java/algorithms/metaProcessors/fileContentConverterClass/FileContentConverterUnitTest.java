//  @formatter:off

package algorithms.metaProcessors.fileContentConverterClass;

import org.junit.*;

import static algorithms.metaProcessors.FileContentConverter.*;

import static _inputs.Data_InputData.*;

public class FileContentConverterUnitTest {

    @Test
    public void readFormatOrdinalTest(){

        int
            result = readFormatOrdinal(sample_mono_wav),
            correct = 0;

        Assert.assertEquals(correct, 0);
    }
}

//  @formatter:on