package com.winiarza.asystent.asystentwiniarza.db;

public class RecipeIngredientsKey {
    private long recipeId;
    private long ingredientId;
    private long measurementId;
    private float amount;

    public RecipeIngredientsKey(long recipeId, long ingredientId, long measurementId,float amount) {
        this.recipeId = recipeId;
        this.ingredientId = ingredientId;
        this.measurementId = measurementId;
        this.amount = amount;
    }

    public long getRecipeId() {
        return recipeId;
    }

    public long getIngredientId() {
        return ingredientId;
    }

    public long getMeasurementId() {
        return measurementId;
    }

    public float getAmount() {
        return amount;
    }
}
