package edu.bsu.cs222;

import java.io.UnsupportedEncodingException;

public class Query {
	private String title, queryTitle;
	private boolean wasSuccessful, redirectStatus;
	private WikiPageData wikiPage;

	private Query(QueryBuilder builder) {
		this.queryTitle = builder.queryTitle;
		this.wasSuccessful = builder.wasSuccessful;
		this.wikiPage = builder.wikiPage;
		this.title = wikiPage.pageTitle();
		this.redirectStatus = wikiPage.checkRedirect();
	}

	public boolean wasSuccessful() { return this.wasSuccessful; }

	public boolean wasRedirected() { return redirectStatus;	}

	public String getTitle() { return title; }

	public String getQuery() {
		return queryTitle;
	}

	public RevisionList revisionList() { return wikiPage.getRevisionList(); }

	public static class QueryBuilder {
		private String queryTitle;
		private boolean wasSuccessful;
		private WikiPageData wikiPage;
		private WikiURL wikiURL;

		public QueryBuilder(String input) {
			this.queryTitle = input;
		}

		public QueryBuilder revQuery() {
			try {
				this.wikiURL = new WikiURL.WikiURLBuilder(queryTitle).revisionURL().build();
			} catch (UnsupportedEncodingException e) {
				this.wasSuccessful = false;
			}
			revPage();
			return this;
		}
		private void revPage() {
			WikipediaConnection wikiConnection = new WikipediaConnection(wikiURL);
			this.wikiPage = new WikiPageData.WikiPageBuilder(wikiConnection.getXML()).wikiRevisions().build();
			this.wasSuccessful = (wikiPage.getNumOfRevs() > 0);
		}

		public QueryBuilder userQuery() {
			try {
				this.wikiURL = new WikiURL.WikiURLBuilder(queryTitle).userURL().build();
			} catch (UnsupportedEncodingException e) {
				this.wasSuccessful = false;
			}
			userPage();
			return this;
		}
		public void userPage() {
			WikipediaConnection wikiConnection = new WikipediaConnection(wikiURL);
			this.wikiPage = new WikiPageData.WikiPageBuilder(wikiConnection.getXML()).wikiUser().build();
			this.wasSuccessful = (wikiPage.getNumOfRevs() > 0);
		}

		public Query build() {
			return new Query(this);
		}
	}
}
