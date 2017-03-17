package com.hust.thanglv.lunchlist;

/**
 * Created by ngoho_000 on 02/03/2017.
 */

public class Restaurant {
    private String type;
    private String address;
    private String name;
    private String discount;
    private String notes="";

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getAddress(){
        return address;
    }
    public void setAddress(String adrr){
        this.address = adrr;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getType() {
        return(type);
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getNotes() {
        return(notes);
    }
    public void setNotes(String notes) {
        this.notes=notes;
    }
    public String toString() {
        return(getName()); }
}
