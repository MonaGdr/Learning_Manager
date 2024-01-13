package com.example.learningmanagmentmvvm.db.entity;

import androidx.annotation.Nullable;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import static androidx.room.ForeignKey.CASCADE;

import java.util.Objects;

@Entity(tableName = "courses" ,
        foreignKeys = @ForeignKey(entity = Category.class, parentColumns = "category_id", childColumns = "course_category_id", onDelete = CASCADE))
public class Course extends BaseObservable {

    public static final String COURSE_ID = "course_id";
    public static final String COURSE_NAME = "course_name";
    public static final String COURSE_EMAIL = "course_email";
    public static final String COURSE_CATEGORY_ID = "course_category_id";

    @ColumnInfo(name = COURSE_ID)
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = COURSE_NAME)
    private String name;

    @ColumnInfo(name = COURSE_EMAIL)
    private String price;


    @ColumnInfo(name = COURSE_CATEGORY_ID)
    //@ForeignKey(entity = Category.class)
    private int categoryId;

    public Course(String name, String price) {
        this.name = name;
        this.price = price;
    }

    @Ignore
    public Course(){

    }

    @Bindable
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public String getName() {
        return name;

    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }

    @Bindable
    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
        notifyPropertyChanged(BR.price);
    }

    @Bindable
    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
        notifyPropertyChanged(BR.categoryId);
    }


    @Override
    public boolean equals(@Nullable Object obj) {
        if ( this == obj )
            return true;

        if ( obj == null || this.getClass() != obj.getClass() )
            return false;

        Course course = (Course) obj;

        return this.id == course.id
                && this.name.equals(course.name)
                && this.price.equals( course.price)
                && this.categoryId == course.categoryId;

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, price, categoryId);
    }
}
