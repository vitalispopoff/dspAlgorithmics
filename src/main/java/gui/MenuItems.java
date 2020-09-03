package gui;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCombination;

import static javafx.scene.input.KeyCombination.*;

public enum MenuItems {

	MENU_FILE,
		OPEN_FILE,
		CLOSE_FILE,
		SAVE,
		QUIT,

	MENU_EDIT,

	MENU_ANALYZE,

	MENU_VIEW,
		FILE_WAVEFORM,
		FILE_AMPLITUDE_DISTRIBUTION,
		FILE_PROPERTIES,

	MENU_HELP,

	SEPARATOR {

		@Override
		public KeyCombination getKeyCombination(){

			return null;
		}

		@Override
		public Menu getMenu(MenuItems... items){

			return null;
		}

		@Override
		public MenuItem getMenuItem(){

			return new SeparatorMenuItem();
		}
	};

//	--------------------------------------------------------------------------------------------------------------------

	public KeyCombination getKeyCombination(){

		KeyCombination
			keyStroke = (KeyCombination) menuItemParams[this.ordinal()][1];

		return keyStroke;
	}

	public MenuItem getMenuItem (){

		MenuItem
			item = new MenuItem((String) menuItemParams[this.ordinal()][0]);

		item.setAccelerator(this.getKeyCombination());

		return item;
	}

	public Menu getMenu(MenuItems... items){

		Menu
			menu = new Menu((String) menuItemParams[this.ordinal()][0]);

		menu.setMnemonicParsing(true);

		menu.setAccelerator((KeyCombination) menuItemParams[this.ordinal()][1]);

		for(MenuItems item : items) {

			menu.getItems().add(item.getMenuItem());
			item.getMenuItem().setMnemonicParsing(true);
		}
		return menu;
	}



	Object[][]
		menuItemParams = {

			{"_File", keyCombination("Alt+F")},
				{"_Open", keyCombination("Ctrl+O")},
				{"Close", keyCombination("Ctrl+W")},
				{"_Save", keyCombination("Ctrl+S")},
				{"_Quit", keyCombination("Ctrl+Q"), },

			{"_Edit", keyCombination("Alt+E")},

			{"_Analyze", keyCombination("Alt+A")},

			{"_View", keyCombination("Alt+V"), },
				{"_Waveform", keyCombination("Alt+W")},
				{"Amplitude _Distribution", keyCombination("Alt+D")},
				{"File _Properties", keyCombination("Alt+P")},

			{"Help", keyCombination("Alt+H")}
	};
}