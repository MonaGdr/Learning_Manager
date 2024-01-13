package com.example.learningmanagmentmvvm.db.entity;

import androidx.databinding.Bindable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.databinding.BaseObservable;




@Entity(tableName = "categories")
public class Category extends BaseObservable{

    public static final String CATEGORY_ID = "category_id";
    public static final String CATEGORY_NAME = "category_name";
    public static final String CATEGORY_DESCRIPTION = "category_description";

    @ColumnInfo(name = CATEGORY_ID)
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = CATEGORY_NAME)
    private String name;


    @ColumnInfo(name = CATEGORY_DESCRIPTION)
    private String description;

    public Category(String name) {
        this.name = name;
    }

    @Ignore
    public Category(){}

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
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        notifyPropertyChanged(BR.description);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
