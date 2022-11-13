package GestionEstancias;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

	private Controller controller;

	@Override
	public void start(Stage stage) throws Exception {

		controller = new Controller();

		stage.setScene(new Scene(controller.getView()));
		stage.setTitle("Gestion Estancias");
		stage.sizeToScene();
		stage.show();

	}

	public static void main(String[] args) {
		launch();
	}

}