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
        String cap = String.valueOf(question.charAt(0)).toUpperCase();
        String[] ans = new String[1];
        text = text.trim();
        if(text.endsWith("?"))
        {
            text = text.substring(0,text.length() - 1);
        }

        text = text.substring(1,text.length());
        text = cap + text + " ?";
        ans[0] = text;
        return conversation;
    }
}
