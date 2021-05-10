package com.company.mathgame;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;
import java.util.Random;

public class Game extends AppCompatActivity {

    private static final long START_TIMER_IN_MILLIS = 60000;
    TextView score, life, time, question;
    EditText answer;
    Button ok, next;

    Random random = new Random();
    int number1, number2, userAnswer, realAnswer;
    int userScore = 0, userLife = 3;

    CountDownTimer timer;
    Boolean timer_running;
    long time_left_in_millis = START_TIMER_IN_MILLIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        score = findViewById(R.id.tv_score);
        life = findViewById(R.id.tv_life);
        time = findViewById(R.id.tv_time);
        question = findViewById(R.id.tv_question);
        answer = findViewById(R.id.et_answer);
        ok = findViewById(R.id.btn_ok);
        next = findViewById(R.id.btn_next);

        gameContinue();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
                if (answer.getText().toString().trim().length() != 0){
                    userAnswer = Integer.parseInt(answer.getText().toString());
                    if (userAnswer == realAnswer) {
                        userScore += 10;
                        score.setText("" + userScore);
                        question.setText("Congratulations, your answer is true.");
                    } else {
                        userLife -= 1;
                        life.setText("" + userLife);
                        question.setText("Sorry, your answer is false.");
                    }
                } else {
                    userLife -= 1;
                    life.setText("" + userLife);
                    question.setText("Sorry, No Answer.");
                    //v.setClickable(false);
                    v.setEnabled(false);
                }

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answer.setText("");
                resetTimer();

                if (userLife <= 0) {
                    Toast.makeText(getApplicationContext(), "Game Over", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Game.this, Result.class);
                    intent.putExtra("score", userScore);
                    startActivity(intent);
                    finish();
                } else {
                    gameContinue();
                    ok.setEnabled(true);
                }
            }
        });
        answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ok.setEnabled(true);
                answer.setText("");
            }
        });

    }

    public void gameContinue() {
        final int min = 10;
        final int max = 100;
        number1 = random.nextInt(max - min) + min;
        number2 = random.nextInt(max - min) + min;
        realAnswer = number1 + number2;
        question.setText(number1 + " + " + number2);
        startTime();
        ok.setEnabled(true);
    }

    public void startTime() {
        timer = new CountDownTimer(time_left_in_millis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time_left_in_millis = millisUntilFinished;
                updateText();
            }

            @Override
            public void onFinish() {
                timer_running = false;
                pauseTimer();
                resetTimer();
                updateText();
                userLife -= 1;
                life.setText("" + userLife);
                question.setText("Sorry! Time is over!");
            }
        }.start();
        timer_running = true;
    }

    public void updateText() {
        int second = (int) (time_left_in_millis / 1000) % 60;
        String time_left = String.format(Locale.getDefault(), "%02d", second);
        time.setText(time_left);
    }

    public void pauseTimer() {
        timer.cancel();
        timer_running = false;
    }

    public void resetTimer() {
        time_left_in_millis = START_TIMER_IN_MILLIS;
        updateText();
    }
}