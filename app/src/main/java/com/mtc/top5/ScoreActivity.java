package com.mtc.top5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ScoreActivity extends AppCompatActivity {

    private TextView scoreTV,timeTV, totalQTV, correctQTV, wrongQTV, unattemptedQTV;
    private Button leaderB, reAttemptB, viewAnsB;
    private long timeTaken;
    private Dialog progressDialog;
    public  int score;
    public MyCompleteListener completeListener;

    private TextView dialogText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        //List<CategoryModel>catList = MyThread.getcurrentFinalCat();

        getSupportActionBar().setTitle("Result");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new Dialog(ScoreActivity.this);
        progressDialog.setContentView(R.layout.dialog_layout);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogText = progressDialog.findViewById(R.id.dialog_text);
        dialogText.setText("Loading...");
        progressDialog.show();
        init();


        loadData();
        QuestionsAdapter.orderedlistOfLists.clear();
        QuestionsAdapter.textviewlistOfLists.clear();
        //do:leaderboard button

        viewAnsB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        reAttemptB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reAttempt();
            }
        });
        saveResult();
    }
    private void init()
    {
        scoreTV = findViewById(R.id.score);
        timeTV = findViewById(R.id.time);
        totalQTV = findViewById(R.id.totalQ);
        correctQTV= findViewById(R.id.correctQ);
        wrongQTV = findViewById(R.id.wrongQ);
        unattemptedQTV = findViewById(R.id.un_attemptedQ);
        leaderB= findViewById(R.id.leaderB);
        reAttemptB = findViewById(R.id.reattemptB);
        viewAnsB = findViewById(R.id.view_answerB);

    }

    private void loadData()
    {
        int correctQ = 0, wrongQ= 0, unattemptQ = 0;
        for( int i = 0; i < MyThread.g_quesList.size();i++)
        {
            if(MyThread.g_quesList.get(i).getSelectedAns() == -1)
            {
                //System.out.println(MyThread.g_quesList.get(i).getQuestion());
                //System.out.println(MyThread.g_quesList.get(i).getSelectedAns());
                unattemptQ ++;
            }
            else
            {//create two new arrays each time in questions adapter. one array will contain all the answers in order and one will be randomised.
                //set the randomised array on to the screen.
                //create a dictionary where each option_num is paired with an option type. e.g option_num = 2= b
                //create  5 if statements for each randomans possibility.
                //if randomans = 2:
                    //optionchoice = b
                    // if RandomArray[option_num-1] == Ordered array[randomans]
                            //correctQ++;
                //System.out.println("cleared");
                //System.out.println(QuestionsAdapter.textviewlistOfLists.get(i));
                //System.out.println(QuestionsAdapter.orderedlistOfLists.get(i));
                //System.out.println(QuestionsAdapter.currentoption);
                if(MyThread.g_quesList.get(i).getCorrectAns() == 1)
                {
                    if (QuestionsAdapter.textviewlistOfLists.get(i).get(MyThread.g_quesList.get(i).getSelectedAns()-1)==QuestionsAdapter.orderedlistOfLists.get(i).get(MyThread.g_quesList.get(i).getCorrectAns()-1))
                    {
                        correctQ++;
                        System.out.println("right");
                        System.out.println(QuestionsAdapter.orderedlistOfLists.get(i));
                        System.out.println(QuestionsAdapter.currentoption);

                    }
                    else
                    {
                        wrongQ++;
                        System.out.println("wrong");
                        System.out.println(QuestionsAdapter.orderedlistOfLists.get(i));
                        System.out.println(QuestionsAdapter.currentoption);
                    }
                }
                if(MyThread.g_quesList.get(i).getCorrectAns() == 2)
                {
                    if (QuestionsAdapter.textviewlistOfLists.get(i).get(MyThread.g_quesList.get(i).getSelectedAns()-1)==QuestionsAdapter.orderedlistOfLists.get(i).get(MyThread.g_quesList.get(i).getCorrectAns()-1))
                    {
                        correctQ++;
                        System.out.println("right");
                        System.out.println(QuestionsAdapter.orderedlistOfLists.get(i));
                        System.out.println(QuestionsAdapter.currentoption);
                    }
                    else
                    {
                        wrongQ++;
                        System.out.println("wrong");
                        System.out.println(QuestionsAdapter.orderedlistOfLists.get(i));
                        System.out.println(QuestionsAdapter.currentoption);
                    }
                }
                if(MyThread.g_quesList.get(i).getCorrectAns() == 3)
                {
                    if (QuestionsAdapter.textviewlistOfLists.get(i).get(MyThread.g_quesList.get(i).getSelectedAns()-1)==QuestionsAdapter.orderedlistOfLists.get(i).get(MyThread.g_quesList.get(i).getCorrectAns()-1))
                    {
                        correctQ++;
                        System.out.println("right");
                        System.out.println(QuestionsAdapter.orderedlistOfLists.get(i));
                        System.out.println(QuestionsAdapter.currentoption);
                    }
                    else
                    {
                        wrongQ++;
                        System.out.println("wrong");
                        System.out.println(QuestionsAdapter.orderedlistOfLists.get(i));
                        System.out.println(QuestionsAdapter.currentoption);
                    }
                }
                if(MyThread.g_quesList.get(i).getCorrectAns() == 4)
                {
                    if (QuestionsAdapter.textviewlistOfLists.get(i).get(MyThread.g_quesList.get(i).getSelectedAns()-1)==QuestionsAdapter.orderedlistOfLists.get(i).get(MyThread.g_quesList.get(i).getCorrectAns()-1))
                    {
                        correctQ++;
                        System.out.println("right");
                        System.out.println(QuestionsAdapter.orderedlistOfLists.get(i));
                        System.out.println(QuestionsAdapter.currentoption);
                    }
                    else
                    {
                        wrongQ++;
                        System.out.println("wrong");
                        System.out.println(QuestionsAdapter.orderedlistOfLists.get(i));
                        System.out.println(QuestionsAdapter.currentoption);
                    }
                }
                if(MyThread.g_quesList.get(i).getCorrectAns() == 5)
                {
                    if (QuestionsAdapter.textviewlistOfLists.get(i).get(MyThread.g_quesList.get(i).getSelectedAns()-1)==QuestionsAdapter.orderedlistOfLists.get(i).get(MyThread.g_quesList.get(i).getCorrectAns()-1))
                    {
                        correctQ++;
                        System.out.println("right");
                        System.out.println(QuestionsAdapter.orderedlistOfLists.get(i));
                        System.out.println(QuestionsAdapter.currentoption);
                    }
                    else
                    {
                        wrongQ++;
                        System.out.println("wrong");
                        System.out.println(QuestionsAdapter.orderedlistOfLists.get(i));
                        System.out.println(QuestionsAdapter.currentoption);
                    }
                }
                /*if (MyThread.g_quesList.get(i).getSelectedAns() == MyThread.g_quesList.get(i).getCorrectAns())
                {
                    correctQ++;
                }
                else
                {
                    wrongQ++;

                }*/
            }
        }
        score = correctQ;
        correctQTV.setText(String.valueOf(correctQ));
        wrongQTV.setText(String.valueOf(wrongQ));
        unattemptedQTV.setText(String.valueOf(unattemptQ));
        totalQTV.setText(String.valueOf(MyThread.g_quesList.size()));

        //int finalScore = (correctQ * 100)/MyThread.g_quesList.size();
        int finalScore = correctQ;
        scoreTV.setText(String.valueOf(finalScore));
        timeTaken = getIntent().getLongExtra("TIME_TAKEN", 0);//THIS IS IN MILLISECONDS
        String time = String.format("%02d : %02d min",
                TimeUnit.MILLISECONDS.toMinutes(timeTaken),
                TimeUnit.MILLISECONDS.toSeconds(timeTaken)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeTaken)));

        timeTV.setText(time);
    }
    private void  reAttempt()
    {
        for (int i =0; i < MyThread.g_quesList.size(); i++)
        {
            MyThread.g_quesList.get(i).setSelectedAns(-1);
            MyThread.g_quesList.get(i).setStatus(MyThread.NOT_VISITED);
        }
        Intent intent = new Intent(ScoreActivity.this,StartQuizActivity.class);
        startActivity(intent);
        finish();;
    }
    private void saveResult()
    {
        MyThread.saveResult(score,this, new MyCompleteListener() {
            @Override
            public void onSuccess() {
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(){
                Toast.makeText(ScoreActivity.this, "Something went wrong",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });
        progressDialog.dismiss();
    }
}