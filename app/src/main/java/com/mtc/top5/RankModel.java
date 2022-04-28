package com.mtc.top5;

public class RankModel {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        System.out.println("Score changed to"+ Integer.toString(score));
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        System.out.println("rank changed to." + Integer.toString(rank));
        this.rank = rank;
    }

    public RankModel(String name, int score, int rank) {
        this.name = name;
        this.score = score;
        this.rank = rank;
    }

    private String name;
    private int score;
    private  int rank;


}
