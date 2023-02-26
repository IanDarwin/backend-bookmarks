package com.darwinsys.bookmarks.model;

import javax.persistence.*;

@Entity
public class Topic {
	@Id
	String id;
	String description;

	// Needed for JPA
	public Topic() {
	}

	// Needed for Q&D demo
	public Topic(String id, String text) {
		this.id = id;
		this.description = text;
	}

	public String toString() { 
		return id + "..." + description;
	}

	public String getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}
}
