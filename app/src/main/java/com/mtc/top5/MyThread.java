package com.mtc.top5;


import android.content.Context;
import android.icu.number.IntegerWidth;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import androidx.fragment.app.Fragment;

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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

class MyThread extends Fragment implements Runnable{
    public static List<CategoryModel> catList;
    public static int NoOfQues = 0;
    public static int j;
    public static ArrayList<ArrayList<String>> qlistOfLists = new ArrayList<ArrayList<String>>();
    public static int currentCatNumDB = 0;
    public static ArrayList<ArrayList<String>> listOfLists = new ArrayList<ArrayList<String>>();
    public static int qlength;
    public static Map<String, String> categoryNumber_numberOfQuestions = new HashMap<String, String>();//remember you set these values inside as a string
    public static List<QuestionModel> g_quesList = new ArrayList<>();
    public static int g_selected_cat_index = 0;
    public static int test_index = 0;
    public static int g_selected_quiz_index = 0;
    public static int startindex;
    public static int endindex;
    public static ArrayList<String> question_cat1;

    public static void setj()
    {
        int i = 0;
        while(i<qlength){
            question_cat1 = qlistOfLists.get(i);
            String question1 = question_cat1.get(0);//may be an error here
            String catno1 = question_cat1.get(1).toString();
            //System.out.println(currentCatNumDB);
            if (Integer.toString(currentCatNumDB).equals(catno1)){
                startindex = i;
                //System.out.println("The start index is below");
                //System.out.println(startindex);
                i = qlength;

            }else{
                /*System.out.println("No"+catno1+currentCatNumDB);
                System.out.println(catno1);
                System.out.println(currentCatNumDB);*/
            }
            //check to see if catno = currentcatnumdb. if so start new loop from there.

            i++;
        }

//COME FIX THIS
        int u = 0;
        while(u<qlength){
            //System.out.println("got");
            ArrayList<String> question_cat = qlistOfLists.get(u);
            String question = question_cat.get(0);//may be an error here
            String catno = question_cat.get(1);

            int foo = Integer.parseInt(catno);
            foo+= 1;
            String newcatno = Integer.toString(foo);

            if (Integer.toString((currentCatNumDB+1)).equals(catno)){
                /*System.out.println("yes");
                System.out.println(currentCatNumDB);
                System.out.println(catno);
                System.out.println(u);*/
                endindex = u;
                u = qlength;
            }
            else
            {
                /*System.out.println("no 2");
                System.out.println(currentCatNumDB);
                System.out.println(foo);*/
            }
            //check to see if catno = currentcatnumdb. if so start new loop from there.
            //System.out.println(question);
            //System.out.println(catno);
            u++;
        }
        //ArrayList<String> allques = new ArrayList<>();
        /*System.out.println("NUMBER OF QUESTIONS ARE BELOW");
        System.out.println(NoOfQues);
        System.out.println(startindex);
        System.out.println(endindex);*/
        j = endindex-startindex;
        System.out.println(Integer.toString(j)+ "j in mythread");
        if (j>9){

            for(int k= startindex; k<endindex;k++)
            {
                question_cat1 = qlistOfLists.get(k);
                //System.out.println(question_cat1);
            }

        }
        else{
            System.out.println("Not enough questions");
        }
        System.out.println("gone past");
        //allques.add(question_cat1.get(randnumbetween0andendindex-startindex))
        //}


    }
    public static void loadquestions()

    {
        System.out.println("selected cat index below");
        System.out.println(test_index);
        int i = 0;
        ArrayList<String> randomnums = new ArrayList<String>();
        while(i < ((test_index+1)*10)){//instead of while loop, try to implement a list shuffle and get the first (g_select_cat_index_1+ 1)*10 questions




            int randomNum = ThreadLocalRandom.current().nextInt(startindex+1, endindex + 1);
            if( randomnums.contains(Integer.toString(randomNum)) ){
                ;
            }
            else{
                ArrayList finalq_ans =  qlistOfLists.get(randomNum);
                String question = finalq_ans.get(0).toString().substring(1);
                String categorynum = finalq_ans.get(1).toString();
                String top1 = finalq_ans.get(2).toString();
                String top2 = finalq_ans.get(3).toString();
                String top3 = finalq_ans.get(4).toString();
                String top4 = finalq_ans.get(5).toString();
                String top5 = finalq_ans.get(6).toString();
                int randomans = ThreadLocalRandom.current().nextInt(0, 5);
                //System.out.println(question+" "+ categorynum+ " "+ top1+ " "+ top2+ " "+top3 + " "+ top4+ " "+ top5 + " "+ Integer.toString(randomans));
                g_quesList.add(new QuestionModel(question,top1,top2,top3,top4,top5,randomans));



                //System.out.println(question);


                randomnums.add(Integer.toString(randomNum));
                i++;
            }


        }



            //allques.add(question_cat1.get(randnumbetween0andendindex-startindex))
        //}







    }


        /*
        for i in range(0, repsonse.length)or response.length = qlength
        if response.getobject(i).getstring("cat_number") or get(1) in quetion list where index 1 has caetgeory num
        loop through a specific category
        for
        firstquestion_answers = response.getjson(object(0))
        startq_id = firstquestions_answers.getString("q_id")
        finalquestion_answers = response.getjsonobject(response.length)
        finalq_id = finalquestions_answers.getString("q_id")
        for i in range NoOfQues:
            x = random number which is between startq_id and final q_id
            add x to a lsit
            if next x  in list then try random number again
            else:
                h = new random number between 1 and 5
                questionanswers = response.get jsonobject(x)
                question = "Below are the top 5" + questionsanswers.getstr("question") + ".What is number " + str(h) +" in the list?"
                answera = questionanswer.getstring("answer_1")
                answer b =..
                answerc= ...
                .
                .
                correctAns = questionanswers.getstrng("answer_" + h)
                g_queslist.add(new questionmodel(question, answera, answerb, answerc,answerd, answere, correctAns));

        */
        //load questions. store them in g_questList. get all questions for corresponding category number.


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
        for (int i = 0; i < 6; i++){
            ArrayList<String> category = listOfLists.get(i);

            System.out.println(category.get(1)+" ITS WORKING");
            catList.add(new CategoryModel(Integer.toString(i),category.get(1), Integer.valueOf(category.get(2)),Integer.valueOf(category.get(3))));

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
                        String catnumb = categories.getString("category_number").toString();
                        ArrayList<String> list1 = new ArrayList<String>();
                        list1.add(Integer.toString(i));
                        list1.add(catname);
                        list1.add("5");
                        list1.add(catnumb);
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
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject questions = response.getJSONObject(i);
                        String quename = questions.getString("question_name").toString();
                        String quecatname = questions.getString("category_number").toString();
                        quecatname = quecatname.substring(1,quecatname.length());
                        String o1 = questions.getString("answer_1").toString();
                        String o2 = questions.getString("answer_2").toString();
                        String o3 = questions.getString("answer_3").toString();
                        String o4 = questions.getString("answer_4").toString();
                        String o5 = questions.getString("answer_5").toString();



                        ArrayList<String> list1 = new ArrayList<String>();
                        list1.add(quename);
                        list1.add(quecatname);
                        list1.add(o1);
                        list1.add(o2);
                        list1.add(o3);
                        list1.add(o4);
                        list1.add(o5);
                        qlistOfLists.add(list1);

                    } catch (JSONException e) {
                        System.out.println("nah didnt work");


                        e.printStackTrace();
                    }
                }
                System.out.println(qlistOfLists);


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