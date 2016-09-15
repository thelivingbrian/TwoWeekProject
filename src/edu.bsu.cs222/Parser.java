package edu.bsu.cs222;

import java.sql.Timestamp;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Parser {

	private Document wikiDoc;
	private String title;
	private boolean wasRedirected;
	private int numOfRevs = 30;
	private Revision[] setOfRevisions;
	
	public Parser(Document doc){		
		wikiDoc=doc;
		parseTitle();
		parseRedirect();
		parseRevisions();
	}
	
	public void parseTitle(){
		NodeList normalizedTagList = wikiDoc.getElementsByTagName("pages");
		Node node = normalizedTagList.item(0).getFirstChild();
		Element e = (Element)node;
		title = e.getAttribute("title");		
	}
	
	public void parseRedirect(){
		NodeList redirectsTagList = wikiDoc.getElementsByTagName("redirects");				
		wasRedirected = (redirectsTagList.getLength()>0);
	}

	public void parseRevisions(){	
		NodeList revisionTagList = wikiDoc.getElementsByTagName("rev");
		
		if (revisionTagList.getLength()<=30){
			numOfRevs = revisionTagList.getLength();
		}
		
		this.setOfRevisions = new Revision[numOfRevs];
		
		for(int i=0; i<numOfRevs; i++){

			Node node = revisionTagList.item(i);
			Element e = (Element)node;
			String authorName = e.getAttribute("user");
			String comment = e.getAttribute("comment");
			String timestamp = e.getAttribute("timestamp");
	        Timestamp ts = Timestamp.valueOf(formatTS(timestamp));

			Revision revision = new Revision();
			revision.setAuthor(authorName);
			revision.setComment(comment);
			revision.setTS(ts);			
			setOfRevisions[i] = revision;
		}
	}
	
	public String formatTS(String ts){
		char[] chars = ts.toCharArray();
	    chars[10] = ' ';
	    chars[19] = ' ';
	    return new String(chars);
	}
	
	public Document getDoc(){
		return wikiDoc;
	}	
	
	public String getTitle() {
		return title;
	}
	
	public boolean redirected() {
		return wasRedirected;
	}
	
	public Revision[] revisionArray(){
		return setOfRevisions;
	}
	
}
