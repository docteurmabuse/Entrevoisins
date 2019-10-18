package com.openclassrooms.entrevoisins.ui.neighbour_list;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
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
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_detail_neighbour);
        final Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AppBarLayout mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;
                    showOption(R.id.action_info);
                } else if (isShow) {
                    isShow = false;
                    hideOption(R.id.action_info);
                }
            }
        });




        TextView tvName = findViewById(R.id.tvName);
        Intent intent = getIntent();
        if (intent !=null){
            neighbour=intent.getParcelableExtra("fav_neighbour");
            if(neighbour!=null){
                 mId =neighbour.getId();
                 mDetailName = neighbour.getName();
                 mDetailAvatar=neighbour.getAvatarUrl();
            }
        }
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_detail_neighbour, menu);
        hideOption(R.id.action_info);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.action_info) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void hideOption(int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(false);
    }

    private void showOption(int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(true);
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
