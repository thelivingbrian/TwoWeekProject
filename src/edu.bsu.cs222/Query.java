package edu.bsu.cs222;

public class Query {

	private String title, queryTitle;
	private boolean redirectStatus;
	private WikipediaConnection wiki;
	private WikiPageData wikiPage;
	
	public Query(String query){
		this.queryTitle = query;
		this.wiki = new WikipediaConnection(query);
		this.wikiPage = new WikiPageData.WikiPageBuilder( wiki.getXML() ).build();
		this.redirectStatus = wikiPage.checkRedirect();
		this.title = wikiPage.pageTitle();
	}

	public boolean wasRedirected(){ return this.redirectStatus; }

	public String getTitle(){ return title; }
	
	public String getQuery(){
		return queryTitle;
	}

	public WikiPageData getPageData() { return wikiPage; }
}
