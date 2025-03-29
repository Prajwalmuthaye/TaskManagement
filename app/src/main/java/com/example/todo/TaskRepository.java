package com.example.todo;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskRepository {
    private TaskDao taskDao;
    private LiveData<List<Task>> allTasks;

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public TaskRepository(Application application)
    {
        TaskDatabase database = TaskDatabase.getInstance(application);
        taskDao = database.taskDao();
        allTasks = taskDao.getAllTasks();

    }
    private static class InsertTaskAsync extends AsyncTask<Task, Void, Void> {
        private TaskDao taskDao;

        private InsertTaskAsync(TaskDao taskDao) {
            this.taskDao = taskDao;
        }

        @Override
        protected Void doInBackground(Task... tasks) {
            taskDao.insert(tasks[0]);
            return null;
        }
    }

    public void insert(Task task)
    {
        executor.execute(() -> taskDao.insert(task));
    }
    public void update(Task task)
    {
        executor.execute(() -> taskDao.update(task));
    }
    public void delete(Task task)
    {
        executor.execute(() -> taskDao.delete(task));
    }
    public LiveData<List<Task>> getAllTasks()
    {
        return allTasks;
    }

    public LiveData<List<Task>> searchTasks(String query) {
        return taskDao.searchTasks("%" + query + "%");
    }
    public LiveData<Task> getTaskById(int id) {
        return taskDao.getTaskById(id);
    }
    public LiveData<List<Task>> filterTasks(boolean status)
    {
        return taskDao.filterTasks(status);
    }
}