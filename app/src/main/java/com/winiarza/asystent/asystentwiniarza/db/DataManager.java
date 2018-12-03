package com.winiarza.asystent.asystentwiniarza.db;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.winiarza.asystent.asystentwiniarza.Models.Ingredient;
import com.winiarza.asystent.asystentwiniarza.Models.Measurement;
import com.winiarza.asystent.asystentwiniarza.Models.Recipe;
import com.winiarza.asystent.asystentwiniarza.db.DAO.IngredientDAO;
import com.winiarza.asystent.asystentwiniarza.db.DAO.MeasurementsDAO;
import com.winiarza.asystent.asystentwiniarza.db.DAO.RecipeDAO;
import com.winiarza.asystent.asystentwiniarza.db.DAO.RecipeIngredientsDAO;

import java.util.ArrayList;

public class DataManager implements IDataManager {

    private Context context;
    private SQLiteDatabase db;
    private IngredientDAO ingredientDAO;
    private MeasurementsDAO measurementsDAO;
    private RecipeIngredientsDAO recipeIngredientsDAO;
    private RecipeDAO recipeDAO;

    public DataManager(Context context) {
        this.context = context;

        SQLiteOpenHelper openHelper = new OpenHelper(this.context);
        db = openHelper.getWritableDatabase();

        ingredientDAO = new IngredientDAO(db);
        measurementsDAO = new MeasurementsDAO(db);
        recipeIngredientsDAO = new RecipeIngredientsDAO(db);
        recipeDAO = new RecipeDAO(db);
    }

    @Override
    public Recipe getRecipe(long recipeId) {
        Recipe recipe = recipeDAO.get(recipeId);

        if (recipe != null){
            ArrayList<RecipeIngredientsKey> keys =
                    recipeIngredientsDAO.getRecipeIngredientsKeys(recipe.getId());
            ArrayList<Ingredient> ingredients = getIngredientsList(keys);
            recipe.getIngredients().addAll(ingredients);
        }

        return recipe;
    }

    @Override
    public ArrayList<Recipe> getRecipes() {
        return recipeDAO.getAll();
    }

    @Override
    public long saveRecipe(Recipe recipe) {
        long recipeId = 0L;

        try{
            db.beginTransaction();
            recipeId = recipeDAO.save(recipe);

            if(recipe.getIngredients().size() > 0 ){
                for(Ingredient ingredient : recipe.getIngredients() ){
                    long ingredientId = 0L;
                    long measurementId = 0L;
                    ingredientId = ingredientDAO.save(ingredient);
                    measurementId = measurementsDAO.save(ingredient.getMeasurement());
                    RecipeIngredientsKey key = new RecipeIngredientsKey(
                            recipeId,
                            ingredientId,
                            measurementId,
                            ingredient.getAmount());
                    recipeIngredientsDAO.save(key);
                }
            }
            db.setTransactionSuccessful();
        }catch (SQLException e){
            Log.d("SQL_MY_ERROR", "Błąd przy zapisie przepisu - anulowano transakcję.",
                    e);
            recipeId = 0L;
        }finally {
            db.endTransaction();
        }

        return recipeId;
    }

    @Override
    public boolean deleteRecipe(long recipeId) {

            recipeDAO.delete(recipeDAO.get(recipeId));

            return true;
    }

    @Override
    public Measurement getMeasurement(long id) {
        return measurementsDAO.get(id);
    }

    @Override
    public long saveMeasurement(Measurement measurement) {
        return measurementsDAO.save(measurement);
    }

    @Override
    public Ingredient getIngredient(long id) {
        return ingredientDAO.get(id);
    }

    @Override
    public ArrayList<Ingredient> getIngredients() {
        return ingredientDAO.getAll();
    }

    @Override
    public ArrayList<Ingredient> getIngredients(long recipeId) {
        // TODO
        return null;
    }

    @Override
    public long saveIngredient(Ingredient ingredient) {
        return ingredientDAO.save(ingredient);
    }

    private ArrayList<Ingredient>getIngredientsList(ArrayList<RecipeIngredientsKey> keys){
        ArrayList<Ingredient> list = new ArrayList<Ingredient>();

        for (RecipeIngredientsKey key: keys ) {
            Ingredient ingredient = getIngredient(key.getIngredientId());
            ingredient.setMeasurement(getMeasurement(key.getMeasurementId()));
            ingredient.setAmount(key.getAmount());
            list.add(ingredient);
        }

        return list;
    }
}
