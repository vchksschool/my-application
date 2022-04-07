package com.mtc.top5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestionsAdapter  extends RecyclerView.Adapter<QuestionsAdapter.ViewHolder> {

    private List<QuestionModel> questionsList;
    public static int currentoption;
    public  static ArrayList<String> qlistcorrect;
    public static ArrayList<String> orderedqlist;
    public static ArrayList<ArrayList<String>> orderedlistOfLists= new ArrayList<ArrayList<String>>();
    public static ArrayList<ArrayList<String>> textviewlistOfLists =new ArrayList<ArrayList<String>>();


    public QuestionsAdapter(List<QuestionModel> questionsList) {
        this.questionsList = questionsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(position);

    }

    @Override
    public int getItemCount() {
        return questionsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView ques;
        private Button optionA, optionB, optionC, optionD, optionE, prevSelectedB;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ques = itemView.findViewById(R.id.tv_question);
            optionA = itemView.findViewById(R.id.optionA);
            optionB = itemView.findViewById(R.id.optionB);
            optionC = itemView.findViewById(R.id.optionC);
            optionD = itemView.findViewById(R.id.optionD);
            optionE = itemView.findViewById(R.id.optionE);
            prevSelectedB = null;


        }

        public Button getOptionA() {
            return optionA;
        }

        private void setData(final int pos) {
            System.out.println("here");
            System.out.println(questionsList);
            QuestionModel qlist = questionsList.get(pos);

            qlistcorrect = new ArrayList<String>();

            qlistcorrect.add(qlist.getOptionA());
            qlistcorrect.add(qlist.getOptionB());
            qlistcorrect.add(qlist.getOptionC());
            qlistcorrect.add(qlist.getOptionD());
            qlistcorrect.add(qlist.getOptionE());
            textviewlistOfLists.add(qlistcorrect);
            //orderedqlist =qlistcorrect;cant do, this will also store the shuffled array
            Collections.shuffle(qlistcorrect);
            orderedqlist = new ArrayList<String>();
            orderedqlist.add(qlist.getOptionA());
            orderedqlist.add(qlist.getOptionB());
            orderedqlist.add(qlist.getOptionC());
            orderedqlist.add(qlist.getOptionD());
            orderedqlist.add(qlist.getOptionE());
            orderedlistOfLists.add(orderedqlist);




            ques.setText("Below are the top 5 " + questionsList.get(pos).getQuestion().replace("-", " ")+"."+ " What is number " + questionsList.get(pos).getCorrectAns()+ " in the list");
            optionA.setText(qlistcorrect.get(0));
            optionB.setText(qlistcorrect.get(1));
            optionC.setText(qlistcorrect.get(2));
            optionD.setText(qlistcorrect.get(3));
            optionE.setText(qlistcorrect.get(4));
            setOption(optionA, 1, pos);
            setOption(optionB, 2, pos);
            setOption(optionC, 3, pos);
            setOption(optionD, 4, pos);
            setOption(optionE, 5, pos);




            optionA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    selectOption(optionA, 1, pos);

                }
            });
            optionB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    selectOption(optionB, 2, pos);

                }
            });
            optionC.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectOption(optionC, 3, pos);
                }
            });
            optionD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectOption(optionD, 4, pos);
                }
            });
            optionE.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectOption(optionE, 5, pos);
                }
            });
        }

        private void selectOption(Button btn, int option_num, int quesID)
        {

            if (prevSelectedB == null)
            {
                //means we havent selected any options yets(case 1)

                btn.setBackgroundResource(R.drawable.selected_btn);//sets selected answer to corresponding questions
                prevSelectedB = btn;
                DBqueries.g_quesList.get(quesID).setSelectedAns(option_num);

            }
            else
            {
                if (prevSelectedB.getId() == btn.getId())//if button clckedd by user is clicked again this will unselect it
                {
                    btn.setBackgroundResource(R.drawable.unselected_btn);
                    DBqueries.g_quesList.get(quesID).setSelectedAns(-1);
                    prevSelectedB = null;
                }
                else// if button clicked is different from previously selected question
                {
                    prevSelectedB.setBackgroundResource(R.drawable.unselected_btn);
                    btn.setBackgroundResource(R.drawable.selected_btn);
                    DBqueries.g_quesList.get(quesID).setSelectedAns(option_num);
                    System.out.println(DBqueries.g_quesList.get(quesID).getQuestion());
                    System.out.println(DBqueries.g_quesList.get(quesID).getSelectedAns());
                    prevSelectedB = btn;

                }

                //already selected one button(Case 2)
            }
        }

        private void setOption(Button btn, int option_num, int quesID)
        {   currentoption= option_num;
            if(DBqueries.g_quesList.get(quesID).getSelectedAns() == option_num)
            {
                btn.setBackgroundResource(R.drawable.selected_btn);
                //MyThread.g_quesList.get(quesID).setSelectedAns(option_num);
                //System.out.println(MyThread.g_quesList.get(quesID).getSelectedAns());

            }
            else
            {
                btn.setBackgroundResource(R.drawable.unselected_btn);
            }
        }
    }




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
