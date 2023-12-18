package com.example.autismapp.Activity;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.autismapp.R;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.Random;

public class Video extends AppCompatActivity {

    private ImageView gifImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        getSupportActionBar().setTitle("Activities");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        gifImg = findViewById(R.id.img1);
        Glide.with(this).load("https://media.giphy.com/media/BqVl9yc94lXvq/giphy.gif").into(gifImg);

        gifImg = findViewById(R.id.img2);
        Glide.with(this).load("https://media.giphy.com/media/K3Sbp8fOgKye4/giphy.gif").into(gifImg);

        gifImg = findViewById(R.id.img3);
        Glide.with(this).load("https://media.giphy.com/media/3o6Ztpe2hEveO3PPZm/giphy.gif").into(gifImg);

        gifImg = findViewById(R.id.img4);
        Glide.with(this).load("https://media.giphy.com/media/RJyzYynwHrxZcFy9K1/giphy.gif").into(gifImg);




//        gifImg = findViewById(R.id.crawling);
//        Glide.with(this).load("https://media4.giphy.com/media/pkqnVgAiYQx2w/giphy.gif?cid=ecf05e474n8j0iwvey3ha46bu036sbcw2u38iynqhqonmwqj&ep=v1_gifs_search&rid=giphy.gif&ct=g").into(gifImg);

//        gifImg = findViewById(R.id.dancing);
//        Glide.with(this).load("https://media.giphy.com/media/RJyzYynwHrxZcFy9K1/giphy.gif").into(gifImg);
//



    }

}

