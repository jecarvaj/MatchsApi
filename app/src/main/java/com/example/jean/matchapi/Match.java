package com.example.jean.matchapi;

/**
 * Created by Jean on 18-12-2017.
 */

public class Match {
    private String localName;
    private String visitName;
    private int localGoals;
    private  int visitGoals;
    private String localImage;
    private String visitImage;
    private String stadiumName;
    private String startTime;


    public Match(String localName, String visitName, int localGoals, int visitGoals, String localImage, String visitImage, String stadiumName, String startTime) {
        this.localName = localName;
        this.visitName = visitName;
        this.localGoals = localGoals;
        this.visitGoals = visitGoals;
        this.localImage = localImage;
        this.visitImage = visitImage;
        this.stadiumName = stadiumName;
        this.startTime = startTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getLocalName() {
        return localName;

    }

    public void setLocalName(String localName) {
        this.localName = localName;
    }

    public String getVisitName() {
        return visitName;
    }

    public void setVisitName(String visitName) {
        this.visitName = visitName;
    }

    public int getLocalGoals() {
        return localGoals;
    }

    public void setLocalGoals(int localGoals) {
        this.localGoals = localGoals;
    }

    public int getVisitGoals() {
        return visitGoals;
    }

    public void setVisitGoals(int visitGoals) {
        this.visitGoals = visitGoals;
    }

    public String getLocalImage() {
        return localImage;
    }

    public void setLocalImage(String localImage) {
        this.localImage = localImage;
    }

    public String getVisitImage() {
        return visitImage;
    }

    public void setVisitImage(String visitImage) {
        this.visitImage = visitImage;
    }

    public String getStadiumName() {
        return stadiumName;
    }

    public void setStadiumName(String stadiumName) {
        this.stadiumName = stadiumName;
    }
}
