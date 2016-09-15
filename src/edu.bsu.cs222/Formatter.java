package edu.bsu.cs222;

import java.sql.Timestamp;

public class Formatter {

	private Page wikipage;
	
	public Formatter(Page page){
		wikipage=page;
	}
	
	public String makeTitle(){
		String titleLine = "Showing results for ";
		titleLine += wikipage.getTitle();
		if (wikipage.redirectStatus()){
			titleLine += " - Redirected from '" + wikipage.getQuery() + "'";
		}
		return titleLine;
	}
	
	public String makeData(){
		String output = "";
		for(int i=0; i< wikipage.numOfRevisions(); i++){
			output+= "Revised on " + readableTS(wikipage.revAtIndex(i).getTimestamp()) + " by " + wikipage.revAtIndex(i).getAuthor() 
					+ ".\n\tcomment:\t"	+ wikipage.revAtIndex(i).getComment() + "\n\n";
		}
		return output;
	}
	
	public String makeUserAnalysis(){
		String output = "User Analysis:\n\n";
		UserList list = new UserList();
		for(int i=1; i<wikipage.numOfRevisions(); i++){
			list.place(wikipage.revAtIndex(i).getAuthor());
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
