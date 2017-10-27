package com.oaksmuth.aeccommunication.View;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.oaksmuth.aeccommunication.Controller.Item;
import com.oaksmuth.aeccommunication.Controller.ListHeader;
import com.oaksmuth.aeccommunication.Controller.ListTopic;
import com.oaksmuth.aeccommunication.Controller.TopicQuery;
import com.oaksmuth.aeccommunication.Controller.TwoTextArrayAdapter;
import com.oaksmuth.aeccommunication.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class PlayFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private ListView listView;

    public PlayFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Reference: https://stackoverflow.com/questions/37177999/java-lang-nullpointerexception-attempt-to-invoke-virtual-method-android-view-v
        View rootView = inflater.inflate(R.layout.fragment_play, container, false);
        listView = (ListView) rootView.findViewById(R.id.list_view);
        List<Item> items = new ArrayList<>();

        TopicQuery topicQuery = new TopicQuery();
        try {
            topicQuery.queryAllTopic(getContext());
        } catch (IOException e) {
            e.printStackTrace();
        }



        items.add(new ListHeader("Header 1"));
        items.add(new ListTopic("Text 1"));
        items.add(new ListTopic("Text 2"));
        items.add(new ListTopic("Text 3"));
        items.add(new ListTopic("Text 4"));
        items.add(new ListHeader("Header 2"));
        items.add(new ListTopic("Text 5"));
        items.add(new ListTopic("Text 6"));
        items.add(new ListTopic("Text 7"));
        items.add(new ListTopic("Text 8"));
        TwoTextArrayAdapter adapter = new TwoTextArrayAdapter(getContext(), items);
        listView.setAdapter(adapter);

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            Toast.makeText(getContext(),"Play",Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
