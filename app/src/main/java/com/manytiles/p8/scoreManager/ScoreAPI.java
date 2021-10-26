package com.manytiles.p8.scoreManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ScoreAPI {

    private static SimpleDateFormat databaseDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());

    private static SQLiteDatabase getSqLiteDatabase(Context context) {
        AdminSQLiteOpenHelper adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(context, "puzzle", null, 1);

        return adminSQLiteOpenHelper.getWritableDatabase();
    }

    public static Score addScore(Context context, int time) {
        if (time >= 0) {
            SQLiteDatabase database = getSqLiteDatabase(context);
            ContentValues contentValues = new ContentValues();
            Date now = new Date();
            contentValues.put("date", databaseDateFormat.format(now));
            contentValues.put("time", time);
            long id = database.insert("scores", null, contentValues);
            database.close();

            return new Score((int) id, now, time);
        } else {
            return null;
        }
    }

    public static ArrayList<Score> getAllScores(Context context) {
        SQLiteDatabase database = getSqLiteDatabase(context);
        Cursor cursor = database.rawQuery("SELECT rowid, date, time FROM scores ORDER BY time DESC", null);
        ArrayList<Score> scores = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(0);
                int time = cursor.getInt(2);
                try {
                    Date date = databaseDateFormat.parse(cursor.getString(1));
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

    public static Score getScore(Context context, int id) {
        if (id >= 0) {
            SQLiteDatabase database = getSqLiteDatabase(context);
            Cursor cursor = database.rawQuery("SELECT rowid, date, time FROM scores WHERE rowid=" + id, null);
            Score score = null;
            if (cursor.moveToFirst()) {
                Integer idDb = cursor.getInt(0);
                Integer time = cursor.getInt(2);
                try {
                    Date date = databaseDateFormat.parse(cursor.getString(1));
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

    public static Score updateScore(Context context, int id, Date date, int time) {
        if (id > 0 && date != null && time >= 0) {
            SQLiteDatabase database = getSqLiteDatabase(context);
            ContentValues contentValues = new ContentValues();
            contentValues.put("date", databaseDateFormat.format(date));
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

    public static void removeScore(Context context, int id) {
        SQLiteDatabase database = getSqLiteDatabase(context);
        database.delete("scores", "rowid =" + id, null);
        database.close();
    }

    public static void purgeScore(Context context) {
        SQLiteDatabase database = getSqLiteDatabase(context);
        database.delete("scores", null, null);
        database.close();
    }

    public static void printScore(Context context) {
        ArrayList<Score> ALL_SCORES = ScoreAPI.getAllScores(context);
        System.out.println("SCORE SIZE: " + ALL_SCORES.size());
        for (Score score : ALL_SCORES) {
            System.out.println(score.toString());
        }
    }
}
