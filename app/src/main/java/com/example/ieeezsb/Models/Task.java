package com.example.ieeezsb.Models;

import androidx.annotation.NonNull;

public class Task {
    private String task_message;

    public String getTask_title() {
        return task_title;
    }

    public void setTask_title(String task_title) {
        this.task_title = task_title;
    }

    private  String task_title;
    public String getTask_names() { return task_names; }

    public void setTask_names(String task_names) { this.task_names = task_names; }

    private String task_names;
    public Task(){}
    public Task( String task_message , String task_name ,String task_title){
        this.task_message = task_message;
        this.task_names = task_name;
        this.task_title = task_title;
    }
    public String getTask_message() { return task_message; }

    public void setTask_message(String task_message) {this.task_message = task_message; }




}
