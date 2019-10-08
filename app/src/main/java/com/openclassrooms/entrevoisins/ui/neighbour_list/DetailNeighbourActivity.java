package com.openclassrooms.entrevoisins.ui.neighbour_list;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.openclassrooms.entrevoisins.R;

public class DetailNeighbourActivity extends AppCompatActivity {
   protected TextView tvName;
    protected TextView tvName2;
   protected String mDetailName;
   protected ImageView ivAvatar;
   protected String mDetailAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_neighbour);

        //add back button

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tvName= findViewById(R.id.tvName);
        tvName2= findViewById(R.id.mName2);
        mDetailName=getIntent().getStringExtra("name");
        mDetailAvatar=getIntent().getStringExtra("avatar");
        tvName.setText(mDetailName);
        tvName2.setText(mDetailName);
        ivAvatar=findViewById(R.id.ivAvatar);
        Glide.with(ivAvatar.getContext())
                .load(mDetailAvatar)
                .into(ivAvatar);
        ImageView backbutton = findViewById(R.id.backbutton);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

}
