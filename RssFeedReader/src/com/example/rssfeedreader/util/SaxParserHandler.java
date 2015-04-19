package com.example.rssfeedreader.util;
/**
 * Data is structured as an XML document, use an SAX parser to retrive XML items.
 * SAX is a stream parser, a handler is created to react on individual events, such as opening
 * and closing tags.
 */

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.example.rssfeedreader.data.RssNews;

public class SaxParserHandler extends DefaultHandler {
	//List of items parsed
	private List<RssNews> rssNews;
	private RssNews currentItem;
	private boolean parsingTitle;
	private boolean parsingLink;
	
	public SaxParserHandler() {
		rssNews = new ArrayList();
	}
	//use this method to returns a list of items that read from the RSS feed
	public List<RssNews> getINews(){
		return rssNews;
	}
   //The StartElement method creates an empty RssNews object when an item start tag is being processed. 
	//When a title or link tag are being processed appropriate indicators are set to true.
	@Override
   	public void startElement(String uri, String localName, String qName,
   			Attributes attributes) throws SAXException {
   		if("item".equals(qName)){
   			currentItem = new RssNews();
   		}else if ("title".equals(qName)){
   			parsingTitle = true;
   		}else if ("link".equals(qName)){
   		 parsingLink = true;
   		}
   	}
   	//The EndElement method adds the current RssNews to the list when a closing item tag is processed. 
	//It sets appropriate indicators to false -  when title and link closing tags are processed 
	@Override
   	public void endElement(String uri, String localName, String qName)
   			throws SAXException {
   		if("item".equals(qName)){
   			rssNews.add(currentItem);
   			currentItem = null;
   		}else if("title".equals(qName)){
   			parsingTitle = false;
   		}else if("link".equals(qName)){
   			parsingLink = false;
   		}
   	}
//Characters method fills current RssNews object with data when title and link tag content is being processed	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		if(parsingTitle){
			if(currentItem != null)
				currentItem.setTitle(new String(ch, start, length));
		}else if(parsingLink){
			if(currentItem != null){
				currentItem.setLink(new String(ch, start, length));
				parsingLink = false;
			}
		}
	}
}
