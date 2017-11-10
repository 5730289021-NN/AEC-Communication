package com.oaksmuth.aeccommunication.Controller;

import android.speech.tts.TextToSpeech;

/**
 * Created by noraw on 11/9/2017.
 */

public interface SpeakerWithView extends TextToSpeech.OnInitListener {
    void onSpeakWithViewAdd(String sentence);
    void onViewAdd(String sentence);
}
