package com.winiarza.asystent.asystentwiniarza;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.winiarza.asystent.asystentwiniarza.Models.Ingredient;
import com.winiarza.asystent.asystentwiniarza.Models.Recipe;
import com.winiarza.asystent.asystentwiniarza.db.DataManager;

import java.util.ArrayList;

public class EditRecipeActivity extends AppCompatActivity {
    private MyApplication app;
    private DataManager dataManager;
    private Recipe recipe;
    private ArrayList<Ingredient> ingredients;
    private IngredientAdapter adapter;

    private TextView recipeName ;
    private TextView editDescription ;
    private ListView listView;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_recipe);

        app = (MyApplication) getApplication();
        dataManager = app.getDataManager();
        long recipeId = getIntent().getLongExtra("recipeId", 0);
        recipe = dataManager.getRecipe(recipeId);

        recipeName = (TextView) findViewById(R.id.NameRecipe);
        editDescription = (TextView) findViewById(R.id.editDescription);
        listView = (ListView) findViewById(R.id.IgredientsList);
        btnSave = (Button)findViewById(R.id.SaveRecipeButton);

        recipeName.setText(recipe.getName());
        editDescription.setText(recipe.getDescription());

        ingredients = recipe.getIngredients();
        adapter = new IngredientAdapter(this, 0, ingredients);
        listView.setAdapter(adapter);

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
}
