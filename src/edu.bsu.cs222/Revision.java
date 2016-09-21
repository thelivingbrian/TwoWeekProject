package edu.bsu.cs222;

import java.sql.Timestamp;

public class Revision {
	
	private String author, comment;
	private Timestamp whenRevised;
	
	public Revision(RevisionBuilder builder) {
		this.author = builder.author;
		this.comment = builder.comment;
		this.whenRevised = builder.timestamp;
	}

	public String getAuthor() {
		return author;
	}
	
	public String getComment() {
		return comment;
	}
	
	public Timestamp getTimestamp() {
		return whenRevised;
	}


	public static class RevisionBuilder {
		private String author, comment;
		private Timestamp timestamp;

		public RevisionBuilder author(String author){ this.author = author; return this; }
		public RevisionBuilder comment(String comment){ this.comment = comment; return this; }
		public RevisionBuilder timestamp(Timestamp ts){ this.timestamp = ts; return this; }
		public Revision build() {
			return new Revision(this);
		}
	}
}
