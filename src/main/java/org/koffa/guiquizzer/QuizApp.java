package org.koffa.guiquizzer;

import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class QuizApp extends javafx.application.Application {
    private final VBox root = new VBox();
    private Stage stage;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        MenuBar fileMenuBar = new MenuBar();
        Menu menuFile = new Menu("File");
        MenuItem selectQuiz = new MenuItem("Select quiz");
        MenuItem createQuiz = new MenuItem("Create a new quiz");
        menuFile.getItems().addAll(selectQuiz, createQuiz);
        fileMenuBar.getMenus().addAll(menuFile);
        selectQuiz.setOnAction((event) -> selectQuiz());
        createQuiz.setOnAction((event) -> createQuiz());
        VBox quizBox = new Quiz(null);
        root.getChildren().addAll(fileMenuBar, quizBox);
        stage.setTitle("QuizApp!");
        stage.setScene(new Scene(
                root, 900, 500
        ));
        stage.show();
    }

    private void createQuiz() {
        CreateQuiz createQuiz = new CreateQuiz();
        createQuiz.start(new Stage());
    }

    private void selectQuiz() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Json file", "*.json"));
        root.getChildren().remove(1);
        root.getChildren().add(new Quiz(fileChooser.showOpenDialog(stage)));
    }

    public static void main(String[] args) {
        launch();
    }
}