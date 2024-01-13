package com.example.learningmanagmentmvvm.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.learningmanagmentmvvm.db.LmRepository;
import com.example.learningmanagmentmvvm.db.entity.Category;
import com.example.learningmanagmentmvvm.db.entity.Course;

import java.util.List;

public class LmViewModel extends AndroidViewModel {

    private LmRepository repository;
    private LiveData <List<Course>> coursesOfSelectedCategory;

    private LiveData <List<Category>> categories;

    public LmViewModel(@NonNull Application application) {
        super(application);
        repository = new LmRepository(application);
    }

    public LiveData<List<Course>> getCoursesOfSelectedCategory( int categoryId ) {
        return repository.getCourses(categoryId);
    }

    public LiveData<List<Category>> getCategories() {
        return repository.getCategories();
    }

    public void addNewCourse( Course course ){
        repository.insertCourse(course);
    }

    public void deleteCourse(Course course){
        repository.deleteCourse(course);
    }

    public void updateCourse(Course course){
        repository.updateCourse(course);
    }
}
