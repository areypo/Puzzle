package com.manytiles.p8.scoreManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Score {
    private int id;
    private Date date;
    private int score;

    public Score(int id, Date date, int score) {
        this.id = id;
        this.date = date;
        this.score = score;
    }

    public Score(Date date, int score) {
        this.date = date;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public String getStringDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("E, dd/MMM/yyyy HH:mm:ss", Locale.getDefault());
        return dateFormat.format(date);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getScore() {
        return score;
    }

    public String getStringScore() {
        return score + "s";
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Score{" +
                "id=" + id +
                ", date=" + date +
                ", score=" + score +
                '}';
    }
}
