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

}
