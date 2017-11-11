package com.oaksmuth.aeccommunication.View;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.oaksmuth.aeccommunication.Controller.ConversationQuery;
import com.oaksmuth.aeccommunication.Controller.SpeakerWithView;
import com.oaksmuth.aeccommunication.Controller.SpeakingNotifier;
import com.oaksmuth.aeccommunication.Model.Conversation;
import com.oaksmuth.aeccommunication.Model.Topic;
import com.oaksmuth.aeccommunication.R;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class QAFragment extends Fragment implements SpeakingNotifier.OnSpeakingFinishedListener, View.OnClickListener,SeekBar.OnSeekBarChangeListener ,SpeakerWithView{

    private Topic topic;
    private ArrayList<Conversation> conversations;
    private TextToSpeech tts;
    private SpeakingNotifier fsn;
    private DecimalFormat df = new DecimalFormat("0.00");

    private TextView initialTextView;
    private TextView speedTextView;
    private TextView pitchTextView;
    private SeekBar speedSeekBar;
    private SeekBar pitchSeekBar;
    private ScrollView scrollView;
    private LinearLayout conversationHolder;
    private ImageButton playButton;
    private ImageButton backwardButton;
    private ImageButton forwardButton;

    private boolean isPlaying = false;
    private boolean isQuestion = true;
    private int playingAt = 0;

    public QAFragment() {
        // Required empty public constructor
    }

    public static QAFragment newInstance() {
        return new QAFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_qa, container, false);
        initialTextView = (TextView) rootView.findViewById(R.id.initialTag);
        speedTextView = (TextView) rootView.findViewById(R.id.speedTextView);
        pitchTextView = (TextView) rootView.findViewById(R.id.pitchTextView);
        speedSeekBar = (SeekBar) rootView.findViewById(R.id.speedSeekBar);
        pitchSeekBar = (SeekBar) rootView.findViewById(R.id.pitchSeekBar);
        scrollView = (ScrollView) rootView.findViewById(R.id.scrollView);
        conversationHolder = (LinearLayout) rootView.findViewById(R.id.conversationHolder);
        playButton = (ImageButton) rootView.findViewById(R.id.playImageButton);
        backwardButton = (ImageButton) rootView.findViewById(R.id.backwardImageButton);
        forwardButton = (ImageButton) rootView.findViewById(R.id.forwardImageButton);
        tts = new TextToSpeech(getContext(), this);
        fsn = new SpeakingNotifier(this);
        tts.setOnUtteranceProgressListener(fsn);
        speedSeekBar.setOnSeekBarChangeListener(this);
        speedSeekBar.setTag("Speed");
        pitchSeekBar.setOnSeekBarChangeListener(this);
        pitchSeekBar.setTag("Pitch");
        playButton.setOnClickListener(this);
        backwardButton.setOnClickListener(this);
        forwardButton.setOnClickListener(this);
        try {
            conversations = new ConversationQuery().queryByTopic(getContext(), this.topic);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if(tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.playImageButton:
            {
                isPlaying = !isPlaying;
                if(isPlaying)
                {
                    playButton.setBackgroundResource(R.drawable.pause);
                    Log.i("onClick:Play:playingAt",String.valueOf(playingAt));
                    Log.i("onClick:Play:isPlaying",String.valueOf(isPlaying));
                    onSpeakWithViewAdd(null);
                }
                else
                {
                    playButton.setBackgroundResource(R.drawable.play);
                    tts.stop();
                    Log.i("onClick:Pause:playingAt",String.valueOf(playingAt));
                    Log.i("onClick:Pause:isPlaying",String.valueOf(isPlaying));
                }
                break;
            }
            case R.id.backwardImageButton:
            {
                tts.stop();
                isQuestion = true;
                playingAt--;
                Log.i("onClick:Back:playingAt",String.valueOf(playingAt));
                Log.i("onClick:Back:isPlaying",String.valueOf(isPlaying));
                if(isPlaying) onSpeakWithViewAdd(null);
                break;
            }
            case R.id.forwardImageButton:
            {
                tts.stop();
                isQuestion = true;
                playingAt++;
                Log.i("onClick:Next:playingAt",String.valueOf(playingAt));
                Log.i("onClick:Next:isPlaying",String.valueOf(isPlaying));
                if(isPlaying) onSpeakWithViewAdd(null);
                break;
            }
        }
    }
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch(seekBar.getTag().toString()) {
            case "Pitch": {
                float pitchValue = Float.parseFloat(df.format((float) (Math.pow(2, (double) progress / 50) / 2)));
                pitchTextView.setText(String.valueOf(pitchValue));
                tts.setPitch(pitchValue);
            }
            case "Speed": {
                float speedValue = Float.parseFloat(df.format((float) (Math.pow(2, (double) progress/50)/2)));
                speedTextView.setText(String.valueOf(speedValue));
                tts.setSpeechRate(speedValue);
            }
        }
    }
    public void onStartTrackingTouch(SeekBar seekBar) {}
    public void onStopTrackingTouch(SeekBar seekBar) {}
    @Override
    public void onInit(int status) {
        if(status == TextToSpeech.SUCCESS) {
            tts.setLanguage(Locale.ENGLISH);
            tts.speak("Category is " + topic.getHeader() + "and topic is " + topic.getTopic(), TextToSpeech.QUEUE_FLUSH, null);
            while(!tts.isSpeaking()) {
                try {
                    Thread.sleep(500);
                    Log.i("TTS","waiting");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            initialTextView.setVisibility(View.INVISIBLE);
        }
    }

    public void onSpeakingFinished() {
        isQuestion = !isQuestion;
        if(isQuestion) playingAt++;
        if(playingAt == conversations.size())
        {
            playingAt = 0;
            playButton.setBackgroundResource(R.drawable.play);
            isPlaying = false;
        }else
        {
            onSpeakWithViewAdd(null);
        }
    }

    public void onSpeakWithViewAdd(String sentence){
        HashMap<String, String> map = new HashMap<>();
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID");
        String toSpeak;
        String toShow;
        if(isQuestion)
        {
            toSpeak = "Question \t" + String.valueOf(playingAt + 1) + "\t:\t" + conversations.get(playingAt).getQA(true);
            toShow = toSpeak;
        }else
        {
            toSpeak = conversations.get(playingAt).getQA(false);
            toShow = "Answer \t" + String.valueOf(playingAt + 1) + "\t:\t" + toSpeak;
        }
        tts.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, map);
        onViewAdd(toShow);
    }

    public void onViewAdd(final String sentence){
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
}
