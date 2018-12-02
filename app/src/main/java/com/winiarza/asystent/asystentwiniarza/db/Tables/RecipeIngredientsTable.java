package com.winiarza.asystent.asystentwiniarza.db.Tables;

import android.database.sqlite.SQLiteDatabase;

public class RecipeIngredientsTable {
    public static final String TABLE_NAME = "recipe_ingredients";

    public static class RecipeIngredientsColumns {
        public static final String RECIPE_ID = "recipe_id";
        public static final String INGREDIENT_ID = "ingredient_id";
        public static final String MEASUREMENT_ID = "measurement_id";
        public static final String AMOUNT = "amount";
    }

    public static void onCreate(SQLiteDatabase db){
        StringBuilder sb = new StringBuilder();

        sb.append("CREATE TABLE " + TABLE_NAME + "(");
        sb.append(RecipeIngredientsColumns.RECIPE_ID + " INTEGER NOT NULL, ");
        sb.append(RecipeIngredientsColumns.INGREDIENT_ID + " INTEGER NOT NULL, ");
        sb.append(RecipeIngredientsColumns.MEASUREMENT_ID + " INTEGER NOT NULL, ");
        sb.append(RecipeIngredientsColumns.AMOUNT + "  REAL, ");

        sb.append("FOREIGN KEY (" + RecipeIngredientsColumns.RECIPE_ID + ") "
                + "REFERENCES " + RecipeTable.TABLE_NAME + "(" + BaseColumns._ID + "), ");

        sb.append("FOREIGN KEY (" + RecipeIngredientsColumns.INGREDIENT_ID + ") "
                + "REFERENCES " + IngredientTable.TABLE_NAME + "(" + BaseColumns._ID + "), ");

        sb.append("FOREIGN KEY (" + RecipeIngredientsColumns.MEASUREMENT_ID + ") "
                + "REFERENCES " + MeasurementTable.TABLE_NAME + "(" + BaseColumns._ID + ") ");

        sb.append(");");
        db.execSQL(sb.toString());
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + RecipeIngredientsTable.TABLE_NAME);
        RecipeIngredientsTable.onCreate(db);
    }
}
