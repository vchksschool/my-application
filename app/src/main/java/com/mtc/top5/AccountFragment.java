package com.mtc.top5;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {
    private LinearLayout logoutB;
    private  Dialog progressDialog;
    private TextView dialogText;
    private TextView profile_img_text, name, score, rank;
    private LinearLayout leaderB, profileB,bookmarksB;
    private BottomNavigationView bottomNavigationView;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
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
        View view =inflater.inflate(R.layout.fragment_account, container, false);
        initiViews(view);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);

        progressDialog = new Dialog(getContext());
        progressDialog.setContentView(R.layout.dialog_layout);
        progressDialog.setCancelable(false);
        progressDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogText = progressDialog.findViewById(R.id.dialog_text);
        dialogText.setText("Loading...");
        progressDialog.show();

        ((MainActivity)getActivity()).getSupportActionBar().setTitle("My Account");
        String UserName= DBqueries.myProfile.getName();
        System.out.println(UserName);
        profile_img_text.setText(UserName.toUpperCase().substring(0,1));
        name.setText(UserName);
        score.setText(String.valueOf(DBqueries.myPerformance.getScore()));
        if(DBqueries.g_usersList.size() == 0)//means we havent fetched top users yet
        {
            DBqueries.getTopUsers(getContext(),new MyCompleteListener() {
                @Override
                public void onSuccess() {


                    if(DBqueries.myPerformance.getScore()!=0) {
                        progressDialog.show();
                        if (!DBqueries.isMeonTopList) {
                            calculateRank();
                        }
                        score.setText("Score: " + DBqueries.myPerformance.getScore());
                        rank.setText("Rank: " + DBqueries.myPerformance.getRank());
                    }
                    progressDialog.dismiss();


                }

                @Override
                public void onFailure() {
                    Toast.makeText(getContext(), "Something went wrong",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }
            });

        }
        else
        {

            score.setText("Score: " + DBqueries.myPerformance.getScore());
            if(DBqueries.myPerformance.getScore() != 0)
                rank.setText("Rank: " + DBqueries.myPerformance.getRank());
            progressDialog.dismiss();
        }

        logoutB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SplashActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                getActivity().finish();

            }
        });
        bookmarksB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        profileB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        leaderB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomNavigationView.setSelectedItemId(R.id.navigation_leaderboard);

            }
        });
        return view;
    }
    private void initiViews(View view)
    {
        logoutB =view.findViewById(R.id.logoutB);
        profile_img_text = view.findViewById(R.id.profile_img_text);
        score =view.findViewById(R.id.total_score);
        name =view.findViewById(R.id.name);
        rank =view.findViewById(R.id.rank);
        leaderB =view.findViewById(R.id.leaderboardB);
        bookmarksB =view.findViewById(R.id.bookmarkB);
        profileB =view.findViewById(R.id.profileB);
        bottomNavigationView = getActivity().findViewById(R.id.bottom_nav_bar);

    }
    private void calculateRank()
    {

        int lowTopScore =  DBqueries.g_usersList.get(DBqueries.g_usersList.size() - 1 ).getScore();//20th users score
        int remaining_users = DBqueries.g_usersCount -20;
        int userSlot = DBqueries.myPerformance.getScore()*remaining_users/ lowTopScore;
        int rank;
        if  (lowTopScore != DBqueries.myPerformance.getScore())
        {
            rank = DBqueries.g_usersCount - userSlot;

        }
        else
        {
            rank = 21;
        }

        DBqueries.myPerformance.setRank(rank);

    }
}