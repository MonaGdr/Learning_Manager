package com.example.learningmanagmentmvvm.db;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;

import com.example.learningmanagmentmvvm.db.entity.Category;
import com.example.learningmanagmentmvvm.db.entity.Course;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class LmRepository {
    private DaoCategory daoCategory;
    private DaoCourse daoCourse;

    private LiveData<List<Course>> courses;

    private LiveData<List<Category>> categories;

    public LmRepository(Application application){
        LmDatabase lmDatabase = LmDatabase.getInstance(application);
        daoCategory = lmDatabase.getCategoryDao();
        daoCourse = lmDatabase.getCourseDao();
    }

    public void insertCourse(Course course){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                //background work
                long insert = daoCourse.insertCourse(course);

                //post execution
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });

    }

    public void deleteCourse(Course course){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                //background work
                int delete = daoCourse.deleteCourse(course);

                //post execution
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });

    }

    public void updateCourse(Course course){
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                //background work
                int update = daoCourse.updateCourse(course);

                //post execution
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });

    }


    //WHY NOT DO THIS IN A BACKGROUND THREAD?
    public LiveData<List<Course>> getCourses(int categoryId){
        return daoCourse.getCourses(categoryId);
    }

    public LiveData<List<Category>> getCategories(){
        return daoCategory.getCategories();
    }

}
