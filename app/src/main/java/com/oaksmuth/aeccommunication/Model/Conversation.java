package com.oaksmuth.aeccommunication.Model;

/**
 * Created by Norawit Nangsue on 10/27/2017.
 * Conversation class consists of question and answer.
 */

public class Conversation {

    private String question;
    private String answer;

    public Conversation(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQA(boolean isQuestion)
    {
        if(isQuestion) return question;
        else return answer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswer() {
        return this.answer;
    }
    //TODO
    public Conversation normalizeQuestion() {
        String question = this.getQuestion();
        question = question.substring(0, 1).toUpperCase() + question.substring(1);
        if(question.endsWith("?"))
        {
            question = question.substring(0,question.length() - 1);
        }
        question = question.trim() + " ?";
        this.setQuestion(question);
        return this;
    }

}
