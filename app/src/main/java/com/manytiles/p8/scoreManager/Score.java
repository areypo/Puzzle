package com.manytiles.p8.scoreManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Score {
    private long id;
    private Date date;
    private long score;
    private int level;

    public Score(long id, Date date, long score, int level) {
        this.id = id;
        this.date = date;
        this.score = score;
        this.level = level;
    }

    public Score(Date date, long score, int level) {
        this.date = date;
        this.score = score;
        this.level = level;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public String getStringScore() {
        return score + "ms";
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Score{" +
                "id=" + id +
                ", date=" + date +
                ", score=" + score +
                ", level=" + level +
                '}';
    }
}
