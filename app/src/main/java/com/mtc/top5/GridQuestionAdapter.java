package com.mtc.top5;

import static com.mtc.top5.DBqueries.ANSWERED;
import static com.mtc.top5.DBqueries.NOT_VISITED;
import static com.mtc.top5.DBqueries.REVIEW;
import static com.mtc.top5.DBqueries.UNANSWERED;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

public class GridQuestionAdapter extends BaseAdapter {
    private Context context;
    private int noOfQues;

    public GridQuestionAdapter(Context context, int noOfQues) {
        this.noOfQues = noOfQues;
        this.context = context;//allows me to get context of question activity class
    }

    @Override
    public int getCount() {
        return noOfQues;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;
        //if view we are getting from the function isnt null then we are setting
        // the view variable
        //to the value we got from the method call
        //if null we create our own view
        if(convertView == null)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ques_grid_item, parent, false);
        }
        else
        {
            view = convertView;
        }


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //THIS ALLOWS USER TO GO TO QUESTION ON GRID VIEW WHEN CLICKED
                if (context instanceof QuestionsActivity)
                    ((QuestionsActivity)context).goToQ(position);

            }
        });

        TextView queTV = view.findViewById(R.id.ques_num);
        //position starts at 0
        queTV.setText(String.valueOf(position+1));
        switch (DBqueries.g_quesList.get(position).getStatus())
        {
            case NOT_VISITED:
                //background colour of circle set to grey
                queTV.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(view.getContext(), R.color.grey)));
                break;
            case UNANSWERED:
                //background colour of circle set to RED
                queTV.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(view.getContext(), R.color.red)));
                break;
            case ANSWERED:
                //background colour of circle set to GREEN
                queTV.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(view.getContext(), R.color.green)));
                break;
            case REVIEW:
                //background colour of circle set to pink
                queTV.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(view.getContext(), R.color.pink)));
                break;
            default:
                break;

        }
        return view;
    }
}
