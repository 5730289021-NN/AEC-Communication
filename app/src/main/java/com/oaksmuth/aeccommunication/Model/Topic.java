package com.oaksmuth.aeccommunication.Model;

/**
 * Created by noraw on 10/27/2017.
 */

public class Topic {

    private String header;
    private String topic;

    public Topic(String header,String topic)
    {
        this.header = header;
        this.topic = topic;
    }

    public String getHeader() {
        return header;
    }

    public String getTopic() {
        return topic;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
