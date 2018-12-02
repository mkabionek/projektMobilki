package com.winiarza.asystent.asystentwiniarza.db.DAO;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.winiarza.asystent.asystentwiniarza.db.RecipeIngredientsKey;
import com.winiarza.asystent.asystentwiniarza.db.Tables.RecipeIngredientsTable;
import com.winiarza.asystent.asystentwiniarza.db.Tables.RecipeTable;

import java.util.ArrayList;

public class RecipeIngredientsDAO {
    private static final String INSERT =
            "INSERT INTO " + RecipeIngredientsTable.TABLE_NAME + " ( "
            + RecipeIngredientsTable.RecipeIngredientsColumns.RECIPE_ID + ", "
            + RecipeIngredientsTable.RecipeIngredientsColumns.INGREDIENT_ID + ", "
            + RecipeIngredientsTable.RecipeIngredientsColumns.MEASUREMENT_ID + ", "
            + RecipeIngredientsTable.RecipeIngredientsColumns.AMOUNT + " ) VALUES (?,?,?,?)";

    private SQLiteDatabase db;
    private SQLiteStatement insertStatement;


    public RecipeIngredientsDAO(SQLiteDatabase db) {
        this.db = db;
        insertStatement = db.compileStatement(INSERT);
    }

    public ArrayList<RecipeIngredientsKey> getRecipeIngredientsKeys(long recipeId){
        ArrayList<RecipeIngredientsKey> list = new ArrayList<RecipeIngredientsKey>();

        String sql = "select * from " + RecipeIngredientsTable.TABLE_NAME
                + " where " + RecipeIngredientsTable.RecipeIngredientsColumns.RECIPE_ID +" = ?;";

        Cursor c = db.rawQuery(sql, new String[]{
                String.valueOf(recipeId)
        });

        if (c.moveToFirst()){
            do {
                RecipeIngredientsKey recipeKey = new RecipeIngredientsKey(
                            c.getLong(0),
                            c.getLong(1),
                            c.getLong(2),
                            c.getFloat(3));
                if (recipeKey != null){
                    list.add(recipeKey);
                }
            } while(c.moveToNext());
        }
        if (!c.isClosed()){
            c.close();
        }

        return list;

    }

    // TODO
    // delete
    // exists

    public long save(RecipeIngredientsKey entity){
        insertStatement.clearBindings();
        insertStatement.bindLong(1, entity.getRecipeId());
        insertStatement.bindLong(2, entity.getIngredientId());
        insertStatement.bindLong(3, entity.getMeasurementId());
        insertStatement.bindDouble(4, entity.getAmount());
        return insertStatement.executeInsert();
    }

}

