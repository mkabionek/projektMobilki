package com.winiarza.asystent.asystentwiniarza.db.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.winiarza.asystent.asystentwiniarza.Models.Measurement;
import com.winiarza.asystent.asystentwiniarza.db.Tables.BaseColumns;
import com.winiarza.asystent.asystentwiniarza.db.Tables.MeasurementTable;

import java.util.ArrayList;

public class MeasurementsDAO implements Dao<Measurement> {

    private static final String INSERT =
            "INSERT INTO " + MeasurementTable.TABLE_NAME + " ( "
                    + MeasurementTable.MeasurementColumns.NAME + " ) VALUES (?) ";

    private SQLiteDatabase db;
    private SQLiteStatement insertStatement;

    public MeasurementsDAO(SQLiteDatabase db) {
        this.db = db;
        insertStatement = db.compileStatement(INSERT);
    }


    @Override
    public long save(Measurement entity) {
        insertStatement.clearBindings();
        insertStatement.bindString(1, entity.getName());
        return insertStatement.executeInsert();
    }

    @Override
    public void update(Measurement entity) {
        final ContentValues values = new ContentValues();
        values.put(MeasurementTable.MeasurementColumns.NAME, entity.getName());
        db.update(MeasurementTable.TABLE_NAME, values,
                BaseColumns._ID + " = ?", new String[]{ String.valueOf(entity.getId()) });
    }

    @Override
    public void delete(Measurement entity) {
        if (entity.getId() > 0){
            db.delete(MeasurementTable.TABLE_NAME,
                    BaseColumns._ID + " = ?",
                    new String[]{ String.valueOf(entity.getId()) });
        }
    }

    @Override
    public Measurement get(long id) {
        Measurement measurement = null;

        Cursor c = db.query(
                MeasurementTable.TABLE_NAME,
                new String[]{ BaseColumns._ID, MeasurementTable.MeasurementColumns.NAME },
                BaseColumns._ID + " = ?", new String[]{ String.valueOf(id) },
                null, null,null, "1"
        );

        if (c.moveToFirst()){
            measurement = buildIngredientFromCursor(c);
        }
        if (!c.isClosed()){
            c.close();
        }

        return measurement;
    }

    @Override
    public ArrayList<Measurement> getAll() {
        ArrayList<Measurement> list = new ArrayList<Measurement>();

        Cursor c = db.query(MeasurementTable.TABLE_NAME,
                new String[]{BaseColumns._ID,MeasurementTable.MeasurementColumns.NAME},
                null, null, null,
                null, null, null);

        if (c.moveToFirst()){
            do {
                Measurement measurement = this.buildIngredientFromCursor(c);
                if (measurement != null){
                    list.add(measurement);
                }
            }while(c.moveToNext());
        }

        return list;
    }

    private Measurement buildIngredientFromCursor(Cursor c){
        Measurement measurement = null;
        if (c != null){
            measurement = new Measurement();
            measurement.setId(c.getLong(0));
            measurement.setName(c.getString(1));
        }
        return measurement;
    }

    // TODO
    // add find measurement method
}
