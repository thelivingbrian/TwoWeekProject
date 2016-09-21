package edu.bsu.cs222;

import org.w3c.dom.Element;

import java.sql.Timestamp;

public class Revision {
	
	private String author, comment, timestampString;
	private Timestamp whenRevised;
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

	public String getReadableTS() {
		return wikiTS.getReadable();
	}
	
	public Timestamp getTimestamp() {
		return whenRevised;
	}


	public static class RevisionBuilder {
		private String author, comment, timestampString;
		private WikiTimestamp wikiTS;
		//private Timestamp timestamp;

		public RevisionBuilder author(String author){ this.author = author; return this; }
		public RevisionBuilder comment(String comment){ this.comment = comment; return this; }
		public RevisionBuilder timestamp(String ts){ this.wikiTS = new WikiTimestamp(ts); return this; }
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
