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
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.AddFavoriteNeighbourEvent;
import com.openclassrooms.entrevoisins.events.DeleteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.FavoriteNeighbourApiService;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class DetailNeighbourActivity extends AppCompatActivity {

    private boolean isFavorite;
    Neighbour neighbour;
    private List<Neighbour> mFavNeighbour;
    private FavoriteNeighbourApiService mFavApiService;
    FloatingActionButton favoriteButton;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_neighbour);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView tvName = findViewById(R.id.tvName);
       // neighbour=(Neighbour) getIntent().getSerializableExtra("neighbour");
        String mDetailName = getIntent().getStringExtra("name");
        String mDetailAvatar = getIntent().getStringExtra("avatar");
        String mId = getIntent().getStringExtra("id");
        mFavApiService= DI.getFavoriteService();
        mFavNeighbour=mFavApiService.getNeighbours();
        neighbour=new Neighbour (Integer.valueOf(mId),mDetailName,mDetailAvatar);

        tvName.setText(mDetailName);
        TextView tvName2 = findViewById(R.id.mName2);
        tvName2.setText(mDetailName);

        //add Neighbour avatar image
        ImageView ivAvatar = findViewById(R.id.ivAvatar);
        Glide.with(ivAvatar.getContext())
                .load(mDetailAvatar)
                .into(ivAvatar);
        //Is in favorite list?
        isFavorite=mFavNeighbour.contains(neighbour);
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
        if (isFavorite){
            favoriteButton.setImageResource(R.drawable.ic_star_yellow_24dp);
        }
        else {
            favoriteButton.setImageResource(R.drawable.ic_star_border_yellow_24dp);
        }

        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFavorite){
                    favoriteButton.setImageResource(R.drawable.ic_star_yellow_24dp);
                    mFavApiService.addFavoriteNeighbour(neighbour);
                   EventBus.getDefault().postSticky(new AddFavoriteNeighbourEvent(neighbour));
                }
                else{
                    favoriteButton.setImageResource(R.drawable.ic_star_border_yellow_24dp);
                    mFavApiService.deleteNeighbour(neighbour);
                    EventBus.getDefault().postSticky(new DeleteNeighbourEvent(neighbour));
                }
            }
        });

    }
    private void addNeighbour()
    {
            isFavorite=true;
        favoriteButton.setImageResource(R.drawable.ic_star_yellow_24dp);
            EventBus.getDefault().postSticky(new AddFavoriteNeighbourEvent(neighbour));

    }
    private void deleteNeighbour()
    {
        isFavorite=false;
        favoriteButton.setImageResource(R.drawable.ic_star_border_yellow_24dp);
        EventBus.getDefault().postSticky(new DeleteNeighbourEvent(neighbour));

    }
}
