package com.mtc.top5;

import static com.mtc.top5.DBqueries.catList;
import static com.mtc.top5.DBqueries.g_quesList;
import static com.mtc.top5.DBqueries.g_selected_cat_index;
import static com.mtc.top5.DBqueries.g_selected_quiz_index;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class QuestionsActivity extends AppCompatActivity{//ned to implement on touch listener if you want drag

    private RecyclerView questionsView;
    private TextView tvQuesID, timerTV, catNameTV;
    private Button submitB,markB, clearSelB;
    private ImageButton prevQuesB, nextQuesB;
    private ImageView quesListB;
    private int quesID;
    private Button btn;
    private final static int START_DRAGGING = 0;
    private final static int STOP_DRAGGING = 1;
    private int status;
    private QuestionsAdapter quesAdapter;
    private CountDownTimer timer;
    private long timeLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        //what is number + correctans + in the list. check if the answer selected is in the correct position that corresponds to the correctans position


        init();


        quesAdapter = new QuestionsAdapter(g_quesList);
        questionsView.setAdapter(quesAdapter);



        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        questionsView.setLayoutManager(layoutManager);

        //btn.setDrawingCacheEnabled(true);
        //btn.setOnTouchListener(questionsView);
//allows the recycler view to actually be seen

        //

        /*ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(questionsView);
        */

        setSnapHelper();//makes sure that pages are dragged to one at a time and that the view cant be held between two pages
        setClickListeners();//sets back and next buttons
        startTimer();//starts timer
    }
    private void init(){
        questionsView = findViewById(R.id.questions_view);
        tvQuesID = findViewById(R.id.tv_quesID);
        timerTV = findViewById(R.id.tv_time);
        catNameTV = findViewById(R.id.qa_catName);
        submitB = findViewById(R.id.submitB);
        markB = findViewById(R.id.markB);
        clearSelB = findViewById(R.id.clear_selB);
        prevQuesB = findViewById(R.id.prev_quesB);
        nextQuesB = findViewById(R.id.next_quesB);
        quesListB =findViewById(R.id.ques_list_gridB);
        quesID = 0;
        tvQuesID.setText("1/"+String.valueOf(g_quesList.size()));
        catNameTV.setText(catList.get(g_selected_cat_index).getName());
    }

    private void setSnapHelper()
    {
        SnapHelper snapHelper = new PagerSnapHelper();//pagersnap helper is better than linear snap helper in this case
        snapHelper.attachToRecyclerView(questionsView);

        questionsView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                View view = snapHelper.findSnapView(recyclerView.getLayoutManager());
                quesID = recyclerView.getLayoutManager().getPosition(view);

                tvQuesID.setText(String.valueOf(quesID+1+  "/"+ String.valueOf(g_quesList.size())));
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    private void setClickListeners()
    {
        prevQuesB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(quesID > 0)
                {
                    questionsView.smoothScrollToPosition(quesID-1);//moves to previous question
                }



            }
        });
        nextQuesB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(quesID<g_quesList.size())// checks were not at the last question
                {
                    questionsView.smoothScrollToPosition(quesID+1);//moves to next
                }

            }
        });
        clearSelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                g_quesList.get(quesID).setSelectedAns(-1);
                quesAdapter.notifyDataSetChanged();//tells the recylr view that its data set has been changed so it will reinitiate after that
            }
        });



        submitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitTest();
            }
        });
    }
    private void submitTest()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(QuestionsActivity.this);
        builder.setCancelable(true);
        View view = getLayoutInflater().inflate(R.layout.alert_dialog_layout, null);
        Button cancelB = view.findViewById(R.id.cancelB);
        Button confirmB = view.findViewById(R.id.confirmB);
        builder.setView(view);
        AlertDialog alertDialog = builder.create();
        cancelB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        confirmB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.cancel();;
                alertDialog.dismiss();
                Intent intent = new Intent(QuestionsActivity.this,ScoreActivity.class);
                long totalTime = g_quesList.get(g_selected_quiz_index).getTime()*60*1000;//maybe error here
                intent.putExtra("TIME_TAKEN", totalTime-timeLeft);
                startActivity(intent);
                QuestionsActivity.this.finish();

            }
        });

        alertDialog.show();
    }
    private void  startTimer()
    {
        long totaltime = g_quesList.get(g_selected_quiz_index).getTime()*60*1000;
        timer = new CountDownTimer(totaltime + 1000, 1000) {
            @Override
            public void onTick(long remainingTime) {
                timeLeft = remainingTime;
                String time = String.format("%02d : %02d min",
                        TimeUnit.MILLISECONDS.toMinutes(remainingTime),
                        TimeUnit.MILLISECONDS.toSeconds(remainingTime)-TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(remainingTime)));
                //2 decimal points for minutes and 2 decimal points for seconds

                timerTV.setText(time);

            }

            @Override
            public void onFinish() {
                //do results activity
                Intent intent = new Intent(QuestionsActivity.this,ScoreActivity.class);
                long totalTime = g_quesList.get(g_selected_quiz_index).getTime()*60*1000;//maybe error here
                intent.putExtra("TIME_TAKEN", totalTime-timeLeft);
                startActivity(intent);
                QuestionsActivity.this.finish();
            }
        };
        timer.start();
    }











/*    @Override
    public boolean onTouch(View view, MotionEvent me) {
        if (me.getAction() == MotionEvent.ACTION_DOWN) {
            status = START_DRAGGING;


        }
        if (me.getAction() == MotionEvent.ACTION_UP) {
            status = STOP_DRAGGING;
            Log.i("Drag", "Stopped Dragging");
        } else if (me.getAction() == MotionEvent.ACTION_MOVE) {
            if (status == START_DRAGGING) {
                System.out.println("Dragging");

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        50, 50);
                layoutParams.setMargins((int) me.getRawX() - 25,
                        (int) me.getRawY() - 50, 0, 0);
                questionsView.removeView(btn);
                questionsView.addView(btn, layoutParams);

                btn.invalidate();
            }
        }
        return false;
    }*/



    /*
    ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP|ItemTouchHelper.DOWN|ItemTouchHelper.START|ItemTouchHelper.END, 0) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {


        }
    };
    */

}





