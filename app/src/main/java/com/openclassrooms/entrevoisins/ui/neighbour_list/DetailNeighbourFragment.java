package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.entrevoisins.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailNeighbourFragment extends Fragment {
    public DetailNeighbourFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle=new Bundle();
        if (bundle!=null){
            String mDetailName=bundle.getString("name");
        }
        return inflater.inflate(R.layout.fragment_detail_neighbour, container, false);
    }
}
