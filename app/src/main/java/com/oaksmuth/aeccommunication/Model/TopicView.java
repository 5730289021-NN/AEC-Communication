package com.oaksmuth.aeccommunication.Model;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.oaksmuth.aeccommunication.Controller.TwoTextArrayAdapter;
import com.oaksmuth.aeccommunication.R;

/**
 * Created by Norawit Nangsue on 10/27/2017.
 */

public class TopicView implements Item {
    private final String topic;
    
    public TopicView(String topic) {
        this.topic = topic;
    }

    @Override
    public int getViewType() {
        return TwoTextArrayAdapter.RowType.LIST_ITEM.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view;
        if (convertView == null) {
            view = inflater.inflate(R.layout.list_topic, null);
        } else {
            view = convertView;
        }
        TextView text = (TextView) view.findViewById(R.id.list_topic);
        text.setText(topic);

        return view;
    }

    @Override
    public String toString() {
        return topic;
    }
}
