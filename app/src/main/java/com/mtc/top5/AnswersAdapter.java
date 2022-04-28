package com.mtc.top5;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AnswersAdapter extends RecyclerView.Adapter<AnswersAdapter.ViewHolder> {

    private List<QuestionModel> quesList;

    public AnswersAdapter(List<QuestionModel> quesList) {
        this.quesList = quesList;
    }

    @NonNull
    @Override
    public AnswersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_item_layout, parent, false);

        return new AnswersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswersAdapter.ViewHolder holder, int position) {

        String ques = quesList.get(position).getQuestion();



            String a = QuestionsActivity.textviewlistOfLists.get(position).get(0);
            String b = QuestionsActivity.textviewlistOfLists.get(position).get(1);
            String c = QuestionsActivity.textviewlistOfLists.get(position).get(2);
            String d = QuestionsActivity.textviewlistOfLists.get(position).get(3);
            String e = QuestionsActivity.textviewlistOfLists.get(position).get(4);
            int selected =quesList.get(position).getSelectedAns();
            int ans = quesList.get(position).getUserAnswerPosition();

            holder.setData(position, ques,a,b,c,d,e,selected,ans);


    }

    @Override
    public int getItemCount() {
        return quesList.size();

    }

    //below 2 methods helped fixed repeaating view issue
    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView qNO, question, optA, optB, optC, optD, optE, result;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            qNO = itemView.findViewById(R.id.quesNo);
            optA = itemView.findViewById(R.id.optA);
            optB = itemView.findViewById(R.id.optB);
            optC = itemView.findViewById(R.id.optC);
            optD = itemView.findViewById(R.id.optD);
            optE = itemView.findViewById(R.id.optE);
            question = itemView.findViewById(R.id.question);
            result = itemView.findViewById(R.id.result);
        }
        //all values for set data are got from on bind view holder
        private void setData(int pos, String ques, String a,String b,String c,String d,String e, int selected, int userAnswerPosition)
        {
            //numofq is view nmber in the recyler view.

            qNO.setText("Question No. "+ String.valueOf(pos+1));
            question.setText("Below are the top 5 " + ques.replace("-", " ")+"."+ " What is number " +userAnswerPosition+ " in the list");
            optA.setText("A " + a);
            optB.setText("B " + b);
            optC.setText("C " + c);
            optD.setText("D " + d);
            optE.setText("E " + e);
            System.out.println("This is current " + String.valueOf(pos)+String.valueOf(selected));

            if(selected == -1)
            {
                //user not answered
                result.setText("Un-Answered");
                result.setTextColor(itemView.getContext().getResources().getColor(R.color.black));

            }
            else
            {

                System.out.println("Selected is "+ String.valueOf(selected));

                if(QuestionsAdapter.textviewlistOfLists.get(pos).get(selected-1)==QuestionsAdapter.orderedlistOfLists.get(pos).get(userAnswerPosition-1))
                {
                    result.setText("CORRECT");
                    result.setTextColor(itemView.getContext().getResources().getColor(R.color.green));
                    setOptionColour(selected, R.color.green);
                }
                else
                {
                    result.setText("WRONG");
                    result.setTextColor(itemView.getContext().getResources().getColor(R.color.red));
                    setOptionColour(selected, R.color.red);
                    for(int i  = 0; i <5; i++)
                    {
                        if (QuestionsAdapter.textviewlistOfLists.get(pos).get(i) == QuestionsAdapter.orderedlistOfLists.get(pos).get(userAnswerPosition-1))

                        {
                            //sets colour of correct answer to green
                            System.out.println("check here");
                            System.out.println(i);
                            System.out.println(QuestionsAdapter.textviewlistOfLists.get(pos).get(i));
                            System.out.println(QuestionsAdapter.orderedlistOfLists.get(pos).get(userAnswerPosition-1));



                            setOptionColour(i+1, R.color.green);
                        }
                    }


                }
            }

        }
        private void setOptionColour(int selected, int colour)
        {
            switch (selected)
            {
                case 1:
                    optA.setTextColor(itemView.getContext().getResources().getColor(colour));
                    System.out.println("color selected for o1");
                    break;
                case 2:
                    optB.setTextColor(itemView.getContext().getResources().getColor(colour));
                    break;
                case 3:
                    optC.setTextColor(itemView.getContext().getResources().getColor(colour));
                    break;
                case 4:
                    optD.setTextColor(itemView.getContext().getResources().getColor(colour));
                    break;
                case 5:
                    optE.setTextColor(itemView.getContext().getResources().getColor(colour));
                    break;
                    default:

            }
        }

    }
}
