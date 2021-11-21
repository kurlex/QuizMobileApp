package com.azizmelek.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_TEXT = "com.azizmelek.android.EXTRA_TEXT";
    private TextView mGreetingTextView;
    private EditText mNameEditText;
    private Button mPlayButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mGreetingTextView = findViewById(R.id.main_textview_greeting);
        mNameEditText = findViewById(R.id.username);
        mPlayButton = findViewById(R.id.main_button_play);
        mPlayButton.setEnabled(false);

        mNameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mPlayButton.setEnabled(!s.toString().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This is where we'll check the user input
            }
        });

        mPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomePage();
            }
        });

    }
    public void openHomePage(){
        EditText userName = (EditText) findViewById(R.id.username);
        String username = userName.getText().toString();
        Intent intent = new Intent(this, Quiz.class);
        intent.putExtra(EXTRA_TEXT, username);
        startActivity(intent);
    }



}