package data.structure.signal;

import java.util.ArrayList;

public interface Previewing {

	//	!--- TODO to be removed	--------------------------------

	boolean
		_switcher1 = false;	// * run new implementation ?

	temporal t = new temporal();
	class temporal{ static{ System.out.println("Previewing > new implementation = " + Previewing._switcher1); }}

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