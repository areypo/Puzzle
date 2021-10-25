package com.manytiles.p8.scoreManager;


import java.util.Date;

public class Score {
    private int id;
    private Date date;
    private int score;

    public Score(int id, Date date, int score) {
        this.id = id;
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

    public void setDate(Date date) {
        this.date = date;
    }

    public int getScore() {
        return score;
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
