package com.winiarza.asystent.asystentwiniarza;

import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.winiarza.asystent.asystentwiniarza.Firebase.ModelFirebase.Recipes;
import com.winiarza.asystent.asystentwiniarza.Models.Ingredient;
import com.winiarza.asystent.asystentwiniarza.Models.Measurement;
import com.winiarza.asystent.asystentwiniarza.Models.Recipe;
import com.winiarza.asystent.asystentwiniarza.db.DataManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private MyApplication app;
    private RecipeAdapter adapter;
    private ArrayList<Recipe> recipes;
    private DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        app = (MyApplication) getApplication();
        dataManager = app.getDataManager();

        recipes = dataManager.getRecipes();

        ListView listView = findViewById(R.id.recipes_list);
        adapter = new RecipeAdapter(this, 0, recipes);
        listView.setAdapter(adapter);


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addValueEventListener(new ValueEventListener() {

            int i = 0;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {
                        Recipe recipe = new Recipe();
                        Recipe recipe1 = new Recipe();
                        Measurement measurement = new Measurement();
                        Measurement measurement1 = new Measurement();
                        Ingredient ingredient = new Ingredient();
                        Ingredient ingredient1 = new Ingredient();
                        ArrayList ingredients = new ArrayList<Ingredient>();
                        String product = ds.getKey();
//                        String product = ds.child("opis").getValue(String.class);
                        String nazwa = ds.child("nazwa").getValue(String.class);
                        Recipes rec = ds.child("skladniki").child("0").getValue(Recipes.class);
                        Recipes rec1 = ds.child("skladniki").child("1").getValue(Recipes.class);


                        measurement.setName(rec.getJednostka());
                        ingredient.setMeasurement(measurement);
                        ingredient.setName(rec.getNazwa());
                        ingredient.setAmount(rec.getIlosc());

                        measurement1.setName(rec1.getJednostka());
                        ingredient1.setMeasurement(measurement1);
                        ingredient1.setName(rec1.getNazwa());
                        ingredient1.setAmount(rec1.getIlosc());

                        ingredients.add(ingredient);
                        ingredients.add(ingredient1);
                        recipe.setName(nazwa);
                        recipe.setIngredients(ingredients);

                        //***********************
                        recipes.add(recipe);
                        adapter.notifyDataSetChanged();
                        //****************************

                        Log.d("TAG", "opis:"+nazwa);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });


        //
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
       int id = item.getItemId();

        if (id == R.id.nav_recipe) {
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
//            finish();
        } else if (id == R.id.nav_new_recipe) {
            Intent i = new Intent(this, NewRecipeActivity.class);
            startActivity(i);
//            finish();
        } else if (id == R.id.nav_reminders) {
            Intent i = new Intent(this, RemindersActivity.class);
            startActivity(i);
//            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
