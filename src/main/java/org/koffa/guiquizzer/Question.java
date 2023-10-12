package org.koffa.guiquizzer;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Question {
    private final String question;
    private final ArrayList<String> answers;
    private final int correctAnswer;
}