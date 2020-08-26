package algorithms.metaProcessors;

import org.junit.*;

import static algorithms.metaProcessors.FileManager.*;
import static _inputs.Data_InputData.*;

public class FileManagerUnitTest {

    @Test
    public void fileManagerTest(){

        Assert.assertArrayEquals(
            loadFile("src\\main\\resources\\sample-mono.wav"),
            sample_mono_wav
        );
    }
}
