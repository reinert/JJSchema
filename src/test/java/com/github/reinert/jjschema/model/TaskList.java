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
