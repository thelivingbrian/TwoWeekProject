package edu.bsu.cs222;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class WikipediaConnection {
	
	private Document XMLResults;

	public WikipediaConnection(String title) {
		try {
			URLConnection connection = connectToWikipedia(title);
			Document document = readXmlDocumentFrom(connection);
			this.XMLResults = document;
		}
		catch (Exception e) {
			
		}
	}
	
	public Document getXML() {
		return XMLResults;
	}

	private URLConnection connectToWikipedia(String title) throws IOException {
		URL url = new URL(createURL(title));
		URLConnection connection = url.openConnection();
		connection.setRequestProperty("User-Agent", "Revision Tracker/0.1 (http://www.cs.bsu.edu/~pvg/courses/cs222Fa16; btlynch@bsu.edu)");
		connection.connect();
		return connection;
	}
	
	public String createURL(String title){
		String URL = "";
		try {
			String encodedTitle = URLEncoder.encode(title, "UTF-8");
			URL = "https://en.wikipedia.org/w/api.php?action=query&prop=revisions&format=xml&rvprop=timestamp%7Ccomment%7Cuser&rvlimit=30&titles=" + encodedTitle +"&redirects=";
		}
		catch (UnsupportedEncodingException e) {
			
		}
		finally {
			return URL;
		}
	}
	
	private Document readXmlDocumentFrom(URLConnection connection) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		return documentBuilder.parse(connection.getInputStream());
	}
	
}