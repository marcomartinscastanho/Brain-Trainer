package com.martinscastanho.marco.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    TextView timerTextView;
    TextView operationTextView;
    TextView scoreTextView;
    GridLayout gridLayout;
    TextView resultTextView;
    Button playAgainButton;
    Integer MAX_TIME = 30*1000; //milliseconds
    Integer correctAnswers=0;
    Integer totalAnswers=0;

    Button button0;
    Button button1;
    Button button2;
    Button button3;

    Integer correctOptionId = 0;
    ArrayList<Integer> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerTextView = findViewById(R.id.timerTextView);
        operationTextView = findViewById(R.id.operationTextView);
        scoreTextView = findViewById(R.id.scoreTextView);
        gridLayout = findViewById(R.id.gridLayout);
        resultTextView = findViewById(R.id.resultTextView);
        playAgainButton = findViewById(R.id.playAgainButton);

        button0 = findViewById(R.id.button0);
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        options = new ArrayList<>();
    }

    public void goButtonClick(View view){
        Button goButton = findViewById(R.id.goButton);

        goButton.setVisibility(View.INVISIBLE);
        timerTextView.setVisibility(View.VISIBLE);
        operationTextView.setVisibility(View.VISIBLE);
        scoreTextView.setVisibility(View.VISIBLE);
        gridLayout.setVisibility(View.VISIBLE);

        setTimer();
        updateScore();
        setNumbers();
    }

    public void optionClick(View view){
        Log.d("Button clicked", view.getTag().toString());
        totalAnswers++;
        if(Integer.parseInt(view.getTag().toString()) == correctOptionId){
            resultTextView.setText("Correct!");
            correctAnswers++;
        }
        else {
            resultTextView.setText("Wrong!");
        }
        if(resultTextView.getVisibility() == View.INVISIBLE){
            resultTextView.setVisibility(View.VISIBLE);
        }
        updateScore();
        setNumbers();
    }

    public void playAgain(View view){
        correctAnswers=0;
        totalAnswers=0;
        playAgainButton.setVisibility(View.INVISIBLE);
        resultTextView.setVisibility(View.INVISIBLE);
        setButtonsEnabled(true);
        setTimer();
        updateScore();
        setNumbers();
    }

    public void setButtonsEnabled(Boolean state){
        button0.setEnabled(state);
        button1.setEnabled(state);
        button2.setEnabled(state);
        button3.setEnabled(state);
    }

    public void setTimer(){
        timerTextView.setText(String.format("%ds", MAX_TIME/1000));
        new CountDownTimer(MAX_TIME+1000, 1000){
            @Override
            public void onTick(long l) {
                timerTextView.setText(String.format("%ds", l/1000));
            }

            @Override
            public void onFinish() {
                setButtonsEnabled(false);

                resultTextView.setText("Done!");
                playAgainButton.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    public void updateScore(){
        scoreTextView.setText(String.format("%d/%d", correctAnswers, totalAnswers));
    }

    public void setNumbers(){
        Integer MAX_NUM=20;
        Random rand = new Random();
        Integer op1 = rand.nextInt(MAX_NUM+1);
        Integer op2 = rand.nextInt(MAX_NUM+1);
        Integer min = (op1 < op2) ? op1 : op2;
        operationTextView.setText(String.format("%2d + %2d", op1, op2));
        correctOptionId = rand.nextInt(4);
        options.clear();
        for(int i=0; i<4; i++){
            if(i==correctOptionId){
                options.add(op1+op2);
            }
            else{
                Integer wrongAnswer = rand.nextInt(((MAX_NUM*2) - min) + 1) + min;
                while (wrongAnswer == op1+op2 || options.contains(wrongAnswer)){
                    wrongAnswer = rand.nextInt(((MAX_NUM*2) - min) + 1) + min;
                }
                options.add(wrongAnswer);
            }
        }
        button0.setText(String.format("%d", options.get(0)));
        button1.setText(String.format("%d", options.get(1)));
        button2.setText(String.format("%d", options.get(2)));
        button3.setText(String.format("%d", options.get(3)));
    }
}
