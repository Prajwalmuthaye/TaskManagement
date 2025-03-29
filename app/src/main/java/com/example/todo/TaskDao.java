package com.example.todo;

import androidx.lifecycle.LiveData;
import androidx.room.*;

import java.util.List;

@Dao
public interface TaskDao {
    @Insert
    void insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);

    @Query("SELECT * FROM tasks ORDER BY isCompleted ASC")
    LiveData<List<Task>> getAllTasks();

    @Query("SELECT * FROM tasks WHERE title LIKE :query")
    LiveData<List<Task>> searchTasks(String query);

    @Query("SELECT * FROM tasks WHERE isCompleted = :status")
    LiveData<List<Task>> filterTasks(boolean status);

    @Query("SELECT * FROM tasks WHERE id = :taskId")
    LiveData<Task> getTaskById(int taskId);
}