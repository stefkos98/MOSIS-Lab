package com.example.myplaces.models;

import androidx.annotation.NonNull;

public class MyPlace {
    String name;
    String description;
    String longitude;
    String latitude;
    int ID;

    public MyPlace(String nme, String desc)
    {
        this.name=nme;
        this.description=desc;
    }

    public MyPlace(String nme)
    {
        this(nme, "");
    }
    public String getName()
    {
        return name;
    }
    public String getDesc()
    {
        return description;
    }
    public void setName(String nme)
    {
        this.name=nme;
    }
    public void setDecs(String desc)
    {
        this.description=desc;
    }
    public String getLongitude() { return longitude; }
    public void setLongitude(String l) { this.longitude=l; }
    public String getLatitude() { return latitude; }
    public void setLatitude(String l) { this.latitude=l; }
    public int geID() { return ID; }
    public void setID(int i) { this.ID=i; }
    @NonNull
    @Override
    public String toString() {
        return this.name;
    }
}
