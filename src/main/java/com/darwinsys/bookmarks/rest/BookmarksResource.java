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
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Path("")
@ApplicationScoped
public class BookmarksResource implements BookmarksService {

	@Inject
	EntityManager em;

	@GET
	@Path("topics")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Topic> topics() {
		return em.createQuery("from Topic t order by t.description", Topic.class).getResultList();
	}

	@GET
	@Path("bookmarksByTopicId/{topic}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Bookmark> bookmarksByTopicId(@PathParam("topic") String topic) {
		return em.
			createQuery("from Bookmark b where b.topicId = ?1 order by b.description", Bookmark.class).
			setParameter(1, topic).
			getResultList();
	}

	@GET
	@Path("bookmarksSearch/{pattern}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Bookmark> bookmarksSearch(@PathParam("pattern") String pattern) {
		var likePattern = String.format("%%%s%%", pattern.toLowerCase());
		System.out.println("BookmarksResource::bookmarksBySearch(" + likePattern + ")");
		return em.
			createQuery("from Bookmark b where lower(b.description) like ?1 order by b.description", Bookmark.class).
			setParameter(1, likePattern).
			getResultList();
	}

	@GET
	@Path("bookmarksRegex/{regex}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Bookmark> bookmarksRegex(@PathParam("regex") String regexString) {
		List<Bookmark> results = new ArrayList<>();
		Pattern patt = Pattern.compile(regexString);
		em.createQuery("from Bookmark b order by b.description", Bookmark.class).getResultList().
			forEach(b -> { if (patt.matcher(b.getDescription()).find()) results.add(b); } );
		return results;
	}

	@GET
	@Path("bookmarkById/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Bookmark bookmarkById(@PathParam("id") long id) {
		return em.find(Bookmark.class, id);
	}

	// Uploads/Inserts into database and returns new pkey as text
	@POST
	@Path("bookmark")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	@Transactional(Transactional.TxType.REQUIRED)
	public long postBookmark(Bookmark bookmark) throws Exception {
		System.out.println("bookmark = " + bookmark);
		if (bookmark.getId() == 0) {
			System.out.println("persisting");
			em.persist(bookmark);
		} else {
			System.out.println("merging");
			em.merge(bookmark);
		}
		// em.flush();
		System.out.println("bookmark.getId() = " + bookmark.getId());
		return bookmark.getId();
	}

	void onBeginTransaction(@Observes @Initialized(TransactionScoped.class) Object event) {
		// This gets invoked when a transaction begins.
		System.out.println("BookmarksResource.onBeginTransaction");
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
