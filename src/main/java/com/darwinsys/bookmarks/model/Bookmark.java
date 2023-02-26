package com.darwinsys.bookmarks.model;

import javax.persistence.*;

@Entity
public class Bookmark {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	long id;

	// Should be in a @ManyToOne with Topic, but can't use over REST
	@Column(name="topic_id")
	String topicId;
	@Column(name="text")
	String description;
	String url;

	// Needed for JPA
	public Bookmark() {
	}

	// Needed for Q&D demo
	public Bookmark(String topicId, String url, String description) {
		this.topicId = topicId;
		this.url = url;
		this.description = description;
	}

	public String toString() { 
		return String.format("Bookmark(topic %s, url %s, text %s)", topicId, url, description);
	}

	public String toHtml() {
		return String.format("<a href='%s'>%s</a>", url, description);
	}

	public long getId() {
		return id;
	}

	public String getTopicId() {
		return topicId;
	}

	public String getUrl() {
		return url;
	}

	public String getDescription() {
		return description;
	}
}
