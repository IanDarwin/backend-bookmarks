package com.darwinsys.quarkus;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

import com.darwinsys.bookmarks.model.Bookmark;

public class BookmarkTest {

	final static String url = "https://telconews.com/foo";
	final static String cut = "What was AT&T Thinking?";
	Bookmark b;

	@BeforeEach
	public void setup() {
        b = new Bookmark("att", url, cut);
	}

	@Test
	public void testIdsAreZeroForNewObjs() {
		assertEquals(0, b.getId());
		assertEquals(0, b.getTopicId());
	}
	@Test
	public void testGetMainFields() {
		assertEquals("att", b.getId());
		assertEquals(url, b.getUrl());
		assertEquals(cut, b.getDescription());
	}
	@Test
	public void testAsHtml() {
		assertEquals("<a href='" + url + "'>" + cut + "</a>", b.toHtml());
	}
}
