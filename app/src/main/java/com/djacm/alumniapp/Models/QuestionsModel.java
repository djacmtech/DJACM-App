package com.djacm.alumniapp.Models;

import com.google.firebase.database.PropertyName;

import java.io.Serializable;

public class QuestionsModel implements Serializable {


    @PropertyName("questions")
    private String questions;
    @PropertyName("answer")
    private String answer;


    private String questionKey;

    QuestionsModel() { }


    public QuestionsModel(String question, String answer) {
        this.questions = question;
        this.answer = answer;
    }

    public QuestionsModel(String question, String answer, String key) {
        questions = question;
        this.answer = answer;
        questionKey = key;
    }

    public String getQuestions() {
        return questions;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getQuestionKey() {
        return questionKey;
    }

    public void setQuestionKey(String questionKey) {
        this.questionKey = questionKey;
    }
}
