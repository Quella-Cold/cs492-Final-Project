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
public interface SaveMealDao {
    @Insert
    void insert(MealListItem item);

    @Delete
    void delete(MealListItem item);

    @Query("SELECT * FROM MealItems")
    LiveData<List<MealListItem>> getAllItems();

    @Query("SELECT * FROM MealItems WHERE address=:address LIMIT 1")
    LiveData<MealListItem> getLocationByName(String address);
}