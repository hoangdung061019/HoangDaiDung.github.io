package com.example.moveeadmin.Activity;

import android.app.ActivityOptions;
import android.os.Bundle;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.moveeadmin.Adapter.MoviesShowAdapter;
import com.example.moveeadmin.Model.GetVideoDetails;
import com.example.moveeadmin.Model.MovieItemClickListenerNew;
import com.example.moveeadmin.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MovieDetailNewActivity extends AppCompatActivity implements MovieItemClickListenerNew {


    private ImageView MovieThumbnailImg,MovieCoverImg;
    private TextView tv_title,tv_description;
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
        similarmoviesRecycler();
        similarMovies();



    }




    public void iniViews() {
        // RvCast = findViewById(R.id.rv_cast);
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
        recycler_similar_movies = findViewById(R.id.recycler_similar_movies);

    }

    public  void similarmoviesRecycler(){
        uploads = new ArrayList<>();
        actionmovies = new ArrayList<>();
        sportmovies = new ArrayList<>();
        advanturemovies = new ArrayList<>();
        comedymovies = new ArrayList<>();
        romanticmovies = new ArrayList<>();

        mDatabaserefence = FirebaseDatabase.getInstance().getReference("videos");


        mDatabaserefence.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    GetVideoDetails upload = postSnapshot.getValue(GetVideoDetails.class);

                    if(upload.getVideo_category().equals("Action")){
                        actionmovies.add(upload);
                    }else if(upload.getVideo_category().equals("Sports")){
                        sportmovies.add(upload);
                    }if(upload.getVideo_category().equals("Adventure")){
                        advanturemovies.add(upload);
                    } else if(upload.getVideo_category().equals("Comedy")){
                        comedymovies.add(upload);
                    }
                    if(upload.getVideo_category().equals("Romantic")){
                        romanticmovies.add(upload);
                    }

                    uploads.add(upload);
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        /*
        moviesShowAdapter = new MoviesShowAdapter(this, uploads,this);
        //adding adapter to recyclerview
        recycler_similar_movies.setAdapter(moviesShowAdapter);
        recycler_similar_movies.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.HORIZONTAL,false));
        moviesShowAdapter.notifyDataSetChanged();

         */
    }

    private void similarMovies(){
        if(current_video_category.equals("Action")) {

            moviesShowAdapter = new MoviesShowAdapter(this, actionmovies, this);
            //adding adapter to recyclerview
            recycler_similar_movies.setAdapter(moviesShowAdapter);
            recycler_similar_movies.setLayoutManager(new LinearLayoutManager(getApplicationContext(),
                    LinearLayoutManager.HORIZONTAL, false));
            moviesShowAdapter.notifyDataSetChanged();
        }else if(current_video_category.equals("Sports")){
            moviesShowAdapter = new MoviesShowAdapter(this, sportmovies, this);
            //adding adapter to recyclerview
            recycler_similar_movies.setAdapter(moviesShowAdapter);
            recycler_similar_movies.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            moviesShowAdapter.notifyDataSetChanged();
        }
        if(current_video_category.equals("Adventure")) {

            moviesShowAdapter = new MoviesShowAdapter(this, advanturemovies, this);
            //adding adapter to recyclerview
            recycler_similar_movies.setAdapter(moviesShowAdapter);
            recycler_similar_movies.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            moviesShowAdapter.notifyDataSetChanged();
        }
        else if(current_video_category.equals("Comedy")){
            moviesShowAdapter = new MoviesShowAdapter(this, comedymovies, this);
            //adding adapter to recyclerview
            recycler_similar_movies.setAdapter(moviesShowAdapter);
            recycler_similar_movies.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            moviesShowAdapter.notifyDataSetChanged();
        } if(current_video_category.equals("Romantic")) {
            moviesShowAdapter = new MoviesShowAdapter(this, romanticmovies, this);
            //adding adapter to recyclerview
            recycler_similar_movies.setAdapter(moviesShowAdapter);
            recycler_similar_movies.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
            moviesShowAdapter.notifyDataSetChanged();
        }


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