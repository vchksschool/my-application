package com.mtc.top5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;

import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;


public class TestActivity extends AppCompatActivity {

    private RecyclerView testView;
    private Toolbar toolbar;
    private List<TestModel> testList;
    private Context mContext;
    public  int NoOfQuestions;

    private List<CategoryModel> catList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        final CountDownLatch latch = new CountDownLatch(1);
        mContext = this;
        MyThread a = new MyThread(latch, mContext);
        catList = a.getcurrentFinalCat();

        int cat_index = getIntent().getIntExtra("CAT_INDEX", 0);
        getSupportActionBar().setTitle(catList.get(cat_index).getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        testView = findViewById(R.id.test_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        testView.setLayoutManager(layoutManager);

        loadTestData();

        TestAdapter adapter = new TestAdapter(testList);
        testView.setAdapter(adapter);
    }

    private void loadTestData()
    {
        testList = new ArrayList<>();
        testList.add(new TestModel("1", 50, 10));
        testList.add(new TestModel("2", 50, 20));
        testList.add(new TestModel("3", 50, 30));
        testList.add(new TestModel("4", 50, 40));


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home);{
            TestActivity.this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}