package edu.bsu.cs222;

import java.sql.Timestamp;

public class Revision {
	
	private String author, comment;
	private Timestamp whenRevised;
	
	public Revision() {

	}

	public void setAuthor(String author){
		this.author = author;
	}
	
	public void setComment(String comment){
		this.comment = comment;
	}
	
	public void setTS(Timestamp ts){
		this.whenRevised = ts;
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

}
