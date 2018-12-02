package com.winiarza.asystent.asystentwiniarza.db.Tables;

import android.database.sqlite.SQLiteDatabase;

public class RecipeTable implements BaseColumns {
    public static final String TABLE_NAME = "recipe";

    public static class RecipeColumns {
        public static final String NAME = "name";
        public static final String DESCRIPTION = "description";
    }

    public static void onCreate(SQLiteDatabase db){
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + RecipeTable.TABLE_NAME + "(");
        sb.append(BaseColumns._ID + " INTEGER PRIMARY KEY, ");
        sb.append(RecipeColumns.NAME + " TEXT,");
        sb.append(RecipeColumns.DESCRIPTION + " TEXT);");
        db.execSQL(sb.toString());
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + RecipeTable.TABLE_NAME);
        RecipeTable.onCreate(db);
    }
}
