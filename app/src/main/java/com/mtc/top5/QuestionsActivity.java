package com.mtc.top5;

import static com.mtc.top5.MyThread.catList;
import static com.mtc.top5.MyThread.g_quesList;
import static com.mtc.top5.MyThread.g_selected_cat_index;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class QuestionsActivity extends AppCompatActivity {
    private RecyclerView questionsView;
    private TextView tvQuesID, timerTV, catNameTV;
    private Button submitB,markB, clearSelB;
    private ImageButton prevQuesB, nextQuesB;
    private ImageView quesListB;
    private int quesID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        //what is number + correctans + in the list. check if the answer selected is in the correct position that corresponds to the correctans position


        init();
        QuestionsAdapter quesAdapter = new QuestionsAdapter(g_quesList);
        questionsView.setAdapter(quesAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        questionsView.setLayoutManager(layoutManager);//allows the recycler view to actually be seen


        setSnapHelper();//makes sure that pages are dragged to one at a time and that the view cant be held between two pages
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

}

