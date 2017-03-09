package com.havrylyuk.countries.activity;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.havrylyuk.countries.R;
import com.havrylyuk.countries.adapter.CountriesRecyclerViewAdapter;
import com.havrylyuk.countries.loader.CountriesLoader;
import com.havrylyuk.countries.model.Country;
import com.havrylyuk.countries.observer.ContentObserver;
import com.havrylyuk.countries.service.CountriesService;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<List<Country>>,
        Observer {

    private static final int LOADER_COUNTRIES = 1;
    private CountriesRecyclerViewAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ContentObserver.getInstance().addObserver(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupRecyclerView();
        setupSwipeRefreshLayout();
        getLoaderManager().initLoader(LOADER_COUNTRIES, null, this);
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(itemAnimator);
        adapter = new CountriesRecyclerViewAdapter(new CountriesRecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(Country country) {
                Toast.makeText(MainActivity.this, "country " + country.getContinentName(), Toast.LENGTH_SHORT).show();
            }
        }, new ArrayList<Country>());
        recyclerView.setAdapter(adapter);
    }

    private void setupSwipeRefreshLayout(){
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_to_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                Intent intent = new Intent(MainActivity.this, CountriesService.class);
                startService(intent);
            }
        });
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<List<Country>> onCreateLoader(int id, Bundle args) {
        if (id == LOADER_COUNTRIES) {
            if (swipeRefreshLayout != null) {
                swipeRefreshLayout.setRefreshing(true);
            }
            if (adapter != null) {
                return new CountriesLoader(this);
            }
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<Country>> loader, List<Country> data) {
        if (loader.getId() == LOADER_COUNTRIES) {
            if (swipeRefreshLayout != null) {
                swipeRefreshLayout.setRefreshing(false);
            }
            if (adapter != null) {
                adapter.setCountryList(data);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Country>> loader) {
        if (loader.getId() == LOADER_COUNTRIES) {
            if (adapter != null) {
                adapter.setCountryList(null);
            }
        }
    }

    @Override
    protected void onDestroy() {
        ContentObserver.getInstance().deleteObserver(this);
        super.onDestroy();
    }

    @Override
    public void update(Observable o, Object arg) {
        getLoaderManager().restartLoader(LOADER_COUNTRIES, null, this);
    }
}
