package com.example.materialdesign;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Toast;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.core.view.GravityCompat;

import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;

    private Fruit[] fruits = {new Fruit("Apple", R.drawable.apple), new Fruit("Banana", R.drawable.banana),
            new Fruit("Orange", R.drawable.orange), new Fruit("Watermelon", R.drawable.watermelon),
            new Fruit("Pear", R.drawable.pear), new Fruit("Grape", R.drawable.grape),
            new Fruit("Pineapple", R.drawable.pineapple), new Fruit("Strawberry", R.drawable.strawberry),
            new Fruit("Cherry", R.drawable.cherry), new Fruit("Mango", R.drawable.mango)};

    private List<Fruit> fruitList = new ArrayList<>();

    private FruitAdapter adapter;

    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout =  findViewById(R.id.drawer_layout);
        NavigationView navView =  findViewById(R.id.nav_view);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
        navView.setCheckedItem(R.id.nav_call);
        navView.setNavigationItemSelectedListener(item -> {
            mDrawerLayout.closeDrawers();
            return true;
        });
        FloatingActionButton fab =  findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Data deleted", Snackbar.LENGTH_SHORT)
                .setAction("Undo", v -> Toast.makeText(MainActivity.this, "Data restored", Toast.LENGTH_SHORT).show())
                .show());
        initFruits();
        RecyclerView recyclerView =  findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new FruitAdapter(fruitList);
        recyclerView.setAdapter(adapter);
        swipeRefresh =  findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(() -> refreshFruits());
    }

    private void refreshFruits() {
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runOnUiThread(() -> {
                initFruits();
                adapter.notifyDataSetChanged();
                swipeRefresh.setRefreshing(false);
            });
        }).start();
    }

    private void initFruits() {
        fruitList.clear();
        for (int i = 0; i < 50; i++) {
            Random random = new Random();
            int index = random.nextInt(fruits.length);
            fruitList.add(fruits[index]);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
        } else if (itemId == R.id.backup) {
            Toast.makeText(this, "You clicked Backup", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.delete) {
            Toast.makeText(this, "You clicked Delete", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.settings) {
            Toast.makeText(this, "You clicked Settings", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

}
