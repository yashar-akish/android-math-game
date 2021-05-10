package com.company.mathgame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Result extends AppCompatActivity implements View.OnClickListener {

    TextView result;
    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        result = findViewById(R.id.tv_result);
        findViewById(R.id.btn_again).setOnClickListener(this);
        findViewById(R.id.btn_exite).setOnClickListener(this);

        Intent intent = getIntent();
        score = intent.getIntExtra("score", 0);
        String userScore = String.valueOf(score);
        result.setText("Your Score is : " + userScore);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_again) {
            Intent intent = new Intent(Result.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else if (v.getId() == R.id.btn_exite) {
            finish();
        }
    }
}