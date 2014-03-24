/*
 * Copyright (c) 2014, Danilo Reinert (daniloreinert@growbit.com)
 *
 * This software is dual-licensed under:
 *
 * - the Lesser General Public License (LGPL) version 3.0 or, at your option, any
 *   later version;
 * - the Apache Software License (ASL) version 2.0.
 *
 * The text of both licenses is available under the src/resources/ directory of
 * this project (under the names LGPL-3.0.txt and ASL-2.0.txt respectively).
 *
 * Direct link to the sources:
 *
 * - LGPL 3.0: https://www.gnu.org/licenses/lgpl-3.0.txt
 * - ASL 2.0: http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package com.github.reinert.jjschema.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.Collection;

public class Person {

    String name;
    int age;
    @JsonManagedReference
    Collection<TaskList> createdTasks;
    @JsonManagedReference("assigned")
    Collection<TaskList> assignedTasks;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Collection<TaskList> getCreatedTasks() {
        return createdTasks;
    }

    public void setCreatedTasks(Collection<TaskList> createdTasks) {
        this.createdTasks = createdTasks;
    }

    public Collection<TaskList> getAssignedTasks() {
        return assignedTasks;
    }

    public void setAssignedTasks(Collection<TaskList> assignedTasks) {
        this.assignedTasks = assignedTasks;
    }

}
