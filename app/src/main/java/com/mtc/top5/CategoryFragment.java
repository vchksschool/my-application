package com.mtc.top5;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.Barrier;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class CategoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    // TODO: Rename and change types of parameters
    private String mParam1;
    private Context mContext;
    private String mParam2;
    private static String URL = "http://51.38.80.233/victory/categories.php";
    public List<String> newlist = new ArrayList<>();
    public CategoryFragment() {
        // Required empty public constructor
    }
    private GridView catView;
    private List<CategoryModel> catList = new ArrayList<>();

    public Context getCont(){
        return mContext;
    }
    public List<CategoryModel> getCatlist(){
        return catList;
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            mContext = getContext();
            RequestQueue requestQueue = Volley.newRequestQueue(mContext);
            catList.add(new CategoryModel("1","before", 2));

            System.out.println(Thread.currentThread().getName());

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest( "http://51.38.80.233/victory/categories.php", new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    System.out.println("array got");
                    for (int i = 0; i < 5; i++) {
                        try {


                            JSONObject categories = response.getJSONObject(i);


                            String catname = categories.getString("category_name").toString();
                            catList.add(new CategoryModel(Integer.toString(i),catname, 2));




                        } catch (JSONException e) {
                            System.out.println("nah didnt work");


                            e.printStackTrace();
                        }



                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    System.out.println("Volley error");

                }
            });
            requestQueue.add(jsonArrayRequest);
        }

    };
    public void LoadList(){
        // you can use whatever List implementation you want here




    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //Thread thread = new Thread(runnable);
        //thread.start();
        final CountDownLatch latch = new CountDownLatch(1);
        mContext = getContext();


        MyThread ak = new MyThread(latch, mContext);


        catList.clear();
        catList = ak.getFinalCat();
        System.out.println(Thread.currentThread().getName() + " done."+ "AFTER CATLIST CALLED"+ catList);
        View view = inflater.inflate(R.layout.fragment_category, container, false); //attach to root is false, therefore the layout from fragment_category is inflated and returned as view

        catView = view.findViewById(R.id.cat_Grid);


        //loadCategories();


        CategoryAdapter adapter = new CategoryAdapter(catList);

        catView.setAdapter(adapter);
        return view;
    }
    private void loadCategories(){
        mContext = getContext();
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        catList.add(new CategoryModel("1","before", 2));

        System.out.println(Thread.currentThread().getName());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest( "http://51.38.80.233/victory/categories.php", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println("array got");
                for (int i = 0; i < 5; i++) {
                    try {


                        JSONObject categories = response.getJSONObject(i);


                        String catname = categories.getString("category_name").toString();
                        catList.add(new CategoryModel(Integer.toString(i),catname, 2));




                    } catch (JSONException e) {
                        System.out.println("nah didnt work");


                        e.printStackTrace();
                    }


                }
                try{

                    System.out.println(Thread.currentThread().getName());
                    Thread.currentThread().join();


                } catch(InterruptedException e) {
                    System.out.println("got interrupted!");
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Volley error");

            }
        });
        requestQueue.add(jsonArrayRequest);




    }
}