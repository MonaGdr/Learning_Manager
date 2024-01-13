package com.example.learningmanagmentmvvm.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.learningmanagmentmvvm.R;
import com.example.learningmanagmentmvvm.databinding.CourseItemBinding;
import com.example.learningmanagmentmvvm.db.entity.Course;
import java.util.ArrayList;

public class CourseAdapter extends RecyclerView.Adapter<CourseAdapter.CourseViewHolder> {

    private OnItemClickListener listener;
    private ArrayList<Course> courses;

    public CourseAdapter(){

    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        CourseItemBinding courseItemBinding = DataBindingUtil.inflate(inflater, R.layout.course_item, parent, false);
        return new CourseViewHolder(courseItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        holder.courseItemBinding.setCourse(courses.get(position));
    }


    @Override
    public int getItemCount() {
        return courses!= null? courses.size() : 0;
    }



    public void setCourses(ArrayList<Course> newCourses) {

        final DiffUtil.DiffResult result = DiffUtil.calculateDiff( new CourseDiffUnitCallback(courses, newCourses) );
        courses = newCourses;
        result.dispatchUpdatesTo(CourseAdapter.this);
    }

    class CourseViewHolder extends RecyclerView.ViewHolder{

        CourseItemBinding courseItemBinding;

        public CourseViewHolder(@NonNull CourseItemBinding courseItemBinding) {
            super(courseItemBinding.getRoot());
            this.courseItemBinding = courseItemBinding;

            //set onClick listener for item view
            courseItemBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();

                    if ( listener != null && position != RecyclerView.NO_POSITION ){
                        listener.onItemClick(courses.get(position));
                    }

                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Course course);
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
