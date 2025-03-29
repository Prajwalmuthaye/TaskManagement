package com.example.todo;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

public class EditTaskActivity extends AppCompatActivity {
    private EditText editTitle, editDescription;
    private Button btnSave;
    private TaskViewModel taskViewModel;
    private int taskId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);

        editTitle = findViewById(R.id.editTextTitle);
        editDescription = findViewById(R.id.editTextDescription);
        btnSave = findViewById(R.id.buttonSave);

        taskViewModel = new ViewModelProvider(this).get(TaskViewModel.class);


        taskId = (getIntent().getIntExtra("TASK_ID", -1));

        // Load Task Data
        taskViewModel.getTaskById(taskId).observe(this, task -> {
            if (task != null) {
                editTitle.setText(task.getTitle());
                editDescription.setText(task.getDescription());
            }
        });


        btnSave.setOnClickListener(v -> {
            String updatedTitle = editTitle.getText().toString();
            String updatedDescription = editDescription.getText().toString();

            if (!updatedTitle.isEmpty()) {
                Task updatedTask = new Task(taskId,updatedTitle,updatedDescription, false);
                taskViewModel.update(updatedTask);
                finish();
            } else {
                Toast.makeText(EditTaskActivity.this, "Title cannot be empty", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
