package com.example.learningmanagmentmvvm.adapter;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.example.learningmanagmentmvvm.db.entity.Course;

import java.util.List;

public class CourseDiffUnitCallback extends DiffUtil.Callback {

    List<Course> oldCourse;
    List <Course> newCourse;

    public CourseDiffUnitCallback(List<Course> oldCourse, List<Course> newCourse) {
        this.oldCourse = oldCourse;
        this.newCourse = newCourse;
    }

    @Override
    public int getOldListSize() {
        return oldCourse !=null ? oldCourse.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return newCourse !=null ? newCourse.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldCourse.get(oldItemPosition).getId() == newCourse.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldCourse.get(oldItemPosition).equals(newCourse.get(newItemPosition));
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
