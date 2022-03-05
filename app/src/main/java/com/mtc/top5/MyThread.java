package com.mtc.top5;


import android.content.Context;
import android.icu.number.IntegerWidth;

import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

class MyThread extends Fragment implements Runnable{
    public static List<CategoryModel> catList;

    public static ArrayList<ArrayList<String>> listOfLists = new ArrayList<ArrayList<String>>();
    public static int qlength;
    public static Map<String, String> categoryNumber_numberOfQuestions = new HashMap<String, String>();//remember you set these values inside as a string



    CountDownLatch latch;
    private Context c;

    MyThread(CountDownLatch latch, Context context) {

        this.latch = latch;
        c = context;

    }
    public List<CategoryModel> getcurrentFinalCat(){
        return  catList;

    }

    public List<CategoryModel> getFinalCat(){
        catList.clear();
        System.out.println("CATLIST WAS JUST CLEARED");

        System.out.println(listOfLists+"before");
        Collections.shuffle(listOfLists);
        System.out.println(listOfLists+"after");
        for (int i = 0; i < 5; i++){
            ArrayList<String> category = listOfLists.get(i);

            System.out.println(category.get(1)+" ITS WORKING");
            catList.add(new CategoryModel(Integer.toString(i),category.get(1), Integer.valueOf(category.get(2))));

        }

        return catList;
    }

    @Override
    public void run() {
        CategoryFragment a = new CategoryFragment();

        catList = a.getCatlist();
        System.out.println("BEFORE REQUEST");



        //catList.add(new CategoryModel("1","before", 2));

        RequestQueue requestQueue = Volley.newRequestQueue(c);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest( "http://51.38.80.233/victory/categories.php", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println("array got");
                for (int i = 0; i < response.length(); i++) {
                    try {


                        JSONObject categories = response.getJSONObject(i);


                        String catname = categories.getString("category_name").toString();
                        ArrayList<String> list1 = new ArrayList<String>();
                        list1.add(Integer.toString(i));
                        list1.add(catname);
                        list1.add("5");
                        listOfLists.add(list1);
                        System.out.println(listOfLists);

                        System.out.println(catname);




                    } catch (JSONException e) {
                        System.out.println("nah didnt work");


                        e.printStackTrace();
                    }


                }
                System.out.println(Thread.currentThread().getName() + " completed." + catList);
                //latch.countDown();


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Volley error");

            }
        });
        requestQueue.add(jsonArrayRequest);

        System.out.println(Thread.currentThread().getName() + " completed.");


        JsonArrayRequest qrequest = new JsonArrayRequest( "http://51.38.80.233/victory/questions.php", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println("array got");
                qlength = response.length();
                System.out.println(qlength);

                /*
                int catcounter = 0
                for (int i = 0; i < response.length(); i++) {
                    try {


                        JSONObject categories = response.getJSONObject(i);


                        String category_number = categories.getString("category_number").toString();
                        if catlist[catcounter] == category_number:
                        newcounter +=1
                        once not cat _number:
                        ategoryNumber_numberOfQuestions.put(catnum,newcounter);






                    } catch (JSONException e) {
                        System.out.println("nah didnt work");


                        e.printStackTrace();
                    }*/
                /*TODO: IMPLEMENT A SEARCH BAR WHICH ALLOWS YOU TO LOOK FOR ALL CATEGORIES AND CLICK ON THEM TO START TEST ACTIVITY(TO DO THIS, YOU WOULDNT USE CAT_INDEX,
                ONLY DISPLAY THE FIRST 6 ITEMS OF CATLIST AND LET THE USER BE ABLE TO SEARCH FOR ANY OTHER ELEMENT OF CAT LIST WITH THIS SEARCHBAR.
                SAVE NEW QUESTION AND CATEGORIES PHP FILE. GET THE CATNUMBERS OF THE CATEGORIES AND COMPARE IT TO THE CATNUMBER OF EACH CATEGORY. IF THE CATNUMBER OF
                EACH QUESTION IS THE SAME AS A CAT NUMBER THEN INCREMENT THIS QLENGTH VALUE BY 1. QLENGTH SHOULD BE A NEW ATTRIBUTE IN CATMODEL AND WILL ALLOW US TO CREATE
                AN APPROPRIATE AMOUNT OF TESTS THAT VARY BY 10 MINUTES.E.G IF THERE ARE 29 QUESTIONS THEN THERE SHOULD BE 2 TESTS. ONE 10 MINUTES AND ONE 20 MINUTES.`*/










                System.out.println(Thread.currentThread().getName() + " completed.");


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Volley error");

            }
        });
        requestQueue.add(qrequest);

        System.out.println(Thread.currentThread().getName() + " completed.");
        latch.countDown();
    }




}