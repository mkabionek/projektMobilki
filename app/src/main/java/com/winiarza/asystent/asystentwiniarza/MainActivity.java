package com.winiarza.asystent.asystentwiniarza;

import android.content.Intent;
import android.os.Bundle;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

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


    private RecipeAdapter adapter;
    private ArrayList<Recipe> recipes;
    private MyApplication app;
    private DataManager dataManager;
    private ListView listView;

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
        adapter = new RecipeAdapter(this, android.R.layout.select_dialog_item, recipes);
        listView.setAdapter(adapter);


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.addValueEventListener(new ValueEventListener() {

            int i = 0;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot ds : dataSnapshot.getChildren()) {

                    Iterable<DataSnapshot> iterable = ds.child("skladniki").getChildren();
                    String opis = ds.child("opis").getValue(String.class);
                    String nazwa = ds.child("nazwa").getValue(String.class);


                    Measurement measurement;
                    Ingredient ingredient;
                    ArrayList ingredients = new ArrayList<Ingredient>();

                    for (DataSnapshot d: iterable){
                        Recipes rec = d.getValue(Recipes.class);
                        measurement = new Measurement();
                        measurement.setName(rec.getJednostka());
                        ingredient = new Ingredient();
                        ingredient.setMeasurement(measurement);
                        ingredient.setName(rec.getNazwa());
                        ingredient.setAmount(rec.getIlosc());
                        ingredients.add(ingredient);
                    }

                    Recipe recipe = new Recipe();
                    recipe.setIngredients(ingredients);
                    recipe.setDescription(opis);
                    recipe.setName(nazwa);

                    recipes.add(recipe);
                    adapter.notifyDataSetChanged();

                    Log.d("TAG", "opis:" + opis);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        listView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Recipe recipe = (Recipe) parent.getItemAtPosition(position);
                        if(recipe.getId() != 0){
                            Intent i = new Intent(MainActivity.this, EditRecipeActivity.class);
                            i.putExtra("recipeId", recipe.getId());
                            startActivity(i);
                        }

                    }
                }
        );

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View arg1, int position, long id) {
                final Recipe recipe = (Recipe) parent.getItemAtPosition(position);

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_recipe_remove, null);
                Button btn = (Button) mView.findViewById(R.id.remove_recipe_button);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        app.getDataManager().deleteRecipe(recipe.getId());
                        Toast.makeText(MainActivity.this, R.string.recipe_removed_info, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(i);
                        dialog.dismiss();
                    }
                });
                dialog.show();
                return true;
            }
        });

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
