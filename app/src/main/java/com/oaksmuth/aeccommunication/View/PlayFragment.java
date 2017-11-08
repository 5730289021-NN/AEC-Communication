package com.oaksmuth.aeccommunication.View;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.oaksmuth.aeccommunication.Model.Item;
import com.oaksmuth.aeccommunication.Model.HeaderView;
import com.oaksmuth.aeccommunication.Model.TopicView;
import com.oaksmuth.aeccommunication.Controller.TopicQuery;
import com.oaksmuth.aeccommunication.Controller.TwoTextArrayAdapter;
import com.oaksmuth.aeccommunication.Model.Topic;
import com.oaksmuth.aeccommunication.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class PlayFragment extends Fragment {
    private OnTopicSelectedListener mCallback;
    private ListView listView;
    private ArrayList<Topic> topics;
    private List<Item> items;

    public PlayFragment() {
        // Required empty public constructor
    }

    public static PlayFragment newInstance() {
        return new PlayFragment();
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
        items = topicsToViews(topics);
        TwoTextArrayAdapter adapter = new TwoTextArrayAdapter(getContext(), items);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(items.get(position).getClass() == TopicView.class)
                {
                    String header = null;
                    for(int r = position ; r >= 0 ; r--)
                    {
                        if(items.get(r).getClass() == HeaderView.class)
                        {
                            header = items.get(r).toString();
                            break;
                        }
                    }
                    mCallback.onTopicSelected(new Topic(header,items.get(position).toString()));
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
                items.add(new HeaderView(tempHeader));
            }
            items.add(new TopicView(topics.get(i).getTopic()));
            if(i == topics.size() - 1) break;
            i++;
            newHeader = !tempHeader.equals(topics.get(i).getHeader());
        }
        return items;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTopicSelectedListener) {
            mCallback = (OnTopicSelectedListener) context;
            Toast.makeText(context,"Play",Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context,"Not an instance of MainActivity",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
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
    public interface OnTopicSelectedListener {
        void onTopicSelected(Topic topic);
    }
}
