package edu.bsu.cs222;

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
	private RevisionList revisionList;
	
	private WikiPageData(WikiPageBuilder builder) {
		this.wikiDoc = builder.wikiXML;
		this.revisionList = builder.revisionList;
		//this.title = builder.title;
		//this.wasRedirected = builder.wasRedirected;
		//this.setOfRevisions = builder.setOfRevisions;
		//this.numOfRevs = builder.numOfRevs;
	}

	public Revision revAtIndex(int index) {
		return revisionList.revisionAtIndex(index);
	}

	public Boolean checkRedirect(){
		NodeList redirectsTagList = wikiDoc.getElementsByTagName("redirects");
		return (redirectsTagList.getLength()>0);
	}

	public String pageTitle() {
		NodeList normalizedTagList = wikiDoc.getElementsByTagName("pages");
		Node node = normalizedTagList.item(0).getFirstChild();
		Element e = (Element)node;
		return e.getAttribute("title");
	}
	/*
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
		checkRedirect = (redirectsTagList.getLength()>0);
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
				.timestamp(timestamp)
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
	*/
	//public String getTitle() {
	//	return title;
	//}
	
	public int getNumOfRevs(){
		return revisionList.getNumOfRevs();
	}

	public static class WikiPageBuilder {
		private final int REVISIONS_WANTED = 10;
		private Document wikiXML;
		//private String title;
		//private boolean wasRedirected;
		//private int numOfRevs;
		//private Revision[] setOfRevisions;
		private RevisionList revisionList;

		public WikiPageBuilder(Document wikiXML) {
			this.wikiXML = wikiXML;
			this.revisionList = new RevisionList(wikiXML.getElementsByTagName("rev"));
			//parsePageData();
		}
/*
		private void setNumOfRevs() {
			NodeList revisionTagList = wikiXML.getElementsByTagName("rev");
			if (revisionTagList.getLength() <= REVISIONS_WANTED) {
				numOfRevs = revisionTagList.getLength();
			} else {
				numOfRevs = REVISIONS_WANTED;
			}
			setOfRevisions = new Revision[numOfRevs];
		}
/*
		public void parseTitle(){
			NodeList normalizedTagList = wikiXML.getElementsByTagName("pages");
			Node node = normalizedTagList.item(0).getFirstChild();
			Element e = (Element)node;
			title = e.getAttribute("title");
		}

		public void parseRedirect(){
			NodeList redirectsTagList = wikiXML.getElementsByTagName("redirects");
			wasRedirected = (redirectsTagList.getLength()>0);
		}

		public void parseRevisions(){
			NodeList revisionTagList = wikiXML.getElementsByTagName("rev");
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
			//String timestamp = e.getAttribute("timestamp");
			return new Revision.RevisionBuilder().buildFromElement(e).build();
		}

		public void parsePageData() {
			setNumOfRevs();
			//parseTitle();
			//parseRedirect();
			parseRevisions();
		}
*/
		public WikiPageData build(){
			return new WikiPageData(this);
		}
	}
	
}
