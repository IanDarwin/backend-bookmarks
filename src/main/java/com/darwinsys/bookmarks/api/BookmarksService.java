package com.darwinsys.bookmarks.api;

import com.darwinsys.bookmarks.model.*;

import java.util.*;

public interface BookmarksService {

	public List<Topic> topics();

	public List<Bookmark> bookmarksByTopicId(String topicId);

	public List<Bookmark> bookmarksSearch(String pattern);

	public List<Bookmark> bookmarksRegex(String regexString);
	public Bookmark bookmarkById(long bookmarkId);
	// Inserts into database and returns new pkey

	public long postBookmark(Bookmark newBookmark) throws Exception;
}
