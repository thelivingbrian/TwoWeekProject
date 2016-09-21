package edu.bsu.cs222;

public class Formatter {

	private Query wikiQuery;
	private WikiPageData wikiPage;
    private String queryRedirect, revData;
	private String queryResult;

	public Formatter(Query query){
		this.wikiQuery = query;
		this.wikiPage = wikiQuery.getPageData();

		if (wikiQuery.wasSuccessful()) {
			queryResult();
			queryRedirect();
		} else {
			queryFailed();
		}
	}

	private void queryFailed() {
		this.queryRedirect = "A wikipedia page for input '" + wikiQuery.getQuery() + "' could not be found.";
		//this.revData = "No Revisions to show";
		this.queryResult = "INVALID INPUT";
	}

	private void queryResult() {
		this.queryResult = "Query Sent";
	}

	public void queryRedirect(){
        this.queryRedirect = "Showing results for " + wikiQuery.getTitle();
        if ( wikiQuery.wasRedirected() ) {
            this.queryRedirect += " - Redirected from '" + wikiQuery.getQuery() + "'";
        }
	}

	public void pageRevisions() {
		for (Revision revision : wikiPage.getRevisionList().getArray()) {

		}
	}
	
	public String formatRevision(Revision revision){
		revData = "";
		//for(int i = 0; i < wikiPage.getNumOfRevs(); i++){
		//	Revision revision = wikiPage.getRevNumber(i);
		this.revData += "Revised on " + revision.getReadableTS() + " by " + revision.getAuthor()
					+ ".\n\tcomment:\t"	+ revision.getComment() + "\n\n";
		return revData;

	}

	public String getQueryRedirect() { return queryRedirect; }

    public String getFormattedData() { return revData; }

	public String getQueryResult() { return queryResult; }

	public String makeUserAnalysis(){
		String output = "User Analysis:\n\n";
		UserList list = new UserList();
		for(int i = 1; i< wikiPage.getNumOfRevs(); i++){
			list.place(wikiPage.getRevNumber(i).getAuthor());
		}
		list.sort();
		for(int i=0; i<list.contributors(); i++){
			output+=list.userAtIndex(i).getName() + " made... '" + list.userAtIndex(i).getCount() + "' revision(s) to this page \n";
		}
		return output;
	}

}
