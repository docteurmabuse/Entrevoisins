package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.entrevoisins.events.AddFavoriteNeighbourEvent;
import com.openclassrooms.entrevoisins.events.DeleteFavoriteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.FavoriteNeighbourApiService;

import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link FavoriteNeighboursFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FavoriteNeighboursFragment extends Fragment {
    private List<Neighbour> mFavNeighbour;
    private FavoriteNeighbourApiService mFavApiService;
    private RecyclerView  mFavRecyclerView;
    private String IS_FAVORITE = "mFavorite";
    static   boolean isFavorite;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     * @return A new instance of fragment FavoriteNeighbourFragment.
     */
    public static FavoriteNeighboursFragment newInstance() {
        FavoriteNeighboursFragment fragment = new FavoriteNeighboursFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        mFavApiService= DI.getFavoriteService();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite_list, container, false);
        Context context = view.getContext();
        mFavRecyclerView = (RecyclerView) view;
        mFavRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mFavRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        initList();
        return view;
    }
    private void initList() {
        mFavNeighbour = mFavApiService.getNeighbours();
        mFavRecyclerView.setAdapter(new MyNeighbourRecyclerViewAdapter(mFavNeighbour,true));
    }
    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    /**
     * Fired if the user clicks on a delete button
     * @param event
     */
    @Subscribe
    public void onDeleteFavoriteNeighbourEvent(DeleteFavoriteNeighbourEvent event) {
        mFavApiService.deleteNeighbour(event.neighbour);
        initList();
    }

    @Subscribe
    public void onAddFavoriteNeighbourEvent(AddFavoriteNeighbourEvent event) {
        mFavApiService.addFavoriteNeighbour(event.neighbour);
        initList();
    }


}
