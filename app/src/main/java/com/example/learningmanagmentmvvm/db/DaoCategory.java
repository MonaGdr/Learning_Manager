package com.example.learningmanagmentmvvm.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.learningmanagmentmvvm.db.entity.Category;


import java.util.List;

@Dao
public interface DaoCategory {
    @Insert
    public long insertCategory(Category category);

    @Delete
    public int deleteCategory(Category category);

    @Update
    public int updateCategory(Category category);

    @Query("SELECT * FROM categories")
    public LiveData<List<Category>> getCategories();


}
