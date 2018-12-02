package com.winiarza.asystent.asystentwiniarza.db;

import com.winiarza.asystent.asystentwiniarza.Models.Ingredient;
import com.winiarza.asystent.asystentwiniarza.Models.Measurement;
import com.winiarza.asystent.asystentwiniarza.Models.Recipe;

import java.util.ArrayList;

public interface IDataManager {
    Recipe getRecipe(long recipeId);
    ArrayList<Recipe> getRecipes();
    long saveRecipe(Recipe recipe);
    boolean deleteRecipe(long recipeId);

    Measurement getMeasurement(long id);
    long saveMeasurement(Measurement measurement);

    Ingredient getIngredient(long id);
    ArrayList<Ingredient> getIngredients();
    ArrayList<Ingredient> getIngredients(long recipeId);
    long saveIngredient(Ingredient ingredient);

}
