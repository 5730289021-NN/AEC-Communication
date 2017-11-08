package com.oaksmuth.aeccommunication.Model;

import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by Norawit Nangsue on 10/26/2017.
 */

public interface Item {
    int getViewType();
    View getView(LayoutInflater inflater, View convertView);
}
