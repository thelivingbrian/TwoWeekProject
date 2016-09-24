package edu.bsu.cs222;

import java.io.IOException;

public class Page {

	private String title, queryTitle;
	private boolean wasRedirected;
	private Revision[] setOfRevisions;
	private WikipediaConnection wiki;
	private Parser parser;
	
	public Page(){
				
	}
	
	public void query(String query) throws IOException {
		this.queryTitle = query;
		this.wiki = new WikipediaConnection(query);
		this.parser = new Parser(wiki.getXML());
		this.wasRedirected = parser.redirected();
		this.title = parser.getTitle();
		this.setOfRevisions = parser.revisionArray();
	}
	
	public Revision revAtIndex(int i){
		return setOfRevisions[i];
	}
	
	public int numOfRevisions(){
		return setOfRevisions.length;
	}
	
	public boolean redirectStatus(){
		return wasRedirected;
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getQuery(){
		return queryTitle;
	}
	
}
