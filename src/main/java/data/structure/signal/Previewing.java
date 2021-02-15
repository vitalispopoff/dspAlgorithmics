package data.structure.signal;

import java.util.ArrayList;

public interface Previewing {

	//	!--- TODO to be removed	--------------------------------

	boolean
		_switcher1 = false;	// * run new implementation ?

	Temporal t = new Temporal();
	class Temporal { static{ System.out.println("Previewing > new ver = " + Previewing._switcher1); }}

	//	!--- TODO ----------------------------------------------



	static ArrayList<SamplePyramid> getCurrentSamples(){

		return _switcher1
			? CurrentFilePreview.getCurrentSamplePyramid()
			: CurrentFilePreview_old.getCurrentSamplePyramid();
	}

	static SamplePyramid getCurrentSamples(int index){

		return _switcher1
			? CurrentFilePreview.getCurrentSamplePyramid().get(index)
			: CurrentFilePreview_old.getCurrentSamplePyramid().get(index);
	}

	static void loadCurrentChan(){

		CurrentFilePreview.addLevelToCurrentSamplePyramid();
		CurrentFilePreview_old.addLevelToCurrentSamplePyramid();
	}
}