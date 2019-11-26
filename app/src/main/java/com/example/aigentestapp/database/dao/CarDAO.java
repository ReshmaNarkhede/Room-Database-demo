package com.example.aigentestapp.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.aigentestapp.database.entity.Car;

import java.util.List;

@Dao
public interface CarDAO {
    @Insert
    public void Add(Car car);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void AddAll(List<Car> order);

    @Query("Select * from Car")
    public List<Car> Get();

    @Delete
    public void Delete(Car usData);

    @Query("SELECT * FROM Car WHERE inMemory =1")
    public List<Car> GetCarInMemory();

    @Query("Delete  FROM Car ")
    public void DeleteAll();

}