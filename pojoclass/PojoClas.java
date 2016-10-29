package com.mohit.college.pojoclass;

/**
 * Created by Mohit on 6/6/2016.
 */


public class PojoClas {
    private String name;
    private String duration;
    private String start;
    private String id;


    public PojoClas(String name, String duration, String start, String id){
        this.name = name;
        this.duration=duration;
        this.start = start;
        this.id =id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setDuration(String duration){

        this.duration = duration;
    }

    public String getDuration(){

        return this.duration;
    }

    public void setStart(String sname){
        this.start = start;
    }

    public String getStart(){
        return this.start;
    }


    public void setId(String sname){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }


}

