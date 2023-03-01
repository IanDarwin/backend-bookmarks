package com.darwinsys.bookmarks.model;
import javax.persistence.*;
import java.util.Objects;

/**
 * Represents one bookmarked URL with a description.
 * JPA Entity field annotations used.
 */
@Entity
public class Bookmark {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	long id;

	// Just for debugging - Delete for production!!
	@PrePersist
	public void prePersist() {
		System.out.println("Bookmark.prePersist: id = " + id);
	}
	@PostPersist
	public void postPersist() {
		System.out.println("Bookmark.postPersist: id = " + id);
	}

	// Should be in a @ManyToOne with Topic, but String easier over REST
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
		return String.format("Bookmark(#%d, topic %s, url %s, text %s)", id, topicId, url, description);
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Bookmark)) return false;
		Bookmark bookmark = (Bookmark) o;
		return getId() == bookmark.getId()
				&& getTopicId().equals(bookmark.getTopicId())
				&& getDescription().equals(bookmark.getDescription())
				&& getUrl().equals(bookmark.getUrl());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getTopicId(), getDescription(), getUrl());
	}
}
