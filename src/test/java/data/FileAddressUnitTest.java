//	@formatter:off

package data;

import org.junit.*;

import static _inputs.Data_InputData.*;
import static data.FileAddress.*;

public class FileAddressUnitTest {

	@Test
	public void establishFileExtensionTest_0(){

		Assert.assertEquals("wav", establishFileExtension(sample_mono_wav));
	}

	@Test
	public void establishFileExtensionTest_1(){

		Assert.assertEquals("aiff", establishFileExtension(sample_mono_aiff));
	}
}

//	@formatter:on