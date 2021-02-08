package data;

import data.structure.Previewing;
import data.structure.signal.*;
//import data.structure.signal.Strip;

import java.util.ArrayList;
import java.util.List;

public abstract class CurrentFilePreview implements Previewing {

	static ArrayList<DataPreviewStructure>
		currentChan = new ArrayList<>();

	public static List<DataPreviewStructure> getCurrentChan(){

		return currentChan;
	}
}