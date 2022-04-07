package com.mtc.top5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class StartQuizActivity2 extends AppCompatActivity {
    private TextView catName, testNo, totalQ, bestScore, time;
    private Button startTestB;
    private ImageView backB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_quiz2);

        init();
        String q1 = Integer.toString(MyThread.NoOfQues);
        q1 = q1.substring(0,1);
        testNo.setText("Quiz. " + q1 );
        totalQ.setText(String.valueOf(MyThread.NoOfQues));
        time.setText(String.valueOf(MyThread.NoOfQues));
        bestScore.setText(String.valueOf(MyThread.myPerformance.getScore()));
    }


    private void init(){
        catName = findViewById(R.id.st_cat_name);
        testNo = findViewById(R.id.st_test_no);
        totalQ = findViewById(R.id.st_total_ques);
        bestScore = findViewById(R.id.st_best_score);
        time = findViewById(R.id.st_time);
        startTestB = findViewById(R.id.start_testB);

        backB = findViewById(R.id.st_backB);

     backB.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             StartQuizActivity2.this.finish();
         }
     });

     startTestB.setOnClickListener((new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Intent intent = new Intent(StartQuizActivity2.this, QuestionsActivity.class);
             startActivity(intent);
             finish();
         }
     }));

    }
}