package com.oaksmuth.aeccommunication.Controller;

import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by noraw on 10/26/2017.
 */

public interface Item {
    public int getViewType();
    public View getView(LayoutInflater inflater, View convertView);
}
