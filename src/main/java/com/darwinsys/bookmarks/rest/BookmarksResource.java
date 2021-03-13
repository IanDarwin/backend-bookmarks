package com.darwinsys.bookmarks.rest;

import com.darwinsys.bookmarks.model.Bookmark;
import com.darwinsys.bookmarks.model.Topic;

import java.util.*;
import java.util.regex.*;

import javax.persistence.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

@Path("")
public class BookmarksResource {

	@PersistenceContext
	EntityManager em;

    @GET
	@Path("topics")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Topic> topics() {
		List<Topic> results = em.createQuery("from Topic").getResultList();
        return results;
    }

    @GET
	@Path("bookmarksByTopicId/{topic}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Bookmark> bookmarksByTopicId(@PathParam("topic") long topic) {
		List<Bookmark> results = em.
			createQuery("from Bookmark where topic_id = ?1").
			setParameter(1, topic).
			getResultList();
        return results;
    }

    @GET
	@Path("bookmarksSearch/{pattern}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Bookmark> bookmarksSearch(@PathParam("pattern") String pattern) {
		var likePattern = String.format("%%%s%%", pattern.toLowerCase());
		System.out.println("BookmarksResource::bookmarksBySearch(" + likePattern + ")");
		return em.
			createQuery("from Bookmark where description like ?1").
			setParameter(1, likePattern).
			getResultList();
    }

    @GET
	@Path("bookmarksRegex/{regex}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Bookmark> bookmarksRegex(@PathParam("regex") String regexString) {
		List<Bookmark> results = new ArrayList();
		Pattern patt = Pattern.compile(regexString);
		em.createQuery("from Bookmark", Bookmark.class).getResultList().
			forEach(b -> { if (patt.matcher(b.getDescription()).find()) results.add(b); } );
		return results;
	}

    @GET
	@Path("bookmarkById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Bookmark bookmarkById(@PathParam("id") long id) {
		return em.find(Bookmark.class, id);
    }
}
