package org.koffa.guiquizzer;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class Quiz extends VBox {
    private ArrayList<Question> questions;
    private int currentQuestionNb = 0;
    private int maxQuestions = 0;
    private final VBox questionBox = new VBox();
    private VBox toggleBox = new VBox();
    private int correctAnswers = 0;
    private int wrongAnswers = 0;

    public Quiz(File questionFile) {
        this.questions = null;

        if(questionFile == null) {
            this.getChildren().add(new Label("No question file selected"));
        }

        else {
            this.questions = getQuestions(questionFile);
            if(questions != null) {
                maxQuestions = questions.size() - 1;
                startQuiz();
                this.getChildren().addAll(questionBox);
            }
            else this.getChildren().add(new Label("Error reading file, make sure it's properly formatted"));
        }

    }

    private ArrayList<Question> getQuestions(File questionFile) {
        try {
            return new Gson().fromJson(Files.readString(questionFile.toPath()),
                    new TypeToken<ArrayList<Question>>(){}.getType());
        } catch (IOException e) {
            return null;
        }
    }
    public void startQuiz() {
        System.out.println(currentQuestionNb + " / " + maxQuestions);
        Button nextButton = new Button("Next");
        nextButton.setOnAction(event -> checkAnswer());
        toggleBox = getQuestionBox(questions.get(currentQuestionNb));
        questionBox.getChildren().setAll(toggleBox, nextButton);
    }

    private void nextQuestion() {
        currentQuestionNb++;
        Button nextButton = new Button("Next");
        nextButton.setOnAction(event -> checkAnswer());
        if(currentQuestionNb == maxQuestions) {
            finishQuiz();
            return;
        }
        toggleBox = getQuestionBox(questions.get(currentQuestionNb));
        questionBox.getChildren().setAll(
                toggleBox, nextButton
        );
    }
    private void checkAnswer() {
        Question currentQuestion = questions.get(currentQuestionNb);
        int currentCorrectAnswer = currentQuestion.getCorrectAnswer();
        System.out.println("Checking answer");
        boolean correctAnswer = false;
        Button nextButton = new Button("Next");
        nextButton.setOnAction(event -> nextQuestion());
        for(int i = 0; i < toggleBox.getChildren().size(); i++) {
            Node currentNode = toggleBox.getChildren().get(i);
            if(currentNode instanceof RadioButton) {
                if(((RadioButton) currentNode).isSelected()) {
                    if(i == currentCorrectAnswer) {
                        correctAnswer = true;
                    }
                    break;
                }
            }
        }
        String message;
        if(correctAnswer) {
            correctAnswers++;
            message = "Correct answer!";
        }
        else {
            message = "Sorry, correct answer was\n" + currentQuestion.getAnswers().get(currentCorrectAnswer - 1);
            wrongAnswers++;
        }
        questionBox.getChildren().setAll(new Label(message), nextButton);
    }

    private void finishQuiz() {
        String finishString = String.format(
                "Good job completing the quiz! \nYou got %d of %d correct answers! \n \n Select a new file to start a new quiz.", correctAnswers, maxQuestions
        );
        questionBox.getChildren().setAll(new Label(finishString));
    }

    private VBox getQuestionBox(Question question) {
        Label statText = new Label("Current question: " + (currentQuestionNb+1) + " of " + maxQuestions + ".\n" +
                "Correct answers: " + correctAnswers + "\n" +
                "Wrong answers: " + wrongAnswers);
        VBox questionBox = new VBox();
        Label qText = new Label(question.getQuestion());
        qText.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
        questionBox.getChildren().addAll(statText, qText);
        ToggleGroup toggleGroup = new ToggleGroup();
        for(String answer : question.getAnswers()) {
            RadioButton radioButton = new RadioButton(answer);
            radioButton.setToggleGroup(toggleGroup);
            questionBox.getChildren().add(radioButton);
        }
        return questionBox;
    }
}
