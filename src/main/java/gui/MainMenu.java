package gui;

import algorithms.metaProcessors.FileManager;
import data.FileCache;
import data.WaveFile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.*;

import java.io.File;
import java.util.List;

import static algorithms.metaProcessors.FileManager.saveFile;
import static gui.Menus.MenuItems.*;

public class MainMenu extends MenuBar {

	@FXML
		public static MainMenu
		mainMenu;



	Stage
		stage;

	Menu
		menuFile = MENU_FILE.getMenu(OPEN_FILE, CLOSE_FILE, SEPARATOR, SAVE, SEPARATOR, QUIT),
		menuEdit = MENU_EDIT.getMenu(),
		menuAnalyze = MENU_ANALYZE.getMenu(),
		menuView = MENU_VIEW.getMenu(FILE_WAVEFORM, FILE_AMPLITUDE_DISTRIBUTION, SEPARATOR,  FILE_PROPERTIES),
		menuHelp = MENU_HELP.getMenu(AUTO_SAVE);

//	--------------------------------------------------------------------------------------------------------------------

	public MainMenu(Stage stage){

//		setHeight(25);

		this.stage = stage;

		this.getMenus().addAll(menuFile, menuEdit, menuAnalyze, menuView, menuHelp);

		setActionsToMenuFile();

/*
	//	! TEMPORAL	//-------------------------------------------------------------------------

		MenuItem
			button = new MenuItem("window size");

		button.setOnAction(e -> System.out.println(stage.getWidth() + ", " + stage.getHeight()));

		Menu
			temporal = new Menu("TEMPORAL");

		temporal.getItems().add(button);

//		getMenus().add(temporal);

	//	!  //-----------------------------------------------------------------------------------
*/	// * disposable

	}

	private void setActionsToMenuFile(){

		List<MenuItem>
			fileItems = menuFile.getItems();

		fileItems.get(0).setOnAction((ActionEvent e) -> {

			FileChooser
				browser = new FileChooser();

			browser.setTitle("Open File");

			browser.setInitialDirectory(new File(System.getProperty("user.home")));
			File file = browser.showOpenDialog(stage);
			WaveFile waveFile = new WaveFile(file);
		});

		fileItems.get(1).setOnAction((ActionEvent e) ->{

			if (FileManager.FileManagerSettings.getAutoSave()) {

				WaveFile
					file = FileCache.loadCurrent();

				file.getFileAddress().setNameToDefault();

				FileManager.saveFile(file);
			}

			FileCache.purgeCache();
		});

		fileItems.get(3).setOnAction((ActionEvent e) -> {

			FileChooser
				browser = new FileChooser();

			browser.setTitle("Save As");

			File
				file = browser.showSaveDialog(stage);

			saveFile(file);
		});

		fileItems.get(5).setOnAction(close -> stage.close());

		List<MenuItem>
			helpItems = menuHelp.getItems();

		helpItems.get(0).setOnAction((ActionEvent e) -> {

			FileManager.FileManagerSettings.setAutoSave(((CheckMenuItem)helpItems.get(0)).isSelected());

			System.out.println("autosave enable : " + FileManager.FileManagerSettings.getAutoSave());
		});


	}
}