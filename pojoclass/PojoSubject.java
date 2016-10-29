package com.mohit.college.pojoclass;

/**
 * Created by Mohit on 6/6/2016.
 */

public class PojoSubject {
    private String name;
    private String id;
    private String sname;
    private String code;



    public PojoSubject(String id,String name,  String sname, String code){
        this.name = name;
        this.id = id;
        this.sname = sname;
        this.code=code;

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

    public void setSname(String sname){
        this.sname = sname;
    }

    public String getSname(){
        return this.sname;
    }


    public void setCode(String code){
        this.sname = code;
    }

    public String getCode(){
        return this.code;
    }

}

