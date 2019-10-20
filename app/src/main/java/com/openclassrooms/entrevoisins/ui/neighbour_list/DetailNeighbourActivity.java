package com.openclassrooms.entrevoisins.ui.neighbour_list;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.events.AddFavoriteNeighbourEvent;
import com.openclassrooms.entrevoisins.events.DeleteFavoriteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.FavoriteNeighbourApiService;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class DetailNeighbourActivity extends AppCompatActivity {

    private boolean isFavorite;
    Neighbour neighbour;
    private List<Neighbour> mFavNeighbour;
    private FavoriteNeighbourApiService mFavApiService;
    int mId;
    String mDetailName;
    String mDetailAvatar;
    FloatingActionButton favoriteButton;
    ImageView backButton;
    private Menu menu;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_neighbour);
        setContentView(R.layout.fragment_detail_neighbour);
//        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set Collapsing Toolbar layout to the screen
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        // Set title of Detail page
        // collapsingToolbar.setTitle(getString(R.string.item_title));


        TextView tvName = findViewById(R.id.tvName);
        Intent intent = getIntent();
        if (intent !=null){
            neighbour=intent.getParcelableExtra("detailNeighbour");
            if(neighbour!=null){
                 mId =neighbour.getId();
                 mDetailName = neighbour.getName();
                 mDetailAvatar=neighbour.getAvatarUrl();
            }
        }
       // mFavApiService= DI.getFavoriteService();
       // mFavNeighbour=mFavApiService.getNeighbours();

        tvName.setText(mDetailName);
        TextView tvName2 = findViewById(R.id.mName2);
        tvName2.setText(mDetailName);

        //add Neighbour avatar image
        ImageView dvAvatar = (ImageView) findViewById(R.id.imageAvatar);
        Glide.with(dvAvatar.getContext())
                .load("http://i.pravatar.cc/150?u=a042581f4e29026704c")
                .into(dvAvatar);
        //Is in favorite list?
//        isFavorite=mFavNeighbour.contains(neighbour);
        //add back button
       // backButton = findViewById(R.id.backbutton);
       // backButton.setOnClickListener(new View.OnClickListener() {
          //  @Override
           // public void onClick(View view) {
           //     finish();
           // }
      //  });
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
                  //  mFavApiService.addFavoriteNeighbour(neighbour);
                   EventBus.getDefault().post(new AddFavoriteNeighbourEvent(neighbour));
                }
                else{
                    favoriteButton.setImageResource(R.drawable.ic_star_border_yellow_24dp);
                   // mFavApiService.deleteNeighbour(neighbour);
                    EventBus.getDefault().post(new DeleteFavoriteNeighbourEvent(neighbour));
                }
            }
        });

    }

    private void addNeighbour()
    {
            isFavorite=true;
        favoriteButton.setImageResource(R.drawable.ic_star_yellow_24dp);
            EventBus.getDefault().post(new AddFavoriteNeighbourEvent(neighbour));

    }
    private void deleteNeighbour()
    {
        isFavorite=false;
        favoriteButton.setImageResource(R.drawable.ic_star_border_yellow_24dp);
        EventBus.getDefault().post(new DeleteFavoriteNeighbourEvent(neighbour));

    }
}
