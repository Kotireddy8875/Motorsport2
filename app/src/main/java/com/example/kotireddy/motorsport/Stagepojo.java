package com.example.kotireddy.motorsport;


public class Stagepojo {

    private String idLeague;
    private String strLeague;
    private String strSport;

    private String strLeagueAlternate;

    public Stagepojo(String lId, String lLeague, String lSport, String lAlternate) {
        this.idLeague=lId;
        this.strLeague=lLeague;
        this.strSport=lSport;
        this.strLeagueAlternate=lAlternate;
    }

    public String getIdLeague() {
        return idLeague;
    }

    public void setIdLeague(String idLeague) {
        this.idLeague = idLeague;
    }

    public String getStrLeague() {
        return strLeague;
    }

    public void setStrLeague(String strLeague) {
        this.strLeague = strLeague;
    }

    public String getStrSport() {
        return strSport;
    }

    public void setStrSport(String strSport) {
        this.strSport = strSport;
    }

    public String getStrLeagueAlternate() {
        return strLeagueAlternate;
    }

    public void setStrLeagueAlternate(String strLeagueAlternate) {
        this.strLeagueAlternate = strLeagueAlternate;
    }

}

