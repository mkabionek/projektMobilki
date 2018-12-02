package com.winiarza.asystent.asystentwiniarza.db.Tables;

import android.database.sqlite.SQLiteDatabase;

public class IngredientTable implements BaseColumns {
    public static final String TABLE_NAME = "ingredient";

    public static class IngredientColumns implements BaseColumns {
        public static final String NAME = "name";
    }

    public static void onCreate(SQLiteDatabase db){
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + IngredientTable.TABLE_NAME + " (");
        sb.append(BaseColumns._ID + " INTEGER PRIMARY KEY, ");
        sb.append(IngredientColumns.NAME + " TEXT);");
        db.execSQL(sb.toString());
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + IngredientTable.TABLE_NAME);
        IngredientTable.onCreate(db);
    }
}
