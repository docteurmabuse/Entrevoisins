package com.openclassrooms.entrevoisins.ui.neighbour_list;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.events.AddFavoriteNeighbourEvent;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class DetailNeighbourActivity extends AppCompatActivity {
    private boolean isFavorite=false;
    private NeighbourApiService mFavApiService;
    private NeighbourApiService mApiService;
    Neighbour neighbour;
    private List<Neighbour> mFavNeighbours;
    FloatingActionButton favoriteButton;
    ImageView backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_neighbour);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView tvName = findViewById(R.id.tvName);
        //mFavNeighbours=(ArrayList<Neighbour> )getIntent().getSerializableExtra("neighbour");
        String mDetailName = getIntent().getStringExtra("name");
        String mDetailAvatar = getIntent().getStringExtra("avatar");
        String mId = getIntent().getStringExtra("id");
        tvName.setText(mDetailName);
        TextView tvName2 = findViewById(R.id.mName2);
        tvName2.setText(mDetailName);
        //add Neighbour avatar image
        ImageView ivAvatar = findViewById(R.id.ivAvatar);
        Glide.with(ivAvatar.getContext())
                .load(mDetailAvatar)
                .into(ivAvatar);
        //add back button
        backButton = findViewById(R.id.backbutton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //add favorite button full if is favorite & empty if is not
        favoriteButton = findViewById(R.id.favoriteBtn);
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // mFavApiService.addFavoriteNeighbour(new Neighbour(Integer.valueOf(mId),mDetailName,mDetailAvatar));
                //mFavApiService.addFavoriteNeighbour(new Neighbour (12, "bbnnb", "http://i.pravatar.cc/150?u=a042581f3e39026702d"));
                if (isFavorite!=true){
                    isFavorite=true;
                    favoriteButton.setImageResource(R.drawable.ic_star_yellow_24dp);
                }
                else {
                    isFavorite=false;
                    favoriteButton.setImageResource(R.drawable.ic_star_border_yellow_24dp);
                }
                 neighbour=new Neighbour (Integer.valueOf(mId),mDetailName,mDetailAvatar);
                //EventBus.getDefault().post(new AddFavoriteNeighbourEvent(neighbour));
               // mFavApiService.addFavoriteNeighbour(neighbour);
                addNeighbour();
                Snackbar.make(v, getIntent().getStringExtra("id"), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }
    private void addNeighbour()
    {

        //mApiService.addFavoriteNeighbour(neighbour);
        //FavoriteNeighbourFragment.mFavNeighbours.add(neighbour);
        //FavoriteNeighbourFragment.mRecyclerView.setAdapter(new MyNeighbourRecyclerViewAdapter(mFavNeighbours));
        EventBus.getDefault().post(new AddFavoriteNeighbourEvent(neighbour));

    }
}
