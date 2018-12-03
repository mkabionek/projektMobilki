package com.winiarza.asystent.asystentwiniarza.db.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.winiarza.asystent.asystentwiniarza.Models.Recipe;
import com.winiarza.asystent.asystentwiniarza.db.Tables.BaseColumns;
import com.winiarza.asystent.asystentwiniarza.db.Tables.RecipeTable;

import java.util.ArrayList;

public class RecipeDAO implements Dao<Recipe> {

    private static final String INSERT =
            "INSERT INTO " + RecipeTable.TABLE_NAME
                    + "(" + RecipeTable.RecipeColumns.NAME + ", "
                    + RecipeTable.RecipeColumns.DESCRIPTION + ") "
                    + " VALUES(?, ?)";

    private SQLiteDatabase db;
    private SQLiteStatement insertStatement;

    public RecipeDAO(SQLiteDatabase db) {
        this.db = db;
        insertStatement = db.compileStatement(INSERT);
    }

    @Override
    public long save(Recipe entity) {
        insertStatement.clearBindings();
        insertStatement.bindString(1, entity.getName());
        insertStatement.bindString(2, entity.getDescription());
        return insertStatement.executeInsert();
    }

    @Override
    public void update(Recipe entity) {
        final ContentValues values = new ContentValues();
        values.put(RecipeTable.RecipeColumns.NAME, entity.getName());
        values.put(RecipeTable.RecipeColumns.DESCRIPTION, entity.getDescription());
        db.update(RecipeTable.TABLE_NAME, values,
                BaseColumns._ID + " = ?", new String[]{String.valueOf(entity.getId())});

    }

    @Override
    public void delete(Recipe entity) {
        if (entity.getId() > 0){
            db.delete(RecipeTable.TABLE_NAME, BaseColumns._ID + "=" + entity.getId(), null);
        }
    }

    @Override
    public Recipe get(long id) {
        Recipe recipe = null;
        Cursor c =
                db.query(
                        RecipeTable.TABLE_NAME,
                        new String[]{
                                BaseColumns._ID, RecipeTable.RecipeColumns.NAME,
                                RecipeTable.RecipeColumns.DESCRIPTION
                        },BaseColumns._ID + " = ?", new String[]{String.valueOf(id)},
                        null, null, null, "1"
                );
        if (c.moveToFirst()){
            recipe = this.buildRecipeFromCursor(c);
        }
        if (!c.isClosed()){
            c.close();
        }


        return recipe;
    }

    @Override
    public ArrayList<Recipe> getAll() {
        ArrayList<Recipe> list = new ArrayList<Recipe>();

        Cursor c =
                db.query(RecipeTable.TABLE_NAME, new String[]{
                                BaseColumns._ID, RecipeTable.RecipeColumns.NAME,
                                RecipeTable.RecipeColumns.DESCRIPTION
                        }, null, null,
                        null, null,
                        RecipeTable.RecipeColumns.DESCRIPTION, null);

        if (c.moveToFirst()){
            do {
                Recipe recipe = this.buildRecipeFromCursor(c);
                if (recipe != null) {
                    list.add(recipe);
                }
            }while (c.moveToNext());
        }

        if (!c.isClosed()){
            c.close();
        }

        return list;
    }

    private Recipe buildRecipeFromCursor(Cursor c){
        Recipe recipe = null;
        if (c != null){
            recipe = new Recipe();
            recipe.setId(c.getLong(0));
            recipe.setName(c.getString(1));
            recipe.setDescription(c.getString(2));
        }
        return recipe;
    }
}
