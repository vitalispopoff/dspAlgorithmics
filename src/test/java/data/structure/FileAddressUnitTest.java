//	@formatter:off

package data.structure;

import org.junit.*;

import static _inputs.Data_InputData.*;
import static data.structure.FileAddress.*;

public class FileAddressUnitTest {

	@Ignore
	@Test
	public void establishFileExtensionTest_0(){

		Assert.assertEquals("wav", establishExtension(sample_mono_wav));
	}

	@Ignore
	@Test
	public void establishFileExtensionTest_1(){

		Assert.assertEquals("aiff", establishExtension(sample_mono_aiff));
	}
}

//	@formatter:on