package edu.bsu.cs222;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class WikiPageData {
	private Document wikiDoc;
	private RevisionList revisionList;
	
	private WikiPageData(WikiPageBuilder builder) {
		this.wikiDoc = builder.wikiXML;
		this.revisionList = builder.revisionList;
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
	
	public int getNumOfRevs(){ return revisionList.getNumOfRevs(); }

	public Revision getRevNumber(int index) { return revisionList.revisionAtIndex(index); }


	public static class WikiPageBuilder {
		private Document wikiXML;
		private RevisionList revisionList;

		public WikiPageBuilder(Document wikiXML) {
			this.wikiXML = wikiXML;
			this.revisionList = new RevisionList(wikiXML.getElementsByTagName("rev"));
		}

		public WikiPageData build(){
			return new WikiPageData(this);
		}
	}
	
}
