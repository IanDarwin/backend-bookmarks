package com.darwinsys.bookmarks.model;

import javax.persistence.*;

@Entity
public class Topic {
	@Id
	long id;

	String name;
	String description;

	// Needed for JPA
	public Topic() {
	}

	// Needed for Q&D demo
	public Topic(String name) {
		this.name = name;
	}

	public String toString() { 
		return name;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}
}
