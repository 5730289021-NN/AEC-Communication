package com.oaksmuth.aeccommunication.View;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.oaksmuth.aeccommunication.Controller.ConversationQuery;
import com.oaksmuth.aeccommunication.Model.Conversation;
import com.oaksmuth.aeccommunication.Model.Topic;
import com.oaksmuth.aeccommunication.R;

public class TeachFragment extends Fragment{

    private TextInputLayout inputLayoutTopic;
    private TextInputLayout inputLayoutQuestion;
    private TextInputLayout inputLayoutAnswer;
    private EditText inputTopic;
    private EditText inputQuestion;
    private EditText inputAnswer;
    private boolean topicInputStatus;
    private boolean questionInputStatus;
    private boolean answerInputStatus;

    private Button submitButton;
    private Button restoreButton;

    public TeachFragment() {
        // Required empty public constructor
    }

    public static TeachFragment newInstance() {
        return new TeachFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_qa, container, false);
        inputLayoutTopic = (TextInputLayout) rootView.findViewById(R.id.input_topic_layout);
        inputLayoutQuestion = (TextInputLayout) rootView.findViewById(R.id.input_question_layout);
        inputLayoutAnswer = (TextInputLayout) rootView.findViewById(R.id.input_answer_layout);
        inputTopic = (EditText) rootView.findViewById(R.id.input_topic);
        inputQuestion = (EditText) rootView.findViewById(R.id.input_question);
        inputAnswer = (EditText) rootView.findViewById(R.id.input_answer);
        submitButton = (Button) rootView.findViewById(R.id.btn_submit);

        inputTopic.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (inputTopic.getText().toString().trim().isEmpty()) {
                    inputLayoutTopic.setError(getString(R.string.please_fill_topic));
                    inputTopic.requestFocus();
                    topicInputStatus = false;
                } else {
                    inputLayoutTopic.setErrorEnabled(false);
                    topicInputStatus = true;
                }
            }
        });
        inputQuestion.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (inputQuestion.getText().toString().trim().isEmpty()) {
                    inputLayoutQuestion.setError(getString(R.string.please_fill_question));
                    inputQuestion.requestFocus();
                    questionInputStatus = false;
                } else {
                    inputLayoutQuestion.setErrorEnabled(false);
                    questionInputStatus = true;
                }
            }
        });
        inputAnswer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (inputAnswer.getText().toString().trim().isEmpty()) {
                    inputLayoutAnswer.setError(getString(R.string.please_fill_answer));
                    inputAnswer.requestFocus();
                    answerInputStatus = false;
                } else {
                    inputLayoutAnswer.setErrorEnabled(false);
                    answerInputStatus = true;
                }
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(topicInputStatus && questionInputStatus && answerInputStatus)
                {
                    ConversationQuery conversationQuery = new ConversationQuery();
                    //conversationQuery.addConversation();
                }
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Toast.makeText(context,"Teach",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
