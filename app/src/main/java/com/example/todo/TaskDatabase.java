package com.example.todo;


import android.content.Context;
import androidx.room.*;

@Database(entities = {Task.class}, version = 2, exportSchema = false)
public abstract class TaskDatabase extends RoomDatabase{
    private static TaskDatabase instance;
    public abstract TaskDao taskDao();

    public static synchronized TaskDatabase getInstance(Context context)
    {
        if (instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            TaskDatabase.class, "task_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }


}
