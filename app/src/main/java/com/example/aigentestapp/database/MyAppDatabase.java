package com.example.aigentestapp.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.aigentestapp.database.dao.CarDAO;
import com.example.aigentestapp.database.entity.Car;

@Database(
        entities = {
                Car.class
        }
        , version = 1, exportSchema = false
)
public abstract class MyAppDatabase extends RoomDatabase {
    public abstract CarDAO carDAO();

}