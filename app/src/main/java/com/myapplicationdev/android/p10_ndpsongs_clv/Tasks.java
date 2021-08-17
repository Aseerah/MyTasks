package com.myapplicationdev.android.p10_ndpsongs_clv;

import java.io.Serializable;

public class Tasks implements Serializable {

	private int id;
	private String title;
	private int yearReleased;
	private int stars;

    public Tasks(String title, int yearReleased, int stars) {
        this.title = title;
        this.yearReleased = yearReleased;
        this.stars = stars;
    }

    public Tasks(int id, String title, int yearReleased, int stars) {
        this.id = id;
        this.title = title;
        this.yearReleased = yearReleased;
        this.stars = stars;
    }

    public int getId() {
        return id;
    }

    public Tasks setId(int id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Tasks setTitle(String title) {
        this.title = title;
        return this;
    }


    public int getYearReleased() {
        return yearReleased;
    }

    public Tasks setYearReleased(int yearReleased) {
        this.yearReleased = yearReleased;
        return this;
    }

    public int getStars() {
        return stars;
    }

    public Tasks setStars(int stars) {
        this.stars = stars;
        return this;
    }
//
//    @Override
//    public String toString() {
//        String starsString = "";
//        if (stars == 5){
//            starsString = "* * * * *";
//        } else if (stars == 4){
//            starsString = "* * * *";
//        }else if (stars == 3){
//            starsString = "* * *";
//        }else if (stars == 2){
//            starsString = "* *";
//        }else {
//            starsString = "*";
//        }

//        //or
//        for(int i = 0; i < stars; i++){
//            starsString += "*";
//        }
//        return title + "\n" + singers + " - " + yearReleased + "\n" + starsString;
//
//        return starsString;
//    }
}