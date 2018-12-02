package com.winiarza.asystent.asystentwiniarza.db.Tables;

import android.database.sqlite.SQLiteDatabase;

public class MeasurementTable implements BaseColumns {
    public static final String TABLE_NAME = "measurement";

    public static class MeasurementColumns implements BaseColumns {
        public static final String NAME = "name";
    }

    public static void onCreate(SQLiteDatabase db){
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE " + MeasurementTable.TABLE_NAME + " (");
        sb.append(BaseColumns._ID + " INTEGER PRIMARY KEY, ");
        sb.append(MeasurementColumns.NAME + " TEXT);");
        db.execSQL(sb.toString());
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + MeasurementTable.TABLE_NAME);
        MeasurementTable.onCreate(db);
    }
}
