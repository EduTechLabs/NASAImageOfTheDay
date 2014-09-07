package com.example.parser;

public class ApodFeedItem {

	private String title;
	private String description ;
	private String date;
	private String imageUrl;
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	@Override
	public String toString() {
		return "\nApodFeedItem [\ntitle=" + title + ", \ndescription=" + description
				+ ", \ndate=" + date + ", \nimageUrl=" + imageUrl + "\n]";
	}
	
	
	
}
