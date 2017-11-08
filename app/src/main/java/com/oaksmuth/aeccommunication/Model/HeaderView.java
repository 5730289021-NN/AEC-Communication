package com.oaksmuth.aeccommunication.Model;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.oaksmuth.aeccommunication.Controller.TwoTextArrayAdapter;
import com.oaksmuth.aeccommunication.R;

/**
 * Created by Norawit Nangsue on 10/27/2017.
 */

public class HeaderView implements Item {

    private final String header;

    public HeaderView(String header) {
        this.header = header;
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
        text.setText(header);
        return view;
    }

    @Override
    public String toString() {
        return header;
    }
}
