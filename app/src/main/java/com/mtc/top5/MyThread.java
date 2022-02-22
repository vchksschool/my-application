package com.mtc.top5;


import android.content.Context;

import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.CountDownLatch;

class MyThread extends Fragment implements Runnable{
    public static List<CategoryModel> catList;


    CountDownLatch latch;
    private Context c;

    MyThread(CountDownLatch latch, Context context) {

        this.latch = latch;
        c = context;

    }
    public List<CategoryModel> getFinalCat(){
        return catList;
    }

    @Override
    public void run() {
        CategoryFragment a = new CategoryFragment();

        catList = a.getCatlist();
        System.out.println("BEFORE REQUEST");



        catList.add(new CategoryModel("1","before", 2));

        RequestQueue requestQueue = Volley.newRequestQueue(c);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest( "http://51.38.80.233/victory/categories.php", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println("array got");
                for (int i = 0; i < 5; i++) {
                    try {


                        JSONObject categories = response.getJSONObject(i);


                        String catname = categories.getString("category_name").toString();
                        catList.add(new CategoryModel(Integer.toString(i),catname, 5));
                        System.out.println(catname);




                    } catch (JSONException e) {
                        System.out.println("nah didnt work");


                        e.printStackTrace();
                    }


                }
                System.out.println(Thread.currentThread().getName() + " completed." + catList);
                latch.countDown();

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Volley error");

            }
        });
        requestQueue.add(jsonArrayRequest);

        System.out.println(Thread.currentThread().getName() + " completed.");
        latch.countDown();
    }




}