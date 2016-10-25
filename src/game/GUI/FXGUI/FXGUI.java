package game.GUI.FXGUI;

import java.io.IOException;

import game.GUI.Action;
import game.engine.GameEngine;
import game.engine.board.GameBoard;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

public class FXGUI extends Application implements GUI {

	GameEngine gameEngine;
	AsciiRenderer renderer;
	TextFlow txtScreen;
	Button btnUp;
	Button btnDown;
	Button btnLeft;
	Button btnRight;

	public static void main(String[] args) {
		launch(args);
	}

	public FXGUI() {
		renderer = new AsciiRenderer();
		gameEngine = new GameEngine();
	}

	/* (non-Javadoc)
	 * @see game.GUI.FXGUI.GUI#update(game.engine.board.GameBoard)
	 */
	@Override
	public void update(GameBoard gameBoard) {
		Text screenText = new Text(renderer.getBoardAsString(gameBoard));
		txtScreen.getChildren().clear();
		txtScreen.getChildren().add(screenText);
	}

    @Override
    public void start(Stage primaryStage) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Adventure Game");
        primaryStage.setScene(scene);
        primaryStage.show();

        txtScreen = (TextFlow) scene.lookup("#txtScreen");
        btnUp = (Button) scene.lookup("#btnUp");
    	btnDown = (Button) scene.lookup("#btnDown");
    	btnLeft = (Button) scene.lookup("#btnLeft");
    	btnRight = (Button) scene.lookup("#btnRight");

    	setupButtons();

    	// draw the starting scene
    	update(gameEngine.getGameBoard());
    }

    private void setupButtons() {
    	btnUp.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				runAction(Action.UP);
			}
    	});

    	btnDown.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				runAction(Action.DOWN);
			}
    	});

    	btnLeft.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				runAction(Action.LEFT);
			}
    	});

    	btnRight.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				runAction(Action.RIGHT);
			}
    	});
    }

    private void runAction(Action action) {
    	if (gameEngine.isActionPossible(action)) {
    		// perform the action
    		gameEngine.runAction(action);
    		// update all the actors
    		gameEngine.updateActors(this);
    		// update the render
    		update(gameEngine.getGameBoard());
    	}
    }
}
