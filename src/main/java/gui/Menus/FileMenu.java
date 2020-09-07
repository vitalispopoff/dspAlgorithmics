package gui.Menus;

import static javafx.scene.input.KeyCombination.keyCombination;

public enum FileMenu {

//	MENU_FILE,
		OPEN_FILE,
		CLOSE_FILE,
		SAVE,
		QUIT
	;

	Object[][]
		fileMenuItemParams = {

			{"_Open", keyCombination("Ctrl+O"), "MenuItem"},
			{"Close", keyCombination("Ctrl+W"), "MenuItem"},
			{"_Save", keyCombination("Ctrl+S"), "MenuItem"},
			{"_Quit", keyCombination("Ctrl+Q"), "MenuItem"}
	};

}
