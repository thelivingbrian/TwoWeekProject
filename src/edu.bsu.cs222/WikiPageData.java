package edu.bsu.cs222;

import java.sql.Timestamp;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class WikiPageData {

	private final int REVISIONS_WANTED = 10;

	private Document wikiDoc;
	private String title;
	private boolean wasRedirected;
	private int numOfRevs;
	private Revision[] setOfRevisions;
	
	public WikiPageData(Document doc){
		wikiDoc=doc;
		setNumOfRevs();
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
		for(int i=0; i<numOfRevs; i++){
			Node node = revisionTagList.item(i);
			Element e = (Element)node;
			setOfRevisions[i] = makeRevision(e);
		}
	}

	private Revision makeRevision(Element e) {
		// TODO - ask Prof. G if declaring variables is cleaner than using builder as is
		//String authorName = e.getAttribute("user");
		//String comment = e.getAttribute("comment");
		String timestamp = e.getAttribute("timestamp");
		Timestamp ts = Timestamp.valueOf(formatTS(timestamp));
		return new Revision.RevisionBuilder()
				.author(e.getAttribute("user"))
				.comment(e.getAttribute("comment"))
				.timestamp(ts)
				.build();
	}

	private void setNumOfRevs() {
		NodeList revisionTagList = wikiDoc.getElementsByTagName("rev");
		if (revisionTagList.getLength() <= REVISIONS_WANTED) {
			numOfRevs = revisionTagList.getLength();
		} else {
			numOfRevs = REVISIONS_WANTED;
		}
		setOfRevisions = new Revision[numOfRevs];
	}

	public String formatTS(String ts){
		char[] chars = ts.toCharArray();
	    chars[10] = ' ';
	    chars[19] = ' ';
	    return new String(chars);
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
