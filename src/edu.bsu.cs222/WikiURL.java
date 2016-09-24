package edu.bsu.cs222;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class WikiURL {
    private String URL;

    WikiURL(WikiURLBuilder builder){
        this.URL = builder.URL;
    }

    public String getURL() { return this.URL; }


    public static class WikiURLBuilder {
        String input, URL;

        public WikiURLBuilder(String input) {
            this.input = input;
        }

        public WikiURLBuilder revisionURL() throws UnsupportedEncodingException {
            String encodedTitle = URLEncoder.encode(input, "UTF-8");
            this.URL = "https://en.wikipedia.org/w/api.php?action=query&prop=revisions&format=xml&rvprop=timestamp%7Ccomment%7Cuser&rvlimit=30&titles="
                    + encodedTitle + "&redirects=";
            return this;
        }

        public WikiURLBuilder userURL() throws UnsupportedEncodingException {
            String encoded = URLEncoder.encode(input, "UTF-8");
            this.URL =  "https://en.wikipedia.org/w/api.php?action=query&format=xml&prop=&list=usercontribs&titles=&uclimit=50&ucstart=2016-07-02T17:03:45Z&ucuser="
                    + encoded + "&ucdir=newer";
            return this;
        }

        public WikiURL build() {
            return new WikiURL(this);
        }
    }

}
