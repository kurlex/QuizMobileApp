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

public class Result extends AppCompatActivity {
    private TextView mGreetingTextView,scoreText;
    private Button replay;
    private String username;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        mGreetingTextView = findViewById(R.id.main_textview_greeting);
        scoreText = findViewById(R.id.score);
        replay = findViewById(R.id.replay);
        Intent intent = getIntent();
        username = intent.getStringExtra("userName").toString();
        int score = intent.getIntExtra("score" , 0);
        mGreetingTextView.setText("Congrats "+username+", you finished the game!");
        scoreText.setText("Your score : "+score);

        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomePage();
                finish();
            }
        });

    }
    public void openHomePage(){
        Intent intent = new Intent(this, Quiz.class);
        intent.putExtra(MainActivity.EXTRA_TEXT, username);
        startActivity(intent);
    }
}