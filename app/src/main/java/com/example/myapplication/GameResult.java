package com.example.myapplication;

public class GameResult {
    private String team1;
    private String team2;
    private String score;

    public GameResult(){
    }

    public void setTeam1(String team1){
        this.team1 = team1;
    }

    public void setTeam2(String team2){
        this.team2 = team2;
    }

    public void setScore(String score){
        this.score = score;
    }

    public String getTeam1(){
        return team1;

    }

    public String getTeam2(){
        return team2;
    }

    public String getScore(){
        return score;
    }
}
