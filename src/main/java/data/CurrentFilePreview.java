package data;

import data.structure.Previewing;
import data.structure.signal.*;
//import data.structure.signal.Strip;

import java.util.ArrayList;
import java.util.List;

public abstract class CurrentFilePreview implements Previewing {

	static ArrayList<SignalTree>
		currentChan = new ArrayList<>();

	public static List<SignalTree> getCurrentChan(){

		return currentChan;
	}
}