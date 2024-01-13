package com.example.learningmanagmentmvvm;

import static com.example.learningmanagmentmvvm.MainActivity.COURSE_ID;
import static com.example.learningmanagmentmvvm.MainActivity.COURSE_NAME;
import static com.example.learningmanagmentmvvm.MainActivity.COURSE_PRICE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.learningmanagmentmvvm.databinding.ActivityAddEditBinding;
import com.example.learningmanagmentmvvm.db.entity.Course;
import com.example.learningmanagmentmvvm.viewModel.LmViewModel;

public class AddEditActivity extends AppCompatActivity {

    //data binding
    ActivityAddEditBinding activityAddEditBinding;
    Course course;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        //data binding
        activityAddEditBinding = DataBindingUtil.setContentView(AddEditActivity.this, R.layout.activity_add_edit);
        course = new Course();
        activityAddEditBinding.setCourse(course);
        AddEditActivityClickHandler clickHandler = new AddEditActivityClickHandler();
        activityAddEditBinding.setClickHandler(clickHandler);


        Intent intent = getIntent();
        if( intent.hasExtra(COURSE_ID) ){
            //edit course
            setTitle("Edit Course");
            course.setName(intent.getExtras().getString(COURSE_NAME));
            course.setPrice(intent.getExtras().getString(COURSE_PRICE));

        }
        else {
            //add new course
            setTitle("Add New Course");
        }

    }

    public class AddEditActivityClickHandler{
        public void onSubmitBtnClickHandler(View view){

            if ( course.getName()!= null && course.getPrice()!=null ){
                Intent intent = new Intent(AddEditActivity.this, MainActivity.class);
                intent.putExtra(COURSE_NAME, course.getName());
                intent.putExtra(COURSE_PRICE, course.getPrice());
                setResult(RESULT_OK, intent);
                finish();

            }else {
                Toast.makeText(AddEditActivity.this, "Fields can not be empty.", Toast.LENGTH_SHORT).show();
            }

        }
    }
}