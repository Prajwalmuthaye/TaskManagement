package com.example.todo;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.widget.CalendarView;

public class AddTask extends AppCompatActivity {
    private EditText editTextTaskTitle, editTextTaskDescription;
    private Button buttonSave;
    private TaskViewModel taskViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        editTextTaskTitle = findViewById(R.id.editTextTaskTitle);
        editTextTaskDescription = findViewById(R.id.editTextTaskDescription);
        buttonSave = findViewById(R.id.buttonSave);

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        buttonSave.setOnClickListener(v -> saveTask());
    }

    private void saveTask() {
        String title = editTextTaskTitle.getText().toString().trim();
        String description = editTextTaskDescription.getText().toString().trim();

        if (title.isEmpty()) {
            Toast.makeText(this, "Task title cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        Task newTask = new Task(title, description, false);
        taskViewModel.insert(newTask);
        Toast.makeText(this, "Task Added", Toast.LENGTH_SHORT).show();
        finish();
    }
}