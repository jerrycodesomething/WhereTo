package com.example.whereto;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class VisitedFragment extends Fragment {

    GridView gridView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.visited_fragment, container, false);

        gridView = (GridView) view.findViewById(R.id.gridview);
        gridView.setAdapter(new ProfileImageAdapter(getContext()));

        return  view;
    }



}
