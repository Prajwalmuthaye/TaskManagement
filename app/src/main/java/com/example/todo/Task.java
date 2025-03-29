package com.example.todo;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasks")
public class Task {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title="";

    private String description="";

    private boolean isCompleted;


    public Task(int id, String title, String description, boolean isCompleted) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.isCompleted = isCompleted;
    }

    @Ignore
    public Task(String title, String description, boolean isCompleted) {

        this.title = title;
        this.description = description;
        this.isCompleted = isCompleted;
    }

    public int getId()
    {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }
    public String getTitle()
    {
        return title;
    }

    public boolean isCompleted()
    {
        return isCompleted;
    }
    public void setCompleted(boolean completed)
    {
        this.isCompleted = completed;
    }
    public String getDescription()
    {
        return description;
    }
}
