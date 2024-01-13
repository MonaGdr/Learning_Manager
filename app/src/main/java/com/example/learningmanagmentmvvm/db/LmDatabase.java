package com.example.learningmanagmentmvvm.db;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import com.example.learningmanagmentmvvm.db.entity.Category;
import com.example.learningmanagmentmvvm.db.entity.Course;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Course.class, Category.class} , version = 2)
public abstract class LmDatabase extends RoomDatabase {

    public abstract DaoCategory getCategoryDao();
    public abstract DaoCourse getCourseDao();

    private static LmDatabase instance;

    public static LmDatabase getInstance( Context context ){
        if ( instance == null ){
            instance = Room.databaseBuilder(context,
                    LmDatabase.class, "db.learningManagement").
                    fallbackToDestructiveMigration().addCallback(dbCallBack).build();

        }
        return instance;
    }

    static RoomDatabase.Callback dbCallBack = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            initData();
        }
    };

    private static void initData() {
        Log.i("MyTag" , "initializing data...");
        DaoCourse daoCourse = instance.getCourseDao();
        DaoCategory daoCategory = instance.getCategoryDao();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {

                Category frontCategory = new Category();
                frontCategory.setName("Front-end");
                frontCategory.setDescription("web development interface");

                Category backCategory = new Category();
                backCategory.setName("Back-end");
                backCategory.setDescription("web development server");

                daoCategory.insertCategory(frontCategory);
                daoCategory.insertCategory(backCategory);


                Course course1 = new Course();
                course1.setName("HTML");
                course1.setPrice("600$");
                course1.setCategoryId(1);

                Course course2 = new Course();
                course2.setName("CSS");
                course2.setPrice("400$");
                course2.setCategoryId(1);

                Course course3 = new Course();
                course3.setName("C++");
                course3.setPrice("700$");
                course3.setCategoryId(2);

                Course course4 = new Course();
                course4.setName("PHP");
                course4.setPrice("500$");
                course4.setCategoryId(2);

                daoCourse.insertCourse(course1);
                daoCourse.insertCourse(course2);
                daoCourse.insertCourse(course3);
                daoCourse.insertCourse(course4);


            }
        });


    }


}
