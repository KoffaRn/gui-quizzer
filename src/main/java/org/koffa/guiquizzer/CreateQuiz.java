package org.koffa.guiquizzer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class CreateQuiz extends Application {

    @Override
    public void start(Stage stage) {
        ArrayList<Question> quiz = new ArrayList<>();
        VBox root = new VBox();
        stage.setTitle("Create new quiz");
        stage.setScene(new Scene(root, 400, 400));
        stage.show();
    }
}
