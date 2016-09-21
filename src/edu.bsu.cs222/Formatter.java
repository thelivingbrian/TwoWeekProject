package edu.bsu.cs222;

import java.sql.Timestamp;

public class Formatter {

	private Query wikiQuery;
	private WikiPageData wikiPage;
	
	public Formatter(Query query){
		this.wikiQuery = query;
		this.wikiPage = wikiQuery.getPageData();
	}
	
	public String makeTitle(){
		String titleLine = "Showing results for ";
		titleLine += wikiQuery.getTitle();
		if (wikiQuery.wasRedirected()){
			titleLine += " - Redirected from '" + wikiQuery.getQuery() + "'";
		}
		return titleLine;
	}
	
	public String makeData(){
		String output = "";
		for(int i = 0; i< wikiPage.getNumOfRevs(); i++){
			Revision revision = wikiPage.revAtIndex(i);
			output += "Revised on " + revision.getReadableTS() + " by " + revision.getAuthor()
					+ ".\n\tcomment:\t"	+ revision.getComment() + "\n\n";
		}
		return output;
	}
	
	public String makeUserAnalysis(){
		String output = "User Analysis:\n\n";
		UserList list = new UserList();
		for(int i = 1; i< wikiPage.getNumOfRevs(); i++){
			list.place(wikiPage.revAtIndex(i).getAuthor());
		}
		list.sort();
		for(int i=0; i<list.contributors(); i++){
			output+=list.userAtIndex(i).getName() + " made... '" + list.userAtIndex(i).getCount() + "' revision(s) to this page \n";
		}
		return output;
	}
	
	
	@SuppressWarnings("deprecation")
	public String readableTS(Timestamp ts){
		String timestamp = "";
		String timeOfDay = "a.m.";
		String minutes = "";
		int hours = ts.getHours();
		timestamp += (ts.getMonth()+1) + "/" + ts.getDate() + "/" + (ts.getYear()+1900) + " at ";
		if (hours > 12){
			hours = hours-12;
			timeOfDay="p.m.";
		}
		if (hours == 0){
			hours = 12;
		}
		if (ts.getMinutes()<10){
			minutes += "0";
		}
		minutes += ts.getMinutes();
		timestamp += hours + ":" + minutes + timeOfDay;
		return timestamp;
	}
	
}
