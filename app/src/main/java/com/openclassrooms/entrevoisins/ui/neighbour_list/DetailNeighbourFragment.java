package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.entrevoisins.R;

/**
 * A placeholder fragment containing Detail neighbour avatar and name.
 */
public class DetailNeighbourFragment extends Fragment {

    public static DetailNeighbourFragment newInstance(){
        DetailNeighbourFragment fragment = new DetailNeighbourFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detail_neighbour, container, false);
    }
}
