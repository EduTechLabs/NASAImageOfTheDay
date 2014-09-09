package com.example.parser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class IotdHandler extends DefaultHandler {


	private static final String TAG = IotdHandler.class.getSimpleName();

	private static final String ITEM = "item";
	private static final String TITLE = "title";
	private static final String PUBDATE = "pubDate";
	private static final String IMAGE = "enclosure";
	private static final String IMAGEURL= "url";
	
	private static final String DESCRIPTION = "description" ;
	
	
	private String url;
	private boolean inTitle = false;
	private boolean inDescription = false;
	private boolean inItem = false;
	private boolean inDate = false;
	
	private ArrayList<ApodFeedItem> feedItemList = null;
	private ApodFeedItem feedItem = null;
	
	private StringBuffer title = new StringBuffer();
	private StringBuffer description = new StringBuffer();
	private StringBuffer date = new StringBuffer();
	private String imgUrl = null;
	

	public IotdHandler(String murl) {
		url = murl;
	}
	
	public IotdHandler() {

	}

	public ArrayList<ApodFeedItem> getFeedItemList() {
		return feedItemList;
	}
	public StringBuffer getTitle() {
		return title;
	}
	public void setTitle(StringBuffer title) {
		this.title = title;
	}
	public StringBuffer getDescription() {
		return description;
	}
	public void setDescription(StringBuffer description) {
		this.description = description;
	}
	public StringBuffer getDate() {
		return date;
	}
	public void setDate(StringBuffer date) {
		this.date = date;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	public void processFeeds(){

		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			SAXParser parser = factory.newSAXParser();
			XMLReader reader = parser.getXMLReader();
			reader.setContentHandler(this);
			InputStream inputStream = new URL(url).openStream();
			Log.d(TAG,inputStream.toString());
			reader.parse(new InputSource(inputStream));

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Bitmap getBitmap(String url){
		HttpURLConnection connection;
		InputStream input;
		Bitmap bitmap;
		try {
			connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setDoInput(true);
			connection.connect();
			input = connection.getInputStream();
			bitmap = BitmapFactory.decodeStream(input);
			input.close();
			return bitmap;

		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

	}

	public void startElement(String uri, String localName, String qName, Attributes attributes){

		if(localName.startsWith(ITEM)){
			inItem = true;
			feedItem = new ApodFeedItem();
			if(feedItemList == null){
				feedItemList = new ArrayList<ApodFeedItem>();
			}
		}else if(inItem){
			
			if(localName.equals(TITLE)){
				inTitle = true;
			}else{
				inTitle = false; 
			}
			
			if(localName.equals(DESCRIPTION)){ 
				inDescription = true;
			}else{
				inDescription = false ;
			}
			
			if(localName.equals(PUBDATE)){
				inDate = true; 	
			}else{
				inDate = false;
			}
			
			if(localName.equals(IMAGE)){
			    imgUrl = attributes.getValue(IMAGEURL);
			    feedItem.setImageUrl(imgUrl);
				Log.d("Rahool","ImageURI: "+imgUrl);
			}
		}
	}

	public void characters(char ch[],int start , int length){
		String chars = (new String(ch).substring(start, start + length));
		if(inTitle){
			feedItem.setTitle(new String(ch,start,length));
			inTitle = false;
		}
		if(inDescription){
//			feedItem.setDescription(new String(ch,start,length));
			feedItem.setDescription(chars);
			inDescription = false;
		}
		if(inDate){
			feedItem.setDate(new String(ch,start,length));
			inDate = false;
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) {
		if(qName.equals(TITLE)){
			inTitle = false;
		}
		
		if(qName.equals(DESCRIPTION)){
			inDescription = false;
		}
		
		if(qName.equals(PUBDATE)){
			inTitle = false;
		}
		
		if(qName.equals(ITEM)){
			feedItemList.add(feedItem);
			inItem = false;
		}
	}
}
