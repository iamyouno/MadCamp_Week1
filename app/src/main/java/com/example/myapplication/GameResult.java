package com.example.myapplication;

public class GameResult {
    private String team1;
    private String team2;
    private String score;
    private String logo1;
    private String logo2;
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

    public void setLogo1(String logo1){
        this.logo1 = logo1;
    }

    public void setLogo2(String logo2){
        this.logo2 = logo2;
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

    public String getLogo1(){
        return logo1;
    }

    public String getLogo2(){
        return logo2;
    }
}
