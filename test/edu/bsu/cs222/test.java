package edu.bsu.cs222;

import java.io.UnsupportedEncodingException;

import org.junit.Assert;
import org.junit.Test;


/**
 * Created by Gunner Bills on 9/24/2016.
 */

public class test {
    Query.QueryBuilder queryBuilder = new Query.QueryBuilder("Indiana");
    Query query = new Query(queryBuilder);

    @Test
    public void createCorrectArticleLink() throws UnsupportedEncodingException {
        WikiURL.WikiURLBuilder builder = new WikiURL.WikiURLBuilder("Indiana").revisionURL();
        WikiURL url = new WikiURL(builder);
        String wikiLink = "https://en.wikipedia.org/w/api.php?action=query&prop=revisions&format=xml&rvprop=timestamp%7Ccomment%7Cuser&rvlimit=30&titles=Indiana&redirects=";
        Assert.assertEquals(url.getURL(), wikiLink);
    }

    @Test
    public void createCorrectUserLink() throws UnsupportedEncodingException {
        WikiURL.WikiURLBuilder builder = new WikiURL.WikiURLBuilder("Graham87").userURL();
        WikiURL url = new WikiURL(builder);
        String userLink = "https://en.wikipedia.org/w/api.php?action=query&format=xml&prop=&list=usercontribs&titles=&uclimit=50&ucstart=2016-07-02T17:03:45Z&ucuser=Graham87&ucdir=newer";
        Assert.assertEquals(url.getURL(), userLink);
    }

    @Test
    public void testForAPage(){
        Query query = new Query.QueryBuilder("Indiana").revQuery().build();
        Assert.assertTrue(query.wasSuccessful());

    }

    @Test
    public void testForNoPage(){
        Query query = new Query.QueryBuilder(";;;").revQuery().build();
        Assert.assertFalse(query.wasSuccessful());

    }

}
