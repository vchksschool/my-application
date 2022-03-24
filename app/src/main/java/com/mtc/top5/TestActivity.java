package com.mtc.top5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
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
        MyThread.g_selected_cat_index = cat_index;
        System.out.println(cat_index);
        getSupportActionBar().setTitle(catList.get(cat_index).getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        testView = findViewById(R.id.test_recycler_view);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        testView.setLayoutManager(layoutManager);

        loadTestData(cat_index);


        TestAdapter adapter = new TestAdapter(testList, cat_index);

        testView.setAdapter(adapter);
    }



    private void loadTestData(int cat_index)
    {
        testList = new ArrayList<>();

        MyThread.currentCatNumDB = MyThread.catList.get(cat_index).getCatNumDB();
        MyThread.setj();
        String looplength = Integer.toString(MyThread.j);
        System.out.println(looplength+"before");
        looplength = looplength.substring(0,1);
        //fix this
        int k = Integer.valueOf(looplength);

        System.out.println(Integer.toString(k)+ "here");
        for (int i = 0; i<k; i++){
            testList.add(new TestModel(Integer.toString((i+1)), 50, (i+1)*10));

        }




    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home);{
            TestActivity.this.finish();
        }

        return super.onOptionsItemSelected(item);
    }
}