package edu.bsu.cs222;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.IOException;
import java.util.List;

public class WikiPageData {
	private Document wikiDoc;
	private List<Revision> revList;
	private RevisionList revisionList;
	
	private WikiPageData(WikiPageBuilder builder) {
		this.wikiDoc = builder.wikiXML;
		revisionList = new RevisionList( builder.nodes );
		//For(Node node : builder.nodes) {}
	}

	public Boolean checkRedirect(){
		NodeList redirectsTagList = wikiDoc.getElementsByTagName("redirects");
		return (redirectsTagList.getLength()>0);
	}

	public String pageTitle() {
		try {
			NodeList normalizedTagList = wikiDoc.getElementsByTagName("pages");
			Node node = normalizedTagList.item(0).getFirstChild();
			Element e = (Element) node;
			return e.getAttribute("title");
		} catch(NullPointerException e) {
			return "Page title is invalid";
		}

	}
	
	public int getNumOfRevs(){ return revisionList.size(); }

	public RevisionList getRevisionList() {
		return revisionList;
	}


	public static class WikiPageBuilder {
		private Document wikiXML;
		private NodeList nodes;
		//private revisionList

		public WikiPageBuilder(Document wikiXML) {
			this.wikiXML = wikiXML;
		}

		public WikiPageBuilder wikiRevisions() throws NullPointerException, IOException {
			//this.nodes = new RevisionList(wikiXML.getElementsByTagName("rev"));
			this.nodes = wikiXML.getElementsByTagName("rev");
			return this;
		}
		public WikiPageBuilder wikiUser() {
			nodes = wikiXML.getElementsByTagName("item");
			return this;
		}
		public WikiPageData build(){
			return new WikiPageData(this);
		}
	}
	
}
