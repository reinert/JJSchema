package com.github.reinert.jjschema.inheritance;

import com.github.reinert.jjschema.Attributes;

public class CollegeStudent extends Student {
    @Attributes(required = true, description = "college major")
    private String major;

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }
}


