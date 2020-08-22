package algorithms.metaProcessors.fileContentConverterClass;


import org.junit.*;

import static algorithms.metaProcessors.FileContentConverter.*;

import static data.InputData.sample_mono_wav;

public class FileContentConverterUnitTest {

    @Test
    public void readFormatOrdinalTest(){

        int
            result = readFormatOrdinal(sample_mono_wav),
            correct = 0;

        Assert.assertEquals(correct, 0);
    }
}
