package com.example.ieeezsb.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ieeezsb.Activities.TasksActivity;
import com.example.ieeezsb.Models.MessageModel;
import com.example.ieeezsb.Models.Task;
import com.example.ieeezsb.R;

import java.util.List;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TasksAdapterViewHolder> {
    private Context context;
    private List<Task> messages;

    public TasksAdapter (Context context , List<Task> messages){
        this.context = context;
        this.messages = messages;
    }


    @NonNull
    @Override
    public TasksAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(context).inflate(R.layout.tasks_recycler,parent,false);
        return new TasksAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TasksAdapterViewHolder holder, int position) {
        Task task = messages.get(position);
        holder.textView2.setText(task.getTask_message());
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class TasksAdapterViewHolder extends RecyclerView.ViewHolder {
        public TextView textView2 ,textname;
        public TasksAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            textView2 = itemView.findViewById(R.id.task_message_rec);
            //textname = itemView.findViewById(R.id.head_name_rec);
        }
    }
}
