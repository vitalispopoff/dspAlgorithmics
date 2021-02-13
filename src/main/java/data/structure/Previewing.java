package data.structure;

import data.structure.signal.SamplePyramid;

import java.util.ArrayList;

public interface Previewing {

	//	!--- TODO to be removed	--------------------------------

	boolean
		_switcher1 = true;	// * run new implementation ?

	temporal t = new temporal();
	class temporal{ static{ System.out.println("Previewing > new implementation = " + Previewing._switcher1); }}

	//	!--- TODO ----------------------------------------------


/*	static List<SamplePyramid> currentChannel(){

		return CurrentFilePreview.getCurrentChannel();
	}*/	// ? disposable upon fixing cleanCurrentFileSignal

/*	static void cleanCurrentFileSignal(){

		CurrentFilePreview.getCurrentChannel().clear();
	}*/	// ! cleanCurrentFileSignal: TODO fix

	static ArrayList<SamplePyramid> getCurrentSamplePyramid(){

		return _switcher1
			? CurrentFilePreview_0.getCurrentSamplePyramid()
			: CurrentFilePreview.getCurrentSamplePyramid();
	}

	static SamplePyramid getCurrentSamplePyramid(int index){

		return _switcher1
			? CurrentFilePreview_0.getCurrentChannel().get(index)
			: CurrentFilePreview.getCurrentChannel().get(index);
	}

	static void loadCurrentChan(){

		CurrentFilePreview.addLevelToCurrentSamplePyramid();
		CurrentFilePreview_0.addLevelToCurrentSamplePyramid();
	}
}