package com.mohit.college.pojoclass;

/**
 * Created by Mohit on 6/6/2016.
 */

public class PojoCourse {
    private String name;
    private String id;

    public PojoCourse(String name, String id){
        this.name = name;
        this.id = id;
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
}
