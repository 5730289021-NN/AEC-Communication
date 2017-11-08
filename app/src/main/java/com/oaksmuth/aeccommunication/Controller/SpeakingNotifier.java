package com.oaksmuth.aeccommunication.Controller;

import android.speech.tts.UtteranceProgressListener;
import android.support.v4.app.Fragment;

import com.oaksmuth.aeccommunication.View.QAFragment;

/**
 * Created by Norawit Nangsue on 11/8/2017.
 * SpeakingNotifier is used for notifying QAFragment in order to speak next conversation
 */

public class SpeakingNotifier extends UtteranceProgressListener{

    private OnSpeakingFinishedListener mCallback;

    public SpeakingNotifier(QAFragment fragment){
        mCallback = fragment;
    }

    @Override
    public void onDone(String utteranceId) {
        mCallback.onSpeakingFinished();
    }
    @Override
    public void onStart(String utteranceId) {}
    @Override
    public void onError(String utteranceId) {}

    public interface OnSpeakingFinishedListener
    {
        void onSpeakingFinished();
    }
}
