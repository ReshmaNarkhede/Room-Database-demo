package com.example.aigentestapp.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Car")
public class Car implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public long Id;
    public String carName;
    public String carModel;
    public String prize;
    public int year;
    public String sellerName;
    public String sellerCity;
    public String sellerPhone;
    public String date;
    public String kmDriven;
    public String fuelType;
    public String description;
    public String inMemory;
    public String image;
}
