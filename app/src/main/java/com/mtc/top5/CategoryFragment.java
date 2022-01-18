package com.mtc.top5;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;


public class CategoryFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CategoryFragment() {
        // Required empty public constructor
    }
    private GridView catView;
    private List<CategoryModel> catList = new ArrayList<>();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_category, container, false); //attach to root is false, therefore the layout from fragment_category is inflated and returned as view

        catView = view.findViewById(R.id.cat_Grid);


        loadCategories();

        CategoryAdapter adapter = new CategoryAdapter(catList);
        catView.setAdapter(adapter);
        return view;
    }

    private void loadCategories()
    {
        catList.clear();
        catList.add(new CategoryModel("1", "Africa", 25));
        catList.add(new CategoryModel("2", "Hiv/aids", 10));
        catList.add(new CategoryModel("3", "Canada", 5));
        catList.add(new CategoryModel("4", "crime", 6));
        catList.add(new CategoryModel("5", "weapons", 7));
        catList.add(new CategoryModel("6", "india", 11));
        catList.add(new CategoryModel("7", "test", 14));
    }
}
