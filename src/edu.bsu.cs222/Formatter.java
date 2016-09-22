package edu.bsu.cs222;

public class Formatter {
	private Query wikiQuery;
    private String queryRedirect, queryResult;

	public void formatQuery(Query query){
		this.wikiQuery = query;
		if (wikiQuery.wasSuccessful()) {
			queryResult();
			queryRedirect();
		} else {
			queryFailed();
		}
	}

	private void queryFailed() {
		this.queryRedirect = "A wikipedia page for input '" + wikiQuery.getQuery() + "' could not be found.";
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
	
	public String formatRevision(Revision revision){
		try {
			String revString = "";
			revString += "Revised on " + revision.getReadableTS() + " by " + revision.getAuthor()
					+ ".\n\tcomment:\t" + revision.getComment() + "\n\n";
			return revString;
		} catch(NullPointerException e) {
			return "Null Revision Error";
		}
	}

	public String getQueryRedirect() { return queryRedirect; }

	public String getQueryResult() { return queryResult; }

}
