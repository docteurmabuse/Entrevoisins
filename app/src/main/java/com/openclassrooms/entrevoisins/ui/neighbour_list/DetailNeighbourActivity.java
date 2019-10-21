package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
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
    private Neighbour neighbour;
    private List<Neighbour> mFavNeighbour;
    private FavoriteNeighbourApiService mFavApiService;
    private FloatingActionButton fab;
    int mId;
    String mDetailName;
    String mDetailAvatar;
    private boolean isFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_neighbour);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mFavApiService = DI.getFavoriteService();
        mFavNeighbour = mFavApiService.getNeighbours();
        neighbour = getIntent().getParcelableExtra(DETAIL_NEIGHBOUR);
        isFavorite = mFavNeighbour.contains(neighbour);

        if (neighbour != null) {
            mId = neighbour.getId();
            mDetailName = neighbour.getName();
            mDetailAvatar = neighbour.getAvatarUrl();
            populateViews();
            fabOnclickListner();
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        // Set title of Detail page
        collapsingToolbar.setTitle(mDetailName);
    }

    public void populateViews() {
        Neighbour neighbour = new Neighbour(mId, mDetailName, mDetailAvatar);
        ImageView DetailAvatar = (ImageView) findViewById(R.id.mDetailAvatar);
        Glide.with(DetailAvatar.getContext())
                .load(mDetailAvatar)
                .into(DetailAvatar);
        TextView DetailName = (TextView) findViewById(R.id.mDetailName);
        DetailName.setText(mDetailName);

    }

    private void fabOnclickListner(){
        fab = (FloatingActionButton) findViewById(R.id.fab);
        if (isFavorite) {
            fab.setImageResource(R.drawable.ic_star_yellow_24dp);
        } else {
            fab.setImageResource(R.drawable.ic_star_border_yellow_24dp);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isFavorite) {
                    fab.setImageResource(R.drawable.ic_star_yellow_24dp);
                    addFavoriteNeighbour();
                } else {
                    fab.setImageResource(R.drawable.ic_star_border_yellow_24dp);
                    deleteFavoriteNeighbour();
                }
            }
        });
    }
    private void addFavoriteNeighbour() {
        isFavorite = true;
        EventBus.getDefault().post(new AddFavoriteNeighbourEvent(neighbour));
    }

    private void deleteFavoriteNeighbour() {
        isFavorite = false;
        EventBus.getDefault().post(new DeleteFavoriteNeighbourEvent(neighbour));
    }
}