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
		List<Topic> results;
		results = em.createQuery("from Topic").getResultList();
        return results;
    }

    @GET
	@Path("/bookmarks/{topic}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Bookmark> bookmarksInTopic(@PathParam("topic") long topic) {
		List<Bookmark> results;
		results = em.
			createQuery("from Bookmark where topic_id = ?1").
			setParameter(1, topic).
			getResultList();
        return results;
    }
}
