package com.darwinsys.bookmarks.model;

import javax.persistence.*;

@Entity
public class Topic {
	@Id // No generation strategy - inserter must provide!
	String id;
	String description;

	// Needed for JPA
	public Topic() {
	}

	// Needed for Q&D demo
	public Topic(String name) {
		this.id = name;
	}

	public String toString() { 
		return id;
	}

	public String getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}
}
