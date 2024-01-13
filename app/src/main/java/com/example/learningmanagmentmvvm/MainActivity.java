package com.example.learningmanagmentmvvm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import com.example.learningmanagmentmvvm.adapter.CourseAdapter;
import com.example.learningmanagmentmvvm.adapter.CourseDiffUnitCallback;
import com.example.learningmanagmentmvvm.databinding.ActivityMainBinding;
import com.example.learningmanagmentmvvm.db.entity.Category;
import com.example.learningmanagmentmvvm.db.entity.Course;
import com.example.learningmanagmentmvvm.viewModel.LmViewModel;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final int ADD_REQUEST_CODE = 1;
    public static final int EDIT_REQUEST_CODE = 2;
    public static final String COURSE_ID = "course_id";
    public static final String COURSE_NAME = "course_name";
    public static final String COURSE_PRICE = "course_price";

    //data binding
    ActivityMainBinding activityMainBinding;

    //view model
    LmViewModel viewModel;

    //data
    Category selectedCategory;
    ArrayList<Category> categoryArrayList;
    ArrayList<Course> coursesArrayList;
    int selectedCourseId;

    //recycler view adapter
    CourseAdapter adapter;
    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //data binding
        activityMainBinding = DataBindingUtil.setContentView(MainActivity.this, R.layout.activity_main);
        MainActivityClickHandler mainActivityClickHandler = new MainActivityClickHandler();
        activityMainBinding.setMainActivityClickHandler(mainActivityClickHandler);

        //view model
        viewModel = new ViewModelProvider(this).get(LmViewModel.class);

        //categories on spinner
        viewModel.getCategories().observe(MainActivity.this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                categoryArrayList = (ArrayList<Category>) categories;
                showOnSpinner(categoryArrayList);

                for (Category category: categories){
                    Log.i("TAG", category.getName());
                }

            }
        });

    }

    private void showOnSpinner(ArrayList<Category> categoryArrayList) {
        ArrayAdapter<Category> spinnerAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.category_item, categoryArrayList);
        spinnerAdapter.setDropDownViewResource(R.layout.category_item);
        activityMainBinding.setSpinnerAdapter(spinnerAdapter);
    }

    private void loadCourses(int categoryId){

        viewModel.getCoursesOfSelectedCategory(categoryId).observe(MainActivity.this, new Observer<List<Course>>() {

            @Override
            public void onChanged(List<Course> courses) {

                coursesArrayList = (ArrayList<Course>) courses;
                loadRecyclerView();

            }
        });

    }

    private void loadRecyclerView() {

        rv = activityMainBinding.rv;
        adapter = new CourseAdapter();
        rv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        rv.setAdapter(adapter);
        adapter.setCourses(coursesArrayList);

        //navigate to add edit activity (edit)
        adapter.setListener(new CourseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Course course) {

                selectedCourseId = course.getId();
                Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
                intent.putExtra(COURSE_ID, selectedCourseId);
                intent.putExtra(COURSE_NAME,course.getName());
                intent.putExtra(COURSE_PRICE,course.getPrice());
                startActivityIfNeeded(intent, EDIT_REQUEST_CODE);
            }
        });

        //delete course on swipe
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Course course = coursesArrayList.get(viewHolder.getAdapterPosition());
                List<Course> oldCourses = coursesArrayList;
                viewModel.deleteCourse(course);
                final DiffUtil.DiffResult result = DiffUtil.calculateDiff(new CourseDiffUnitCallback(oldCourses, coursesArrayList));
                result.dispatchUpdatesTo(adapter);



            }
        }).attachToRecyclerView(rv);

    }

    //add and edit course
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        //add new course
        if ( requestCode == ADD_REQUEST_CODE && resultCode == RESULT_OK ){

            Course course = new Course();
            course.setName(data.getStringExtra(COURSE_NAME));
            course.setPrice(data.getStringExtra(COURSE_PRICE));
            course.setCategoryId(selectedCategory.getId());
            viewModel.addNewCourse(course);


        }
        //edit course
        else if(requestCode == EDIT_REQUEST_CODE && resultCode == RESULT_OK){

            Course course = new Course();
            course.setName(data.getStringExtra(COURSE_NAME));
            course.setPrice(data.getStringExtra(COURSE_PRICE));
            course.setCategoryId(selectedCategory.getId());
            course.setId(selectedCourseId);
            viewModel.updateCourse(course);

        }
    }

    public class MainActivityClickHandler{

        public void onFabAddClickListener(View view){

            //navigate to add edit activity (add)
            Intent intent = new Intent(MainActivity.this, AddEditActivity.class);
            startActivityForResult(intent, ADD_REQUEST_CODE);
        }

        public void onSelectedSpinnerItem(AdapterView<?> parent, View view, int position, long id){

            //load the courses of the selected category
            selectedCategory = (Category) parent.getItemAtPosition(position);
            loadCourses(selectedCategory.getId());


        }
    }
}