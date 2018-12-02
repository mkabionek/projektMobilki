package com.winiarza.asystent.asystentwiniarza.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.winiarza.asystent.asystentwiniarza.db.Tables.IngredientTable;
import com.winiarza.asystent.asystentwiniarza.db.Tables.MeasurementTable;
import com.winiarza.asystent.asystentwiniarza.db.Tables.RecipeIngredientsTable;
import com.winiarza.asystent.asystentwiniarza.db.Tables.RecipeTable;

public class OpenHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "recipesDatabase";
    private static final int DATABASE_VERSION = 1;

    public OpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.context = context;
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        IngredientTable.onCreate(db);
        MeasurementTable.onCreate(db);
        RecipeTable.onCreate(db);
        RecipeIngredientsTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        IngredientTable.onUpgrade(db, oldVersion, newVersion);
        MeasurementTable.onUpgrade(db, oldVersion, newVersion);
        RecipeTable.onUpgrade(db, oldVersion, newVersion);
        RecipeIngredientsTable.onUpgrade(db, oldVersion, newVersion);
    }
}
