package com.oaksmuth.aeccommunication.Controller;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.oaksmuth.aeccommunication.Model.Conversation;
import com.oaksmuth.aeccommunication.Model.Topic;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Norawit Nangsue on 10/27/2017.
 */

public class ConversationQuery {
    public ArrayList<Conversation> queryByTopic(Context context, Topic topic) throws IOException {
        ArrayList<Conversation> conversations = new ArrayList<>();
        DatabaseHelper helper = new DatabaseHelper(context);
        helper.openDatabase(true);
        Cursor cursor = helper.rawQuery("SELECT Question, Answer FROM Conversation INNER JOIN Topic ON TopicID = Topic.ID WHERE Topic.TopicString = ?",new String[] {topic.getTopic()});
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            conversations.add(new Conversation(cursor.getString(cursor.getColumnIndex("Question")),cursor.getString(cursor.getColumnIndex("Answer"))));
        }
        helper.close();
        cursor.close();
        return conversations;
    }

    public void addConversation(Context context, Topic topic, Conversation conversation) throws IOException {
        //TODO
    }

    public Conversation queryByQuestion(Context context, Conversation conversation) {
        DatabaseHelper helper = new DatabaseHelper(context);
        try {
            helper.openDatabase(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Cursor cursor = helper.rawQuery("SELECT Answer FROM Conversation WHERE Question = ? COLLATE NOCASE",new String[] {conversation.normalizeQuestion().getQuestion()});
        cursor.moveToFirst();
        if(cursor.getCount() >0) conversation.setAnswer(cursor.getString(cursor.getColumnIndex("Answer")));
        else conversation.setAnswer("Sorry, I don't know for \"" + conversation.normalizeQuestion().getQuestion() + "\"" );
        helper.close();
        cursor.close();
        return conversation;
    }

}
