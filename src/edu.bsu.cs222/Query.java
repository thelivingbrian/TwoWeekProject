package edu.bsu.cs222;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class Query {
	private String title, queryTitle, sorting;
	private boolean wasSuccessful, redirectStatus;
	private WikiPageData wikiPage;

	Query(QueryBuilder builder){
		this.sorting = builder.sorting;
		this.queryTitle = builder.queryTitle;
		this.wasSuccessful = builder.wasSuccessful;
		this.wikiPage = builder.wikiPage;
		try {
			this.title = wikiPage.pageTitle();
			this.redirectStatus = wikiPage.checkRedirect();
		}
		catch(NullPointerException e){
			this.title = "Unable to connect to internet, please try again";
			this.redirectStatus = false;
		}

	}

	public boolean wasSuccessful() { return this.wasSuccessful; }

	public boolean wasRedirected() { return redirectStatus;	}

	public String getTitle() { return title; }

	public String getQuery() {
		return queryTitle;
	}

	public String getSorting() {
		return sorting;
	}

	public RevisionList revisionList() { return wikiPage.getRevisionList(); }

	public static class QueryBuilder {
		private String queryTitle, sorting;
		private boolean wasSuccessful;
		private WikiPageData wikiPage;
		private WikiURL wikiURL;

		public QueryBuilder(String input) {
			this.queryTitle = input;
		}

		public QueryBuilder revQuery() {
			try {
				this.sorting = "Title";
				this.wikiURL = new WikiURL.WikiURLBuilder(queryTitle).revisionURL().build();
			} catch (UnsupportedEncodingException e) {
				this.wasSuccessful = false;
			}
			revPage();
			return this;
		}

		private void revPage() {
			try {
				WikipediaConnection wikiConnection = new WikipediaConnection(wikiURL);
				this.wikiPage = new WikiPageData.WikiPageBuilder(wikiConnection.getXML()).wikiRevisions().build();
				this.wasSuccessful = (wikiPage.getNumOfRevs() > 0);
			}
			catch(IOException e){
				this.wasSuccessful = false;
			}
			catch(NullPointerException e){
				this.wasSuccessful = false;
			}
		}

		public QueryBuilder userQuery() {
			try {
				this.sorting = "User";
				this.wikiURL = new WikiURL.WikiURLBuilder(queryTitle).userURL().build();
			} catch (UnsupportedEncodingException e) {
				this.wasSuccessful = false;
			}
			userPage();
			return this;
		}

		public void userPage() {
			try {
				WikipediaConnection wikiConnection = new WikipediaConnection(wikiURL);
				this.wikiPage = new WikiPageData.WikiPageBuilder(wikiConnection.getXML()).wikiUser().build();
				this.wasSuccessful = (wikiPage.getNumOfRevs() > 0);
			}
			catch(IOException e){
				this.wasSuccessful = false;
			}
			catch(NullPointerException e){
				this.wasSuccessful = false;
			}
		}

		public Query build() {
			return new Query(this);
		}
	}
}
