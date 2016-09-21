package edu.bsu.cs222;

public class Query {

	private String title, queryTitle;
	private boolean redirectStatus;
	private WikiPageData wikiPage;
	
	public Query(String query){
		this.queryTitle = query;
		attemptConnection();
	}

	public void attemptConnection(){
		WikipediaConnection wiki = new WikipediaConnection(queryTitle);
		this.wikiPage = new WikiPageData.WikiPageBuilder( wiki.getXML() ).pageExists().build();
		this.redirectStatus = wikiPage.checkRedirect();
		this.title = wikiPage.pageTitle();
	}

	public boolean wasSuccessful(){
		if ( queryTitle.equals("") ) { return false; }
		if ( !(wikiPage.exists()) ) { return false; }
		else { return true; }
	}

	public boolean wasRedirected(){ return this.redirectStatus; }

	public String getTitle(){ return title; }
	
	public String getQuery(){
		return queryTitle;
	}

	public WikiPageData getPageData() { return wikiPage; }
}
