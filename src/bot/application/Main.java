package bot.application;

import bot.controller.Controller;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setWidth(900);
        primaryStage.setHeight(700);
        primaryStage.setResizable(false);
        Controller mainController = drawScene(primaryStage);
        primaryStage.setOnHiding(e -> mainController.saveUserItemsToXML());
    }

    private Controller drawScene(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/MainView.fxml"));
        primaryStage.setTitle("Lounge bot");
        primaryStage.setScene(new Scene(loader.load(), 800, 600));
        Controller newWindowController = loader.getController();
        primaryStage.show();
        return newWindowController;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
