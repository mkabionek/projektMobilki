package com.winiarza.asystent.asystentwiniarza.Firebase.ModelFirebase;

import java.util.LinkedList;
import java.util.List;

public class Product {
    private String description;
    private List<Recipes> recipies;
    public Product(){

    }
    public Product(String description, List<Recipes> recipies) {
        this.description = description;
        this.recipies = recipies;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Recipes> getRecipies() {
        return recipies;
    }

    public void setRecipies(List<Recipes> recipies) {
        this.recipies = recipies;
    }


}
