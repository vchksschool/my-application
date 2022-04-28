package com.mtc.top5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

public class AnswersActivity extends AppCompatActivity {
    private Toolbar toolb;
    private RecyclerView ansview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answers);
        ansview =findViewById(R.id.anstest_recycler_view);
        toolb = findViewById(R.id.anstoolbar);
        setSupportActionBar(toolb);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setTitle("Answers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        ansview.setLayoutManager(layoutManager);

        AnswersAdapter adapter = new AnswersAdapter(DBqueries.g_quesList);
        ansview.setAdapter(adapter);//this line shows the answers list
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //back button finishes activity

        if(item.getItemId() == android.R.id.home);{
            AnswersActivity.this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}