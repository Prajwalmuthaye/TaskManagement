package com.example.todo;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import java.util.List;

public class TaskViewModel extends AndroidViewModel {
    private TaskRepository repository;
    private LiveData<List<Task>> allTasks;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        repository = new TaskRepository(application);
        allTasks = repository.getAllTasks();
    }

    public LiveData<List<Task>> getAllTasks() {
        return allTasks;
    }

    public void insert(Task task) {
        repository.insert(task);
    }

    public void update(Task task) {

        repository.update(task);
    }

    public void delete(Task task) {
        repository.delete(task);
    }
    public LiveData<Task> getTaskById(int id) {
        return repository.getTaskById(id);
    }
    public LiveData<List<Task>> searchTasks(String query) {
        return repository.searchTasks(query);
    }

    public LiveData<List<Task>> filterTasks(boolean status) {
        return repository.filterTasks(status);
    }
}
