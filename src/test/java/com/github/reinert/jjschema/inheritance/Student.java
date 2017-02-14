package com.github.reinert.jjschema.inheritance;

import com.github.reinert.jjschema.Attributes;

public class Student {
    @Attributes(required = true, description = "student name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

