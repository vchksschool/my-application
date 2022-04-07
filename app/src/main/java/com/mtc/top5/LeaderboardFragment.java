package com.mtc.top5;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LeaderboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LeaderboardFragment extends Fragment {


    private TextView totalUsersTV, myImgTextTV, myScoreTV,myRankTV;
    private RecyclerView usersView;
    private RankAdapter adapter;
    private  Dialog progressDialog;
    private TextView dialogText;
    private FrameLayout frameLayout;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LeaderboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LeaderboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LeaderboardFragment newInstance(String param1, String param2) {
        LeaderboardFragment fragment = new LeaderboardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_leaderboard, container, false);

        ((MainActivity)getActivity()).getSupportActionBar().setTitle("Leaderboard");
        initViews(view);


        progressDialog = new Dialog(getContext());
        progressDialog.setContentView(R.layout.dialog_layout);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogText = progressDialog.findViewById(R.id.dialog_text);
        dialogText.setText("Loading...");
        progressDialog.show();





        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        usersView.setLayoutManager(layoutManager);
        adapter = new RankAdapter(MyThread.g_usersList);
        usersView.setAdapter(adapter);



        MyThread.getTopUsers(getContext(), new MyCompleteListener() {
            @Override
            public void onSuccess() {

                adapter.notifyDataSetChanged();
                System.out.println("breach");
                if(MyThread.myPerformance.getScore()!=0) {
                    System.out.println("breach1");
                    if (!MyThread.isMeonTopList) {
                        System.out.println("breach2");
                        calculateRank();
                    }
                    System.out.println("got here");
                    System.out.println(MyThread.myPerformance.getRank() );
                    totalUsersTV.setText("Total Users: "+ MyThread.g_usersCount);
                    myScoreTV.setText("Score: " + MyThread.myPerformance.getScore());
                    myRankTV.setText("Rank: " + MyThread.myPerformance.getRank());
                }
                System.out.println("breach");
                progressDialog.dismiss();


            }

            @Override
            public void onFailure() {
                System.out.println("breach bad");
                Toast.makeText(getContext(), "Something went wrong",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        });

        totalUsersTV.setText("Total Users: "+ MyThread.g_usersCount);
        myImgTextTV.setText(MyThread.myPerformance.getName().toUpperCase().substring(0,1));
        myScoreTV.setText(String.valueOf(MyThread.myPerformance.getScore()));
        return view;
    }
    private void initViews(View view)
    {
        frameLayout = view.findViewById(R.id.frameLayout);
        totalUsersTV = view.findViewById(R.id.total_users);
        myImgTextTV = view.findViewById(R.id.img_text);
        myScoreTV = view.findViewById(R.id.total_score);
        myRankTV = view.findViewById(R.id.rankR);
        usersView = view.findViewById(R.id.users_view);

    }
    private void calculateRank()
    {
        System.out.println(MyThread.g_usersList);
        int lowTopScore =  MyThread.g_usersList.get(MyThread.g_usersList.size() - 1 ).getScore();//20th users score.1
        int remaining_users =MyThread.g_usersCount -20;//0
        int userSlot = MyThread.myPerformance.getScore()*remaining_users/ lowTopScore;
        int rank;
        if  (lowTopScore != MyThread.myPerformance.getScore())
        {
            rank = MyThread.g_usersCount - userSlot;

        }
        else
        {
            rank = 21;
        }

        MyThread.myPerformance.setRank(rank);
        System.out.println(rank);

    }
}