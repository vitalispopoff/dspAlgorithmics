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
		cacheIsEmpty = new SimpleBooleanProperty(data.FileCache.getFileCache().size() == 0);

	private static Stage
		stage;

	public MainMenuController() {

		cacheIsEmptyProperty().bind(FileCache.cacheIsEmptyStaticProperty());
	}

//	-------------------------------------------------------------------------------------------

	public static void setStage(Stage s) {

		stage = s;
	}

	public void openFile(ActionEvent event) {

		if ( ! FileCache.getCacheIsEmpty()) FileCache.purgeCache();

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


			System.out.println("\n\tMainMenuController > openFile : retrieving opened file from fileCache\n");

			String
//				path = waveFile.getFileAddress().getPath();
				path = FileCache.getCurrentFile().getFileAddress().getPath();

			FileManager.FileManagerSettings.setCurrentDefaultPath(path);

			System.out.println(" MainMenuController > openFile : cache " + FileCache.getCurrentFile().toString());

//			setCacheIsEmpty();
		}
	}

	public void close(ActionEvent event) {

		if (FileManager.FileManagerSettings.getAutoSave()) {

			System.out.println("\n\tMainMenuController > close : retrieving current file from fileCache for an autosave \n");

			WaveFile
				waveFile = FileCache.getCurrentFile();

			FileManager.autoSaveFile();
		}

		FileCache.purgeCache();

//		setCacheIsEmpty();
	}

	public void save(ActionEvent event) {

		FileChooser
			browser = new FileChooser();

		browser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Audio Files", "*.mp3"));
		browser.setTitle("Save As");
		browser.setInitialDirectory(new File(FileManager.FileManagerSettings.getCurrentDefaultPath()));

		File
			file = browser.showSaveDialog(stage);

//		System.out.println(file.toString());

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

//		System.out.println("autosave enable : " + FileManager.FileManagerSettings.getAutoSave());
	}

//	-------------------------------------------------------------------------------------------

	public static boolean cacheIsEmpty() {

		return cacheIsEmpty.get();
	}

	public final boolean getCacheIsEmpty() {

		return cacheIsEmpty.get();
	}

//	public final void setCacheIsEmpty(){
//
//		cacheIsEmpty.set(FileCache.fileCache.size() == 0);
//	}

	public final BooleanProperty cacheIsEmptyProperty() {

		return cacheIsEmpty;

	}

	public static BooleanProperty cacheIsEmptyStaticProperty() {

		return cacheIsEmpty;
	}

//	--------------------

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