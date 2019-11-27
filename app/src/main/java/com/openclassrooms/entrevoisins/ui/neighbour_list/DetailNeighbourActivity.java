package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.openclassrooms.entrevoisins.R;
import com.openclassrooms.entrevoisins.di.DI;
import com.openclassrooms.entrevoisins.model.Neighbour;
import com.openclassrooms.entrevoisins.service.NeighbourApiService;

import java.util.List;

import static com.openclassrooms.entrevoisins.ui.neighbour_list.MyNeighbourRecyclerViewAdapter.DETAIL_NEIGHBOUR;

/**
 *
 */
public class DetailNeighbourActivity extends AppCompatActivity {
    int mId;
    String mDetailName;
    String mDetailAvatar;
    Boolean  isFavorite;
    private Neighbour neighbour;
    private List<Neighbour> mFavNeighbour;
    private NeighbourApiService mFavApiService;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFavApiService = DI.getNeighbourApiService();
        mFavNeighbour = mFavApiService.getNeighbours();
        neighbour = getIntent().getParcelableExtra(DETAIL_NEIGHBOUR);

        if (neighbour != null) {
            getFavoriteNeighbour();
            populateViews();
            fabOnclickListner();
        }
    }

    public void getFavoriteNeighbour() {
        mId = neighbour.getId();
        mDetailName = neighbour.getName();
        mDetailAvatar = neighbour.getAvatarUrl();
        isFavorite = neighbour.isFavorite();
    }

    public void populateViews() {
        setContentView(R.layout.activity_detail_neighbour);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ImageView DetailAvatar = findViewById(R.id.mDetailAvatar);
        Glide.with(DetailAvatar.getContext())
                .load(mDetailAvatar)
                .into(DetailAvatar);

        CollapsingToolbarLayout collapsingToolbar =
                findViewById(R.id.toolbar_layout);
        // Set title of Detail page
        collapsingToolbar.setTitle(mDetailName);

        TextView DetailName = findViewById(R.id.mDetailName);
        DetailName.setText(mDetailName);

        fab = findViewById(R.id.fab);
        if (isFavorite) {
            fab.setImageResource(R.drawable.ic_star_yellow_24dp);
            fab.hide();
            fab.show();
        } else {
            fab.setImageResource(R.drawable.ic_star_border_yellow_24dp);
            fab.hide();
            fab.show();
        }
    }

    private void fabOnclickListner() {

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isFavorite) {
                    fab.setImageResource(R.drawable.ic_star_yellow_24dp);
                    fab.hide();
                    fab.show();
                    addFavoriteNeighbour(view);
                } else {
                    deleteFavoriteNeighbour(view);
                }
            }
        });
    }

    private void addFavoriteNeighbour(View view) {
        mFavApiService.addFavoriteNeighbour(neighbour);
        isFavorite=true;
        Snackbar.make(view, "Vous venez d'ajouter " + mDetailName +" à vos voisins favoris!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private void deleteFavoriteNeighbour(View view) {
        fab.setImageResource(R.drawable.ic_star_border_yellow_24dp);
        fab.hide();
        fab.show();
        Snackbar.make(view, "Ce voisin a été supprimé de vos favoris!", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        isFavorite=false;
        mFavApiService.deleteFavoriteNeighbour(neighbour);
    }

}