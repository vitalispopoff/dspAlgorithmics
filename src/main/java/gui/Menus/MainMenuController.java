package gui.Menus;

import algorithms.metaProcessors.FileManager;
import data.*;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.input.KeyCombination;
import javafx.stage.*;

import java.io.File;

import static algorithms.metaProcessors.FileManager.saveFile;

public class MainMenuController {

	private static final BooleanProperty
		cacheIsEmptyDue = new SimpleBooleanProperty(data.FileCache.fileCache.size() == 0);

	static Stage
		stage;

//	-------------------------------------------------------------------------------------------

	public static void setStage(Stage s){
		stage = s;
	}

	public void openFile(ActionEvent event){

		if(FileCache.fileCache.size() > 0) FileCache.purgeCache();

		FileChooser
			browser = new FileChooser();

		String
			currentDefPath = FileManager.FileManagerSettings.getCurrentDefaultPath();

		browser.setTitle("Open File");
		browser.setInitialDirectory(new File(currentDefPath));

		File file = browser.showOpenDialog(stage);

		if (file != null) {

			WaveFile
				waveFile = new WaveFile(file);

			String
				path = waveFile.getFileAddress().getPath();

			FileManager.FileManagerSettings.setCurrentDefaultPath(path);

			setCacheIsEmptyDue();

/*disposable*/	System.out.println(
					"MainMenuController>openFile "
					+ FileCache.loadCurrent().getHeader().toString()
			);

		}
	}

	public void close(ActionEvent event){

		if (FileManager.FileManagerSettings.getAutoSave()) {

			WaveFile
				waveFile = FileCache.loadCurrent();

			FileManager.autoSaveFile();
		}

		FileCache.purgeCache();

		setCacheIsEmptyDue();
	}

	public void save(ActionEvent event){

		FileChooser
			browser = new FileChooser();

		browser.setTitle("Save As");
		browser.setInitialDirectory(new File(FileManager.FileManagerSettings.getCurrentDefaultPath()));

		File
			file = browser.showSaveDialog(stage);

		if (file != null) saveFile(file);
	}

	public void quit(ActionEvent event){

		close(event); // ? is it ok ?

		stage.close();
	}

	public void enableAutoSave(ActionEvent event){

		boolean
		checkBoxIsSelected = ((CheckMenuItem)event.getSource()).isSelected();

		FileManager.FileManagerSettings.setAutoSave(checkBoxIsSelected);

		System.out.println("autosave enable : " + FileManager.FileManagerSettings.getAutoSave());
	}

	private void setActionsToMenuFile(){

//		List<MenuItem> fileItems = menuFile.getItems();

/*
		fileItems.get(0).setOnAction((ActionEvent e) -> {

			FileChooser
				browser = new FileChooser();

			browser.setTitle("Open File");

			browser.setInitialDirectory(new File(System.getProperty("user.home")));
			File file = browser.showOpenDialog(stage);
			WaveFile waveFile = new WaveFile(file);
		});
*/	// ? open file disposable ?

/*
		fileItems.get(1).setOnAction((ActionEvent e) ->{

			if (FileManager.FileManagerSettings.getAutoSave()) {

				WaveFile
					file = FileCache.loadCurrent();

				file.getFileAddress().setNameToDefault();

				FileManager.saveFile(file);
			}

			FileCache.purgeCache();
		});
*/	// ? autosave disposable ?

/*
		fileItems.get(3).setOnAction((ActionEvent e) -> {

			FileChooser
				browser = new FileChooser();

			browser.setTitle("Save As");

			File
				file = browser.showSaveDialog(stage);

			saveFile(file);
		});
*/	// ? save disposable?

/*
		fileItems.get(5).setOnAction(close -> stage.close());
*/	// ? quit disposable ?

//		List<MenuItem> helpItems = menuHelp.getItems();

/*

		helpItems.get(0).setOnAction((ActionEvent e) -> {

			FileManager.FileManagerSettings.setAutoSave(((CheckMenuItem)helpItems.get(0)).isSelected());

			System.out.println("autosave enable : " + FileManager.FileManagerSettings.getAutoSave());
		});
*/	// ? autosave setting disposable ?

	}

//	-------------------------------------------------------------------------------------------

	public static boolean cacheIsEmpty(){

		return cacheIsEmptyDue.get();
	}
	public final boolean getCacheIsEmptyDue(){

		return cacheIsEmptyDue.get();
	}

	public final void setCacheIsEmptyDue(){

		cacheIsEmptyDue.set(FileCache.fileCache.size() == 0);
	}

	public final void setCacheIsEmptyDue(boolean b){

		setCacheIsEmptyDue();
	}	// just in case: a dummy overload.

	public final BooleanProperty cacheIsEmptyDueProperty(){

		return cacheIsEmptyDue;

	}

	public static BooleanProperty cacheIsEmptyStaticProperty(){

		return cacheIsEmptyDue;
	}

//	--------------------

	public final KeyCombination getFileKey(){

		return KeyCombination.keyCombination("Alt+F");
	}

	public final KeyCombination getOpen_fileKey(){

		return KeyCombination.keyCombination("Ctrl+O");
	}

	public final KeyCombination getClose_fileKey(){

		return KeyCombination.keyCombination("Ctrl+W");
	}

	public final KeyCombination getSave_fileKey(){

		return KeyCombination.keyCombination("Ctrl+Shift+S");
	}

	public final KeyCombination getQuitKey(){

		return KeyCombination.keyCombination("Ctrl+Q");
	}
}