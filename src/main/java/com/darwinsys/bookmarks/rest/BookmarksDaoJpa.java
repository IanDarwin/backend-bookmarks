package com.darwinsys.bookmarks.rest;

import com.darwinsys.bookmarks.api.BookmarksService;
import com.darwinsys.bookmarks.model.Bookmark;
import com.darwinsys.bookmarks.model.Topic;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.BeforeDestroyed;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.TransactionScoped;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@ApplicationScoped
public class BookmarksDaoJpa implements BookmarksService {

	@Inject
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

	// Uploads/Inserts into database and returns new pkey as text
	@Transactional(Transactional.TxType.REQUIRED)
	public long postBookmark(Bookmark bookmark) throws Exception {
		if (bookmark.getId() == 0) {
			System.out.println("Persisting " + bookmark);
			em.persist(bookmark);
		} else {
			System.out.println("Merging " + bookmark);
			em.merge(bookmark);
		}
		// em.flush();
		System.out.println("bookmark.getId() = " + bookmark.getId());
		return bookmark.getId();
	}

	void onBeginTransaction(@Observes @Initialized(TransactionScoped.class) Object event) {
		// This gets invoked when a transaction begins.
		System.out.println("BookmarksResource.onBeginTransaction: " + event);
	}

	void onBeforeEndTransaction(@Observes @BeforeDestroyed(TransactionScoped.class) Object event) {
		// This gets invoked before a transaction ends (commit or rollback).
		System.out.println("BookmarksResource.onBeforeEndTransaction");
	}

	 void onAfterEndTransaction(@Observes @Destroyed(TransactionScoped.class) Object event) {
		// This gets invoked after a transaction ends (commit or rollback).
		System.out.println("BookmarksResource.onAfterEndTransaction");
	}
}
