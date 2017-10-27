package com.oaksmuth.aeccommunication.Model;

/**
 * Created by noraw on 10/27/2017.
 */

public class Conversation {

    private String question;
    private String answer;

    public Conversation(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

}
