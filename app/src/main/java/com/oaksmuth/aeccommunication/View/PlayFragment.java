package com.oaksmuth.aeccommunication.View;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.oaksmuth.aeccommunication.Controller.ConversationQuery;
import com.oaksmuth.aeccommunication.Controller.Item;
import com.oaksmuth.aeccommunication.Controller.ListHeader;
import com.oaksmuth.aeccommunication.Controller.ListTopic;
import com.oaksmuth.aeccommunication.Controller.TopicQuery;
import com.oaksmuth.aeccommunication.Controller.TwoTextArrayAdapter;
import com.oaksmuth.aeccommunication.Model.Topic;
import com.oaksmuth.aeccommunication.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class PlayFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private ListView listView;
    private ArrayList<Topic> topics;

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
        topics = new ArrayList<>();

        TopicQuery topicQuery = new TopicQuery();
        try {
            topics = topicQuery.queryAllTopic(getContext());
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Item> items = topicsToViews(topics);
        TwoTextArrayAdapter adapter = new TwoTextArrayAdapter(getContext(), items);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ConversationQuery conversationQuery = new ConversationQuery();
                try {
                    conversationQuery.queryByTopic(getContext(),topics.get(position));

                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction.setCustomAnimations(R.anim.enter_from_left, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_left);
                    transaction.replace(R.id.content, new ConversationFragment());
                    transaction.addToBackStack(null);
                    transaction.commit();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        return rootView;
    }

    private ArrayList<Item> topicsToViews(ArrayList<Topic> topics){
        ArrayList<Item> items = new ArrayList<>();
        int i = 0;
        String tempHeader = topics.get(0).getHeader();
        boolean newHeader = true;
        while(true)
        {
            if(newHeader)
            {
                tempHeader = topics.get(i).getHeader();
                items.add(new ListHeader(tempHeader));
            }
            items.add(new ListTopic(topics.get(i).getTopic()));
            if(i == topics.size() - 1) break;
            i++;
            newHeader = !tempHeader.equals(topics.get(i).getHeader());
        }
        return items;
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
