package com.example.rssfeedreader.util;

import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import android.provider.OpenableColumns;

import com.example.rssfeedreader.R;
import com.example.rssfeedreader.data.RssNews;

/**
 * this class is responsible for launching SAX parser, and process the RSS data.
 */
public class RssReader {
//define a field for the RSS feed URL
	//private String rssUrl = "http://www.npr.org/rss/rss.php?id=1001";
	private String rssUrl;
	
//set this url with the constructor
	public RssReader(String rssUrl){
		this.rssUrl = rssUrl;
	}
//Get RSS items. This method will be called to get the parsing process result.
	public List<RssNews> getNews() throws Exception {
		//get an SAX Parser Factory object
		SAXParserFactory saxFactory = SAXParserFactory.newInstance();
		//Using factory to create a new SAX parser instance
		SAXParser saxParser = saxFactory.newSAXParser();
		//need the SAX parser handler object
		SaxParserHandler handler = new SaxParserHandler();
		//call the method parsing our RSS Feed
		saxParser.parse(rssUrl, handler);
		//The result of the parsing process is being stored in the handler project
		return handler.getINews();
		
	}
}
