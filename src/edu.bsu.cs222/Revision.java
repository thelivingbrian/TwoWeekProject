package edu.bsu.cs222;

import org.w3c.dom.Element;

public class Revision {
	
	private String author, comment;
	private WikiTimestamp wikiTS;
	
	public Revision(RevisionBuilder builder) {
		this.author = builder.author;
		this.comment = builder.comment;
		this.wikiTS = builder.wikiTS;
	}

	public String getAuthor() {
		return author;
	}
	
	public String getComment() {
		return comment;
	}

	public String getReadableTS() {	return wikiTS.getReadable(); }

	public String getTitle(){
		return "Title";
	}


	public static class RevisionBuilder {
		private String author, comment;
		private WikiTimestamp wikiTS;

		public RevisionBuilder buildFromElement(Element e) {
			this.author = e.getAttribute("user");
			this.comment = e.getAttribute("comment");
			this.wikiTS = new WikiTimestamp(e.getAttribute("timestamp"));
			return this;
		}

		public Revision build() {
			return new Revision(this);
		}
	}
}
