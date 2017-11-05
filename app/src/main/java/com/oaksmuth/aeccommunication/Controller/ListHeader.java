package com.oaksmuth.aeccommunication.Controller;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.oaksmuth.aeccommunication.R;

/**
 * Created by Oak on 10/27/2017.
 */

public class ListHeader implements Item{

    private final String headerTitle;

    public ListHeader(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    @Override
    public int getViewType() {
        return TwoTextArrayAdapter.RowType.HEADER_ITEM.ordinal();
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view;
        if (convertView == null) {
            view = inflater.inflate(R.layout.list_header, null);
        } else {
            view = convertView;
        }
        TextView text = (TextView) view.findViewById(R.id.separator);
        text.setText(headerTitle);
        return view;
    }
}
