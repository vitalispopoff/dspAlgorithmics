// ! reintroduced from bbee59cb3aea8cdf019909244b20b84d9e8daa59 : grid scalable and simplified
// ? https://github.com/vitalispopoff/dspAlgorithmics/commit/bbee59cb3aea8cdf019909244b20b84d9e8daa59

// ? class is disposable ?
package gui;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class Grid extends VBox{

	int
		initWidthParam = 0,
		initHeightParam= 0;

	Color
		color = Color.LIGHTBLUE;

	int
		elementIndex = 18;

	public Grid(BorderPane root) {
	}
}