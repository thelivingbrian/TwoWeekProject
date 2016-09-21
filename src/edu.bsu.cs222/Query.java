package edu.bsu.cs222;

public class Query {

	private String title, queryTitle;
	private boolean wasRedirected;
	private Revision[] setOfRevisions;
	private WikipediaConnection wiki;
	private WikiPageData wikiPageData;
	
	//public Query(){
				
	//}
	
	public void query(String query){
		this.queryTitle = query;
		this.wiki = new WikipediaConnection(query);
		this.wikiPageData = new WikiPageData(wiki.getXML());
		this.wasRedirected = wikiPageData.redirected();
		this.title = wikiPageData.getTitle();
		this.setOfRevisions = wikiPageData.revisionArray();
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
