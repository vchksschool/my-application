package com.mtc.top5;

import static com.mtc.top5.DBqueries.ANSWERED;
import static com.mtc.top5.DBqueries.NOT_VISITED;
import static com.mtc.top5.DBqueries.REVIEW;
import static com.mtc.top5.DBqueries.UNANSWERED;
import static com.mtc.top5.DBqueries.catList;
import static com.mtc.top5.DBqueries.g_quesList;
import static com.mtc.top5.DBqueries.g_selected_cat_index;
import static com.mtc.top5.DBqueries.g_selected_quiz_index;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
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
    private GridQuestionAdapter gridAdapter;
    private DrawerLayout draw;
    private ImageButton drawcloseb;
    private GridView qlistGV;
    private ImageView mrkIMG;
    public  static ArrayList<String> qlistcorrect;
    public static ArrayList<ArrayList<String>> textviewlistOfLists =new ArrayList<ArrayList<String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.questions_grid_layout);


        init();
        for(int i = 0; i<g_quesList.size(); i++)
        {
            //adds in all questions to textview list (even unanswered ones)
            QuestionModel qlist =g_quesList.get(i);

            qlistcorrect = new ArrayList<String>();

            qlistcorrect.add(qlist.getOptionA());
            qlistcorrect.add(qlist.getOptionB());
            qlistcorrect.add(qlist.getOptionC());
            qlistcorrect.add(qlist.getOptionD());
            qlistcorrect.add(qlist.getOptionE());
            textviewlistOfLists.add(qlistcorrect);

        }

        quesAdapter = new QuestionsAdapter(g_quesList);
        questionsView.setAdapter(quesAdapter);



        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        questionsView.setLayoutManager(layoutManager);
        gridAdapter = new GridQuestionAdapter(this, g_quesList.size());//passing size of list to grid adapter
        qlistGV.setAdapter(gridAdapter);//setting adapter to grid view


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
        mrkIMG = findViewById(R.id.markimg);
        qlistGV = findViewById(R.id.queslistGV);
        quesID = 0;
        draw = findViewById(R.id.drawer_layout);
        drawcloseb = findViewById(R.id.drawcloseB);
        tvQuesID.setText("1/"+String.valueOf(g_quesList.size()));
        catNameTV.setText(catList.get(g_selected_cat_index).getName());
        g_quesList.get(0).setStatus(UNANSWERED); //SOLUTION TO FIRST FAILED TEST(SETS FIRST QUESTION UNANSWERED INSTEAD F NOT VISITED)
    }

    private void setSnapHelper()
    {
        SnapHelper snapHelper = new PagerSnapHelper();//pagersnap helper is better than linear snap helper in this case
        snapHelper.attachToRecyclerView(questionsView);

        questionsView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //CHANGES ON EACH SCROLL TO NEXT QUESTION
                View view = snapHelper.findSnapView(recyclerView.getLayoutManager());
                quesID = recyclerView.getLayoutManager().getPosition(view);
                if(g_quesList.get(quesID).getStatus()== NOT_VISITED)
                {
                    //If question wasnt visited before ths then the questions status is set to unanswered
                    g_quesList.get(quesID).setStatus(UNANSWERED);
                }
                //BELOW IF-ELSE MAKES SURE REVIEW ICON DOESNT STAY WHEN SCROLLING TO NEXT QUESTION

                if (g_quesList.get(quesID).getStatus() == REVIEW)
                {
                    mrkIMG.setVisibility(View.VISIBLE);

                }
                else
                {
                    mrkIMG.setVisibility(View.GONE);
                }
                tvQuesID.setText(String.valueOf(quesID+1+  "/"+ String.valueOf(g_quesList.size()))) ;
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
                g_quesList.get(quesID).setStatus(UNANSWERED);
                mrkIMG.setVisibility(View.GONE);
                quesAdapter.notifyDataSetChanged();//tells the recylEr view that its data set has been changed so it will reinitiate after that
            }
        });



        submitB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitTest();
            }
        });
        quesListB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //checking if draw is already open
                if(!draw.isDrawerOpen(GravityCompat.END))
                {
                    //open drawer
                    gridAdapter.notifyDataSetChanged();
                    draw.openDrawer(GravityCompat.END);
                }
            }
        });
        drawcloseb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(draw.isDrawerOpen(GravityCompat.END))
                {
                    //Closes drawer if close button clicked and drawer is open
                    draw.closeDrawer(GravityCompat.END);
                }
            }
        });
        markB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mrkIMG.getVisibility() != View.VISIBLE)
                {
                    mrkIMG.setVisibility(View.VISIBLE);
                    g_quesList.get(quesID).setStatus(REVIEW);
                }
                else
                {
                    //if already visible, unmark question
                    mrkIMG.setVisibility(View.GONE);

                    if (g_quesList.get(quesID).getSelectedAns() != -1)
                    {
                        //IF QUESTIONS ANSWERED
                        g_quesList.get(quesID).setStatus(ANSWERED);
                    }
                    else
                    {
                        g_quesList.get(quesID).setStatus(UNANSWERED);
                    }
                }
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
    public void goToQ(int position)
    {
        //GOES TO SPECIFIED QUESTION POSITION
        questionsView.smoothScrollToPosition(position);

        if(draw.isDrawerOpen(GravityCompat.END))
        {
            //CLOSES DRAWER IF OPEN
            draw.closeDrawer(GravityCompat.END);
        }
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





