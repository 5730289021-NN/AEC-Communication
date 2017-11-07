package com.oaksmuth.aeccommunication.View;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
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
import android.widget.Toast;

import com.oaksmuth.aeccommunication.Controller.ConversationQuery;
import com.oaksmuth.aeccommunication.Model.Conversation;
import com.oaksmuth.aeccommunication.Model.Topic;
import com.oaksmuth.aeccommunication.R;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;

public class QAFragment extends Fragment implements View.OnClickListener,SeekBar.OnSeekBarChangeListener,TextToSpeech.OnInitListener{

    private Topic topic;
    private ArrayList<Conversation> conversations;
    private TextToSpeech tts;
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

    private PlayTTSTask playTTSTask;
    private boolean isPlaying = false;
    private boolean isQuestion = true;
    private int playingAt = 0;

    public QAFragment() {
        // Required empty public constructor
    }

    public static QAFragment newInstance() {
        QAFragment fragment = new QAFragment();
        return fragment;
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
        playTTSTask = new PlayTTSTask();
        tts = new TextToSpeech(getContext(), this);
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

    private void changeToItsState()
    {
        isQuestion = !isQuestion;
        playingAt = isQuestion?playingAt-1:playingAt;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
                Log.i("onClick", "Play Clicked");
                isPlaying = !isPlaying;
                if(isPlaying) {
                    if(playTTSTask.getStatus() != AsyncTask.Status.RUNNING) playTTSTask.execute();
                    playButton.setImageResource(R.drawable.pause);
                }
                else {
                    tts.stop();
                    changeToItsState();
                    playButton.setImageResource(R.drawable.play);
                }
            }
            case R.id.backwardImageButton:
            {
                changeToItsState();
                playingAt--;
                isQuestion = true;
                tts.stop();
            }
            case R.id.forwardImageButton:
            {
                changeToItsState();
                playingAt++;
                isQuestion = true;
                tts.stop();
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

    private class PlayTTSTask extends AsyncTask<Void, String, Void>
    {
        @Override
        protected Void doInBackground(Void... voids) {
            Log.i("Do In Background", "Initiated");
            while(isPlaying && !tts.isSpeaking()) {
                //On New Conversation
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (isQuestion) {
                    Log.i("Do In Background", "isPlaying & !tts.isSpeaking & isQuestion");
                    tts.speak(conversations.get(playingAt).getQuestion(), TextToSpeech.QUEUE_FLUSH, null);
                    publishProgress(conversations.get(playingAt).getQuestion());
                } else {
                    Log.i("Do In Background", "isPlaying & !tts.isSpeaking & !isQuestion");
                    tts.speak(conversations.get(playingAt).getAnswer(), TextToSpeech.QUEUE_FLUSH, null);
                    publishProgress(conversations.get(playingAt).getAnswer());
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            TextView newTextView = new TextView(getContext());
            if (isQuestion) {
                String questionString = "Question\t" + (playingAt + 1) + "\t:\t" + values[0] + "\n";
                newTextView.setText(questionString);
                isQuestion = false;
            } else {
                String answerString = "Answer\t" + (playingAt + 1) + "\t:\t" + values[0] + "\n";
                newTextView.setText(answerString);
                isQuestion = true;
                playingAt++;
                if(playingAt == conversations.size())
                {
                    isPlaying = false;
                }
            }
            scrollView.fullScroll(View.FOCUS_DOWN);
            newTextView.setTextColor(Color.BLACK);
            conversationHolder.addView(newTextView);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            playButton.setBackgroundResource(R.drawable.play);
            isPlaying = false;
            playingAt = 0;
        }
    }

}
