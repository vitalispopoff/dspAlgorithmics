package gui.Menus;

import algorithms.metaProcessors.FileManager;
import data.*;
import data.AudioFile;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.input.KeyCombination;
import javafx.stage.*;

import java.io.File;

import static algorithms.metaProcessors.FileManager.saveFile;

public class MainMenuController {

	private static final BooleanProperty
		cacheIsEmpty = new SimpleBooleanProperty(data.FileCache.getFileCache().size() == 0);

	private static Stage
		stage;


	public MainMenuController() {

		cacheIsEmptyProperty().bind(FileCache.fileCacheIsEmptyStaticProperty());
	}


//	-------------------------------------------------------------------------------------------

	public static void setStage(Stage s) {

		stage = s;
	}

	public void openFile(ActionEvent event) {

//		if (FileCache.getFileCacheIsNotEmpty()) FileCache.purgeCache(); // temporarily disabled
		if (FileCache.getFileCache().size()>0) FileCache.purgeCache();

		FileChooser
			browser = new FileChooser();

		String
			currentDefPath = FileManager.FileManagerSettings.getCurrentDefaultPath();

		browser.setTitle("Open File");
		browser.setInitialDirectory(new File(currentDefPath));

		File file = browser.showOpenDialog(stage);

		if (file != null) {

			AudioFile
				audioFile = new WaveFile(file);

			String
//				path = waveFile.getFileAddress().getPath();
				path = FileCache.getFile().getFileAddress().getPath();

			FileManager.FileManagerSettings.setCurrentDefaultPath(path);
		}
	}

	public void close(ActionEvent event) {

		if (FileManager.FileManagerSettings.getAutoSave()) {

			AudioFile
				audioFile = FileCache.getFile();

			FileManager.autoSaveFile();
		}

		FileCache.purgeCache();
	}

	public void save(ActionEvent event) {

		FileChooser
			browser = new FileChooser();

		browser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Audio Files", "*.mp3"));
		browser.setTitle("Save As");
		browser.setInitialDirectory(new File(FileManager.FileManagerSettings.getCurrentDefaultPath()));

		File
			file = browser.showSaveDialog(stage);

		if (file != null) saveFile(file);
	}

	public void quit(ActionEvent event) {

		close(event); // ? is it ok ?

		stage.close();
	}

	public void enableAutoSave(ActionEvent event) {

		boolean
			checkBoxIsSelected = ((CheckMenuItem) event.getSource()).isSelected();

		FileManager.FileManagerSettings.setAutoSave(checkBoxIsSelected);
	}


//	-------------------------------------------------------------------------------------------

	public static boolean cacheIsEmpty() {

		return cacheIsEmpty.get();
	}

	public final boolean getCacheIsEmpty() {

		return cacheIsEmpty.get();
	}

	public final BooleanProperty cacheIsEmptyProperty() {

		return cacheIsEmpty;

	}

	public static BooleanProperty cacheIsEmptyStaticProperty() {

		return cacheIsEmpty;
	}



	public final KeyCombination getFileKey() {

		return KeyCombination.keyCombination("Alt+F");
	}

	public final KeyCombination getOpen_fileKey() {

		return KeyCombination.keyCombination("Ctrl+O");
	}

	public final KeyCombination getClose_fileKey() {

		return KeyCombination.keyCombination("Ctrl+W");
	}

	public final KeyCombination getSave_fileKey() {

		return KeyCombination.keyCombination("Ctrl+Shift+S");
	}

	public final KeyCombination getQuitKey() {

		return KeyCombination.keyCombination("Ctrl+Q");
	}

}