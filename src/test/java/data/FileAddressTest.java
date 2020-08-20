package data;

import org.junit.*;

import static resources.InputData.*;
import static data.FileAddress.establishFileExtension;

public class FileAddressTest {

	@Test
	public void establishFileExtensionTest_0(){

		Assert.assertEquals("wav", establishFileExtension(waveFile));
	}

	@Test
	public void establishFileExtensionTest_1(){

		Assert.assertEquals("aiff", establishFileExtension(aiffFile));
	}
}