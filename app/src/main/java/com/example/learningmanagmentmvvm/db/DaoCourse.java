package com.example.learningmanagmentmvvm.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.learningmanagmentmvvm.db.entity.Category;
import com.example.learningmanagmentmvvm.db.entity.Course;

import java.util.List;

@Dao
public interface DaoCourse {

    @Insert
    public long insertCourse(Course course);

    @Delete
    public int deleteCourse(Course course);

    @Update
    public int updateCourse(Course course);

    @Query("SELECT * FROM courses")
    public LiveData <List<Course>> getCourses();

    @Query("SELECT * FROM courses WHERE course_category_id==:categoryId")
    public LiveData<List<Course>> getCourses(int categoryId);
}
