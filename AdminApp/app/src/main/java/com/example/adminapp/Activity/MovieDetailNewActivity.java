package com.example.adminapp.Activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.adminapp.Adapter.MoviesShowAdapter;
import com.example.adminapp.Model.GetVideoDetails;
import com.example.adminapp.Model.MovieItemClickListenerNew;
import com.example.adminapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MovieDetailNewActivity extends AppCompatActivity implements MovieItemClickListenerNew {

    LinearLayout btnEdit, btnDel;

    public static String TAG = "TAG";
    private ImageView MovieThumbnailImg,MovieCoverImg;
    private EditText tv_title,tv_description;
    private FloatingActionButton play_fab;
    private RecyclerView RvCast,recycler_similar_movies;
    MoviesShowAdapter moviesShowAdapter;
    DatabaseReference mDatabaserefence ;
    private List<GetVideoDetails> uploads,actionmovies, sportmovies,
            comedymovies,romanticmovies,advanturemovies;;
    String current_Video_url;
    String current_video_category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail_new);


        iniViews();


        play_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MovieDetailNewActivity.this,
                        "just click fb play button", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MovieDetailNewActivity.this , MoviePlayerActivity.class);
                intent.putExtra("videoUri",current_Video_url);
                // Toast.makeText(VideoPlayerActivity.this, ""+arrayListVideos.get(position).getVideo_path(),
                // Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        });

    }




    public void iniViews() {

        Boolean check =true;
        // RvCast = findViewById(R.id.rv_cast);
        play_fab = findViewById(R.id.play_fab);
        String movieTitle = getIntent().getExtras().getString("title");
        String imageResourceId = getIntent().getExtras().getString("imgURL");
        String imagecover = getIntent().getExtras().getString("imgCover");
        String movieDetailText = getIntent().getExtras().getString("movieDetails");
        String movieurl = getIntent().getExtras().getString("movieUrl");
        String movieCategory = getIntent().getExtras().getString("movieCategory");
        current_Video_url= movieurl;
        current_video_category = movieCategory;
        MovieThumbnailImg = findViewById(R.id.detail_movie_img);
        Glide.with(this).load(imageResourceId).into(MovieThumbnailImg);
        //MovieThumbnailImg.setImageResource(imageResourceId);
        MovieCoverImg = findViewById(R.id.detail_movie_cover);
        Glide.with(this).load(imagecover).into(MovieCoverImg);
        tv_title = findViewById(R.id.detail_movie_title);
        tv_title.setText(movieTitle);
        //getSupportActionBar().setTitle(movieTitle);
        tv_description = findViewById(R.id.detail_movie_desc);
        tv_description.setText(movieDetailText);

        // setup animation
        MovieCoverImg.setAnimation(AnimationUtils.loadAnimation(this,R.anim.scale_animation));
        play_fab.setAnimation(AnimationUtils.loadAnimation(this,R.anim.scale_animation));





    }





    @Override
    public void onMovieClick(GetVideoDetails movie, ImageView movieImageView) {

        tv_title.setText(movie.getVideo_name());
        //getSupportActionBar().setTitle(movie.getVideo_name());
        Glide.with(this).load(movie.getVideo_thumb()).into(MovieThumbnailImg);
        Glide.with(this).load(movie.getVideo_thumb()).into(MovieCoverImg);
        tv_description.setText(movie.getVideo_description());

        MovieCoverImg.setAnimation(AnimationUtils.loadAnimation(this,R.anim.scale_animation));
        play_fab.setAnimation(AnimationUtils.loadAnimation(this,R.anim.scale_animation));
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MovieDetailNewActivity.this,
                movieImageView,"sharedName");
        options.toBundle();
        current_Video_url = movie.getVideo_url();
        current_video_category = movie.getVideo_category();



    }


}