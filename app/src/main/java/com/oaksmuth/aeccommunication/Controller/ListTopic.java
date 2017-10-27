package com.oaksmuth.aeccommunication.Controller;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.oaksmuth.aeccommunication.R;

/**
 * Created by Oak on 10/27/2017.
 */

public class ListTopic implements Item{
    private final String topicTitle;
    
    public ListTopic(String topicTitle) {
        this.topicTitle = topicTitle;
    }

    @Override
    public int getViewType() {
        return TwoTextArrayAdapter.RowType.LIST_ITEM.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view;
        if (convertView == null) {
            view = (View) inflater.inflate(R.layout.list_topic, null);
            // Do some initialization
        } else {
            view = convertView;
        }
        TextView text = (TextView) view.findViewById(R.id.list_topic);
        text.setText(topicTitle);

        return view;
    }

}
