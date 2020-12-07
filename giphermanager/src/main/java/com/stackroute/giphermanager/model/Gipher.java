package com.stackroute.giphermanager.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * This is Gipher Favourites.
 * 
 * @author L.GANESH
 *
 */
@Document
public class Gipher {

	@Id
	private String id;
	
	private String giphyId;
	private String title;
	private String giphyUrl;
	private String recomendationSystemId;
	private Long bookMarkedBy;
	private String giphyObject;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGiphyId() {
		return giphyId;
	}
	public void setGiphyId(String giphyId) {
		this.giphyId = giphyId;
	}
	public String getRecomendationSystemId() {
		return recomendationSystemId;
	}
	public void setRecomendationSystemId(String recomendationSystemId) {
		this.recomendationSystemId = recomendationSystemId;
	}
	
	public Long getBookMarkedBy() {
		return bookMarkedBy;
	}
	public void setBookMarkedBy(Long bookMarkedBy) {
		this.bookMarkedBy = bookMarkedBy;
	}
	public String getGiphyObject() {
		return giphyObject;
	}
	public void setGiphyObject(String giphyObject) {
		this.giphyObject = giphyObject;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getGiphyUrl() {
		return giphyUrl;
	}
	public void setGiphyUrl(String giphyUrl) {
		this.giphyUrl = giphyUrl;
	}

}
