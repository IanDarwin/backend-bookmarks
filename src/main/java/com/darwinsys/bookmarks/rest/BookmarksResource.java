package com.darwinsys.bookmarks.rest;

import com.darwinsys.bookmarks.api.BookmarksService;
import com.darwinsys.bookmarks.model.Bookmark;
import com.darwinsys.bookmarks.model.Topic;

import java.util.*;
import java.util.regex.*;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("")
public class BookmarksResource implements BookmarksService {

	@PersistenceContext
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
			createQuery("from Bookmark b where b.topic.id = ?1 order by b.description", Bookmark.class).
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

	// Uploads/Inserts into database and returns new pkey
	@POST
	@Path("bookmark")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Transactional(Transactional.TxType.REQUIRED)
	public long postBookmark(Bookmark bookmark) {
		System.out.println("bookmark = " + bookmark);
		if (bookmark.getId() == 0) {
			em.persist(bookmark);
		} else {
			em.merge(bookmark);
		}
		return bookmark.getId();
	}
}
