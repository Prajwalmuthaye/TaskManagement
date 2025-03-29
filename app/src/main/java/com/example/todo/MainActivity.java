package com.example.todo;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.text.Editable;

import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.*;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TaskViewModel taskViewModel;
    private TaskAdapter adapter;
    private EditText searchTask;
    private Switch filterSwitch;
    private FloatingActionButton fabAddTask;
    private Switch darkModeSwitch;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        searchTask = findViewById(R.id.searchTask);
        filterSwitch = findViewById(R.id.filterSwitch);
        fabAddTask = findViewById(R.id.fab);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        darkModeSwitch = findViewById(R.id.darkModeSwitch);
        sharedPreferences = getSharedPreferences("AppSettings", MODE_PRIVATE);

        // Load saved dark mode state
        boolean isDarkMode = sharedPreferences.getBoolean("DarkMode", false);
        darkModeSwitch.setChecked(isDarkMode);
        applyDarkMode(isDarkMode);

        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            applyDarkMode(isChecked);
            sharedPreferences.edit().putBoolean("DarkMode", isChecked).apply();
        });
        adapter = new TaskAdapter(new ArrayList<>(), new TaskAdapter.OnTaskClickListener() {
            @Override
            public void onTaskClick(Task task) {
                task.setCompleted(!task.isCompleted());
                taskViewModel.update(task);
            }

            @Override
            public void onEditText(Task task) {
                Intent intent = new Intent(MainActivity.this, EditTaskActivity.class);
                intent.putExtra("TASK_ID", task.getId());
                startActivity(intent);
            }

            @Override
            public void onDeleteText(Task task) {
                taskViewModel.delete(task);
                Toast.makeText(MainActivity.this, "Task Deleted", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);
        taskViewModel.getAllTasks().observe(this, tasks -> adapter.setTasks(tasks));

        searchTask.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                taskViewModel.searchTasks(s.toString()).observe(MainActivity.this, filteredTasks -> adapter.setTasks(filteredTasks));
            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}
        });

        filterSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            taskViewModel.filterTasks(isChecked).observe(MainActivity.this, filteredTasks -> adapter.setTasks(filteredTasks));
        });

        fabAddTask.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddTask.class);
            startActivity(intent);
        });
    }
    private void applyDarkMode(boolean enable) {
        if (enable) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }
}

