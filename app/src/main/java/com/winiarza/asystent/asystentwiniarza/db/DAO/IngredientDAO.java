package com.winiarza.asystent.asystentwiniarza.db.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.winiarza.asystent.asystentwiniarza.Models.Ingredient;
import com.winiarza.asystent.asystentwiniarza.db.Tables.BaseColumns;
import com.winiarza.asystent.asystentwiniarza.db.Tables.IngredientTable;

import java.util.ArrayList;

public class IngredientDAO implements Dao<Ingredient> {

    private static final String INSERT =
            "INSERT INTO " + IngredientTable.TABLE_NAME + " ( "
                    + IngredientTable.IngredientColumns.NAME + " ) VALUES (?) ";

    private SQLiteDatabase db;
    private SQLiteStatement insertStatement;

    public IngredientDAO(SQLiteDatabase db) {
        this.db = db;
        insertStatement = db.compileStatement(INSERT);
    }

    @Override
    public long save(Ingredient entity) {
        insertStatement.clearBindings();
        insertStatement.bindString(1, entity.getName());
        return insertStatement.executeInsert();
    }

    @Override
    public void update(Ingredient entity) {
        final ContentValues values = new ContentValues();
        values.put(IngredientTable.IngredientColumns.NAME, entity.getName());
        db.update(IngredientTable.TABLE_NAME, values,
                BaseColumns._ID + " = ?", new String[]{ String.valueOf(entity.getId()) });
    }

    @Override
    public void delete(Ingredient entity) {
        if (entity.getId() > 0){
            db.delete(IngredientTable.TABLE_NAME,
                    BaseColumns._ID + " = ?",
                    new String[]{ String.valueOf(entity.getId()) });
        }
    }

    @Override
    public Ingredient get(long id) {
        Ingredient ingredient = null;

        Cursor c = db.query(
                IngredientTable.TABLE_NAME,
                new String[]{ BaseColumns._ID, IngredientTable.IngredientColumns.NAME },
                BaseColumns._ID + " = ?", new String[]{ String.valueOf(id) },
                null, null,null, "1"
        );

        if (c.moveToFirst()){
            ingredient = buildIngredientFromCursor(c);
        }
        if (!c.isClosed()){
            c.close();
        }

        return ingredient;
    }

    @Override
    public ArrayList<Ingredient> getAll() {
        ArrayList<Ingredient> list = new ArrayList<Ingredient>();

        Cursor c = db.query(IngredientTable.TABLE_NAME,
                new String[]{BaseColumns._ID,IngredientTable.IngredientColumns.NAME},
                null, null, null,
                null, null, null);

        if (c.moveToFirst()){
            do {
                Ingredient ingredient = this.buildIngredientFromCursor(c);
                if (ingredient != null){
                    list.add(ingredient);
                }
            }while(c.moveToNext());
        }

        return list;
    }

    private Ingredient buildIngredientFromCursor(Cursor c){
        Ingredient ingredient = null;
        if (c != null){
            ingredient = new Ingredient();
            ingredient.setId(c.getLong(0));
            ingredient.setName(c.getString(1));
        }
        return ingredient;
    }

    // TODO
    // add find ingredient method
}
