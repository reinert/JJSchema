package com.github.reinert.jjschema.model;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonManagedReference;

public class Person {

	String name;
	int age;
	@JsonManagedReference
	Collection<TaskList> createdTasks;
//	@JsonManagedReference("assigned")
//	Collection<TaskList> assignedTasks;
	
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
//	public Collection<TaskList> getAssignedTasks() {
//		return assignedTasks;
//	}
//	public void setAssignedTasks(Collection<TaskList> assignedTasks) {
//		this.assignedTasks = assignedTasks;
//	}
	
}
