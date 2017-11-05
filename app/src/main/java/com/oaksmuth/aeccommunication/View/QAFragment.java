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
import java.util.Locale;

public class QAFragment extends Fragment {

    private Topic topic;
    private ArrayList<Conversation> conversations;
    private TextToSpeech tts;
    private DecimalFormat df;

    private TextView initialTextView;
    private TextView speedTextView;
    private TextView pitchTextView;
    private SeekBar speedSeekBar;
    private SeekBar pitchSeekBar;
    private ScrollView scrollView;
    private ImageButton playButton;
    private ImageButton backwardButton;
    private ImageButton forwardButton;

    private boolean isPlaying;
    private boolean isQuestion;
    private int playingAt;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_conversation, container, false);
        initialTextView = (TextView) rootView.findViewById(R.id.initialTag);
        speedTextView = (TextView) rootView.findViewById(R.id.speedTextView);
        pitchTextView = (TextView) rootView.findViewById(R.id.pitchTextView);
        speedSeekBar = (SeekBar) rootView.findViewById(R.id.speedSeekBar);
        pitchSeekBar = (SeekBar) rootView.findViewById(R.id.pitchSeekBar);
        scrollView = (ScrollView) rootView.findViewById(R.id.scrollView);
        playButton = (ImageButton) rootView.findViewById(R.id.playImageButton);
        backwardButton = (ImageButton) rootView.findViewById(R.id.backwardImageButton);
        forwardButton = (ImageButton) rootView.findViewById(R.id.forwardImageButton);

        df = new DecimalFormat("0.00");
        tts = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS) {
                    tts.setLanguage(Locale.ENGLISH);
                    tts.speak("Category is " + topic.getHeader() + "and topic is " + topic.getTopic(), TextToSpeech.QUEUE_FLUSH, null);
                    while(!tts.isSpeaking()) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    initialTextView.setVisibility(View.INVISIBLE);
                }
            }
        });

        speedSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float speedValue = Float.parseFloat(df.format((float) (Math.pow(2, (double) progress/50)/2)));
                speedTextView.setText(String.valueOf(speedValue));
                tts.setSpeechRate(speedValue);
            }
            public void onStartTrackingTouch(SeekBar seekBar) {}
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        pitchSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                float pitchValue = Float.parseFloat(df.format((float) (Math.pow(2, (double) progress/50)/2)));
                pitchTextView.setText(String.valueOf(pitchValue));
                tts.setPitch(pitchValue);
            }
            public void onStartTrackingTouch(SeekBar seekBar) {}
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        try {
            conversations = new ConversationQuery().queryByTopic(getContext(), this.topic);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return inflater.inflate(R.layout.fragment_conversation, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Toast.makeText(context, "Conversation Fragment", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    private class PlayTTSTask extends AsyncTask<ArrayList<Conversation>, String, Void>
    {
        @Override
        protected Void doInBackground(ArrayList<Conversation>... conversations) {
            Log.i("Do In Background", "Initiated");
            while(isPlaying && !tts.isSpeaking()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (isQuestion) {
                    Log.i("Do In Background", "isPlaying & !tts.isSpeaking & isQuestion");
                    tts.speak(conversations[0].get(playingAt).getQuestion(), TextToSpeech.QUEUE_FLUSH, null);
                    publishProgress(conversations[0].get(playingAt).getQuestion());
                } else {
                    Log.i("Do In Background", "isPlaying & !tts.isSpeaking & !isQuestion");
                    tts.speak(conversations[0].get(playingAt).getAnswer(), TextToSpeech.QUEUE_FLUSH, null);
                    publishProgress(conversations[0].get(playingAt).getAnswer());
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            TextView textTextView = new TextView(getContext());
            if (isQuestion) {
                textTextView.setText("Question\t" + (playingAt + 1) + "\t:\t" + values[0] + "\n");
                isQuestion = false;
            } else {
                textTextView.setText("Answer\t" + (playingAt + 1) + "\t:\t" + values[0] + "\n");
                isQuestion = true;
                playingAt++;
            }
            scrollView.fullScroll(View.FOCUS_DOWN);
            textTextView.setTextColor(Color.BLACK);
            textLayout.addView(textTextView);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            isFinished = true;
            playButton.setBackgroundResource(R.drawable.play);
            isPlaying = false;
        }
    }

}
