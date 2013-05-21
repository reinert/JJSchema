package com.github.reinert.jjschema.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

public class Task {

    String text;
    @JsonBackReference
    TaskList list;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public TaskList getList() {
        return list;
    }

    public void setList(TaskList list) {
        this.list = list;
    }

}
