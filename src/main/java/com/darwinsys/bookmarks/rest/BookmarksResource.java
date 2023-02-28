package com.darwinsys.bookmarks.rest;

import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.darwinsys.bookmarks.api.BookmarksService;
import com.darwinsys.bookmarks.model.Bookmark;
import com.darwinsys.bookmarks.model.Topic;

@Path("")
@ApplicationScoped
public class BookmarksResource implements BookmarksService {

	@Inject
	BookmarksDaoJpa dao;

	@GET
	@Path("topics")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Topic> topics() {
		return dao.topics();
	}

	@GET
	@Path("bookmarksByTopicId/{topic}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Bookmark> bookmarksByTopicId(@PathParam("topic") String topic) {
		return dao.bookmarksByTopicId(topic);
	}

	@GET
	@Path("bookmarksSearch/{pattern}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Bookmark> bookmarksSearch(@PathParam("pattern") String pattern) {
		return dao.bookmarksSearch(pattern);
	}

	@GET
	@Path("bookmarksRegex/{regex}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Bookmark> bookmarksRegex(@PathParam("regex") String regexString) {
		return dao.bookmarksRegex(regexString);
	}

	@GET
	@Path("bookmarkById/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Bookmark bookmarkById(@PathParam("id") long id) {
		return dao.bookmarkById(id);
	}

	// Uploads/Inserts into database and returns new pkey as text
	@POST
	@Path("bookmark")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public long postBookmark(Bookmark bookmark) throws Exception {
		return dao.postBookmark(bookmark);
	}
}
