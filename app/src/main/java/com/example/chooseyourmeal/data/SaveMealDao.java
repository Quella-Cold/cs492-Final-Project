package com.example.chooseyourmeal.data;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;
@Dao
public interface SaveWeatherDao {
    @Insert
    void insert(LocationItem weather);

    @Delete
    void delete(LocationItem weather);

    @Query("SELECT * FROM LocationItems")
    //LiveData<List<LocationItem>> getAllItems();

    @Query("SELECT * FROM LocationItems WHERE locationName=:locationName LIMIT 1")
  //  LiveData<LocationItem> getLocationByName(String locationName);
}