package com.mtc.top5;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TestAdapter extends RecyclerView.Adapter<TestAdapter.ViewHolder> {
    private List<TestModel> testList;
    private int cat_index;

    public TestAdapter(List<TestModel> testList, int cat_index) {
        this.cat_index = cat_index;
        this.testList = testList;
    }

    @NonNull
    @Override
    public TestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.test_item_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TestAdapter.ViewHolder holder, int position) {
        int progress = testList.get(position).getTopScore();
        holder.setData(position, progress);

    }

    @Override
    public int getItemCount() {
        return testList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView testNo;
        private TextView topScore;
        private ProgressBar progressBar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            testNo = itemView.findViewById(R.id.testNo);
            //topScore = itemView.findViewById(R.id.scoretext);
            //
            //progressBar = itemView.findViewById(R.id.testProgressBar);
            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), QuestionsActivity.class);
                    itemView.getContext().startActivity(intent);
                }
            });*/

        }

        private  void setData(int position, int progress)
        {
            testNo.setText(String.valueOf((position+1)*10) + " Minute Quiz");
            //topScore.setText(String.valueOf(progress) + " %");
            //progressBar.setProgress(progress);
            itemView.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    DBqueries.g_quesList.clear();
                    DBqueries.NoOfQues = (position+1)*10;
                    DBqueries.test_index = position;
                    DBqueries.currentCatNumDB = DBqueries.catList.get(cat_index).getCatNumDB();
                    System.out.println("Below is the current catnumber");
                    System.out.println(DBqueries.currentCatNumDB);

                    DBqueries.loadquestions();
                    Intent intent = new Intent(itemView.getContext(), StartQuizActivity2.class);
                    itemView.getContext().startActivity(intent);
                }
            });
        }
    }
}
