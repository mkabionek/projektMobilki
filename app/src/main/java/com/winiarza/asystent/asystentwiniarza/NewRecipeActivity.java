package com.winiarza.asystent.asystentwiniarza;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.winiarza.asystent.asystentwiniarza.Models.Ingredient;
import com.winiarza.asystent.asystentwiniarza.Models.Measurement;
import com.winiarza.asystent.asystentwiniarza.Models.Recipe;
import com.winiarza.asystent.asystentwiniarza.db.DataManager;

import java.util.ArrayList;

public class NewRecipeActivity extends AppCompatActivity {
    private ArrayList<Ingredient> ingredients;
    private IngredientAdapter adapter;
    private EditText editIgredientName;
    private EditText editIngredientAmount;
    private EditText editMeasurement;
    private EditText nameRecipeEditText;
    private EditText descriptionRecipe;
    private Button btnSave;
    private MyApplication app;
    private DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);
        app = (MyApplication) getApplication();
        dataManager = app.getDataManager();

        ListView listView = (ListView) findViewById(R.id.IgredientsList);
        btnSave = (Button)findViewById(R.id.saveRecipeButton);

        ingredients = new ArrayList<Ingredient>();
        adapter = new IngredientAdapter(this, 0, ingredients);
        listView.setAdapter(adapter);

        editIgredientName = findViewById(R.id.editIgredientName);
        editIngredientAmount = findViewById(R.id.editIngredientAmount);
        editMeasurement = findViewById(R.id.editMeasurement);
        nameRecipeEditText = findViewById(R.id.NameRecipeEditText);
        descriptionRecipe = findViewById(R.id.descriptionRecipeEditText);

        Button addIngredient = findViewById(R.id.addIngredient);

        addIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Measurement m = new Measurement();
                m.setName(editMeasurement.getText().toString());

                Ingredient i = new Ingredient();
                i.setMeasurement(m);
                i.setAmount(Float.valueOf(editIngredientAmount.getText().toString()));
                i.setName(editIgredientName.getText().toString());
                ingredients.add(i);
                adapter.notifyDataSetChanged();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Recipe recipe = new Recipe();
                recipe.setName(nameRecipeEditText.getText().toString());
                recipe.setIngredients(ingredients);
                recipe.setDescription(descriptionRecipe.getText().toString());
                dataManager.saveRecipe(recipe);
            }
        });

//        btnSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "Przepis został utworzony", Toast.LENGTH_SHORT).show();
//                Intent i = new Intent(NewRecipeActivity.this, MainActivity.class);
//                startActivity(i);
//                finish();
//            }
//        });
    }
}
