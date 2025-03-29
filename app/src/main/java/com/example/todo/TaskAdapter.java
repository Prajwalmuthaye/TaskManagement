package com.example.todo;

import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskHolder> {
    private List<Task> tasks =  new ArrayList<>();
    private OnTaskClickListener listener;

    public TaskAdapter(List<Task> tasks, OnTaskClickListener listener) {
        this.tasks = tasks;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TaskHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_task_adapter, parent, false);
        return new TaskHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskHolder holder, int position) {
        Task task = tasks.get(position);
        holder.title.setText(task.getTitle());
        holder.checkBox.setChecked(task.isCompleted());
        holder.checkBox.setOnClickListener(v -> listener.onTaskClick(task));
        holder.editText.setOnClickListener(v -> listener.onEditText(task));
        holder.deleteText.setOnClickListener(v -> listener.onDeleteText(task));
        holder.textViewDescription.setText(task.getDescription());


        holder.itemView.setOnClickListener(v -> {
            task.setCompleted(!task.isCompleted());
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    class TaskHolder extends RecyclerView.ViewHolder {
        TextView title;
        CheckBox checkBox;

        TextView textViewDescription;

        ImageButton editText,deleteText;
        public TaskHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.task_title);
            checkBox = itemView.findViewById(R.id.task_checkbox);
            editText = itemView.findViewById(R.id.edit_task);
            deleteText = itemView.findViewById(R.id.delete_task);
            textViewDescription = itemView.findViewById(R.id.description);

        }
    }
    public interface OnTaskClickListener {
        void onTaskClick(Task task);

        void onEditText(Task task);

        void onDeleteText(Task task);
    }
}