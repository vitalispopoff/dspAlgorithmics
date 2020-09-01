package gui;

import data.Wave;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MainMenu extends MenuBar {

	static Menu
		menuFile = new Menu("File"),
		menuEdit = new Menu("Edit"),
		menuView = new Menu("View"),
		menuAbout = new Menu("About");

	static MenuItem
		openFile = new MenuItem("Open"),
		closeFile = new MenuItem("Close"),
		saveAs = new MenuItem("Save as"),
		exitApp = new MenuItem("Exit");

	static FileChooser
		browser = new FileChooser();

	static {
		openFile.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
		closeFile.setAccelerator(KeyCombination.keyCombination("Ctrl+W"));
		saveAs.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+S"));
		exitApp.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));


		exitApp.setOnAction((ActionEvent close) -> System.exit(0));


		menuFile.getItems().addAll(
			openFile,
			closeFile,
			new SeparatorMenuItem(),
			saveAs,
			new SeparatorMenuItem(),
			exitApp
		);
	}

//	--------------------------------------------------------------------------------------------------------------------

	public MainMenu(Stage stage){

		this.getMenus().addAll(menuFile, menuEdit, menuView, menuAbout);

		setOpenAction(stage);
		setSaveAsAction(stage);
	}

//	--------------------------------------------------------------------------------------------------------------------

	void setOpenAction(Stage stage){

		openFile.setOnAction((ActionEvent open) -> {

			browser.setInitialDirectory(new File(System.getProperty("user.home")));
			File file = browser.showOpenDialog(stage);
			Wave wave = new Wave(file);
		});
	}

	void setSaveAsAction(Stage stage){

		saveAs.setOnAction((ActionEvent e) -> browser.showOpenDialog(stage));
	}
}