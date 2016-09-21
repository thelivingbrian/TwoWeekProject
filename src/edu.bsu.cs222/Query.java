package edu.bsu.cs222;

public class Query {

	private String title, queryTitle;
	private boolean redirectStatus;
	private Revision[] setOfRevisions;
	private WikipediaConnection wiki;
	private WikiPageData wikiPage;
	
	//public Query(){
				
	//}
	
	public Query(String query){
		this.queryTitle = query;
		this.wiki = new WikipediaConnection(query);
		this.wikiPage = new WikiPageData.WikiPageBuilder( wiki.getXML() ).build();
		this.redirectStatus = wikiPage.checkRedirect();
		this.title = wikiPage.pageTitle();
	}

	public boolean wasRedirected(){ return this.redirectStatus; }
	/*
	public Revision revisionNumber(int i){
		return wikiPage.revAtIndex(i);  //setOfRevisions[i];
	}
	
	public int numOfRevisions(){
		return setOfRevisions.length;
	}
	
	public boolean redirectStatus(){
		return checkRedirect;
	}
	*/
	public String getTitle(){ return title; }
	
	public String getQuery(){
		return queryTitle;
	}

	public WikiPageData getPageData() { return wikiPage; }
}
