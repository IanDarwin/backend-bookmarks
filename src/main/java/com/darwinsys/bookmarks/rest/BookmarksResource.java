package com.darwinsys.bookmarks.rest;

import com.darwinsys.bookmarks.model.Bookmark;
import com.darwinsys.bookmarks.model.Topic;

import java.util.List;

import javax.persistence.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;

@Path("/rs")
public class BookmarksResource {

	@PersistenceContext
	EntityManager em;

    @GET
	@Path("/topics")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Topic> topics() {
		List<Topic> results = em.createQuery("from Topic").getResultList();
        return results;
    }

    @GET
	@Path("/bookmarksByTopicId/{topic}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Bookmark> bookmarksByTopicId(@PathParam("topic") long topic) {
		List<Bookmark> results = em.
			createQuery("from Bookmark where topic_id = ?1").
			setParameter(1, topic).
			getResultList();
        return results;
    }

    @GET
	@Path("/bookmarksSearch/{pattern}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Bookmark> bookmarksByTopicId(@PathParam("pattern") String pattern) {
		return em.
			createQuery("from Bookmark where description like lower(%?1%)").
			setParameter(1, pattern.toLowerCase()).
			getResultList();
    }

    @GET
	@Path("/bookmarkById/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Bookmark bookmarkById(@PathParam("id") long id) {
		return em.find(Bookmark.class, id);
    }
}
