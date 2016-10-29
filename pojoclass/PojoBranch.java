package com.mohit.college.pojoclass;

/**
 * Created by Mohit on 6/6/2016.
 */

public class PojoBranch {
    private String name;
    private String id;
    private String seats;


    public PojoBranch(String name, String id, String seats){
        this.name = name;
        this.id = id;
        this.seats = seats;

    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }

    public void setId(String id){

        this.id = id;
    }

    public String getId(){

        return this.id;
    }

    public void setSeats(String seats){
        this.seats = seats;
    }

    public String getSeats(){
        return this.seats;
    }

}

