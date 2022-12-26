package com.darwinsys.bookmarks.model;

import javax.persistence.*;

@Entity
public class Bookmark {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	long id;

	@ManyToOne
	Topic topic;
	String description;
	String url;

	// Needed for JPA
	public Bookmark() {
	}

	// Needed for Q&D demo
	public Bookmark(Topic topic, String url, String description) {
		this.topic = topic;
		this.url = url;
		this.description = description;
	}
	public Bookmark(String topic, String url, String description) {
		this(new Topic(topic), url, description);
	}

	public String toString() { 
		return String.format("Bookmark(topic %s, url %s, text %s", topic, url, description);
	}

	public String toHtml() {
		return String.format("<a href='%s'>%s</a>", url, description);
	}
	public long getId() {
		return id;
	}

	public String getTopicId() {
		return topic.getId();
	}

	public String getUrl() {
		return url;
	}

	public String getDescription() {
		return description;
	}
}
