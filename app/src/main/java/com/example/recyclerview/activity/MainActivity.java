package com.example.recyclerview.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.recyclerview.model.Movie;
import com.example.recyclerview.adapter.MyAdapter;
import com.example.recyclerview.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Movie> movies;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movies = this.getAllMovies();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(this);

        //If you want to use GridLayoutManager, uncomment this
        //mLayoutManager = new GridLayoutManager(this, 2);

        //If you want to use StaggeredGridLayoutManager, uncomment this
        //mLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL); //recomendable para fotos que son de distintos tamaños

        mAdapter = new MyAdapter(movies, R.layout.recycler_view_item, new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Movie movie, int position) {
                //Toast.makeText(MainActivity.this, name + " - " + position, Toast.LENGTH_LONG).show();
                removeMovie(position);
            }
        });

        //this is used if the RecyclerView doesn't resize
        mRecyclerView.setHasFixedSize(true);
        //add a default effect, is disabled with "null"
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        //We link the LayoutManager and the adapter to the RecyclerView
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_name:
                this.addMovie(0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private List<Movie> getAllMovies(){
        return new ArrayList<Movie>(){{
            add(new Movie("Ant-Man", R.drawable.ant_man));
            add(new Movie("John Wick 4", R.drawable.john_wick_4));
            add(new Movie("Super Mario Bros", R.drawable.super_mario));
            add(new Movie("Guardians of the Galaxy", R.drawable.guardians_galaxy));
            add(new Movie("Spider-Man", R.drawable.spiderman_across_universe));
            add(new Movie("Transformers", R.drawable.transformers));
        }};
    }

    private void addMovie(int position) {
        movies.add(position, new Movie("New Movie " +(++counter), R.drawable.coming_soon) );
        //We notify of a new item inserted in the collection
        mAdapter.notifyItemInserted(position);
        //we scroll to the new element
        mLayoutManager.scrollToPosition(position);
    }

    private void removeMovie(int position) {
        movies.remove(position);
        //We notify of a new item deleted in the collection
        mAdapter.notifyItemRemoved(position);
    }
}