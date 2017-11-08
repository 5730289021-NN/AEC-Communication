package com.oaksmuth.aeccommunication.Controller;

import android.content.Context;
import android.database.Cursor;

import com.oaksmuth.aeccommunication.Model.Topic;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Norawit Nangsue on 10/27/2017.
 */

public class TopicQuery {

    public ArrayList<Topic> queryAllTopic(Context context) throws IOException {
        ArrayList<Topic> topics = new ArrayList<>();
        DatabaseHelper helper = new DatabaseHelper(context);
        helper.openDatabase();
        Cursor cursor = helper.rawQuery("SELECT Header.HeaderString, TopicString FROM Topic INNER JOIN Header ON Topic.HeaderID = Header.ID",null);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            topics.add(new Topic(cursor.getString(cursor.getColumnIndex("HeaderString")),cursor.getString(cursor.getColumnIndex("TopicString"))));
        }
        helper.close();
        cursor.close();
        return topics;
    }

    public int findConversationAmount(Context context) throws IOException {
        DatabaseHelper helper = new DatabaseHelper(context);
        helper.openDatabase();
        Cursor cursor = helper.rawQuery("SELECT COUNT(ID) FROM Conversation",null);
        cursor.moveToFirst();
        int value = cursor.getInt(cursor.getColumnIndex("COUNT(ID)"));
        helper.close();
        cursor.close();
        return value;
    }
}
