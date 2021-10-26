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

    private final static String TABLE_NAME = "scores";
    private final static String ID_COL = "id";
    private final static String DATE_COL = "date";
    private final static String SCORE_COL = "score";
    private final static String LEVEL_COL = "level";

    private AdminSQLiteOpenHelper adminSQLiteOpenHelper;

    private static SimpleDateFormat databaseDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault());

    public ScoreAPI(Context context) {
        this.adminSQLiteOpenHelper = new AdminSQLiteOpenHelper(context, "puzzle", null, 1);
    }

    private SQLiteDatabase getSqLiteDatabase() {
        return adminSQLiteOpenHelper.getWritableDatabase();
    }

    public Score addScore(long score, int level) {
        if (score >= 0 && level > 0) {
            SQLiteDatabase database = getSqLiteDatabase();
            ContentValues contentValues = new ContentValues();
            Date now = new Date();
            contentValues.put(DATE_COL, databaseDateFormat.format(now));
            contentValues.put(SCORE_COL, score);
            contentValues.put(LEVEL_COL, level);
            long id = database.insert("scores", null, contentValues);
            database.close();

            return new Score(id, now, score, level);
        } else {
            return null;
        }
    }

    public ArrayList<Score> getAllScores() {
        SQLiteDatabase database = getSqLiteDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM scores ORDER BY time DESC", null);
        ArrayList<Score> scores = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                try {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(ID_COL));
                    Date date = databaseDateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow(DATE_COL)));
                    long score = cursor.getLong(cursor.getColumnIndexOrThrow(SCORE_COL));
                    int level = cursor.getInt(cursor.getColumnIndexOrThrow(LEVEL_COL));
                    scores.add(new Score(id, date, score, level));
                } catch (IllegalArgumentException | ParseException ignored) {
                }
                cursor.moveToNext();
            }
        }
        database.close();

        return scores;
    }

    public ArrayList<Score> getBetterScores(int levelReq, int size) {
        SQLiteDatabase database = getSqLiteDatabase();
        String[] selectArgs = new String[]{String.valueOf(levelReq), String.valueOf(size)};
        Cursor cursor = database.rawQuery("SELECT * FROM scores WHERE level = ? ORDER BY score DESC LIMIT ?", selectArgs);
        ArrayList<Score> scores = new ArrayList<>();
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                try {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(ID_COL));
                    Date date = databaseDateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow(DATE_COL)));
                    long score = cursor.getLong(cursor.getColumnIndexOrThrow(SCORE_COL));
                    int level = cursor.getInt(cursor.getColumnIndexOrThrow(LEVEL_COL));
                    scores.add(new Score(id, date, score, level));
                } catch (IllegalArgumentException | ParseException ignored) {
                }
                cursor.moveToNext();
            }
        }
        database.close();

        return scores;
    }

    public Score getScore(int idReq) {
        if (idReq >= 0) {
            SQLiteDatabase database = getSqLiteDatabase();
            String[] selectArgs = new String[]{String.valueOf(idReq)};
            Cursor cursor = database.rawQuery("SELECT * FROM scores WHERE id = ?", selectArgs);
            Score scoreObj = null;
            if (cursor.moveToFirst()) {
                try {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(ID_COL));
                    Date date = databaseDateFormat.parse(cursor.getString(cursor.getColumnIndexOrThrow(DATE_COL)));
                    long score = cursor.getLong(cursor.getColumnIndexOrThrow(SCORE_COL));
                    int level = cursor.getInt(cursor.getColumnIndexOrThrow(LEVEL_COL));
                    scoreObj = new Score(id, date, score, level);
                } catch (IllegalArgumentException | ParseException ignored) {
                }
                cursor.moveToNext();
            }
            database.close();

            return scoreObj;
        } else {
            return null;
        }
    }

    public Score updateScore(int id, Date date, long score, int level) {
        if (id > 0 && date != null && score >= 0 && level > 0) {
            SQLiteDatabase database = getSqLiteDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(DATE_COL, databaseDateFormat.format(date));
            contentValues.put(SCORE_COL, score);
            contentValues.put(LEVEL_COL, level);
            String[] updateArgs = new String[]{String.valueOf(id)};
            int updatedRows = database.update("scores", contentValues, "id = ?", updateArgs);
            database.close();

            if (updatedRows == 1) {
                return new Score(id, date, score, level);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public void removeScore(int id) {
        SQLiteDatabase database = getSqLiteDatabase();
        String[] removeArgs = new String[]{String.valueOf(id)};
        database.delete("scores", "id = ?", removeArgs);
        database.close();
    }

    public void purgeScore() {
        SQLiteDatabase database = getSqLiteDatabase();
        database.delete("scores", null, null);
        database.close();
    }

    public void printScore() {
        ArrayList<Score> allScores = this.getAllScores();
        System.out.println("SCORE SIZE: " + allScores.size());
        for (Score score : allScores) {
            System.out.println(score.toString());
        }
    }
}
