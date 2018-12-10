package com.winiarza.asystent.asystentwiniarza;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.winiarza.asystent.asystentwiniarza.Models.Ingredient;
import com.winiarza.asystent.asystentwiniarza.Models.Recipe;
import com.winiarza.asystent.asystentwiniarza.db.DataManager;

import java.util.ArrayList;

public class EditRecipeActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener {
        private MyApplication app;
        private DataManager dataManager;
        private Recipe recipe;
        private ArrayList<Ingredient> ingredients;
        private IngredientAdapter adapter;

        private TextView recipeName;
        private TextView editDescription;
        private ListView listView;
        private Button btnSave;
        private static final String id = null;
        CheckBox checkBox;
        @Override
        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);

        checkBox = findViewById(R.id.favorite_checkbox);
        checkBox.setOnCheckedChangeListener(this);
        app = (MyApplication) getApplication();
        dataManager = app.getDataManager();
        long recipeId = getIntent().getLongExtra("recipeId", 0);
        recipe = dataManager.getRecipe(recipeId);

        recipeName = (TextView) findViewById(R.id.NameRecipe);
        editDescription = (TextView) findViewById(R.id.editDescription);
        listView = (ListView) findViewById(R.id.IgredientsList);
        btnSave = (Button) findViewById(R.id.SaveRecipeButton);

        recipeName.setText(recipe.getName());
        editDescription.setText(recipe.getDescription());

        ingredients = recipe.getIngredients();
        adapter = new IngredientAdapter(this, 0, ingredients);
        listView.setAdapter(adapter);

        Button btnSave;
        btnSave = (Button) findViewById(R.id.SaveRecipeButton);

        btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Przepis został zmieniony", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(EditRecipeActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
        });
    }
        @Override
        public void onCheckedChanged (CompoundButton buttonView,boolean isChecked){ }

        @Override
        protected void onPause () {
        super.onPause();
        long recipeId = getIntent().getLongExtra("recipeId", 0);
        SharedPreferences sharedPreferences = getPreferences(0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //editor.putString("CHECK_BOX_VALUE", checkBox.getText().toString());
        editor.putBoolean(Long.toString(recipeId), checkBox.isChecked());
        editor.commit();
    }
        @Override
        protected void onResume () {
        super.onResume();
        long recipeId = getIntent().getLongExtra("recipeId", 0);
        SharedPreferences sharedPreferences = getPreferences(0);
        boolean checkBoxValue = sharedPreferences.getBoolean(Long.toString(recipeId), false);
        checkBox.setChecked(checkBoxValue);

        }
    }

