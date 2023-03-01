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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Topic topic = (Topic) o;

		if (!getId().equals(topic.getId())) return false;
		return getDescription().equals(topic.getDescription());
	}

	@Override
	public int hashCode() {
		int result = getId().hashCode();
		result = 31 * result + getDescription().hashCode();
		return result;
	}
}
