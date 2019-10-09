package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;

public class DetailNeighbourActivity extends AppCompatActivity {
    private boolean isFavorite=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_neighbour);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        TextView tvName = findViewById(R.id.tvName);
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
        ImageView backButton = findViewById(R.id.backbutton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ImageView favoriteButton = findViewById(R.id.favoriteBtn);
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getPreferences(MODE_PRIVATE);
                preferences.edit().putInt("id", Integer.valueOf(mId));
                preferences.edit().putString("name", mDetailName);
                preferences.edit().putString("avatarUrl",mDetailAvatar);
                if (isFavorite!=true){
                    isFavorite=true;
                    favoriteButton.setImageResource(R.drawable.ic_star_yellow_24dp);
                    preferences.edit().putBoolean("favorite",isFavorite);

                }
                else {
                    isFavorite=false;
                    favoriteButton.setImageResource(R.drawable.ic_star_border_yellow_24dp);
                    preferences.edit().putBoolean("favorite",isFavorite);
                }
                Snackbar.make(v, getIntent().getStringExtra("id"), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });

    }

}
