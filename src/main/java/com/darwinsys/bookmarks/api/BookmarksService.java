package com.darwinsys.bookmarks.api;

import com.darwinsys.bookmarks.model.*;

import java.util.*;

import javax.persistence.*;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("")
@Produces(MediaType.APPLICATION_JSON)
public interface BookmarksService {
	@GET@Path("topics")
	public List<Topic> topics();
	@GET@Path("bookmarksByTopicId/{topic}")
	public List<Bookmark> bookmarksByTopicId(@PathParam("topic") long topic);
	@GET@Path("bookmarksSearch/{pattern}")
	public List<Bookmark> bookmarksSearch(@PathParam("pattern") String pattern);
	@GET@Path("bookmarksRegex/{regex}")
	public List<Bookmark> bookmarksRegex(@PathParam("regex") String regexString);
	@GET@Path("bookmarkById/{id}")
	public Bookmark bookmarkById(@PathParam("id") long id);
	// Inserts into database and returns new pkey
	@POST@Path("bookmark")
	public long addBookmark(Bookmark newBookmark);
}
