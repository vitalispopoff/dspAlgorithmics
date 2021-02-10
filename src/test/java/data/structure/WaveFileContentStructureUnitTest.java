package data.structure;

import org.junit.*;

import static data.structure.header.WaveFileContentStructure.*;

public class WaveFileContentStructureUnitTest {

	@Test
	public void getLocationTest_0(){

		int[]
			correct = {40, 4},
			returned = DATA_SIZE.getLocation();

		Assert.assertArrayEquals(correct, returned);
	}
}
