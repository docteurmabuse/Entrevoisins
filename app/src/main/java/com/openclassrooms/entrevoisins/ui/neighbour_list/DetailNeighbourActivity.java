package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
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
import com.openclassrooms.entrevoisins.events.DeleteFavoriteNeighbourEvent;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.FavoriteNeighbourApiService;

import static com.openclassrooms.entrevoisins.ui.neighbour_list.MyNeighbourRecyclerViewAdapter.DETAIL_NEIGHBOUR;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
/**

 */
public class DetailNeighbourActivity extends AppCompatActivity {
    int mId;
    String mDetailName;
    String mDetailAvatar;
    private Neighbour neighbour;
    private List<Neighbour> mFavNeighbour;
    private FavoriteNeighbourApiService mFavApiService;
    private FloatingActionButton fab;
    private boolean isFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFavApiService = DI.getFavoriteService();
        mFavNeighbour = mFavApiService.getNeighbours();
        neighbour = getIntent().getParcelableExtra(DETAIL_NEIGHBOUR);

        if (neighbour != null) {
            isFavorite = mFavApiService.favorite(neighbour);
            getFavoriteNeighbour();
            populateViews();
            fabOnclickListner();
        }
    }

    public void getFavoriteNeighbour(){
        mId = neighbour.getId();
        mDetailName = neighbour.getName();
        mDetailAvatar = neighbour.getAvatarUrl();
    }
    public void populateViews() {
        setContentView(R.layout.activity_detail_neighbour);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView DetailAvatar = (ImageView) findViewById(R.id.mDetailAvatar);
        Glide.with(DetailAvatar.getContext())
                .load(mDetailAvatar)
                .into(DetailAvatar);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        // Set title of Detail page
        collapsingToolbar.setTitle(mDetailName);

        TextView DetailName = (TextView) findViewById(R.id.mDetailName);
        DetailName.setText(mDetailName);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        if (isFavorite) {
            fab.setImageResource(R.drawable.ic_star_yellow_24dp);
        } else {
            fab.setImageResource(R.drawable.ic_star_border_yellow_24dp);
        }
    }

    private void fabOnclickListner(){

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isFavorite) {
                    fab.setImageResource(R.drawable.ic_star_yellow_24dp);
                    addFavoriteNeighbour(view);
                } else {
                    alreadyFavoriteNeighbour(view);
                }
            }
        });
    }
    private void addFavoriteNeighbour(View view) {
        isFavorite = true;
        mFavApiService.addFavoriteNeighbour(neighbour);
        Snackbar.make(view, "Vous venez d'ajouter "+mDetailName+" à vos voisins favoris!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private void alreadyFavoriteNeighbour(View view) {
        Snackbar.make(view, "Ce voisin fait déjà partie de vos favoris!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();    }
}