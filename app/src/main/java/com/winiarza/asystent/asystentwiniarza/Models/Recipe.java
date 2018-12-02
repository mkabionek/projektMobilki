package com.winiarza.asystent.asystentwiniarza.Models;

import java.util.ArrayList;

public class Recipe {
    private long id;
    private String name;
    private String Description;
    private ArrayList<Ingredient> ingredients;

    public Recipe() {
        this.ingredients = new ArrayList<Ingredient>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
