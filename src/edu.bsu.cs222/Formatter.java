package edu.bsu.cs222;

public class Formatter {

	private Query wikiQuery;
	private WikiPageData wikiPage;
    private String queryResult, queryData;
	
	public Formatter(Query query){
		this.wikiQuery = query;
		if ( wikiQuery.wasSuccessful() ) {
            this.wikiPage = wikiQuery.getPageData();
            formatResult();
            formatRevData();
        }
	}
	
	public void formatResult(){
        this.queryResult = "Showing results for " + wikiQuery.getTitle();
        if ( wikiQuery.wasRedirected() ) {
            this.queryResult += " - Redirected from '" + wikiQuery.getQuery() + "'";
        }
	}
	
	public void formatRevData(){
		this.queryData = "";
		for(int i = 0; i < wikiPage.getNumOfRevs(); i++){
			Revision revision = wikiPage.getRevNumber(i);
			this.queryData += "Revised on " + revision.getReadableTS() + " by " + revision.getAuthor()
					+ ".\n\tcomment:\t"	+ revision.getComment() + "\n\n";
		}
	}

	public String getFormattedResult() { return queryResult; }

    public String getFormattedData() { return queryData; }
	
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
