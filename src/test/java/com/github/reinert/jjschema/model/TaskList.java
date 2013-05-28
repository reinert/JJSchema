package com.github.reinert.jjschema.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.Collection;
import java.util.List;

public class TaskList {

    @JsonManagedReference
    List<Task> taks;
    @JsonBackReference
    Person creator;
    @JsonBackReference("assigned")
    Collection<Person> assignedPersons;

    public List<Task> getTaks() {
        return taks;
    }

    public void setTaks(List<Task> taks) {
        this.taks = taks;
    }

    public Person getCreator() {
        return creator;
    }

    public void setCreator(Person creator) {
        this.creator = creator;
    }

    public Collection<Person> getAssignedPersons() {
        return assignedPersons;
    }

    public void setAssignedPersons(Collection<Person> assignedPersons) {
        this.assignedPersons = assignedPersons;
    }

}
