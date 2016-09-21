package edu.bsu.cs222;

public class Query {

	private String title, queryTitle;
	private boolean wasSuccessful, redirectStatus;
	private WikiPageData wikiPage;
	
	public Query(String query){
		this.queryTitle = query;
		attemptConnection();
	}

	private void attemptConnection(){
		try {
			WikipediaConnection wiki = new WikipediaConnection(queryTitle);
			this.wikiPage = new WikiPageData.WikiPageBuilder(wiki.getXML()).build();
			this.redirectStatus = wikiPage.checkRedirect();
			this.title = wikiPage.pageTitle();
			this.wasSuccessful = true;
		} catch (NullPointerException e) {
			this.wasSuccessful = false;
		}
	}

	public boolean wasSuccessful(){ return this.wasSuccessful; }

	public boolean wasRedirected(){ return this.redirectStatus; }

	public String getTitle(){ return title; }
	
	public String getQuery(){
		return queryTitle;
	}

	public WikiPageData getPageData() { return wikiPage; }

	public RevisionList getRevisions() { return wikiPage.getRevisionList(); }
}
