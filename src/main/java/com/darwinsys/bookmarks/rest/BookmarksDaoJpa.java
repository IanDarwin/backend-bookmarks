package com.darwinsys.bookmarks.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.BeforeDestroyed;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.TransactionScoped;

import com.darwinsys.bookmarks.api.BookmarksService;
import com.darwinsys.bookmarks.model.Bookmark;
import com.darwinsys.bookmarks.model.Topic;

@ApplicationScoped
public class BookmarksDaoJpa implements BookmarksService {

	@PersistenceContext(unitName="bookmarks")
	EntityManager em;

	public List<Topic> topics() {
		return em.createQuery("from Topic t order by t.description", Topic.class).getResultList();
	}

	public List<Bookmark> bookmarksByTopicId(String topic) {
		return em.
			createQuery("from Bookmark b where b.topicId = ?1 order by b.description", Bookmark.class).
			setParameter(1, topic).
			getResultList();
	}

	public List<Bookmark> bookmarksSearch(String pattern) {
		var likePattern = String.format("%%%s%%", pattern.toLowerCase());
		System.out.println("BookmarksResource::bookmarksBySearch(" + likePattern + ")");
		return em.
			createQuery("from Bookmark b where lower(b.description) like ?1 order by b.description", Bookmark.class).
			setParameter(1, likePattern).
			getResultList();
	}

	public List<Bookmark> bookmarksRegex(String regexString) {
		List<Bookmark> results = new ArrayList<>();
		Pattern patt = Pattern.compile(regexString);
		em.createQuery("from Bookmark b order by b.description", Bookmark.class).getResultList().
			forEach(b -> { if (patt.matcher(b.getDescription()).find()) results.add(b); } );
		return results;
	}

	public Bookmark bookmarkById(long id) {
		return em.find(Bookmark.class, id);
	}

	// Uploads/Inserts into database and returns new pkey #
	public long postBookmark(Bookmark bookmark) throws Exception {
		if (bookmark.getId() == 0) {
			System.out.println("Persisting " + bookmark);
			em.persist(bookmark);
		} else {
			System.out.println("Merging " + bookmark);
			em.merge(bookmark);
		}
		// To be portable, one should flush() before getting the id.
		em.flush();
		return bookmark.getId();
	}
}
