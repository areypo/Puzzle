package com.manytiles.p8.scoreManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ScoreManager {
    private final static String TABLE_NAME = "scores";
    private final static String PUNTUACION_COL = "puntuacion";
    private final static String DATE_COL = "date";
    private final static String DIFICULTAD_COL = "dificultad";

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());
    private Context context;
    private AdminSQLiteOpenHelper adminSQLiteOpenHelper;

    public ScoreManager(Context context) {
        this.context = context;
        this.adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(context, "puzzle", null, 1);

    }

    public void actualizarBd(){
        SQLiteDatabase database = adminSQLiteOpenHelper.getWritableDatabase();
        adminSQLiteOpenHelper.onUpgrade(database,1,1);
    }

    private SQLiteDatabase getSqLiteDatabase(Context context) {
        SQLiteDatabase database = adminSQLiteOpenHelper.getWritableDatabase();
        return database;
    }

    public Score addScore(long puntuacion, int dificultad) {
        SQLiteDatabase database = getSqLiteDatabase(this.context);
        ContentValues contentValues = new ContentValues();
        Date now = new Date();
        contentValues.put(DATE_COL, dateFormat.format(now));
        contentValues.put(PUNTUACION_COL, puntuacion);
        contentValues.put(DIFICULTAD_COL, dificultad);
        long id = database.insert("scores", null, contentValues);
        database.close();
        return new Score((int) id, now, puntuacion);
    }

    public ArrayList<Score> getAllScores() {
        SQLiteDatabase database = getSqLiteDatabase(this.context);
        Cursor cursor = database.rawQuery("SELECT id, date, puntuacion FROM scores ORDER BY time DESC", null);
        ArrayList<Score> scores = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(0);
                int time = cursor.getInt(2);
                try {
                    Date date = dateFormat.parse(cursor.getString(1));
                    scores.add(new Score(id, date, time));
                } catch (ParseException parseException) {
                    scores.add(new Score(id, null, time));
                }
                cursor.moveToNext();
            }
        }
        database.close();

        return scores;
    }

    public List<Score> getBetterScores(int dificultad, int limit) {
        SQLiteDatabase database = getSqLiteDatabase(this.context);

        String[] selectionArg = new String[]{String.valueOf(dificultad), String.valueOf(limit)};
        Cursor cursor = database.rawQuery("SELECT * FROM scores WHERE dificultad = ? ORDER BY puntuacion DESC LIMIT ?", selectionArg);
        ArrayList<Score> scores = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id  = cursor.getInt(0);
                String fecha = cursor.getString(cursor.getColumnIndex(DATE_COL));
                int puntuacion = cursor.getInt(cursor.getColumnIndex(PUNTUACION_COL));
                try {
                    Date date = dateFormat.parse(fecha);
                    scores.add(new Score(id, date, puntuacion));
                } catch (ParseException parseException) {
                    scores.add(new Score(id, null, puntuacion));
                }
                cursor.moveToNext();
            }
        }
        database.close();

        return scores;
    }

    public Score getScore(Context context, int id) {
        if (id >= 0) {
            SQLiteDatabase database = getSqLiteDatabase(context);
            Cursor cursor = database.rawQuery("SELECT rowid, date, time FROM scores WHERE rowid=" + id, null);
            Score score = null;
            if (cursor.moveToFirst()) {
                Integer idDb = cursor.getInt(0);
                Integer time = cursor.getInt(2);
                try {
                    Date date = dateFormat.parse(cursor.getString(1));
                    score = new Score(idDb, date, time);
                } catch (ParseException parseException) {
                    score = new Score(id, null, time);
                }
            }
            database.close();

            return score;
        } else {
            return null;
        }
    }

    public Score updateScore(Context context, int id, Date date, int time) {
        if (id > 0 && date != null && time >= 0) {
            SQLiteDatabase database = getSqLiteDatabase(context);
            ContentValues contentValues = new ContentValues();
            contentValues.put("date", dateFormat.format(date));
            contentValues.put("time", time);
            int updatedRows = database.update("scores", contentValues, "rowid=" + id, null);
            database.close();

            if (updatedRows == 1) {
                return new Score(id, date, time);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public void removeScore(Context context, int id) {
        SQLiteDatabase database = getSqLiteDatabase(context);
        database.delete("scores", "rowid =" + id, null);
        database.close();
    }

    public void purgeScore(Context context) {
        SQLiteDatabase database = getSqLiteDatabase(context);
        database.delete("scores", null, null);
        database.close();
    }

    public void printScore(Context context) {
        ArrayList<Score> ALL_SCORES = this.getAllScores();
        System.out.println("SCORE SIZE: " + ALL_SCORES.size());
        for (Score score : ALL_SCORES) {
            System.out.println(score.toString());
        }
    }
}
