package com.oaksmuth.aeccommunication.View;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.oaksmuth.aeccommunication.Controller.ConversationQuery;
import com.oaksmuth.aeccommunication.Controller.SpeakerWithView;
import com.oaksmuth.aeccommunication.Model.Conversation;
import com.oaksmuth.aeccommunication.R;

import java.util.Locale;

public class TalkFragment extends Fragment implements SpeakerWithView {

    private final int SPEECH_RECOGNITION_CODE = 1;
    private LinearLayout conversationHolder;
    private ScrollView scrollView;
    private TextView inputText;
    private Button askButton;

    private TextToSpeech tts;
    private ConversationQuery conversationQuery;
    public TalkFragment() {
        // Required empty public constructor
    }

    public static TalkFragment newInstance() {
        return new TalkFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_talk, container, false);
        conversationHolder = (LinearLayout) rootView.findViewById(R.id.talkConversationHolder);
        scrollView = (ScrollView) rootView.findViewById(R.id.talkScrollView);
        inputText = (TextView) rootView.findViewById(R.id.talkAskInput);
        askButton = (Button) rootView.findViewById(R.id.talkAskButton);

        conversationQuery = new ConversationQuery();

        askButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question = inputText.getText().toString();
                question = Character.toUpperCase(question.charAt(0)) + question.substring(1);
                //add question mark at the last
                Toast.makeText(getContext(),question,Toast.LENGTH_LONG).show();
                Conversation response = conversationQuery.queryByQuestion(getContext(),new Conversation(question,""));
                onSpeakWithViewAdd(response.getAnswer());
            }
        });
        askButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.US);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak something...");
                try {
                    startActivityForResult(intent, SPEECH_RECOGNITION_CODE);
                } catch (ActivityNotFoundException a) {
                    Toast.makeText(getContext(), "Sorry! Speech recognition is not supported in this device.",
                            Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });


        return rootView;
    }
    @Override
    public void onInit(int status) {

    }
    @Override
    public void onSpeakWithViewAdd(final String sentence){
        tts.speak(sentence, TextToSpeech.QUEUE_FLUSH, null);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextView tv = new TextView(getContext());
                scrollView.fullScroll(View.FOCUS_DOWN);
                tv.setTextColor(Color.BLACK);
                tv.setText(sentence);
                conversationHolder.addView(tv);
            }
        });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Toast.makeText(context,"Talk",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
