package com.azizmelek.android;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Quiz extends AppCompatActivity {
    public static final String USER_NAME_QUIZ = "Quiz.userName";
    private final String[] questions = {
            "What's the biggest animal in the world?",
            "Which country is brie cheese originally from?",
            "What is the capital of Iceland?",
            "Which planet is closest to the sun?",
            "How many valves does the heart have?",
            "Which city had the first ever fashion week?"
    };
    private final String [][] opt = {
            {"The blue whale", "Elephent","Ant", "Mouse"},
            {"France","Sweden","Netherland","brazil"},
            {"Reykjavík","Paris","washington","Tunis"},
            {"Mercury","Mars","Earth","Jupiter"},
            {"Four","Three","Seven","Five"},
            {"New York","Paris","Tunis","Berlin"}
    };
    private final Integer[] indexes = { 0, 1, 2, 3};
    private final Integer[] indexesG = { 0, 1, 2, 3,4,5};
    private final List<Integer> interG = Arrays.asList(indexesG);
    private int displayedQuestions, Bonus;
    private final Random rand = new Random();
    private int scoreVal,indx;
    private boolean uncounted;
    private String username;
    Button[] opts;
    private Button next;
    TextView score,userName,numQuestions,question;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        opts  = new Button[]{findViewById(R.id.opt1), findViewById(R.id.opt2), findViewById(R.id.opt3), findViewById(R.id.opt4)};
        userName = findViewById(R.id.username);
        score = findViewById(R.id.score);
        numQuestions = findViewById(R.id.QuestionNum);
        question = findViewById(R.id.Question);
        next = findViewById(R.id.next);
        next.setEnabled(false);
        uncounted = false;
        Bonus = rand.nextInt(4);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //loading the next question
                indx = interG.get(displayedQuestions);
                //if we the users finished 5 questions we should outputs his score
                if(displayedQuestions == 5){
                    //display score
                    openHomePage();
                    //finish();
                }
                else
                    nextQuery();
            }
        });

        //get the intent
        Intent intent = getIntent();

        //gets the username from the previous activity
        username = intent.getStringExtra(MainActivity.EXTRA_TEXT);
        userName.setText("Welcome!, "+ username);

        //counts the number of displayed questions
        displayedQuestions = 0;


        //index of the current displayed question
        indx = interG.get(displayedQuestions);

        //user's score
        scoreVal = 0;
        Collections.shuffle(interG);
        //initialize the behaviour of each button
        for(int i = 0;i<4;i++) {
            int finalI = i;
            opts[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //checks whether the selected button is the answer
                    if(opt[indx][0].trim().equals(opts[finalI].getText().toString().trim())){
                        scoreVal += 5;
                        opts[finalI].setBackgroundTintList(getResources().getColorStateList(R.color.green));
                        //if the users answers correctly a bonus question the wheel should be displayed
                        if(Bonus+1 == displayedQuestions){
                            //load spin wheel
                            Intent intent1 =  new Intent(v.getContext(), homePage.class);
                            startActivityForResult(intent1 , 200);
                        }
                    }else{
                        //the score can't be negative! maybe...
                        if(uncounted==true){
                            Toast.makeText(Quiz.this, "your answer isn't counted this time", Toast.LENGTH_SHORT).show();
                            uncounted = false;
                        }else
                            scoreVal = Math.max(scoreVal - 5, 0);
                        opts[finalI].setBackgroundTintList(getResources().getColorStateList(R.color.red));
                    }
                    //disable the button
                    for(int j = 0;j<4;j++)
                            opts[j].setEnabled(false);
                    score.setText("Score : "+scoreVal);
                    next.setEnabled(true);
                }
            });
        }
        nextQuery();
    }

   @SuppressLint("ResourceAsColor")
   private void nextQuery() {
        //the options must be shuffled each time
        numQuestions.setText((displayedQuestions==Bonus ? "Bonus " : "") + "Question "+(displayedQuestions+1)+"/5");
        question.setText(questions[indx]);

       List<Integer> inter = Arrays.asList(indexes);
       Collections.shuffle(inter);
        for(int i = 0;i<4;i++) {
            opts[i].setText(opt[indx][inter.get(i)]);
            //reset button's
            opts[i].setEnabled(true);
            opts[i].setBackgroundTintList(getResources().getColorStateList(R.color.purple));
        }
        displayedQuestions++;
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 200:
                if(resultCode == Activity.RESULT_OK){
                    int sector_index = data.getIntExtra("sector_index",0);
                    switch (sector_index){
                        case 0:
                            scoreVal *= 2;
                            break;
                        case 2:
                            scoreVal += scoreVal/2;
                            break;
                        case 3:
                            scoreVal += (3 * scoreVal)/4;
                            break;
                        case 5:
                            uncounted = true;
                            break;
                        case 6:
                            scoreVal += scoreVal/4;
                            break;
                    }
                }
        }
        score.setText("Score : "+scoreVal);
    }
    public void openHomePage(){
        Intent intent = new Intent(this, Result.class);
        intent.putExtra("userName", username);
        intent.putExtra("score", scoreVal);
        startActivity(intent);
    }
}