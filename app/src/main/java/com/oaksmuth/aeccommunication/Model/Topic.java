package com.oaksmuth.aeccommunication.Model;

/**
 * Created by Norawit Nangsue on 10/27/2017.
 */

public class Topic {

    private String header;
    private String topic;

    public Topic(String header,String topic)
    {
        this.header = header;
        this.topic = topic;
    }

    public boolean equals(Topic topic){
        if(this.getHeader() == topic.getHeader() && this.getTopic() == this.getTopic())
        {
            return true;
        }
        return false;
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
